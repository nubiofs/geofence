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
import java.util.HashSet;
import java.util.Set;

/**
 * Transform a UserGroup into its id.
 *
 */
public class FK2UserGroupSetAdapter extends XmlAdapter<Set<IdNameBundle>, Set<UserGroup>> {

    @Override
    public Set<UserGroup> unmarshal(Set<IdNameBundle> inSet) throws ParseException {
        if(inSet == null)
            return null;

        Set<UserGroup> ret = new HashSet<UserGroup>();
        for (IdNameBundle in : inSet) {
            UserGroup ug = new UserGroup();
            ug.setId(in.getId());
            ug.setName(in.getName());
            ret.add(ug);
        }

        return ret;
    }

    @Override
    public Set<IdNameBundle> marshal(Set<UserGroup> inSet) throws ParseException {
        if(inSet == null)
            return null;

        Set<IdNameBundle> ret = new HashSet<IdNameBundle>();
        for (UserGroup ug : inSet) {
            if (ug != null) {
                IdNameBundle in = new IdNameBundle();
                in.setId(ug.getId());
                in.setName(ug.getName());
                ret.add(in);
            }
        }

        return ret;
    }
}
