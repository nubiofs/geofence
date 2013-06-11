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
package it.geosolutions.csv2geofence.impl;

import it.geosolutions.csv2geofence.config.model.internal.UserOp;
import it.geosolutions.geofence.services.rest.model.RESTBatchOperation;
import it.geosolutions.geofence.services.rest.model.RESTInputUser;
import it.geosolutions.geofence.services.rest.model.util.IdName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Transforms UserOps into RESTBatchoperations
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class UsersProcessor {

    private final static Logger LOGGER = LogManager.getLogger(UsersProcessor.class);

    public List<RESTBatchOperation> buildUserBatchOps(List<UserOp> ops, Map<String, String> availableGroups) {
        List<RESTBatchOperation> ret = new ArrayList<RESTBatchOperation>(ops.size());

        for (UserOp userOp : ops) {
            LOGGER.debug("Preparing for output " + userOp);
            RESTBatchOperation restOp = buildBatchOperation(userOp, availableGroups);
            ret.add(restOp);
        }

        return ret;
    }

    protected RESTBatchOperation buildBatchOperation(UserOp userOp, Map<String, String> availableGroups) {

        RESTBatchOperation restOp = new RESTBatchOperation();
        restOp.setService(RESTBatchOperation.ServiceName.users);

        switch(userOp.getType()) {
            case INSERT:
                restOp.setType(RESTBatchOperation.TypeName.insert);

                // inserting a user: we need all the fields
                RESTInputUser restUser = new RESTInputUser();
                restUser.setEnabled(true);
                restUser.setEmailAddress(userOp.getMailAddress());
                restUser.setFullName(userOp.getFullName());
                restUser.setName(userOp.getUserName());
                restUser.setGroups(buildGroupList(userOp, availableGroups));

                restOp.setPayload(restUser);

                break;

            case UPDATE:
                restOp.setType(RESTBatchOperation.TypeName.update);

                // when updating, we're only reassigning groups
                restOp.setName(userOp.getUserName()); // set the key

                RESTInputUser restUpdateUser = new RESTInputUser();
                restUpdateUser.setGroups(buildGroupList(userOp, availableGroups)); // and the groups

                restOp.setPayload(restUpdateUser);

                break;

            case DELETE:
                restOp.setType(RESTBatchOperation.TypeName.delete);
                restOp.setName(userOp.getUserName());

                break;

            default:
                LOGGER.error("Unexpected operation type '"+userOp.getType()+"' for operation " + userOp);
                throw new IllegalStateException("Unexpected operation type '"+userOp.getType()+"' for operation " + userOp);                
        }

        return restOp;
    }

    protected List<IdName> buildGroupList(UserOp userOp, Map<String, String> availableGroups) {
        List<IdName> ret = new ArrayList<IdName>(userOp.getGroups().size());
        for (String groupName : userOp.getGroups()) {
            String groupRealName = availableGroups.get(groupName.toUpperCase());
            if(groupRealName == null)
                throw new IllegalStateException("Can't find group name '"+groupName+"' for " + userOp);

            ret.add(new IdName(groupRealName));
        }
        return ret;
    }


}
