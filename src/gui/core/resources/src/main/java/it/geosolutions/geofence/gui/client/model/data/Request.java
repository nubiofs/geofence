/*
 * $ Header: it.geosolutions.geofence.gui.client.model.data.Request,v. 0.1 26-gen-2011 14.07.59 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 26-gen-2011 14.07.59 $
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
 * The Class Request.
 */
public class Request extends BeanModel implements IsSerializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -750499207539541183L;

    /** The request. */
    private String request;

    /** The path. */
    private String path;

    /**
     * Instantiates a new request.
     *
     * @param request
     *            the request
     */
    public Request(String request)
    {
        this();
        setRequest(request);
    }

    /**
     * Instantiates a new request.
     */
    public Request()
    {
        super();
        setPath("geofence/resources/images/request.jpg");
    }

    /**
     * Sets the request.
     *
     * @param request
     *            the new request
     */
    public void setRequest(String request)
    {
        this.request = request;
        set(BeanKeyValue.REQUEST.getValue(), this.request);
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
     * Gets the request.
     *
     * @return the request
     */
    public String getRequest()
    {
        return request;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((path == null) ? 0 : path.hashCode());
        result = (prime * result) + ((request == null) ? 0 : request.hashCode());

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
        if (!(obj instanceof Request))
        {
            return false;
        }

        Request other = (Request) obj;
        if (path == null)
        {
            if (other.path != null)
            {
                return false;
            }
        }
        else if (!path.equals(other.path))
        {
            return false;
        }
        if (request == null)
        {
            if (other.request != null)
            {
                return false;
            }
        }
        else if (!request.equals(other.request))
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
        builder.append("Request [");
        if (path != null)
        {
            builder.append("path=").append(path).append(", ");
        }
        if (request != null)
        {
            builder.append("request=").append(request);
        }
        builder.append("]");

        return builder.toString();
    }

}
