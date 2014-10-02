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
package it.geosolutions.geofence.services.rest.model.config;

import it.geosolutions.geofence.core.model.GSInstance;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name = "GSInstanceList")
public class RESTFullGSInstanceList {

    private List<GSInstance> list;

    public RESTFullGSInstanceList() {
        this(10);
    }

    public RESTFullGSInstanceList(int initialCapacity) {
        list = new ArrayList<GSInstance>(initialCapacity);
    }

    @XmlElement(name = "GSInstance")
    public List<GSInstance> getList() {
        return list;
    }

    public void setList(List<GSInstance> list) {
        this.list = list;
    }

    public void add(GSInstance gs) {
        list.add(gs);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + list.size() + " items]";
    }
}
