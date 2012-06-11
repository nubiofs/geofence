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


import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.UserGroup;
import it.geosolutions.geofence.services.UserGroupAdminService;
import it.geosolutions.geofence.services.UserAdminService;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;
import it.geosolutions.geofence.services.rest.exception.BadRequestRestEx;
import it.geosolutions.geofence.services.rest.exception.NotFoundRestEx;
import it.geosolutions.geofence.services.rest.model.RESTOutputUser;
import it.geosolutions.geofence.services.rest.model.RESTShortUser;
import it.geosolutions.geofence.services.rest.model.util.IdName;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public abstract class BaseRESTServiceImpl {

    private static final Logger LOGGER = Logger.getLogger(BaseRESTServiceImpl.class);

    private UserAdminService userService;
    private UserGroupAdminService userGroupService;


    protected UserGroup getUserGroup(IdName filter) throws BadRequestRestEx, NotFoundRestEx {

        try {
            if ( filter.getId() != null ) {
                return userGroupService.get(filter.getId());
            } else if ( filter.getName() != null ) {
                return userGroupService.get(filter.getName());
            } else {
                throw new BadRequestRestEx("Bad UserGroup filter " + filter);
            }
        } catch (NotFoundServiceEx e) {
            LOGGER.warn("UserGroup not found " + filter);
            throw new NotFoundRestEx("UserGroup not found " + filter);
        }
    }

    // ==========================================================================
    protected static RESTShortUser toShortUser(GSUser user) {
        RESTShortUser shu = new RESTShortUser();
        shu.setId(user.getId());
        shu.setExtId(user.getExtId());
        shu.setUserName(user.getName());
        shu.setEnabled(user.getEnabled());

        return shu;
    }

    protected static RESTOutputUser toOutputUser(GSUser user) {
        RESTOutputUser ret = new RESTOutputUser();
        ret.setId(user.getId());
        ret.setExtId(user.getExtId());
        ret.setName(user.getName());
        ret.setEnabled(user.getEnabled());
        ret.setAdmin(user.isAdmin());
        ret.setFullName(user.getFullName());
        ret.setEmailAddress(user.getEmailAddress());

        List<IdName> groups = new ArrayList<IdName>();
        for (UserGroup group : user.getGroups()) {
            IdName nameId = new IdName(group.getId(), group.getName());
            groups.add(nameId);
        }
        ret.setGroups(groups);

        return ret;
    }



    // ==========================================================================
    public void setUserAdminService(UserAdminService service) {
        this.userService = service;
    }

    public void setUserGroupAdminService(UserGroupAdminService service) {
        this.userGroupService = service;
    }
}
