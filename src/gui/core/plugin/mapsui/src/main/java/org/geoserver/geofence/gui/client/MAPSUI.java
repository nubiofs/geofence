/*
 * $ Header: it.geosolutions.geofence.gui.client.MAPSUI,v. 0.1 25-gen-2011 11.30.32 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.30.32 $
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
package it.geosolutions.geofence.gui.client;

import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;

import it.geosolutions.geofence.gui.client.mvc.MAPSController;
import it.geosolutions.geofence.gui.client.mvc.MapController;


// TODO: Auto-generated Javadoc
/**
 * The Class MAPSUI.
 */
public class MAPSUI implements EntryPoint
{

    /** The dispatcher. */
    private Dispatcher dispatcher;

    /*
     * (non-Javadoc)
     *
     * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
     */
    public void onModuleLoad()
    {
        dispatcher = Dispatcher.get();

        dispatcher.addController(new MapController());
        dispatcher.addController(new MAPSController());
        // dispatcher.addController(new FeatureController());
//        dispatcher.addController(new FilterController());

        dispatcher.dispatch(GeofenceEvents.INIT_MAPS_UI_MODULE);

    }

}
