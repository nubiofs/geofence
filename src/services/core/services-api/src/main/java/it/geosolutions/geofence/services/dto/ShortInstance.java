/*
 *  Copyright (C) 2007 - 2012 GeoSolutions S.A.S.
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

import it.geosolutions.geofence.core.model.GSInstance;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A compact representation of GSInstance useful in lists.
 *
 * @author Etj (etj at geo-solutions.it)
 */
@XmlRootElement(name = "GSInstance")
@XmlType(propOrder = {"id", "name", "url", "description"})
public class ShortInstance implements Serializable {

    private long id;
    private String name;
    private String url;
    private String description;

    public ShortInstance() {
    }

    public ShortInstance(GSInstance i) {
        this.id = i.getId();
        this.name = i.getName();
        this.url = i.getBaseURL();
        this.description = i.getDescription();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[id:" + id
                + " name:" + name
                + " url:" + url
                + ']';
    }
}
