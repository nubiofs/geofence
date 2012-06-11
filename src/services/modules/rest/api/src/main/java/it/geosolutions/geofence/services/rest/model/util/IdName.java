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
package it.geosolutions.geofence.services.rest.model.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name = "identifier")
public class IdName {
    private  String name;
    private  Long id;

    protected IdName() {
    }

    public IdName(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public IdName(String name) {
        setName(name);
    }

    public IdName(Long id) {
        setId(id);
    }

    @XmlElement
    public Long getId() {
        return id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
        this.name = null;
    }

    public void setName(String name) {
        this.name = name;
        this.id=null;
    }

    @Override
    public String toString() {
        return "[id:" +id+ " name:" + name + ']';
    }
}
