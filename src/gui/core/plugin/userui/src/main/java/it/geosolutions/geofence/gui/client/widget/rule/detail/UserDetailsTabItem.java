/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.rule.detail.UserDetailsTabItem,v. 0.1 5-apr-2011 16.30.38 created by tdipisa <tobia.dipisa at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 5-apr-2011 16.30.38 $
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
package it.geosolutions.geofence.gui.client.widget.rule.detail;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.TabItem;

import it.geosolutions.geofence.gui.client.GeofenceEvents;
import it.geosolutions.geofence.gui.client.Resources;
import it.geosolutions.geofence.gui.client.model.GSUser;
import it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteServiceAsync;


/**
 * The Class UserDetailsTabItem.
 */
public class UserDetailsTabItem extends TabItem
{

    /** The user details widget. */
    private UserDetailsWidget userDetailsWidget;

    /** The user. */
    private GSUser user;

    /**
     * Instantiates a new user tab item.
     *
     * @param tabItemId
     *            the tab item id
     */
    private UserDetailsTabItem(String tabItemId)
    {
        // TODO: add I18n message
        // super(I18nProvider.getMessages().profiles());
        super("User Details");
        setId(tabItemId);
        setIcon(Resources.ICONS.addAOI());
    }

    /**
     * Instantiates a new rule details tab item.
     *
     * @param tabItemId
     *            the tab item id
     * @param model
     *            the model
     * @param workspacesService
     *            the workspaces service
     */
    public UserDetailsTabItem(String tabItemId, GSUser model, GsUsersManagerRemoteServiceAsync usersService)
    {
        this(tabItemId);
        this.user = model;

        setUserDetailsWidget(new UserDetailsWidget(this.user, usersService));
        add(getUserDetailsWidget());

        setScrollMode(Scroll.NONE);

        this.addListener(Events.Select, new Listener<BaseEvent>()
            {

                public void handleEvent(BaseEvent be)
                {
                    if (userDetailsWidget.getUserDetailsInfo().getModel() == null)
                    {
                        Dispatcher.forwardEvent(GeofenceEvents.LOAD_USER_LIMITS, user);
                    }
                }

            });

    }

    /**
     * Sets the user details widget.
     *
     * @param userDetailsWidget
     *            the new user details widget
     */
    public void setUserDetailsWidget(UserDetailsWidget userDetailsWidget)
    {
        this.userDetailsWidget = userDetailsWidget;
    }

    /**
     * Gets the user limits widget.
     *
     * @return the user limits widget
     */
    public UserDetailsWidget getUserDetailsWidget()
    {
        return userDetailsWidget;
    }

}
