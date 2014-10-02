/*
 *  Copyright (C) 2007 - 2010 GeoSolutions S.A.S.
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

import it.geosolutions.geofence.core.model.GFUser;
import it.geosolutions.geofence.core.model.GSUser;

import java.io.Serializable;


/**
 * A compact representation of GSUser useful in lists.
 *
 * @author Etj (etj@geo-solutions.it)
 */
public class ShortUser implements Serializable
{

    private static final long serialVersionUID = -2484627092672856852L;

    private long id;

    private String name;

    public ShortUser()
    {
    }

    public ShortUser(GSUser user)
    {
        this.id = user.getId();
        this.name = user.getName();
    }

    public ShortUser(GFUser user)
    {
        this.id = user.getId();
        this.name = user.getName();
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

    @Override
    public String toString()
    {
        return getClass().getSimpleName()+"[id:" + id + " name:" + name + ']';
    }

}
