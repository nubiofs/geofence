/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2011, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.geofence.rest;

import it.geosolutions.geofence.GeofenceAccessManager;

import java.util.logging.Logger;

import org.geotools.util.logging.Logging;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.StringRepresentation;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class GeoFenceInfo extends Resource {
    static final Logger LOGGER = Logging.getLogger(GeoFenceInfo.class);

    private GeofenceAccessManager accessManager;

    public GeoFenceInfo(GeofenceAccessManager accessManager) {
        this.accessManager = accessManager;
    }

    @Override
    public boolean allowGet() {
        return true;
    }

    @Override
    public boolean allowPut() {
        return false;
    }

    
    
    @Override
	public void handleGet() {
		Representation representation = new StringRepresentation(accessManager
				.getConfiguration().getInstanceName());
        getResponse().setEntity(representation);
	}





}
