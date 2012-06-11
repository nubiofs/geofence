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

package it.geosolutions.geofence.core.dao;

import com.googlecode.genericdao.search.Search;
import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.UserGroup;
import java.util.Set;

/**
 * Public interface to define operations on GSUsers
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */

public interface GSUserDAO extends RestrictedGenericDAO<GSUser> {

    Set<UserGroup> getGroups(Long id);

    /** Fetch a GSUser with all of its related groups */
    GSUser getFull(Long id);
    /** Fetch a GSUser with all of its related groups */
    GSUser getFull(String name);

}
