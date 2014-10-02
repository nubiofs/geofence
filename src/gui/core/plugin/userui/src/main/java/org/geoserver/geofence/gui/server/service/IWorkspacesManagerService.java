/*
 * $ Header: it.geosolutions.geofence.gui.server.service.IWorkspacesManagerService,v. 0.1 28-gen-2011 18.30.05 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 18.30.05 $
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

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadResult;

import it.geosolutions.geofence.gui.client.ApplicationException;
import it.geosolutions.geofence.gui.client.model.GSInstance;
import it.geosolutions.geofence.gui.client.model.Rule;
import it.geosolutions.geofence.gui.client.model.data.Layer;
import it.geosolutions.geofence.gui.client.model.data.LayerStyle;
import it.geosolutions.geofence.gui.client.model.data.Workspace;


// TODO: Auto-generated Javadoc
/**
 * The Interface IWorkspacesManagerService.
 */
public interface IWorkspacesManagerService
{

    /**
     * Gets the workspaces.
     *
     * @param config
     *            the config
     * @param URL
     *            the uRL
     * @return the workspaces
     * @throws ApplicationException
     *             the application exception
     */
    public PagingLoadResult<Workspace> getWorkspaces(int offset, int limit, String URL,
        GSInstance gsInstance) throws ApplicationException;

    /**
     * Gets the layers.
     *
     * @param config
     *            the config
     * @param baseURL
     *            the base url
     * @param workspace
     *            the workspace
     * @return the layers
     * @throws ApplicationException
     *             the application exception
     */
    public PagingLoadResult<Layer> getLayers(int offset, int limit, String baseURL,
        GSInstance gsInstance, String workspace, String service) throws ApplicationException;

    /**
     * @param gsInstance
     * @return List<LayerStyle>
     * @throws ApplicationException
     */
    public List<LayerStyle> getStyles(Rule rule) throws ApplicationException;
}
