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
package it.geosolutions.geofence.services.rest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name = "RuleList")
public class RESTOutputRuleList implements Iterable<RESTOutputRule>{

    private List<RESTOutputRule> list;

    public RESTOutputRuleList() {
        this(10);
    }

    public RESTOutputRuleList(int initialCapacity) {
        list = new ArrayList<RESTOutputRule>(initialCapacity);
    }

    @XmlElement(name = "rule")
    public List<RESTOutputRule> getList() {
        return list;
    }

    public void setList(List<RESTOutputRule> userList) {
        this.list = userList;
    }

    public void add(RESTOutputRule user) {
        list.add(user);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + list.size() + " users]";
    }

    @Override
    public Iterator<RESTOutputRule> iterator() {
        return list.iterator();
    }
}
