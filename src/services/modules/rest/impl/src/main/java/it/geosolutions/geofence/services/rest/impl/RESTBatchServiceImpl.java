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

import it.geosolutions.geofence.services.rest.RESTBatchService;
import it.geosolutions.geofence.services.rest.RESTGSInstanceService;
import it.geosolutions.geofence.services.rest.RESTRuleService;
import it.geosolutions.geofence.services.rest.RESTUserGroupService;
import it.geosolutions.geofence.services.rest.RESTUserService;
import it.geosolutions.geofence.services.rest.exception.BadRequestRestEx;
import it.geosolutions.geofence.services.rest.exception.ConflictRestEx;
import it.geosolutions.geofence.services.rest.exception.GeoFenceRestEx;
import it.geosolutions.geofence.services.rest.exception.InternalErrorRestEx;
import it.geosolutions.geofence.services.rest.exception.NotFoundRestEx;
import it.geosolutions.geofence.services.rest.model.RESTBatch;
import it.geosolutions.geofence.services.rest.model.RESTBatchOperation;
import it.geosolutions.geofence.services.rest.model.RESTInputGroup;
import it.geosolutions.geofence.services.rest.model.RESTInputInstance;
import it.geosolutions.geofence.services.rest.model.RESTInputRule;
import it.geosolutions.geofence.services.rest.model.RESTInputUser;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RESTBatchServiceImpl
        extends BaseRESTServiceImpl
        implements InitializingBean, RESTBatchService
