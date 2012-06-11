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
package it.geosolutions.geofence.services;

import java.util.List;
import java.util.Map;

import it.geosolutions.geofence.core.model.Profile;
import it.geosolutions.geofence.services.dto.ShortProfile;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;


/**
 * Operations on {@link Profile Profile}s.
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
public interface ProfileAdminService extends GetProviderService<Profile>
{

    // ==========================================================================
    // Basic operations

    long insert(ShortProfile profile);

    long update(ShortProfile profile) throws NotFoundServiceEx;

    boolean delete(long id) throws NotFoundServiceEx;

    @Override
    Profile get(long id) throws NotFoundServiceEx;

    long getCount(String nameLike);

    List<ShortProfile> getList(String nameLike, Integer page, Integer entries);

    List<Profile> getFullList(String nameLike, Integer page, Integer entries);

    // ==========================================================================

    public Map<String, String> getCustomProps(Long id);

    public void setCustomProps(Long id, Map<String, String> props);

}
