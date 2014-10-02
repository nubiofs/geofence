/*
 * $ Header: it.geosolutions.geofence.gui.client.Application,v. 0.1 14-gen-2011 19.26.40 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.26.40 $
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

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.geosolutions.geofence.gui.client.configuration.GeofenceGlobalConfiguration;
import it.geosolutions.geofence.gui.client.mvc.AppController;
import it.geosolutions.geofence.gui.client.service.ConfigurationRemote;

// TODO: Auto-generated Javadoc
/**
 * The Class Application.
 */
public class Application implements EntryPoint {

	/** The dispatcher. */
	private Dispatcher dispatcher;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		GXT.hideLoadingPanel("loading");

		dispatcher = Dispatcher.get();

		dispatcher.addController(new AppController());

		ConfigurationRemote.Util.getInstance().initServerConfiguration(
				new AsyncCallback<GeofenceGlobalConfiguration>() {

					public void onSuccess(GeofenceGlobalConfiguration result) {
						GeofenceUtils.getInstance().setGlobalConfiguration(
								result);
						Dispatcher
								.forwardEvent(GeofenceEvents.INIT_GEOFENCE_WIDGET);

					}

					public void onFailure(Throwable caught) {
						Info.display("Configuration Service Error",
								caught.getMessage());

					}
				});

		// Dispatcher.forwardEvent(GeofenceEvents.INIT_GEOFENCE_WIDGET);

	}
}
