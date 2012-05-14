/*
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S. http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geofence.services.rest.model.config.adapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name = "item")
public class RemappedType
{

    private Long oldId;
    private Long newId;

    public RemappedType(Long key, Long value)
    {
        this.oldId = key;
        this.newId = value;
    }

    public RemappedType()
    {
    }

    @XmlAttribute
    public Long getOld()
    {
        return oldId;
    }

    public void setOld(Long key)
    {
        this.oldId = key;
    }

    @XmlAttribute
    public Long getNew()
    {
        return newId;
    }

    public void setNew(Long value)
    {
        this.newId = value;
    }
}
