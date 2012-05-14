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
package it.geosolutions.geofence.core.model.adapter.dual;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name = "Map")
public class MapType implements Iterable<MapEntryType> {


    List<MapEntryType> entries = new LinkedList<MapEntryType>();

    @XmlElement(name = "entry")
    public List<MapEntryType> getEntries() {
        return entries;
    }

    public void setEntries(List<MapEntryType> entries) {
        this.entries = entries;
    }

    public void add(Map.Entry<String, String> entry) {
        entries.add(new MapEntryType(entry.getKey(), entry.getValue()));
    }

    @Override
    public Iterator<MapEntryType> iterator() {
        return entries.iterator();
    }
}
