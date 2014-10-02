/*
 * $ Header: it.geosolutions.geofence.gui.client.configuration.WorkspaceConfigOpts,v. 0.1 07-gen-2012 16.27.48 created by Tobia Di Pisa <tobia.dipisa at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 07-gen-2012 16.27.48 $
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
package it.geosolutions.geofence.gui.client.configuration;

/**
 * Class WorkspaceConfigOpts.
 *
 * @author Tobia Di Pisa
 *
 */
public class WorkspaceConfigOpts
{

    private boolean showDefaultGroups = false;

    /**
     *
     */
    public WorkspaceConfigOpts()
    {
        super();
    }

    /**
     * @param showDefaultGroups
     */
    public WorkspaceConfigOpts(boolean showDefaultGroups)
    {
        super();
        this.showDefaultGroups = showDefaultGroups;
    }

    /**
     * @return the showDefaultGroups
     */
    public boolean isShowDefaultGroups()
    {
        return showDefaultGroups;
    }

    /**
     * @param showDefaultGroups
     *            the showDefaultGroups to set
     */
    public void setShowDefaultGroups(boolean showDefaultGroups)
    {
        this.showDefaultGroups = showDefaultGroups;
    }
}
