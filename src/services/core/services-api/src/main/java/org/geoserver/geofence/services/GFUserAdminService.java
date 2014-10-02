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

import it.geosolutions.geofence.core.model.GFUser;
import it.geosolutions.geofence.services.dto.ShortUser;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;

import java.util.List;


/**
 * Operations on {@link GFUser GFUser}s.
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
public interface GFUserAdminService extends GetProviderService<GFUser>
{

    // ==========================================================================
    // Basic operations

    long insert(GFUser user);

    long update(GFUser user) throws NotFoundServiceEx;

    boolean delete(long id) throws NotFoundServiceEx;

    @Override
    GFUser get(long id) throws NotFoundServiceEx;
    GFUser get(String name) throws NotFoundServiceEx;

    long getCount(String nameLike);

    List<ShortUser> getList(String nameLike, Integer page, Integer entries);

    List<GFUser> getFullList(String nameLike, Integer page, Integer entries);
}
