/*
 *  Copyright (C) 2007 - 2012 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 * 
 *  GPLv3 + Classpath exception
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geofence.cache;

import com.google.common.base.Ticker;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import it.geosolutions.geofence.services.RuleReaderService;
import it.geosolutions.geofence.services.dto.AccessInfo;
import it.geosolutions.geofence.services.dto.RuleFilter;
import it.geosolutions.geofence.services.dto.ShortRule;

import it.geosolutions.geofence.GeofenceAccessManager;
import java.util.concurrent.ExecutionException;
import org.geotools.util.logging.Logging;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A delegating {@link it.geosolutions.georepo.services.RuleReaderService} with caching capabilities.
 * <P/>
 * Cache eviction policy is LRU.<br/>
 * Cache coherence is handled by entry timeout.<br/>
 * <p/>
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class CachedRuleReader implements RuleReaderService {

    static final Logger LOGGER = Logging.getLogger(CachedRuleReader.class);

    private RuleReaderService realRuleReaderService;

    private LoadingCache<RuleFilter, AccessInfo> ruleCache;
    private LoadingCache<String, Boolean>        adminCache;

    private final CacheInitParams cacheInitParams = new CacheInitParams();

    public CachedRuleReader() {
        LOGGER.setLevel(Level.ALL);
    }

    /**
     * Init the cache, using the provided init params.
     * Please use {@link #getCacheInitParams() } to set the cache parameters before
     * <code>init()</code>ting the cache
     */
    public void init() {
        ruleCache  = getCacheBuilder().build(new RuleLoader());
        adminCache = getCacheBuilder().build(new UserLoader());
    }

    protected CacheBuilder getCacheBuilder() {
        CacheBuilder builder = CacheBuilder.newBuilder()
                .maximumSize(cacheInitParams.getSize())
                .refreshAfterWrite(cacheInitParams.getRefreshMilliSec(), TimeUnit.MILLISECONDS) // reloadable after x time
                .expireAfterWrite(cacheInitParams.getExpireMilliSec(), TimeUnit.MILLISECONDS) // throw away entries too old
                ;
        //.expireAfterAccess(timeoutMillis, TimeUnit.MILLISECONDS)
        //                .removalListener(MY_LISTENER)
        // this should only be used while testing
        if(cacheInitParams.getCustomTicker() != null) {
            LOGGER.log(Level.SEVERE, "Setting a custom Ticker in the cache {0}", cacheInitParams.getCustomTicker().getClass().getName());
            builder.ticker(cacheInitParams.getCustomTicker());
        }
        return builder;
    }



    private class RuleLoader extends CacheLoader<RuleFilter, AccessInfo> {

        @Override
        public AccessInfo load(RuleFilter filter) throws Exception {
            if(LOGGER.isLoggable(Level.FINE))
                LOGGER.log(Level.FINE, "Loading {0}", filter);
            return realRuleReaderService.getAccessInfo(filter);
        }

        @Override
        public ListenableFuture<AccessInfo> reload(final RuleFilter filter, AccessInfo accessInfo) throws Exception {
            if(LOGGER.isLoggable(Level.FINE))
                LOGGER.log(Level.FINE, "Reloading {0}", filter);

            // this is a sync implementation
            AccessInfo ret = realRuleReaderService.getAccessInfo(filter);
            return Futures.immediateFuture(ret);

            // next there is an asynchronous implementation, but in tests it seems to hang
//            return ListenableFutureTask.create(new Callable<AccessInfo>() {
//                @Override
//                public AccessInfo call() throws Exception {
//                    if(LOGGER.isLoggable(Level.FINE))
//                        LOGGER.log(Level.FINE, "Asynch reloading {0}", filter);
//                    return realRuleReaderService.getAccessInfo(filter);
//                }
//            });
        }
    }

    private class UserLoader extends CacheLoader<String, Boolean> {

        @Override
        public Boolean load(String userName) throws Exception {
            if(LOGGER.isLoggable(Level.FINE))
                LOGGER.log(Level.FINE, "Loading user '"+userName+"'");
            return realRuleReaderService.isAdmin(userName);
        }

        @Override
        public ListenableFuture<Boolean> reload(final String userName, Boolean isAdmin) throws Exception {
            if(LOGGER.isLoggable(Level.FINE))
                LOGGER.log(Level.FINE, "Reloading user '"+userName+"'");

            // this is a sync implementation
            Boolean ret = realRuleReaderService.isAdmin(userName);
            return Futures.immediateFuture(ret);

            // todo: we may want a asynchronous implementation
        }
    }

    public void invalidateAll() {
        if(LOGGER.isLoggable(Level.WARNING))
            LOGGER.log(Level.WARNING, "Forcing cache invalidation");
        ruleCache.invalidateAll();
        adminCache.invalidateAll();
    }

    /**
     * <B>Deprecated method are not cached.</B>
     *
     * @deprecated Use {@link #getAccessInfo(RuleFilter filter) }
     */
    @Override
    public AccessInfo getAccessInfo(String userName, String profileName, String instanceName, String service, String request, String workspace, String layer) {
        LOGGER.severe("DEPRECATED METHODS ARE NOT CACHED");
        return realRuleReaderService.getAccessInfo(userName, profileName, instanceName, service, request, workspace, layer);
    }

    private AtomicLong dumpCnt = new AtomicLong(0);

    @Override
    public AccessInfo getAccessInfo(RuleFilter filter) {
        if(LOGGER.isLoggable(Level.FINE))
            LOGGER.log(Level.FINE, "Request for {0}", filter);

        if(LOGGER.isLoggable(Level.INFO))
            if(dumpCnt.incrementAndGet() % 10 == 0) {
                LOGGER.info("Rules   :"+ruleCache.stats());
                LOGGER.info("isAdmin :"+adminCache.stats());
                LOGGER.fine("params  :"+cacheInitParams);
            }

        try {
            return ruleCache.get(filter);
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex); // fixme: handle me
        }
    }

    /**
     * <B>Deprecated method are not cached.</B>
     *
     * @deprecated Use {@link #getMatchingRules(RuleFilter filter) }
     */
    @Override
    public List<ShortRule> getMatchingRules(String userName, String profileName, String instanceName, String service, String request, String workspace, String layer) {
        LOGGER.severe("DEPRECATED METHODS ARE NOT CACHED");
        return realRuleReaderService.getMatchingRules(userName, profileName, instanceName, service, request, workspace, layer);
    }

    @Override
    public List<ShortRule> getMatchingRules(RuleFilter filter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     */
    @Override
    public boolean isAdmin(String userName) {
        try {
            return adminCache.get(userName);
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex); // fixme: handle me
        }
//        LOGGER.log(Level.WARNING, "Call to isAdmin({0}) is not cached", userName);
//        return realRuleReaderService.isAdmin(userName);
    }

    //--------------------------------------------------------------------------
    public void setRealRuleReaderService(RuleReaderService realRuleReaderService) {
        this.realRuleReaderService = realRuleReaderService;
    }


    public CacheInitParams getCacheInitParams() {
        return cacheInitParams;
    }

    public CacheStats getStats() {
        return ruleCache.stats();
    }

    public CacheStats getUserStats() {
        return adminCache.stats();
    }

    public long getCacheSize() {
        return ruleCache.size();
    }

    public long getUserCacheSize() {
        return adminCache.size();
    }

    /**
     * May be useful if an external peer doesn't want to use the guava dep.
     */
    public String getStatsString() {
        return ruleCache.stats().toString();
    }

    public class CacheInitParams {
        long size = 100;
        long refreshMilliSec = 15000;
        long expireMilliSec  = 30000;
        Ticker customTicker = null; // testing only

        public long getExpireMilliSec() {
            return expireMilliSec;
        }

        public void setExpireMilliSec(long expireMilliSec) {
            this.expireMilliSec = expireMilliSec;
        }

        public long getRefreshMilliSec() {
            return refreshMilliSec;
        }

        public void setRefreshMilliSec(long refreshMilliSec) {
            this.refreshMilliSec = refreshMilliSec;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public Ticker getCustomTicker() {
            return customTicker;
        }

        public void setCustomTicker(Ticker customTicker) {
            this.customTicker = customTicker;
        }

        @Override
        public String toString() {
            return "Init[size=" + size + " refrMsec=" + refreshMilliSec + ", expMsec=" + expireMilliSec + ']';
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                +"["
                + "Rule:"+ruleCache.stats()
                + " isAdmin:"+adminCache.stats()
                + " " + cacheInitParams
                + "]";
    }

}
