/*
 *  Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.geosolutions.geofence.services.dto;

import java.io.Serializable;
import java.util.Date;

import it.geosolutions.geofence.core.model.UserGroup;


/**
 * A compact representation of UserGroup useful in lists.
 *
 * @author Etj (etj at geo-solutions.it)
 */
public class ShortGroup implements Serializable
{

    private static final long serialVersionUID = -8410646966443187827L;
    private long id;
    private String name;
    private String extId;
    private Date dateCreation;
    private Boolean enabled;

    public ShortGroup()
    {
    }

    public ShortGroup(UserGroup group)
    {
        this.id = group.getId();
        this.name = group.getName();
        this.dateCreation = group.getDateCreation();
        this.enabled = group.getEnabled();
        this.extId = group.getExtId();
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getDateCreation()
    {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation)
    {
        this.dateCreation = dateCreation;
    }

    public Boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getExtId()
    {
        return extId;
    }

    public void setExtId(String extId)
    {
        this.extId = extId;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() +
            "[id=" + id +
            " name=" + name +
            " enabled=" + enabled +
            ']';
    }

}
