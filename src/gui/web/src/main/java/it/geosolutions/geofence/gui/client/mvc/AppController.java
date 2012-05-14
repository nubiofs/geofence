/*
 * $ Header: it.geosolutions.geofence.gui.client.mvc.AppController,v. 0.1 14-gen-2011 19.27.23 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.27.23 $
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
package it.geosolutions.geofence.gui.client.mvc;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

import it.geosolutions.geofence.gui.client.GeofenceEvents;


// TODO: Auto-generated Javadoc
/**
 * The Class AppController.
 */
public class AppController extends Controller
{

    /** The app view. */
    private AppView appView;

    /**
     * Instantiates a new app controller.
     */
    public AppController()
    {
        registerEventTypes(
            GeofenceEvents.INIT_GEOFENCE_MAIN_UI,
            GeofenceEvents.SESSION_EXPIRED,
            GeofenceEvents.SAVE);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    public void initialize()
    {
        appView = new AppView(this);
    }

    /**
     * On error.
     *
     * @param ae
     *            the ae
     */
    protected void onError(AppEvent ae)
    {
        System.out.println("error: " + ae.<Object>getData());
    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
     * .mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event)
    {
        if (event.getType() == GeofenceEvents.SESSION_EXPIRED)
        {
            appView.reload();
        }

        if (event.getType() == GeofenceEvents.SAVE)
        {
            onSaveContext();
        }

        forwardToView(appView, event);
    }

    /**
     * On save context.
     */
    private void onSaveContext()
    {
        // TODO: this logic should probably not occur in the Controller, and should not
        // have to assume names of controls, etc. Some Controller/View reorganization is probably
        // necessary,
        // possibly a View/Controller for each admin mode
//        switch (this.currentAdminMode) {
//        case NOTIFICATION_DISTRIBUTION:
//            saveWatch();
//            break;
//        case GEOCONSTRAINTS:
//            saveGeoConstraint();
//            break;
//        case MEMBER:
//            saveMemberNodeAssignment();
//            break;
//        }
    }

}
