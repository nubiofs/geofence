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

package it.geosolutions.geofence.services.rest.model.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.geosolutions.geofence.core.model.GSInstance;
import it.geosolutions.geofence.core.model.Rule;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */

@XmlRootElement(name = "RuleList")
public class RESTFullRuleList
{

    private List<Rule> list;

    public RESTFullRuleList()
    {
        this(10);
    }

    public RESTFullRuleList(int initialCapacity)
    {
        list = new ArrayList<Rule>(initialCapacity);
    }

    @XmlElement(name = "Rule")
    public List<Rule> getList()
    {
        return list;
    }

    public void setList(List<Rule> list)
    {
        this.list = list;
    }

    public void add(Rule rule)
    {
        list.add(rule);
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "[" + list.size() + " items]";
    }

}
