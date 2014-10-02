/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.RuleManagementWidget,v. 0.1 25-feb-2011 16.31.41 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-feb-2011 16.31.41 $
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
package it.geosolutions.geofence.gui.client.widget;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import it.geosolutions.geofence.gui.client.Constants;
import it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.service.InstancesManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.service.ProfilesManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.service.RulesManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.service.WorkspacesManagerRemoteServiceAsync;


// TODO: Auto-generated Javadoc
/**
 * The Class RuleManagementWidget.
 */
public class RuleManagementWidget extends ContentPanel
{

    /** The rules info. */
    private RuleGridWidget rulesInfo;

    /**
     * Instantiates a new rule management widget.
     *
     * @param rulesService
     *            the rules service
     * @param gsUsersService
     *            the gs users service
     * @param profilesService
     *            the profiles service
     * @param instancesService
     *            the instances service
     * @param workspacesService
     *            the workspaces service
     */
    public RuleManagementWidget(RulesManagerRemoteServiceAsync rulesService,
        GsUsersManagerRemoteServiceAsync gsUsersService,
        ProfilesManagerRemoteServiceAsync profilesService,
        InstancesManagerRemoteServiceAsync instancesService,
        WorkspacesManagerRemoteServiceAsync workspacesService)
    {
        setMonitorWindowResize(true);
        setHeaderVisible(false);
        setFrame(true);
        setLayout(new FitLayout());
        setScrollMode(Scroll.NONE);
        setAutoWidth(true);
        setHeight(Constants.SOUTH_PANEL_DIMENSION - 25);

        setRulesInfo(new RuleGridWidget(rulesService, gsUsersService, profilesService, instancesService,
                workspacesService));
        add(getRulesInfo().getGrid());
        setBottomComponent(this.getRulesInfo().getToolBar());
    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.widget.Component#onWindowResize(int, int)
     */
    @Override
    protected void onWindowResize(int width, int height)
    {
        // TODO Auto-generated method stub
        super.setWidth(width - 5);
        super.layout();
    }

    /**
     * Sets the rules info.
     *
     * @param rulesInfo
     *            the new rules info
     */
    public void setRulesInfo(RuleGridWidget rulesInfo)
    {
        this.rulesInfo = rulesInfo;
    }

    /**
     * Gets the rules info.
     *
     * @return the rules info
     */
    public RuleGridWidget getRulesInfo()
    {
        return rulesInfo;
    }

}
