/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.tab.GsUsersTabItem,v. 0.1 25-gen-2011 11.23.48 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.23.48 $
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
package it.geosolutions.geofence.gui.client.widget.tab;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.TabItem;

import it.geosolutions.geofence.gui.client.Constants;
import it.geosolutions.geofence.gui.client.Resources;
import it.geosolutions.geofence.gui.client.i18n.I18nProvider;
import it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.service.ProfilesManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.widget.UserManagementWidget;


// TODO: Auto-generated Javadoc
/**
 * The Class GsUsersTabItem.
 */
public class GsUsersTabItem extends TabItem
{

    /** The profile management widget. */
    private UserManagementWidget userManagementWidget;

    /**
     * Instantiates a new gs users tab item.
     */
    public GsUsersTabItem(String tabItemId)
    {
        super(I18nProvider.getMessages().userManagementLabel());
        setId(tabItemId);
        setIcon(Resources.ICONS.user());
    }

    /**
     * Instantiates a new gs users tab item.
     * @param usersTabItemId
     *
     * @param gsManagerServiceRemote
     *            the gs manager service remote
     * @param profilesManagerServiceRemote
     */
    public GsUsersTabItem(String tabItemId, GsUsersManagerRemoteServiceAsync gsManagerServiceRemote,
        ProfilesManagerRemoteServiceAsync profilesManagerServiceRemote)
    {
        this(tabItemId);
        setScrollMode(Scroll.NONE);
        setAutoWidth(true);
        setHeight(Constants.SOUTH_PANEL_DIMENSION - 25);

        setUserManagementWidget(new UserManagementWidget(gsManagerServiceRemote,
                profilesManagerServiceRemote));
        add(getUserManagementWidget());

        getUserManagementWidget().getUsersInfo().getLoader().load(0, it.geosolutions.geofence.gui.client.Constants.DEFAULT_PAGESIZE);
    }

    /**
     * Sets the feature management widget.
     *
     * @param userManagementWidget
     *            the new feature management widget
     */
    public void setUserManagementWidget(UserManagementWidget userManagementWidget)
    {
        this.userManagementWidget = userManagementWidget;
    }

    /**
     * Gets the feature management widget.
     *
     * @return the feature management widget
     */
    public UserManagementWidget getUserManagementWidget()
    {
        return userManagementWidget;
    }

}
