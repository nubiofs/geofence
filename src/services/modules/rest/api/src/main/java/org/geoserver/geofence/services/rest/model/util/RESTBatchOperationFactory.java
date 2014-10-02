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
package it.geosolutions.geofence.services.rest.model.util;

import it.geosolutions.geofence.services.rest.model.RESTBatchOperation;
import it.geosolutions.geofence.services.rest.model.RESTInputGroup;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RESTBatchOperationFactory {

    public static RESTBatchOperation createUserInputOp() {
        RESTBatchOperation op = new RESTBatchOperation();
        op.setService(RESTBatchOperation.ServiceName.users);
        op.setType(RESTBatchOperation.TypeName.insert);
        return op;
    }

    public static RESTBatchOperation createInstanceInputOp() {
        RESTBatchOperation op = new RESTBatchOperation();
        op.setService(RESTBatchOperation.ServiceName.instances);
        op.setType(RESTBatchOperation.TypeName.insert);
        return op;
    }

    public static RESTBatchOperation createRuleInputOp() {
        RESTBatchOperation op = new RESTBatchOperation();
        op.setService(RESTBatchOperation.ServiceName.rules);
        op.setType(RESTBatchOperation.TypeName.insert);
        return op;
    }

    public static RESTBatchOperation createUserUpdateOp(String username) {
        RESTBatchOperation op = new RESTBatchOperation();
        op.setService(RESTBatchOperation.ServiceName.users);
        op.setType(RESTBatchOperation.TypeName.update);
        op.setName(username);
        return op;
    }

    public static RESTBatchOperation createUserUpdateOp(Long userId) {
        RESTBatchOperation op = new RESTBatchOperation();
        op.setService(RESTBatchOperation.ServiceName.users);
        op.setType(RESTBatchOperation.TypeName.update);
        op.setId(userId);
        return op;
    }

    public static RESTBatchOperation createGroupInputOp(String name) {
        RESTInputGroup group = new RESTInputGroup();
        group.setEnabled(Boolean.TRUE);
        group.setName(name);

        RESTBatchOperation op = new RESTBatchOperation();
        op.setService(RESTBatchOperation.ServiceName.groups);
        op.setType(RESTBatchOperation.TypeName.insert);
        op.setPayload(group);
        
        return op;
    }

    public static RESTBatchOperation createDeleteRuleOp(Long ruleId) {
        RESTBatchOperation op = new RESTBatchOperation();
        op.setService(RESTBatchOperation.ServiceName.rules);
        op.setType(RESTBatchOperation.TypeName.delete);
        op.setId(ruleId);
        return op;
    }

}
