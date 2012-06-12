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
package it.geosolutions.geofence.services.rest.impl;

import java.util.List;

import it.geosolutions.geofence.core.model.UserGroup;
import it.geosolutions.geofence.services.UserGroupAdminService;
import it.geosolutions.geofence.services.dto.ShortGroup;
import it.geosolutions.geofence.services.exception.BadRequestServiceEx;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;
import it.geosolutions.geofence.services.rest.RESTUserGroupService;
import it.geosolutions.geofence.services.rest.exception.BadRequestRestEx;
import it.geosolutions.geofence.services.rest.exception.ConflictRestEx;
import it.geosolutions.geofence.services.rest.exception.InternalErrorRestEx;
import it.geosolutions.geofence.services.rest.exception.NotFoundRestEx;
import it.geosolutions.geofence.services.rest.model.RESTInputGroup;
import it.geosolutions.geofence.services.rest.model.config.RESTFullUserGroupList;

import org.apache.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RESTUserGroupServiceImpl implements RESTUserGroupService {

    private static final Logger LOGGER = Logger.getLogger(RESTUserGroupServiceImpl.class);
    private UserGroupAdminService userGroupAdminService;

    @Override
    public RESTFullUserGroupList getUserGroups(String nameLike, Integer page, Integer entries) {
        List<UserGroup> groups = userGroupAdminService.getFullList(nameLike, page, entries);

//        // load lazy data
//        for (UserGroup profile : profiles)
//        {
//            Map<String, String> props = profileService.getCustomProps(profile.getId());
//            profile.setCustomProps(props);
//        }

        RESTFullUserGroupList ret = new RESTFullUserGroupList();
        ret.setList(groups);

        return ret;
    }

    @Override
    public long getCount(String nameLike) {
        return userGroupAdminService.getCount(nameLike);
    }

    @Override
    public void delete(Long id) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx {
        try {
            userGroupAdminService.delete(id);
        } catch (NotFoundServiceEx ex) {
            LOGGER.warn(ex);
            throw new NotFoundRestEx("UserGroup not found");
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }
    }

    @Override
    public UserGroup get(Long id) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx {
        try {
            UserGroup ret = userGroupAdminService.get(id);

            return ret;
        } catch (NotFoundServiceEx ex) {
            LOGGER.warn(ex);
            throw new NotFoundRestEx("UserGroup not found");
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }
    }

    @Override
    public Long insert(RESTInputGroup userGroup) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx, ConflictRestEx {

        // check that no group with same name exists
        boolean exists;
        try {
            userGroupAdminService.get(userGroup.getName());
            exists = true;
        } catch (NotFoundServiceEx ex) {
            // well, ok, usergroup does not exist
            exists = false;
        } catch (Exception ex) {
            // something went wrong
            LOGGER.error(ex.getMessage(), ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }

        if(exists)
            throw new ConflictRestEx("UserGroup '"+userGroup.getName()+"' already exists");

        // ok: insert it
        ShortGroup insert = new ShortGroup();
        insert.setEnabled(userGroup.getEnabled());
        insert.setExtId(userGroup.getExtId());
        insert.setName(userGroup.getName());

        Long id = userGroupAdminService.insert(insert);

        return id;
    }

    @Override
    public void update(Long id, RESTInputGroup userGroup) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx {

        try {
            UserGroup old = userGroupAdminService.get(id);

            if ( (userGroup.getExtId() != null) && !userGroup.getExtId().equals(old.getExtId()) ) {
                throw new BadRequestServiceEx("ExtId can't be updated");
            }

            ShortGroup shortGroup = new ShortGroup(old);

            if ( userGroup.getEnabled() != null ) {
                shortGroup.setEnabled(userGroup.getEnabled());
            }

            userGroupAdminService.update(shortGroup);

//            if ( userGroup.getCustomProps() != null ) {
//                profileService.setCustomProps(id, userGroup.getCustomProps());
//            }

        } catch (NotFoundServiceEx ex) {
            LOGGER.warn("UserGroup not found: " + id);
            throw new NotFoundRestEx("UserGroup not found: " + id);
        }
    }

    // ==========================================================================
    // ==========================================================================
    
    public void setUserGroupAdminService(UserGroupAdminService service) {
        this.userGroupAdminService = service;
    }
}
