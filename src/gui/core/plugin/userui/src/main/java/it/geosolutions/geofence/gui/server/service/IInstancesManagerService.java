/*
 * $ Header: it.geosolutions.geofence.gui.server.service.IInstancesManagerService,v. 0.1 28-gen-2011 11.33.16 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 11.33.16 $
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
package it.geosolutions.geofence.gui.server.service;

import com.extjs.gxt.ui.client.data.PagingLoadResult;

import it.geosolutions.geofence.gui.client.ApplicationException;
import it.geosolutions.geofence.gui.client.model.GSInstance;


/**
 * The Interface IInstancesManagerService.
 */
public interface IInstancesManagerService
{

    /**
     * Gets the instances.
     *
     * @param config
     *            the config
     * @return the instances
     * @throws ApplicationException
     *             the application exception
     */
    public PagingLoadResult<GSInstance> getInstances(int offset, int limit, boolean full) throws ApplicationException;

    /**
     * Get the instance
     *
     * @param config
     * @param l
     * @return
     */
    public GSInstance getInstance(int offset, int limit, long l) throws ApplicationException;

    /**
     * Delete instance.
     *
     * @param instance
     *            the instance
     */
    public void deleteInstance(GSInstance instance);

    /**
     * Save instance.
     *
     * @param instance
     *            the instance
     */
    public void saveInstance(GSInstance instance);

}
