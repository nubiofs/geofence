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

package it.geosolutions.geofence.services;

import it.geosolutions.geofence.core.model.GFUser;
import it.geosolutions.geofence.core.model.GSInstance;
import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.UserGroup;
import it.geosolutions.geofence.services.dto.RuleFilter.IdNameFilter;
import it.geosolutions.geofence.services.dto.ShortGroup;
import it.geosolutions.geofence.services.dto.ShortRule;
import it.geosolutions.geofence.services.dto.ShortUser;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;
import java.util.List;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class ServiceTestBase extends TestCase {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    protected static UserAdminService userAdminService;
    protected static GFUserAdminService gfUserAdminService;
    protected static UserGroupAdminService userGroupAdminService;
    protected static InstanceAdminService instanceAdminService;
    protected static RuleAdminService ruleAdminService;
    protected static RuleReaderService ruleReaderService;

    protected static ClassPathXmlApplicationContext ctx = null;

    public ServiceTestBase() {
//        LOGGER = Logger.getLogger(getClass());

        synchronized(ServiceTestBase.class) {
            if(ctx == null) {
                String[] paths = {
                        "classpath*:applicationContext.xml"
//                         ,"applicationContext-test.xml"
                };
                ctx = new ClassPathXmlApplicationContext(paths);

                userAdminService     = (UserAdminService)ctx.getBean("userAdminService");
                gfUserAdminService   = (GFUserAdminService)ctx.getBean("gfUserAdminService");
                userGroupAdminService  = (UserGroupAdminService)ctx.getBean("userGroupAdminService");
                instanceAdminService = (InstanceAdminService)ctx.getBean("instanceAdminService");
                ruleAdminService     = (RuleAdminService)ctx.getBean("ruleAdminService");
                ruleReaderService    = (RuleReaderService)ctx.getBean("ruleReaderService");
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        LOGGER.info("############################################ Running " + getClass().getSimpleName() + "::" + getName() );
        super.setUp();
        removeAll();
    }

    public void testCheckServices() {
        assertNotNull(userAdminService);
        assertNotNull(gfUserAdminService);
        assertNotNull(userGroupAdminService);
        assertNotNull(instanceAdminService);
        assertNotNull(ruleAdminService);
    }

    protected void removeAll() throws NotFoundServiceEx {
        LOGGER.info("***** removeAll()");
        removeAllRules();
        removeAllUsers();
        removeAllGRUsers();
        removeAllUserGroups();
        removeAllInstances();
    }

    protected void removeAllRules() throws NotFoundServiceEx {
        List<ShortRule> list = ruleAdminService.getAll();
        for (ShortRule item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = ruleAdminService.delete(item.getId());
            assertTrue("Rule not removed", ret);
        }

        assertEquals("Rules have not been properly deleted", 0, ruleAdminService.getCountAll());
    }

    protected void removeAllUsers() throws NotFoundServiceEx {
        List<ShortUser> list = userAdminService.getList(null,null,null);
        for (ShortUser item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = userAdminService.delete(item.getId());
            assertTrue("User not removed", ret);
        }

        assertEquals("Users have not been properly deleted", 0, userAdminService.getCount(null));
    }

    protected void removeAllGRUsers() throws NotFoundServiceEx {
        List<ShortUser> list = gfUserAdminService.getList(null,null,null);
        for (ShortUser item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = gfUserAdminService.delete(item.getId());
            assertTrue("User not removed", ret);
        }

        assertEquals("GRUsers have not been properly deleted", 0, gfUserAdminService.getCount(null));
    }

    protected void removeAllUserGroups() throws NotFoundServiceEx {
        List<ShortGroup> list = userGroupAdminService.getList(null,null,null);
        for (ShortGroup item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = userGroupAdminService.delete(item.getId());
            assertTrue("UserGroup not removed", ret);
        }

        assertEquals("UserGroups have not been properly deleted", 0, userGroupAdminService.getCount(null));
    }

    protected void removeAllInstances() throws NotFoundServiceEx {
        List<GSInstance> list = instanceAdminService.getAll();
        for (GSInstance item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = instanceAdminService.delete(item.getId());
            assertTrue("GSInstance not removed", ret);
        }

        assertEquals("Instances have not been properly deleted", 0, instanceAdminService.getCount(null));
    }

    protected GSUser createUser(String base, UserGroup group) {

        GSUser user = new GSUser();
        user.setName( base );
        user.getGroups().add(group);
        userAdminService.insert(user);
        return user;
    }

    protected GFUser createUser(String base) {

        GFUser user = new GFUser();
        user.setName( base );
        gfUserAdminService.insert(user);
        return user;
    }

    protected UserGroup createUserGroup(String base) {

        ShortGroup sgroup = new ShortGroup();
        sgroup.setName(base);
        long id = userGroupAdminService.insert(sgroup);
        try {
            return userGroupAdminService.get(id);
        } catch (NotFoundServiceEx ex) {
            throw new RuntimeException("Should never happen ("+id+")", ex);
        }
    }

    protected GSUser createUserAndGroup(String base) {

        UserGroup group = createUserGroup(base);
        return createUser(base, group);
    }

}
