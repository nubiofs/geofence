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

import it.geosolutions.geofence.services.rest.exception.ConflictRestEx;
import it.geosolutions.geofence.services.rest.model.RESTInputGroup;
import it.geosolutions.geofence.services.rest.model.RESTInputUser;
import it.geosolutions.geofence.services.rest.model.RESTOutputUser;
import it.geosolutions.geofence.services.rest.model.util.IdName;
import java.util.ArrayList;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RESTUserGroupServiceImplTest extends RESTBaseTest {
    private static final Logger LOGGER = LogManager.getLogger(RESTUserGroupServiceImplTest.class);

    @Test
    public void testInsert() {
        RESTInputGroup group = new RESTInputGroup();
        group.setName("g1");
        Response res = restUserGroupService.insert(group);
        long gid1 = (Long)res.getEntity();

        RESTInputUser user = new RESTInputUser();
        user.setName("user0");
        user.setEnabled(Boolean.TRUE);
        user.setGroups(new ArrayList<IdName>());
        user.getGroups().add(new IdName("g1"));

        Response userResp = restUserService.insert(user);
        Long id = (Long)userResp.getEntity();

        {
            RESTOutputUser out = restUserService.get(id);
            assertNotNull(out);
            assertEquals("user0", out.getName());            
        }

    }

    @Test
    public void testInsertDup() {

        {
            RESTInputGroup group1 = new RESTInputGroup();
            group1.setName("g1");
            restUserGroupService.insert(group1);
        }

        LOGGER.info("Inserting dup");
        try {
            RESTInputGroup group2 = new RESTInputGroup();
            group2.setName("g1");
            restUserGroupService.insert(group2);
            fail("409 not trapped");
        } catch(ConflictRestEx e) {
            LOGGER.info("Exception properly trapped");
        }
    }
}
