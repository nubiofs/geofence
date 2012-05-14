/*
 * $ Header: it.geosolutions.geofence.gui.client.view.UsersView,v. 0.1 10-feb-2011 11.50.15 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 11.50.15 $
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
package it.geosolutions.geofence.gui.client.view;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;

import it.geosolutions.geofence.gui.client.GeofenceEvents;
import it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.service.ProfilesManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.widget.AddGsUserWidget;


// TODO: Auto-generated Javadoc
/**
 * The Class UsersView.
 */
public class UsersView extends View
{

    /** The gs manager service remote. */
    private GsUsersManagerRemoteServiceAsync gsManagerServiceRemote =
        GsUsersManagerRemoteServiceAsync.Util.getInstance();

    /** The profiles manager service remote. */
    private ProfilesManagerRemoteServiceAsync profilesManagerServiceRemote =
        ProfilesManagerRemoteServiceAsync.Util.getInstance();

    private AddGsUserWidget addGsUser;

    /**
     * Instantiates a new users view.
     *
     * @param controller
     *            the controller
     */
    public UsersView(Controller controller)
    {
        super(controller);

        this.addGsUser = new AddGsUserWidget(GeofenceEvents.SAVE_USER, true);
        this.addGsUser.setGsUserService(gsManagerServiceRemote);
        this.addGsUser.setProfileService(profilesManagerServiceRemote);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)
     */
    @Override
    protected void handleEvent(AppEvent event)
    {
        if (event.getType() == GeofenceEvents.CREATE_NEW_USER)
        {
            onCreateNewUser(event);
        }

    }

    /**
     * On create new profile.
     *
     * @param event
     *            the event
     */
    private void onCreateNewUser(AppEvent event)
    {
        this.addGsUser.show();
    }
}
