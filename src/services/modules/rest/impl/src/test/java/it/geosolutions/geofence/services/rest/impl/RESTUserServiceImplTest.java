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

import it.geosolutions.geofence.core.model.UserGroup;
import it.geosolutions.geofence.services.rest.RESTUserGroupService;
import it.geosolutions.geofence.services.rest.RESTUserService;
import it.geosolutions.geofence.services.rest.exception.ConflictRestEx;
import it.geosolutions.geofence.services.rest.model.RESTInputGroup;
import it.geosolutions.geofence.services.rest.model.RESTInputUser;
import it.geosolutions.geofence.services.rest.model.RESTOutputUser;
import it.geosolutions.geofence.services.rest.model.RESTShortUser;
import it.geosolutions.geofence.services.rest.model.RESTShortUserList;
import it.geosolutions.geofence.services.rest.model.config.RESTFullUserGroupList;
import it.geosolutions.geofence.services.rest.model.util.IdName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RESTUserServiceImplTest {
    private static final Logger LOGGER = Logger.getLogger(RESTUserServiceImplTest.class);


    protected static ClassPathXmlApplicationContext ctx = null;

    protected static RESTUserService restUserService;
    protected static RESTUserGroupService restUserGroupService;

    public RESTUserServiceImplTest() {

        synchronized(RESTUserServiceImplTest.class) {
            if(ctx == null) {
                String[] paths = {
                        "classpath*:applicationContext.xml"
//                         ,"applicationContext-test.xml"
                };
                ctx = new ClassPathXmlApplicationContext(paths);


                for(String name : ctx.getBeanDefinitionNames()) {
                    if(name.startsWith("rest") )
                        LOGGER.warn("  BEAN ===> " + name);                    
                }

                restUserService       = (RESTUserService)ctx.getBean("restUserService");
                restUserGroupService  = (RESTUserGroupService)ctx.getBean("restUserGroupService");
            }
            
            assertNotNull(restUserService);
            assertNotNull(restUserGroupService);
        }
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void before() throws Exception {

//        assertNotNull(restUserService);
//        assertNotNull(restUserGroupService);

        RESTShortUserList users = restUserService.getUsers(null, null, null);
        for (RESTShortUser user : users) {
            LOGGER.warn("Removing " + user);
            restUserService.delete(user.getId(), true);            
        }
        RESTFullUserGroupList userGroups = restUserGroupService.getUserGroups(null, null, null);
        for (UserGroup group : userGroups) {
            LOGGER.warn("Removing " + group);
            restUserGroupService.delete(group.getId());
        }

    }

    @Test
    public void testInsert() {
        RESTInputGroup group = new RESTInputGroup();
        group.setName("g1");
        long gid1 = restUserGroupService.insert(group);


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
        RESTInputGroup group = new RESTInputGroup();
        group.setName("g1");
        long gid1 = restUserGroupService.insert(group);

        {
            RESTInputUser user = new RESTInputUser();
            user.setName("user0");
            user.setEnabled(Boolean.TRUE);
            user.setGroups(new ArrayList<IdName>());
            user.getGroups().add(new IdName("g1"));

            Response userResp = restUserService.insert(user);
            Long id = (Long)userResp.getEntity();
        }

        LOGGER.info("Inserting dup");
        {
            RESTInputUser user = new RESTInputUser();
            user.setName("user0");
            user.setEnabled(Boolean.TRUE);
            user.setGroups(new ArrayList<IdName>());
            user.getGroups().add(new IdName("g1"));

            try {
                Response userResp = restUserService.insert(user);
                Long id = (Long)userResp.getEntity();
                fail("409 not trapped");
            } catch(ConflictRestEx e) {
                LOGGER.info("Exception properly trapped");
            }
        }
    }

    @Test
    public void testUpdateGroup() {
        {
            for(String name: Arrays.asList("g1","g2","g3","g4")) {
                RESTInputGroup group1 = new RESTInputGroup();
                group1.setName(name);
                long gid1 = restUserGroupService.insert(group1);
                LOGGER.info("Created group id:"+gid1 + " name:"+name);
            }
        }

        Long uid;


        { // insert user
            RESTInputUser user = new RESTInputUser();
            user.setName("user0");
            user.setEnabled(Boolean.TRUE);
            user.setGroups(new ArrayList<IdName>());
            user.getGroups().add(new IdName("g1"));

            Response userResp = restUserService.insert(user);
            uid = (Long)userResp.getEntity();
        }

        { // check user
            RESTOutputUser out = restUserService.get(uid);
            assertNotNull(out);
            assertEquals("user0", out.getName());
            assertTrue(out.isEnabled());
            assertEquals(1, out.getGroups().size());
            assertEquals("g1", out.getGroups().get(0).getName());
        }

        { // update 1: no group change
            RESTInputUser user = new RESTInputUser();
            user.setEnabled(false);
            restUserService.update(uid, user);
        }
        { // check user
            RESTOutputUser out = restUserService.get(uid);
            assertNotNull(out);
            assertFalse(out.isEnabled());
            assertEquals("user0", out.getName());
            assertEquals(1, out.getGroups().size());
            assertEquals("g1", out.getGroups().get(0).getName());
        }

        { // update 2: groups
            RESTInputUser user = new RESTInputUser();
            user.setGroups(new ArrayList<IdName>());
            user.getGroups().add(new IdName("g2"));
            restUserService.update(uid, user);
        }
        { // check user
            RESTOutputUser out = restUserService.get(uid);
            assertNotNull(out);
            assertEquals("user0", out.getName());
            assertEquals(1, out.getGroups().size());
            assertEquals("g2", out.getGroups().get(0).getName());
        }

        { // update 3: groups
            RESTInputUser user = new RESTInputUser();
            user.setGroups(new ArrayList<IdName>());
            user.getGroups().add(new IdName("g3"));
            user.getGroups().add(new IdName("g4"));
            restUserService.update(uid, user);
        }
        { // check user
            RESTOutputUser out = restUserService.get(uid);
            assertNotNull(out);
            assertEquals("user0", out.getName());
            assertEquals(2, out.getGroups().size());
            Set<String> set = new HashSet<String>();
            for (IdName idName : out.getGroups()) {
                set.add(idName.getName());
            }
            assertTrue(set.contains("g3"));
            assertTrue(set.contains("g4"));
        }


    }

}
