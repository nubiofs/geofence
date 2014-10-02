/*
 * $ Header: it.geosolutions.geofence.gui.server.gwt.InstancesManagerServiceImpl,v. 0.1 28-gen-2011 11.36.40 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 11.36.40 $
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

import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import it.geosolutions.geofence.gui.client.ApplicationException;
import it.geosolutions.geofence.gui.client.model.GSInstance;
import it.geosolutions.geofence.gui.client.service.InstancesManagerRemoteService;
import it.geosolutions.geofence.gui.server.service.IInstancesManagerService;
import it.geosolutions.geofence.gui.spring.ApplicationContextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class InstancesManagerServiceImpl.
 */
public class InstancesManagerRemoteServiceImpl extends RemoteServiceServlet implements InstancesManagerRemoteService
{


    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4502086167905144601L;

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The instances manager service. */
    private IInstancesManagerService instancesManagerService;

    /**
     * Instantiates a new instances manager service impl.
     */
    public InstancesManagerRemoteServiceImpl()
    {
        this.instancesManagerService = (IInstancesManagerService) ApplicationContextUtil.getInstance().getBean("instancesManagerServiceGWT");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * it.geosolutions.geofence.gui.client.service.InstancesManagerRemoteService#getInstances(com.extjs
     * .gxt.ui.client.data.PagingLoadConfig)
     */
    public PagingLoadResult<GSInstance> getInstances(int offset, int limit, boolean full) throws ApplicationException
    {
        return instancesManagerService.getInstances(offset, limit, full);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * it.geosolutions.geofence.gui.client.service.InstancesManagerRemoteService#getInstances(com.extjs
     * .gxt.ui.client.data.PagingLoadConfig)
     */
    public GSInstance getInstance(int offset, int limit, long id) throws ApplicationException
    {
        return instancesManagerService.getInstance(offset, limit, id);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.client.service.InstancesManagerRemoteService#deleteInstance(it.geosolutions.geofence.gui.client.model.Instance)
     */
    public void deleteInstance(GSInstance instance) throws ApplicationException
    {
        instancesManagerService.deleteInstance(instance);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.client.service.InstancesManagerRemoteService#saveInstance(it.geosolutions.geofence.gui.client.model.Instance)
     */
    public void saveInstance(GSInstance instance) throws ApplicationException
    {
        instancesManagerService.saveInstance(instance);
    }

	public void testConnection(it.geosolutions.geofence.gui.client.model.GSInstance instance)  throws ApplicationException {
		instancesManagerService.testConnection(instance);
		
	}

}
