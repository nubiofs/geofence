/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.UserManagementWidget,v. 0.1 25-feb-2011 16.31.40 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-feb-2011 16.31.40 $
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
import it.geosolutions.geofence.gui.client.service.ProfilesManagerRemoteServiceAsync;


// TODO: Auto-generated Javadoc
/**
 * The Class UserManagementWidget.
 */
public class UserManagementWidget extends ContentPanel
{

    /** The users info. */
    private UserGridWidget usersInfo;

    /**
     * Instantiates a new user management widget.
     *
     * @param gsManagerServiceRemote
     *            the gs manager service remote
     * @param profilesManagerServiceRemote
     *            the profiles manager service remote
     */
    public UserManagementWidget(GsUsersManagerRemoteServiceAsync gsManagerServiceRemote,
        ProfilesManagerRemoteServiceAsync profilesManagerServiceRemote)
    {
        setMonitorWindowResize(true);
        setHeaderVisible(false);
        setFrame(true);
        setLayout(new FitLayout());
        setScrollMode(Scroll.NONE);
        setAutoWidth(true);
        setHeight(Constants.SOUTH_PANEL_DIMENSION - 25);

        setUsersInfo(new UserGridWidget(gsManagerServiceRemote, profilesManagerServiceRemote));
        setBottomComponent(this.getUsersInfo().getToolBar());
        add(getUsersInfo().getGrid());
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
     * Sets the users info.
     *
     * @param usersInfo
     *            the new users info
     */
    public void setUsersInfo(UserGridWidget usersInfo)
    {
        this.usersInfo = usersInfo;
    }

    /**
     * Gets the users info.
     *
     * @return the users info
     */
    public UserGridWidget getUsersInfo()
    {
        return usersInfo;
    }

}
