/*
 * $ Header: it.geosolutions.geofence.gui.client.model.Profile,v. 0.1 25-gen-2011 11.24.45 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.24.45 $
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
package it.geosolutions.geofence.gui.client.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.google.gwt.user.client.rpc.IsSerializable;


// TODO: Auto-generated Javadoc
/**
 * The Class Profile.
 */
public class Profile extends BeanModel implements IsSerializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3475163929906592234L;

    /** The id. */
    private long id;

    /** The name. */
    private String name;

    /** The date creation. */
    private Date dateCreation;

    /** The enabled. */
    private boolean enabled;

    /** The custom props. */
    private Map<String, String> customProps = new HashMap<String, String>();

    /** The path. */
    private String path;

    /**
     * Instantiates a new profile.
     */
    public Profile()
    {
        setPath("geofence/resources/images/profile.jpg");
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId()
    {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name)
    {
        this.name = name;
        set(BeanKeyValue.NAME.getValue(), name);
    }

    /**
     * Gets the date creation.
     *
     * @return the date creation
     */
    public Date getDateCreation()
    {
        return dateCreation;
    }

    /**
     * Sets the date creation.
     *
     * @param dateCreation
     *            the new date creation
     */
    public void setDateCreation(Date dateCreation)
    {
        this.dateCreation = dateCreation;
        set(BeanKeyValue.DATE_CREATION.getValue(), dateCreation);
    }

    /**
     * Checks if is the enabled.
     *
     * @return the enabled
     */
    public boolean isEnabled()
    {
        return enabled;
    }

    /**
     * Sets the enabled.
     *
     * @param enabled
     *            the new enabled
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
        set(BeanKeyValue.PROFILE_ENABLED.getValue(), enabled);
    }

    /**
     * Gets the custom props.
     *
     * @return the custom props
     */
    public Map<String, String> getCustomProps()
    {
        return customProps;
    }

    /**
     * Sets the custom props.
     *
     * @param customProps
     *            the new custom props
     */
    public void setCustomProps(Map<String, String> customProps)
    {
        this.customProps = customProps;
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

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((customProps == null) ? 0 : customProps.hashCode());
        result = (prime * result) + ((dateCreation == null) ? 0 : dateCreation.hashCode());
        result = (prime * result) + (enabled ? 1231 : 1237);
        result = (prime * result) + (int) (id ^ (id >>> 32));
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
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
        if (!(obj instanceof Profile))
        {
            return false;
        }

        Profile other = (Profile) obj;
        if (customProps == null)
        {
            if (other.customProps != null)
            {
                return false;
            }
        }
        else if (!customProps.equals(other.customProps))
        {
            return false;
        }
        if (dateCreation == null)
        {
            if (other.dateCreation != null)
            {
                return false;
            }
        }
        else if (!dateCreation.equals(other.dateCreation))
        {
            return false;
        }
        if (enabled != other.enabled)
        {
            return false;
        }
        if (id != other.id)
        {
            return false;
        }
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals(other.name))
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
        builder.append("Profile [");
        if (customProps != null)
        {
            builder.append("customProps=").append(customProps).append(", ");
        }
        if (dateCreation != null)
        {
            builder.append("dateCreation=").append(dateCreation).append(", ");
        }
        builder.append("enabled=").append(enabled).append(", id=").append(id).append(", ");
        if (name != null)
        {
            builder.append("name=").append(name).append(", ");
        }
        if (path != null)
        {
            builder.append("path=").append(path);
        }
        builder.append("]");

        return builder.toString();
    }
}