//        implements RESTUserGroupService
{
    private static final Logger LOGGER = Logger.getLogger(RESTBatchServiceImpl.class);

    private final static String OP_INSERT = "insert";
    private final static String OP_UPDATE = "update";
    private final static String OP_DELETE = "delete";

    private final static String OP_ADDGROUP = "addGroup";
    private final static String OP_DELGROUP = "delGroup";


    private RESTUserService restUserService;
    private RESTUserGroupService restUserGroupService;
    private RESTGSInstanceService restInstanceService;
    private RESTRuleService restRuleService;


    @Transactional(value="geofenceTransactionManager")
    @Override
    public Response exec(RESTBatch batch) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx {
        if(LOGGER.isInfoEnabled() )
            LOGGER.info("Running batch with " + batch.getList().size() + " operations");

        for (RESTBatchOperation op : batch.getList()) {
            if(LOGGER.isInfoEnabled() )
                LOGGER.info("Running " + op);

            try {
                switch(op.getService()) {
                    case users:
                        dispatchUserOp(op);
                        break;

                    case groups:
                        dispatchGroupOp(op);
                        break;

                    case instances:
                        dispatchInstanceOp(op);
                        break;

                    case rules:
                        dispatchRuleOp(op);
                        break;

                    default:
                        throw new BadRequestRestEx("Unhandled service for operation " + op);
                }
            } catch(GeoFenceRestEx ex) {
                throw ex;
            } catch(Exception ex) {
                LOGGER.error("Unexpected error: " + ex.getMessage(), ex);
                throw new InternalErrorRestEx("Unexpected exception: " + ex.getMessage());
            }
        }

       return Response.status(Status.OK).entity("OK\n").build();
    }

    protected void dispatchRuleOp(RESTBatchOperation op) throws NotFoundRestEx, BadRequestRestEx {
        if(OP_INSERT.equals( op.getType()) ) {
            ensurePayload(op);
            restRuleService.insert((RESTInputRule)op.getPayload());
        } else if(OP_UPDATE.equals(op.getType()) ) {
            ensurePayload(op);
            if(op.getId() != null)
                restRuleService.update(op.getId(), (RESTInputRule)op.getPayload());
            else
                throw new BadRequestRestEx("Missing identifier for op " + op);
        } else if(OP_DELETE.equals(op.getType() )) {
            if(op.getId() != null)
                restRuleService.delete(op.getId());
            else
                throw new BadRequestRestEx("Missing identifier for op " + op);
        } else {
            throw new BadRequestRestEx("Operation not bound " + op);
        }
    }

    protected void dispatchInstanceOp(RESTBatchOperation op) throws NotFoundRestEx, InternalErrorRestEx, ConflictRestEx, BadRequestRestEx {
        if(OP_INSERT.equals( op.getType()) ) {
            ensurePayload(op);
            restInstanceService.insert((RESTInputInstance)op.getPayload());
        } else if(OP_UPDATE.equals(op.getType()) ) {
            ensurePayload(op);
            if(op.getId() != null)
                restInstanceService.update(op.getId(), (RESTInputInstance)op.getPayload());
            else if(op.getName() != null)
                restInstanceService.update(op.getName(), (RESTInputInstance)op.getPayload());
            else
                throw new BadRequestRestEx("Missing identifier for op " + op);
        } else if(OP_DELETE.equals(op.getType() )) {
            boolean cascade = op.getCascade()==null? false: op.getCascade().booleanValue();
            if(op.getId() != null)
                restInstanceService.delete(op.getId(), cascade);
            else if(op.getName() != null)
                restInstanceService.delete(op.getName(), cascade);
            else
                throw new BadRequestRestEx("Missing identifier for op " + op);
        } else {
            throw new BadRequestRestEx("Operation not bound " + op);
        }
    }

    protected void dispatchGroupOp(RESTBatchOperation op) throws BadRequestRestEx, NotFoundRestEx, ConflictRestEx, InternalErrorRestEx {
        if(OP_INSERT.equals( op.getType()) ) {
            ensurePayload(op);
            restUserGroupService.insert((RESTInputGroup)op.getPayload());
        } else if(OP_UPDATE.equals(op.getType()) ) {
            ensurePayload(op);
            if(op.getId() != null)
                restUserGroupService.update(op.getId(), (RESTInputGroup)op.getPayload());
            else if(op.getName() != null)
                restUserGroupService.update(op.getName(), (RESTInputGroup)op.getPayload());
            else
                throw new BadRequestRestEx("Missing identifier for op " + op);
        } else if(OP_DELETE.equals(op.getType() )) {
            boolean cascade = op.getCascade()==null? false: op.getCascade().booleanValue();
            if(op.getId() != null)
                restUserGroupService.delete(op.getId(), cascade);
            else if(op.getName() != null)
                restUserGroupService.delete(op.getName(), cascade);
            else
                throw new BadRequestRestEx("Missing identifier for op " + op);
        } else {
            throw new BadRequestRestEx("Operation not bound " + op);
        }
    }

    protected void dispatchUserOp(RESTBatchOperation op) throws NotFoundRestEx, BadRequestRestEx, InternalErrorRestEx, ConflictRestEx, UnsupportedOperationException {
        if(OP_INSERT.equals( op.getType()) ) {
            ensurePayload(op);
            restUserService.insert((RESTInputUser)op.getPayload());
        } else if(OP_UPDATE.equals(op.getType()) ) {
            ensurePayload(op);
            if(op.getId() != null)
                restUserService.update(op.getId(), (RESTInputUser)op.getPayload());
            else if(op.getName() != null)
                restUserService.update(op.getName(), (RESTInputUser)op.getPayload());
            else
                throw new BadRequestRestEx("Missing identifier for op " + op);
        } else if(OP_DELETE.equals(op.getType() )) {
            boolean cascade = op.getCascade()==null? false: op.getCascade().booleanValue();
            if(op.getId() != null)
                restUserService.delete(op.getId(), cascade);
            else if(op.getName() != null)
                restUserService.delete(op.getName(), cascade);
            else
                throw new BadRequestRestEx("Missing identifier for op " + op);
        } else if(OP_ADDGROUP.equals(op.getType()) ) {
            throw new UnsupportedOperationException("Not implemented yet");
        } else if(OP_DELGROUP.equals(op.getType()) ) {
            throw new UnsupportedOperationException("Not implemented yet");
        } else {
            throw new BadRequestRestEx("Operation not bound " + op);
        }
    }

    // ==========================================================================

    private void ensurePayload(RESTBatchOperation op) throws BadRequestRestEx {
        if(op.getPayload() == null)
            throw new BadRequestRestEx("Empty payload in operation " + op);
    }

    // ==========================================================================
    // ==========================================================================
    
//    public void setUserGroupAdminService(UserGroupAdminService service) {
//        this.userGroupAdminService = service;
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    // ==========================================================================

    public void setRestInstanceService(RESTGSInstanceService restInstanceService) {
        this.restInstanceService = restInstanceService;
    }

    public void setRestRuleService(RESTRuleService restRuleService) {
        this.restRuleService = restRuleService;
    }

    public void setRestUserGroupService(RESTUserGroupService restUserGroupService) {
        this.restUserGroupService = restUserGroupService;
    }

    public void setRestUserService(RESTUserService restUserService) {
        this.restUserService = restUserService;
    }

}
