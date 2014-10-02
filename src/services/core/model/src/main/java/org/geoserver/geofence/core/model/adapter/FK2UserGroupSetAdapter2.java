/* ====================================================================
 *
 * Copyright (C) 2007 - 2012 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */

package it.geosolutions.geofence.core.model.adapter;

import it.geosolutions.geofence.core.model.adapter.dual.IdNameBundle;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.vividsolutions.jts.io.ParseException;
import it.geosolutions.geofence.core.model.UserGroup;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Transform a UserGroup into its id.
 *
 */
public class FK2UserGroupSetAdapter2 extends XmlAdapter<ArrayList<Long>, Set<UserGroup>> {

    @Override
    public Set<UserGroup> unmarshal(ArrayList<Long> inSet) throws ParseException {
        if(inSet == null)
            return null;

        Set<UserGroup> ret = new HashSet<UserGroup>();
        for (Long in : inSet) {
            UserGroup ug = new UserGroup();
            ug.setId(in);
            ret.add(ug);
        }

        return ret;
    }

    @Override
    public ArrayList<Long> marshal(Set<UserGroup> inSet) throws ParseException {
        if(inSet == null)
            return null;

        System.out.println("Marshalling " + inSet.size() + " groups");

        ArrayList<Long> ret = new ArrayList<Long>(inSet.size());
        for (UserGroup ug : inSet) {
            if (ug != null) {
                ret.add(ug.getId());
                System.out.println("Added group " + ug.getId());
            }
        }

        return ret;
    }
}
