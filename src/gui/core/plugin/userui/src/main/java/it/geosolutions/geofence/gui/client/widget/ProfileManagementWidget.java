/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.ProfileManagementWidget,v. 0.1 25-feb-2011 16.31.41 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
import it.geosolutions.geofence.gui.client.service.ProfilesManagerRemoteServiceAsync;


// TODO: Auto-generated Javadoc
/**
 * The Class ProfileManagementWidget.
 */
public class ProfileManagementWidget extends ContentPanel
{

    /** The profiles info. */
    private ProfileGridWidget profilesInfo;

    /**
     * Instantiates a new profile management widget.
     *
     * @param profilesManagerServiceRemote
     *            the profiles manager service remote
     */
    public ProfileManagementWidget(ProfilesManagerRemoteServiceAsync profilesManagerServiceRemote)
    {
        setMonitorWindowResize(true);
        setHeaderVisible(false);
        setFrame(true);
        setLayout(new FitLayout());
        setScrollMode(Scroll.NONE);
        setAutoWidth(true);
        setHeight(Constants.SOUTH_PANEL_DIMENSION - 25);

        setProfilesInfo(new ProfileGridWidget(profilesManagerServiceRemote));
        add(getProfilesInfo().getGrid());
        setBottomComponent(this.getProfilesInfo().getToolBar());
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
     * Sets the profiles info.
     *
     * @param profilesInfo
     *            the new profiles info
     */
    public void setProfilesInfo(ProfileGridWidget profilesInfo)
    {
        this.profilesInfo = profilesInfo;
    }

    /**
     * Gets the profiles info.
     *
     * @return the profiles info
     */
    public ProfileGridWidget getProfilesInfo()
    {
        return profilesInfo;
    }

}
