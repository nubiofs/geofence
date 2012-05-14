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

package it.geosolutions.geofence.services.rest.impl;

import java.util.List;

import com.vividsolutions.jts.geom.MultiPolygon;

import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.Profile;
import it.geosolutions.geofence.services.ProfileAdminService;
import it.geosolutions.geofence.services.UserAdminService;
import it.geosolutions.geofence.services.exception.BadRequestServiceEx;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;
import it.geosolutions.geofence.services.rest.RESTUserService;
import it.geosolutions.geofence.services.rest.exception.*;
import it.geosolutions.geofence.services.rest.model.RESTShortUser;
import it.geosolutions.geofence.services.rest.model.RESTShortUserList;
import it.geosolutions.geofence.services.rest.utils.MultiPolygonUtils;

import org.apache.log4j.Logger;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RESTUserServiceImpl implements RESTUserService
{
    private static final Logger LOGGER = Logger.getLogger(RESTUserServiceImpl.class);

    // ==========================================================================

    public static RESTShortUser transformUser(GSUser user)
    {
        RESTShortUser shu = new RESTShortUser();
        shu.setId(user.getId());
        shu.setExtId(user.getExtId());
        shu.setUserName(user.getName());
        shu.setEnabled(user.getEnabled());
        shu.setProfile(user.getProfile().getName());

        return shu;
    }

    private UserAdminService userService;
    private ProfileAdminService profileService;

    @Override
    public void delete(Long id) throws BadRequestServiceEx, NotFoundRestEx
    {
        try
        {
            userService.delete(id);
        }
        catch (NotFoundServiceEx ex)
        {
            LOGGER.warn("User not found: " + id);
            throw new NotFoundRestEx("User not found: " + id);
        }
    }

    @Override
    public GSUser get(Long id) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx
    {
        try
        {
            return userService.get(id);
        }
        catch (NotFoundServiceEx ex)
        {
            LOGGER.warn("User not found: " + id);
            throw new NotFoundRestEx("User not found: " + id);
        }
    }

    @Override
    public Long insert(GSUser user) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx
    {
        try
        {
            // resolve profile
            Profile placeHolderProfile = user.getProfile();
            if (placeHolderProfile == null)
            {
                throw new BadRequestServiceEx("Can't insert user without profile");
            }

            Long profileId = placeHolderProfile.getId();

            LOGGER.warn("ProfileService is " + profileService);
            LOGGER.warn("ProfileId is " + profileId);

            Profile profile = profileService.get(profileId);
            user.setProfile(profile);

            // Simplify geometry
            MultiPolygon origmp = user.getAllowedArea();
            if (origmp != null)
            {
                user.setAllowedArea(MultiPolygonUtils.simplifyMultiPolygon(origmp));
            }

            return userService.insert(user);

        }
        catch (NotFoundServiceEx ex)
        {
            LOGGER.warn("Profile not found " + user.getProfile(), ex);
            throw new NotFoundRestEx(ex.getMessage());
        }
        catch (BadRequestServiceEx ex)
        {
            LOGGER.warn("Bad request: " + ex.getMessage(), ex);
            throw new BadRequestRestEx(ex.getMessage());
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage(), ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }
    }

    @Override
    public void update(Long id, GSUser user) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx
    {

        if (!id.equals(user.getId()))
        {
            throw new BadRequestServiceEx("Id mismatch");
        }

        try
        {
            GSUser old = userService.get(id);

            if ((user.getExtId() != null) && !user.getExtId().equals(old.getExtId()))
            {
                throw new BadRequestServiceEx("ExtId can't be updated");
            }

            // resolve profile
            Profile placeHolderProfile = user.getProfile();
            if (placeHolderProfile == null)
            {
                throw new BadRequestServiceEx("Can't set a user without profile");
            }

            Profile profile = profileService.get(placeHolderProfile.getId());
            user.setProfile(profile);

            userService.update(user);

        }
        catch (NotFoundServiceEx ex)
        {
            LOGGER.warn("Problems updating user " + id + ": " + ex.getMessage(), ex);
            throw new NotFoundRestEx(ex.getMessage());
        }
        catch (BadRequestServiceEx ex)
        {
            LOGGER.warn("Problems updating user " + id + ": " + ex.getMessage(), ex);
            throw new BadRequestRestEx(ex.getMessage());
        }
        catch (Exception ex)
        {
            LOGGER.error(ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }
    }

    @Override
    public RESTShortUserList getUsers(String nameLike, Integer page, Integer entries)
    {
        List<GSUser> list = userService.getFullList(null, null, null);
        RESTShortUserList ret = new RESTShortUserList(list.size());
        for (GSUser user : list)
        {
            ret.add(transformUser(user));
        }

        return ret;
    }

    @Override
    public long getCount(String nameLike)
    {
        return userService.getCount(nameLike);
    }

    // ==========================================================================

    public void setUserAdminService(UserAdminService userService)
    {
        this.userService = userService;
    }

    public void setProfileAdminService(ProfileAdminService profileService)
    {
        this.profileService = profileService;
    }


}
