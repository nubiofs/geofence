/*
 * $ Header: it.geosolutions.geofence.gui.server.service.impl.InstancesManagerServiceImpl,v. 0.1 28-gen-2011 11.33.07 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 11.33.07 $
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadResult;

import it.geosolutions.geofence.gui.client.ApplicationException;
import it.geosolutions.geofence.gui.client.model.GSInstance;
import it.geosolutions.geofence.gui.client.model.data.rpc.RpcPageLoadResult;
import it.geosolutions.geofence.gui.server.service.IInstancesManagerService;
import it.geosolutions.geofence.gui.service.GeofenceRemoteService;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Class InstancesManagerServiceImpl.
 */
@Component("instancesManagerServiceGWT")
public class InstancesManagerServiceImpl implements IInstancesManagerService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The geofence remote service. */
	@Autowired
	private GeofenceRemoteService geofenceRemoteService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.geosolutions.geofence.gui.server.service.IInstancesManagerService#
	 * getInstances(com.extjs.gxt.ui.client.data.PagingLoadConfig)
	 */
	public PagingLoadResult<GSInstance> getInstances(int offset, int limit,
			boolean full) throws ApplicationException {
		int start = offset;

		List<GSInstance> instancesListDTO = new ArrayList<GSInstance>();

		if (full) {
			GSInstance all = new GSInstance();
			all.setId(-1);
			all.setName("*");
			all.setBaseURL("*");
			instancesListDTO.add(all);
		}

		long instancesCount = geofenceRemoteService.getInstanceAdminService()
				.getCount(null) + 1;

		Long t = new Long(instancesCount);

		int page = (start == 0) ? start : (start / limit);

		List<it.geosolutions.geofence.core.model.GSInstance> instancesList = geofenceRemoteService
				.getInstanceAdminService().getList(null, page, limit);

		if (instancesList == null) {
			if (logger.isErrorEnabled()) {
				logger.error("No server instace found on server");
			}
			throw new ApplicationException("No server instance found on server");
		}

		Iterator<it.geosolutions.geofence.core.model.GSInstance> it = instancesList
				.iterator();

		while (it.hasNext()) {
			it.geosolutions.geofence.core.model.GSInstance remote_instance = it
					.next();
			GSInstance local_instance = new GSInstance();
			local_instance.setId(remote_instance.getId());
			local_instance.setName(remote_instance.getName());
			local_instance.setDescription(remote_instance.getDescription());
			local_instance.setDateCreation(remote_instance.getDateCreation());
			local_instance.setBaseURL(remote_instance.getBaseURL());
			local_instance.setUsername(remote_instance.getUsername());
			local_instance.setPassword(remote_instance.getPassword());
			instancesListDTO.add(local_instance);
		}

		return new RpcPageLoadResult<GSInstance>(instancesListDTO, offset,
				t.intValue());
	}

	/**
	 * 
	 * @param config
	 * @param name
	 * @return
	 */
	public GSInstance getInstance(int offset, int limit, long id) {
		it.geosolutions.geofence.core.model.GSInstance remote_instance = geofenceRemoteService
				.getInstanceAdminService().get(id);
		if (remote_instance == null) {
			if (logger.isErrorEnabled()) {
				logger.error("No server instaces have been found!");
			}
			throw new ApplicationException("No server instance found on server");
		}

		GSInstance local_instance = new GSInstance();
		local_instance.setId(remote_instance.getId());
		local_instance.setName(remote_instance.getName());
		local_instance.setDescription(remote_instance.getDescription());
		local_instance.setDateCreation(remote_instance.getDateCreation());
		local_instance.setBaseURL(remote_instance.getBaseURL());
		local_instance.setUsername(remote_instance.getUsername());
		local_instance.setPassword(remote_instance.getPassword());
		return local_instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.geosolutions.geofence.gui.server.service.IInstancesManagerService#
	 * deleteInstance(it.geosolutions.geofence.gui.client.model.Instance)
	 */
	public void deleteInstance(GSInstance instance) {
		it.geosolutions.geofence.core.model.GSInstance remote_instance = null;
		try {
			remote_instance = geofenceRemoteService.getInstanceAdminService()
					.get(instance.getId());
			geofenceRemoteService.getInstanceAdminService().delete(
					remote_instance.getId());
		} catch (NotFoundServiceEx e) {
			logger.error(e.getLocalizedMessage(), e.getCause());
			throw new ApplicationException(e.getLocalizedMessage(),
					e.getCause());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.geosolutions.geofence.gui.server.service.IInstancesManagerService#
	 * saveInstance(it.geosolutions.geofence.gui.client.model.Instance)
	 */
	public void saveInstance(GSInstance instance) {
		it.geosolutions.geofence.core.model.GSInstance remote_instance = null;
		if (instance.getId() >= 0) {
			try {
				remote_instance = geofenceRemoteService
						.getInstanceAdminService().get(instance.getId());
				remote_instance.setName(instance.getName());
				remote_instance.setDateCreation(instance.getDateCreation());
				remote_instance.setDescription(instance.getDescription());
				remote_instance.setBaseURL(instance.getBaseURL());
				remote_instance.setPassword(instance.getPassword());
				remote_instance.setUsername(instance.getUsername());
				geofenceRemoteService.getInstanceAdminService().update(
						remote_instance);
			} catch (NotFoundServiceEx e) {
				logger.error(e.getLocalizedMessage(), e.getCause());
				throw new ApplicationException(e.getLocalizedMessage(),
						e.getCause());
			}
		} else {
			try {
				remote_instance = new it.geosolutions.geofence.core.model.GSInstance();
				remote_instance.setName(instance.getName());
				remote_instance.setDateCreation(instance.getDateCreation());
				remote_instance.setDescription(instance.getDescription());
				remote_instance.setBaseURL(instance.getBaseURL());
				remote_instance.setPassword(instance.getPassword());
				remote_instance.setUsername(instance.getUsername());
				geofenceRemoteService.getInstanceAdminService().insert(
						remote_instance);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage(), e.getCause());
				throw new ApplicationException(e.getLocalizedMessage(),
						e.getCause());
			}
		}
	}
}
