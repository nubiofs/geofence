/*
 * $ Header: it.geosolutions.geofence.gui.server.gwt.ConfigurationRemoteImpl,v. 0.1 14-gen-2011 19.27.45 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.27.45 $
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
package it.geosolutions.geofence.gui.server.gwt;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import it.geosolutions.geofence.gui.client.configuration.GeofenceGlobalConfiguration;
import it.geosolutions.geofence.gui.client.service.ConfigurationRemote;
import it.geosolutions.geofence.gui.server.service.IStartupService;
import it.geosolutions.geofence.gui.spring.ApplicationContextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


// TODO: Auto-generated Javadoc
/**
 * The Class ConfigurationRemoteImpl.
 */
public class ConfigurationRemoteImpl extends RemoteServiceServlet implements ConfigurationRemote
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6320939080552026131L;

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The startup service. */
    private IStartupService startupService;

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);

        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        ApplicationContextUtil.getInstance().setSpringContext(context);

        this.startupService = (IStartupService) ApplicationContextUtil.getInstance().getBean(
                "startupService");

        logger.info("SPRING CONTEXT INITIALIZED" + this.startupService);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geofence.gui.client.service.ConfigurationRemote#
     * initServerConfiguration()
     */
    public GeofenceGlobalConfiguration initServerConfiguration()
    {
        // TODO Auto-generated method stub
        return startupService.initServerConfiguration();
    }

}
