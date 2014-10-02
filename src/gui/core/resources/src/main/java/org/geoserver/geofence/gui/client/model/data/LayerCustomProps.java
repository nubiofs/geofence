/*
 * $ Header: it.geosolutions.geofence.gui.client.model.data.LayerCustomProps,v. 0.1 8-feb-2011 15.34.49 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 8-feb-2011 15.34.49 $
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
 * The Class LayerCustomProps.
 */
public class LayerCustomProps extends BeanModel implements IsSerializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 264528825285369296L;

    /** The prop key. */
    private String propKey;

    /** The prop value. */
    private String propValue;

    /** The path. */
    private String path;

    /**
     * Instantiates a new layer custom props.
     */
    public LayerCustomProps()
    {
        super();
        setPath("geofence/resources/images/layer.jpg");
    }

    /**
     * Sets the layer.
     *
     * @param propKey
     *            the new layer
     */
    public void setPropKey(String propKey)
    {
        this.propKey = propKey;
        set(BeanKeyValue.PROP_KEY.getValue(), this.propKey);
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
    public String getPropKey()
    {
        return propKey;
    }

    /**
     * Sets the path.
     *
     * @param propValue
     *            the new path
     */
    public void setPropValue(String propValue)
    {
        this.propValue = propValue;
        set(BeanKeyValue.PROP_VALUE.getValue(), this.propValue);
    }

    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPropValue()
    {
        return propValue;
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
        result = (prime * result) + ((propKey == null) ? 0 : propKey.hashCode());
        result = (prime * result) + ((propValue == null) ? 0 : propValue.hashCode());

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
        if (!(obj instanceof LayerCustomProps))
        {
            return false;
        }

        LayerCustomProps other = (LayerCustomProps) obj;
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
        if (propKey == null)
        {
            if (other.propKey != null)
            {
                return false;
            }
        }
        else if (!propKey.equals(other.propKey))
        {
            return false;
        }
        if (propValue == null)
        {
            if (other.propValue != null)
            {
                return false;
            }
        }
        else if (!propValue.equals(other.propValue))
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
        builder.append("LayerCustomProps [");
        if (path != null)
        {
            builder.append("path=").append(path).append(", ");
        }
        if (propKey != null)
        {
            builder.append("propKey=").append(propKey).append(", ");
        }
        if (propValue != null)
        {
            builder.append("propValue=").append(propValue);
        }
        builder.append("]");

        return builder.toString();
    }

}
