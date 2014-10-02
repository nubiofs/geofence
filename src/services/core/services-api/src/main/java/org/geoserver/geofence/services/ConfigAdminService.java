/*
 *  Copyright (C) 2007 - 2014 GeoSolutions S.A.S.
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
package it.geosolutions.geofence.services;

import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.services.dto.ShortUser;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;


import java.util.List;

/**
 * Operations on {@link GSUser GSUser}s.
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
public interface ConfigAdminService
{

    // ==========================================================================
    // Basic operations

    long insert(GSUser user);

    long update(GSUser user) throws NotFoundServiceEx;

    boolean delete(long id) throws NotFoundServiceEx;

    GSUser get(long id) throws NotFoundServiceEx;

    List<ShortUser> getAll();

    List<ShortUser> getList(String nameLike, Integer page, Integer entries);

    long getCount(String nameLike);
}
