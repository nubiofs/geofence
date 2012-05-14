/*
 * $ Header: it.geosolutions.geofence.gui.client.model.data.Layer,v. 0.1 28-gen-2011 18.23.56 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 28-gen-2011 18.23.56 $
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
 * The Class Layer.
 */
public class Layer extends BeanModel implements IsSerializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7312704465426834699L;

    /** The layer. */
    private String layer;

    /** The path. */
    private String path;

    /**
     * Instantiates a new layer.
     *
     * @param layer
     *            the layer
     */
    public Layer(String layer)
    {
        this();
        setLayer(layer);
    }

    /**
     * Instantiates a new layer.
     */
    public Layer()
    {
        super();
        setPath("geofence/resources/images/layer.jpg");
    }

    /**
     * Sets the layer.
     *
     * @param layer
     *            the new layer
     */
    public void setLayer(String layer)
    {
        this.layer = layer;
        set(BeanKeyValue.LAYER.getValue(), this.layer);
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
     * Gets the layer.
     *
     * @return the layer
     */
    public String getLayer()
    {
        return layer;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((layer == null) ? 0 : layer.hashCode());
        result = (prime * result) + ((path == null) ? 0 : path.hashCode());

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
        if (!(obj instanceof Layer))
        {
            return false;
        }

        Layer other = (Layer) obj;
        if (layer == null)
        {
            if (other.layer != null)
            {
                return false;
            }
        }
        else if (!layer.equals(other.layer))
        {
            return false;
        }
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

        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Layer [");
        if (layer != null)
        {
            builder.append("layer=").append(layer).append(", ");
        }
        if (path != null)
        {
            builder.append("path=").append(path);
        }
        builder.append("]");

        return builder.toString();
    }

}
