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

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RemapperAdapter extends XmlAdapter<MapType, Map<Long, Long>>
{

    @Override
    public MapType marshal(Map<Long, Long> v) throws Exception
    {
        MapType ret = new MapType();
//        System.out.println("marshalling...");
        for (Map.Entry<Long, Long> entry : v.entrySet())
        {
//            System.out.println("marshalling " + entry.getKey()+":"+entry.getValue());
            ret.add(entry);
        }

        return ret;
    }

    @Override
    public Map<Long, Long> unmarshal(MapType v) throws Exception
    {
        Map<Long, Long> ret = new HashMap<Long, Long>();
//        System.out.println("unmarshalling...");
        for (RemappedType entry : v)
        {
//            System.out.println("unmarshalling " + entry.getKey() + ":" + entry.getValue());
            ret.put(entry.getOld(), entry.getNew());
        }

        return ret;
    }


}
