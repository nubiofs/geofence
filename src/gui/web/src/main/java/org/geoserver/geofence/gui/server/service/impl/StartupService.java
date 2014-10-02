/*
 * $ Header: it.geosolutions.geofence.gui.server.service.impl.StartupService,v. 0.1 14-gen-2011 19.27.51 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.27.51 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.geofence.gui.server.service.impl;

import it.geosolutions.geofence.core.model.GFUser;
import it.geosolutions.geofence.gui.client.configuration.GeofenceGlobalConfiguration;
import it.geosolutions.geofence.gui.server.service.IStartupService;
import it.geosolutions.geofence.login.util.MD5Util;
import it.geosolutions.geofence.services.GFUserAdminServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import org.springframework.beans.factory.annotation.Autowired;


// TODO: Auto-generated Javadoc
/**
 * The Class StartupService.
 */
public class StartupService implements IStartupService, InitializingBean
{
    private static final Logger LOGGER = LogManager.getLogger(StartupService.class);

    /** The geofence global configuration. */
    @Autowired
    private GeofenceGlobalConfiguration geofenceGlobalConfiguration;

    @Autowired
    GFUserAdminServiceImpl gfUserAdminService;

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geofence.gui.server.service.IStartupService#initServerConfiguration()
     */
    public GeofenceGlobalConfiguration initServerConfiguration()
    {
        // TODO Auto-generated method stub
        return geofenceGlobalConfiguration;
    }

    public void afterPropertiesSet() throws Exception {
        long cnt = gfUserAdminService.getCount(null);
        if(cnt == 0) {
            LOGGER.warn("No GF users found. Creating the default admin.");
            
            GFUser user = new GFUser();
            user.setFullName("Default admin");
            user.setName("admin");
            user.setPassword(MD5Util.getHash("geofence"));
            user.setEnabled(Boolean.TRUE);
            gfUserAdminService.insert(user);
        }
    }

    public void setGfUserAdminService(GFUserAdminServiceImpl gfUserAdminService) {
        this.gfUserAdminService = gfUserAdminService;
    }
}
