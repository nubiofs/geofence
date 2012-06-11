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
package it.geosolutions.geofence.services;

import java.util.List;
import java.util.Map;

import it.geosolutions.geofence.core.model.UserGroup;
import it.geosolutions.geofence.services.dto.ShortGroup;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;


/**
 * Operations on {@link UserGroup UserGroup}s.
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
public interface UserGroupAdminService extends GetProviderService<UserGroup>
{

    // ==========================================================================
    // Basic operations

    long insert(ShortGroup group);

    long update(ShortGroup group) throws NotFoundServiceEx;

    boolean delete(long id) throws NotFoundServiceEx;

    @Override
    UserGroup get(long id) throws NotFoundServiceEx;
    UserGroup get(String name) throws NotFoundServiceEx;

    long getCount(String nameLike);

    List<ShortGroup> getList(String nameLike, Integer page, Integer entries);

    List<UserGroup> getFullList(String nameLike, Integer page, Integer entries);

    // ==========================================================================

//    public Map<String, String> getCustomProps(Long id);

//    public void setCustomProps(Long id, Map<String, String> props);

}
