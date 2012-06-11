/*
 * Copyright (C) 2007 - 2012 GeoSolutions S.A.S. http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geofence.services.rest.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.geosolutions.geofence.core.model.GFUser;
import it.geosolutions.geofence.core.model.GSInstance;
import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.LayerDetails;
import it.geosolutions.geofence.core.model.UserGroup;
import it.geosolutions.geofence.core.model.Rule;
import it.geosolutions.geofence.services.GFUserAdminService;
import it.geosolutions.geofence.services.GetProviderService;
import it.geosolutions.geofence.services.InstanceAdminService;
import it.geosolutions.geofence.services.UserGroupAdminService;
import it.geosolutions.geofence.services.RuleAdminService;
import it.geosolutions.geofence.services.UserAdminService;
import it.geosolutions.geofence.services.dto.ShortGroup;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;
import it.geosolutions.geofence.services.rest.RESTConfigService;
import it.geosolutions.geofence.services.rest.exception.BadRequestRestEx;
import it.geosolutions.geofence.services.rest.exception.InternalErrorRestEx;
import it.geosolutions.geofence.services.rest.exception.NotFoundRestEx;
import it.geosolutions.geofence.services.rest.model.config.RESTConfigurationRemapping;
import it.geosolutions.geofence.services.rest.model.config.RESTFullConfiguration;
import it.geosolutions.geofence.services.rest.model.config.RESTFullGRUserList;
import it.geosolutions.geofence.services.rest.model.config.RESTFullGSInstanceList;
import it.geosolutions.geofence.services.rest.model.config.RESTFullUserGroupList;
import it.geosolutions.geofence.services.rest.model.config.RESTFullRuleList;
import it.geosolutions.geofence.services.rest.model.config.RESTFullUserList;
import it.geosolutions.geofence.services.rest.utils.InstanceCleaner;

import org.apache.log4j.Logger;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RESTConfigServiceImpl implements RESTConfigService
{
    private static final Logger LOGGER = Logger.getLogger(RESTConfigServiceImpl.class.toString());

    private UserAdminService userAdminService;
    private UserGroupAdminService userGroupAdminService;
    private RuleAdminService ruleAdminService;
    private InstanceAdminService instanceAdminService;
    private GFUserAdminService grUserAdminService;

    private InstanceCleaner instanceCleaner;

    public RESTFullConfiguration getConfiguration()
    {
        return getConfiguration(false);
    }

    @Override
    public RESTFullConfiguration getConfiguration(Boolean includeGRUsers)
    {
        RESTFullConfiguration cfg = new RESTFullConfiguration();

        RESTFullUserList users = new RESTFullUserList();
        users.setList(userAdminService.getFullList(null, null, null));
        cfg.setUserList(users);

        RESTFullUserGroupList profiles = new RESTFullUserGroupList();
        profiles.setList(userGroupAdminService.getFullList(null, null, null));
        cfg.setUserGroupList(profiles);

        RESTFullGSInstanceList instances = new RESTFullGSInstanceList();
        instances.setList(instanceAdminService.getList(null, null, null));
        cfg.setGsInstanceList(instances);

        RESTFullRuleList rules = new RESTFullRuleList();
        rules.setList(ruleAdminService.getListFull(null, null, null));
        cfg.setRuleList(rules);

        if (includeGRUsers.booleanValue())
        {
            RESTFullGRUserList grUsers = new RESTFullGRUserList();
            grUsers.setList(grUserAdminService.getFullList(null, null, null));
            cfg.setGrUserList(grUsers);
        }

        return cfg;
    }

    public synchronized RESTConfigurationRemapping setConfiguration(RESTFullConfiguration config)
    {
        return setConfiguration(config, false);
    }

    @Override
    public synchronized RESTConfigurationRemapping setConfiguration(RESTFullConfiguration config,
        Boolean includeGRUsers)
    {
        LOGGER.warn("SETTING CONFIGURATION");

        if (includeGRUsers)
        {
            if ((config.getGrUserList() == null) || (config.getGrUserList().getList() == null) || config.getGrUserList().getList().isEmpty())
            {
                throw new BadRequestRestEx("Can't restore internal users: no internal user defined");
            }
        }

        instanceCleaner.removeAll();

        RESTConfigurationRemapping remap = new RESTConfigurationRemapping();

        RemapperCache<UserGroup, UserGroupAdminService> groupCache = new RemapperCache<UserGroup, UserGroupAdminService>(userGroupAdminService, remap.getUserGroups());
        RemapperCache<GSUser, UserAdminService> userCache = new RemapperCache<GSUser, UserAdminService>(userAdminService, remap.getUsers());
        RemapperCache<GSInstance, InstanceAdminService> instanceCache =
            new RemapperCache<GSInstance, InstanceAdminService>(instanceAdminService, remap.getInstances());


        try
        {
            // === UserGroups
            for (UserGroup group : config.getUserGroupList().getList())
            {
                Long oldId = group.getId();
                ShortGroup sp = new ShortGroup(group);
                long newId = userGroupAdminService.insert(sp);
                LOGGER.info("Remapping userGroup " + oldId + " -> " + newId);
                remap.getUserGroups().put(oldId, newId);
            }

            // === Users
            for (GSUser user : config.getUserList().getList())
            {
                for (UserGroup userGroup : user.getGroups()) {
                    Long oldGroupId = userGroup.getId();
                    UserGroup group = groupCache.get(oldGroupId);
                    user.getGroups().add(group);
                }

                Long oldId = user.getId();
                user.setId(null);

                long newId = userAdminService.insert(user);
                LOGGER.info("Remapping user " + oldId + " -> " + newId);
                remap.getUsers().put(oldId, newId);
            }


            // === GSInstances
            for (GSInstance instance : config.getGsInstanceList().getList())
            {
                Long oldId = instance.getId();
                instance.setId(null);

                long newId = instanceAdminService.insert(instance);
                LOGGER.info("Remapping gsInstance " + oldId + " -> " + newId);
                remap.getInstances().put(oldId, newId);
            }

            // === Rules
            for (Rule rule : config.getRuleList().getList())
            {
                Long oldId = rule.getId();
                rule.setId(null);

                if (rule.getUserGroup() != null)
                {
                    rule.setUserGroup(groupCache.get(rule.getUserGroup().getId()));
                }

                if (rule.getGsuser() != null)
                {
                    rule.setGsuser(userCache.get(rule.getGsuser().getId()));
                }

                if (rule.getInstance() != null)
                {
                    rule.setInstance(instanceCache.get(rule.getInstance().getId()));
                }

                // the prob here is that layerdetails is a reverse reference, so only hibernate should be setting it.
                // using JAXB, it's injected, but we have to make hibernate eat it.
                LayerDetails ld = rule.getLayerDetails();
                rule.setLayerDetails(null);

                long newId = ruleAdminService.insert(rule);
                LOGGER.info("Remapping rule " + oldId + " -> " + newId);
                remap.getRules().put(oldId, newId);

                if (ld != null)
                {
                    ruleAdminService.setDetails(newId, ld);
                }
            }
        }
        catch (RemapperException e)
        {
            LOGGER.error("Exception in remapping: Configuration will be erased");
            instanceCleaner.removeAll();
            throw new BadRequestRestEx(e.getMessage());

        }
        catch (NotFoundRestEx e)
        {
            LOGGER.error("Internal exception in remapping: Configuration will be erased");
            instanceCleaner.removeAll();
            throw e;
        }

        // === Internal users
        if (includeGRUsers)
        {
            instanceCleaner.removeAllGRUsers();

            for (GFUser grUser : config.getGrUserList().getList())
            {
                Long oldId = grUser.getId();
                grUser.setId(null);

                long newId = grUserAdminService.insert(grUser);
                LOGGER.info("Remapping internal user " + oldId + " -> " + newId);
                remap.remap(oldId, grUser);
            }

        }

        return remap;
    }


    @Override
    public RESTFullUserList getUsers() throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx
    {
        List<GSUser> users = userAdminService.getFullList(null, null, null);

        RESTFullUserList ret = new RESTFullUserList();
        ret.setList(users);

        return ret;
    }

    // not @Override: not available as a standalone service
    public RESTFullGRUserList getGRUsers() throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx
    {
        List<GFUser> users = grUserAdminService.getFullList(null, null, null);

        RESTFullGRUserList ret = new RESTFullGRUserList();
        ret.setList(users);

        return ret;
    }

    @Override
    public RESTFullUserGroupList getUserGroups() throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx
    {
        List<UserGroup> groups = userGroupAdminService.getFullList(null, null, null);

        RESTFullUserGroupList ret = new RESTFullUserGroupList();
        ret.setList(groups);

        return ret;

    }

    // ==========================================================================


    // ==========================================================================

    public void setInstanceCleaner(InstanceCleaner instanceCleaner)
    {
        this.instanceCleaner = instanceCleaner;
    }

    // ==========================================================================

    public void setUserGroupAdminService(UserGroupAdminService service) {
        this.userGroupAdminService = service;
    }

    public void setUserAdminService(UserAdminService service)
    {
        this.userAdminService = service;
    }

    public void setInstanceAdminService(InstanceAdminService service)
    {
        this.instanceAdminService = service;
    }

    public void setRuleAdminService(RuleAdminService service)
    {
        this.ruleAdminService = service;
    }

    public void setGrUserAdminService(GFUserAdminService service)
    {
        this.grUserAdminService = service;
    }

    // ==========================================================================


    class RemapperCache<TYPE, SERVICE extends GetProviderService<TYPE>>
    {
        private Map<Long, TYPE> cache = new HashMap<Long, TYPE>();
        private final Map<Long, Long> idRemapper;
        private final SERVICE service;

        public RemapperCache(SERVICE service, Map<Long, Long> idRemapper)
        {
            this.idRemapper = idRemapper;
            this.service = service;
        }


        TYPE get(Long oldId) throws RemapperException, NotFoundRestEx
        {
            Long newId = idRemapper.get(oldId);
            if (newId == null)
            {
                LOGGER.error("Can't remap " + oldId);
                throw new RemapperException("Can't remap " + oldId);
            }

            TYPE cached = cache.get(newId);
            try
            {
                if (cached == null)
                {
                    cached = service.get(newId.longValue()); // may throw NotFoundServiceEx
                    cache.put(newId, cached);
                }

                return cached;
            }
            catch (NotFoundServiceEx ex)
            {
                LOGGER.error(ex.getMessage(), ex);
                throw new NotFoundRestEx(ex.getMessage());
            }
        }
    }

    class RemapperException extends Exception
    {

        public RemapperException(Throwable cause)
        {
            super(cause);
        }

        public RemapperException(String message, Throwable cause)
        {
            super(message, cause);
        }

        public RemapperException(String message)
        {
            super(message);
        }

        public RemapperException()
        {
        }
    }

}
