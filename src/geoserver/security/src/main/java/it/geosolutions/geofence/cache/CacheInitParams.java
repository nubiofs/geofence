/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2011, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.geofence.cache;

import it.geosolutions.geofence.GeofenceAccessManagerConfiguration;

import java.io.Serializable;

import com.google.common.base.Ticker;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class CacheInitParams implements Serializable, Cloneable {
    long size = 100;
    long refreshMilliSec = 15000;
    long expireMilliSec  = 30000;
    volatile Ticker customTicker = null; // testing only
    
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
    
    /**
     * Creates a copy of the configuration object.
     */
    public CacheInitParams clone() {
        CacheInitParams copy = new CacheInitParams();
        copy.setSize(getSize());
        copy.setRefreshMilliSec(getRefreshMilliSec());
        copy.setExpireMilliSec(getExpireMilliSec());
        return copy;
    }
}
