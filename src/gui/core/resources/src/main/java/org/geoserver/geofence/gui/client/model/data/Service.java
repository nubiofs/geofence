/*
 * $ Header: it.geosolutions.geofence.gui.client.model.data.Service,v. 0.1 26-gen-2011 14.07.58 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 26-gen-2011 14.07.58 $
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
package it.geosolutions.geofence.gui.client.model.data;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.google.gwt.user.client.rpc.IsSerializable;

import it.geosolutions.geofence.gui.client.model.BeanKeyValue;


// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class Service extends BeanModel implements IsSerializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2828906795801803648L;

    /** The service. */
    private String service;

    /** The path. */
    private String path;

    /**
     * Instantiates a new service.
     *
     * @param service
     *            the service
     */
    public Service(String service)
    {
        this();
        setService(service);
    }

    /**
     * Instantiates a new service.
     */
    public Service()
    {
        super();
        setPath("geofence/resources/images/service.jpg");
    }

    /**
     * Sets the service.
     *
     * @param service
     *            the new service
     */
    public void setService(String service)
    {
        this.service = service;
        set(BeanKeyValue.SERVICE.getValue(), this.service);
    }

    /**
     * Sets the path.
     *
     * @param path
     *            the new path
     */
    public void setPath(String path)
    {
        this.path = path;
        set(BeanKeyValue.PATH.getValue(), path);
    }

    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Gets the service.
     *
     * @return the service
     */
    public String getService()
    {
        return service;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((service == null) ? 0 : service.hashCode());

        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Service))
        {
            return false;
        }

        Service other = (Service) obj;
        if (service == null)
        {
            if (other.service != null)
            {
                return false;
            }
        }
        else if (!service.equals(other.service))
        {
            return false;
        }

        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Service [");
        if (service != null)
        {
            builder.append("service=").append(service);
        }
        builder.append("]");

        return builder.toString();
    }

}
