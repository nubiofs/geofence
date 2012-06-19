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

import it.geosolutions.geofence.core.model.GSUser;
import java.util.List;

import it.geosolutions.geofence.core.model.UserGroup;
import it.geosolutions.geofence.services.UserGroupAdminService;
import it.geosolutions.geofence.services.dto.RuleFilter;
import it.geosolutions.geofence.services.dto.RuleFilter.SpecialFilterType;
import it.geosolutions.geofence.services.dto.ShortGroup;
import it.geosolutions.geofence.services.exception.BadRequestServiceEx;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;
import it.geosolutions.geofence.services.rest.RESTUserGroupService;
import it.geosolutions.geofence.services.rest.exception.BadRequestRestEx;
import it.geosolutions.geofence.services.rest.exception.ConflictRestEx;
import it.geosolutions.geofence.services.rest.exception.GeoFenceRestEx;
import it.geosolutions.geofence.services.rest.exception.InternalErrorRestEx;
import it.geosolutions.geofence.services.rest.exception.NotFoundRestEx;
import it.geosolutions.geofence.services.rest.model.RESTInputGroup;
import it.geosolutions.geofence.services.rest.model.RESTInputUser;
import it.geosolutions.geofence.services.rest.model.RESTOutputGroup;
import it.geosolutions.geofence.services.rest.model.RESTOutputUser;
import it.geosolutions.geofence.services.rest.model.config.RESTFullUserGroupList;
import it.geosolutions.geofence.services.rest.model.util.IdName;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RESTUserGroupServiceImpl
        extends BaseRESTServiceImpl
        implements RESTUserGroupService {

    private static final Logger LOGGER = Logger.getLogger(RESTUserGroupServiceImpl.class);
//    private UserGroupAdminService userGroupAdminService;

    @Override
    public RESTFullUserGroupList getList(String nameLike, Integer page, Integer entries) {
        List<ShortGroup> groups = userGroupAdminService.getList(nameLike, page, entries);
        return new RESTFullUserGroupList(groups);
    }

    @Override
    public long count(String nameLike) {
        return userGroupAdminService.getCount(nameLike);
    }

    @Override
    public ShortGroup get(Long id) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx {
        try {
            UserGroup ret = userGroupAdminService.get(id);
            return new ShortGroup(ret);
        } catch (NotFoundServiceEx ex) {
            LOGGER.warn("UserGroup not found: " + id);
            throw new NotFoundRestEx("UserGroup not found: " + id);
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }
    }

    @Override
    public ShortGroup get(String name) throws NotFoundRestEx, InternalErrorRestEx {
        try {
            UserGroup ret = userGroupAdminService.get(name);
            return new ShortGroup(ret);
        } catch (NotFoundServiceEx ex) {
            LOGGER.warn("UserGroup not found: " + name);
            throw new NotFoundRestEx("UserGroup not found: " + name);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }
    }

    @Override
    public Response insert(RESTInputGroup userGroup) throws NotFoundRestEx, InternalErrorRestEx, ConflictRestEx {

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
        try {
            ShortGroup insert = new ShortGroup();
            insert.setEnabled(userGroup.isEnabled());
            insert.setExtId(userGroup.getExtId());
            insert.setName(userGroup.getName());

            Long id = userGroupAdminService.insert(insert);
            return Response.status(Status.CREATED).tag(id.toString()).entity(id).build();

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }
    }

    @Override
    public void update(String name, RESTInputGroup group) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx {
        try {
            UserGroup old = userGroupAdminService.get(name);
            update(old.getId(), group);
        } catch (NotFoundServiceEx ex) {
            LOGGER.warn("UserGroup not found: " + name);
            throw new NotFoundRestEx("UserGroup not found: " + name);
        }
    }

    @Override
    public void update(Long id, RESTInputGroup group) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx {

        try {
            ShortGroup newGroup = new ShortGroup();
            newGroup.setId(id);

            if ( (group.getExtId() != null) ) {
                throw new BadRequestRestEx("ExtId can't be updated");
            }

            if ( (group.getName() != null) ) {
                throw new BadRequestRestEx("Name can't be updated");
            }

            if ( group.isEnabled() != null ) {
                newGroup.setEnabled(group.isEnabled());
            }

            userGroupAdminService.update(newGroup);

        } catch (GeoFenceRestEx ex) {
            // already handled
            throw ex;
        } catch (NotFoundServiceEx ex) {
            LOGGER.warn("Group not found id: " + id + ": " + ex.getMessage(), ex);
            throw new NotFoundRestEx(ex.getMessage());
        } catch (BadRequestServiceEx ex) {
            LOGGER.warn("Problems updating group id:" + id + ": " + ex.getMessage(), ex);
            throw new BadRequestRestEx(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }
    }

    @Override
    public Response delete(Long id, boolean cascade) throws ConflictRestEx, NotFoundRestEx, InternalErrorRestEx {
        try {
            if ( cascade ) {
                ruleAdminService.deleteRulesByGroup(id);
            } else {
                RuleFilter filter = new RuleFilter(SpecialFilterType.ANY);
                filter.setUserGroup(id);
                filter.getUser().setIncludeDefault(false);
                long cnt = ruleAdminService.count(filter);
                if ( cnt > 0 ) {
                    throw new ConflictRestEx("Existing rules reference the group " + id);
                }
            }

            if ( ! userGroupAdminService.delete(id)) {
                LOGGER.warn("Group not found: " + id);
                throw new NotFoundRestEx("Group not found: " + id);
            }

            return Response.status(Status.OK).entity("OK\n").build();

        } catch (GeoFenceRestEx ex) { // already handled
            throw ex;
        } catch (NotFoundServiceEx ex) {
            LOGGER.warn("Group not found: " + id);
            throw new NotFoundRestEx("Group not found: " + id);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }
    }

    @Override
    public Response delete(String name, boolean cascade) throws ConflictRestEx, NotFoundRestEx, InternalErrorRestEx {
        try {
            long id = userGroupAdminService.get(name).getId();
            return this.delete(id, cascade);
        } catch (NotFoundServiceEx ex) {
            LOGGER.warn("Group not found: " + name);
            throw new NotFoundRestEx("Group not found: " + name);
        } catch (GeoFenceRestEx ex) { // already handled
            throw ex;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }

    }

    // ==========================================================================
    // ==========================================================================
    
//    public void setUserGroupAdminService(UserGroupAdminService service) {
//        this.userGroupAdminService = service;
//    }

}
