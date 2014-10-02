/*
 *  Copyright (C) 2007 - 2010 GeoSolutions S.A.S.
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
import it.geosolutions.geofence.core.model.UserGroup;
import it.geosolutions.geofence.services.dto.ShortUser;
import it.geosolutions.geofence.services.exception.BadRequestServiceEx;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;

import java.util.List;
import java.util.Set;


/**
 * Operations on {@link GSUser GSUser}s.
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
public interface UserAdminService extends GetProviderService<GSUser>
{

    // ==========================================================================
    // Basic operations

    long insert(GSUser user);

    long update(GSUser user) throws NotFoundServiceEx;

    boolean delete(long id) throws NotFoundServiceEx;

    /**
     * Retrieves basic info on Users. <BR>
     * If you need structured info (such as Groups), use the {@link #getFull(long)} method.
     *
     * @return Basic GSUser, with some info left unreferenced.
     *
     * @throws NotFoundServiceEx
     */
    @Override
    GSUser get(long id) throws NotFoundServiceEx;
    GSUser get(String name) throws NotFoundServiceEx;
    GSUser getFull(long id) throws NotFoundServiceEx;

    Set<UserGroup> getUserGroups(long id);

    long getCount(String nameLike);

    List<ShortUser> getList(String nameLike, Integer page, Integer entries);

    List<GSUser> getFullList(String nameLike, Integer page, Integer entries) throws BadRequestServiceEx;
    List<GSUser> getFullList(String nameLike, Integer page, Integer entries, boolean fetchGroups) throws BadRequestServiceEx;
}
