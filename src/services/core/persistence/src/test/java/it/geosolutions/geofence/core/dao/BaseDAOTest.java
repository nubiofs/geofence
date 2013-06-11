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

package it.geosolutions.geofence.core.dao;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import it.geosolutions.geofence.core.model.GFUser;
import it.geosolutions.geofence.core.model.UserGroup;
import java.util.List;

import junit.framework.TestCase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.Rule;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public abstract class BaseDAOTest extends TestCase {
    protected final Logger LOGGER;

    protected static GSUserDAO userDAO;
    protected static GFUserDAO gfUserDAO;
    protected static UserGroupDAO userGroupDAO;
    protected static RuleDAO ruleDAO;
    protected static LayerDetailsDAO detailsDAO;
    protected static RuleLimitsDAO limitsDAO;

    protected static ClassPathXmlApplicationContext ctx = null;

    public BaseDAOTest() {
        LOGGER = LogManager.getLogger(getClass());

        synchronized(BaseDAOTest.class) {
            if(ctx == null) {
                String[] paths = {
                        "applicationContext.xml"
//                         ,"applicationContext-test.xml"
                };
                ctx = new ClassPathXmlApplicationContext(paths);

                userDAO = (GSUserDAO)ctx.getBean("gsUserDAO");
                gfUserDAO = (GFUserDAO)ctx.getBean("gfUserDAO");
                userGroupDAO = (UserGroupDAO)ctx.getBean("userGroupDAO");
                ruleDAO = (RuleDAO)ctx.getBean("ruleDAO");
                detailsDAO = (LayerDetailsDAO)ctx.getBean("layerDetailsDAO");
                limitsDAO = (RuleLimitsDAO)ctx.getBean("ruleLimitsDAO");
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        LOGGER.info("################ Running " + getClass().getSimpleName() + "::" + getName() );
        super.setUp();

        removeAll();
        LOGGER.info("##### Ending setup for " + getName() + " ###----------------------");
    }

    @Test
    public void testCheckDAOs() {

        assertNotNull(userDAO);
        assertNotNull(gfUserDAO);
        assertNotNull(userGroupDAO);
        assertNotNull(ruleDAO);
        assertNotNull(detailsDAO);
    }

    protected void removeAll() {
        removeAllRules();
        removeAllUsers(); 
        removeAllUserGroups();
    }

    protected void removeAllUsers() {
        List<GSUser> list = userDAO.findAll();
        for (GSUser item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = userDAO.remove(item);
            assertTrue("User not removed", ret);
        }

        assertEquals("Users have not been properly deleted", 0, userDAO.count(null));
    }

    protected void removeAllGRUsers() {
        List<GFUser> list = gfUserDAO.findAll();
        for (GFUser item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = gfUserDAO.remove(item);
            assertTrue("User not removed", ret);
        }

        assertEquals("GRUsers have not been properly deleted", 0, gfUserDAO.count(null));
    }

    protected void removeAllRules() {
        List<Rule> list = ruleDAO.findAll();
        for (Rule item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = ruleDAO.remove(item);
            assertTrue("Rule not removed", ret);
        }

        assertEquals("Rules have not been properly deleted", 0, ruleDAO.count(null));
    }

    protected void removeAllUserGroups() {
        List<UserGroup> list = userGroupDAO.findAll();
        for (UserGroup item : list) {
            LOGGER.info("Removing " + item);
            boolean ret = userGroupDAO.remove(item);
            assertTrue("UserGroup not removed", ret);
        }

        assertEquals("UserGroups have not been properly deleted", 0, userGroupDAO.count(null));
    }

    protected GSUser createUser(String base, UserGroup userGroup) {

        GSUser user = new GSUser();
        user.setName( base );
        user.getGroups().add(userGroup);
        return user;
    }

    protected UserGroup createUserGroup(String base) {

        UserGroup group = new UserGroup();
        group.setName(base);
        userGroupDAO.persist(group);
        return group;
    }

    protected GSUser createUserAndGroup(String base) {

        UserGroup group = createUserGroup(base);
        return createUser(base, group);
    }


    protected final static String MULTIPOLYGONWKT = "MULTIPOLYGON(((48.6894038 62.33877482, 48.7014874 62.33877482, 48.7014874 62.33968662, 48.6894038 62.33968662, 48.6894038 62.33877482)))";
    protected final static String POLYGONWKT = "POLYGON((48.6894038 62.33877482, 48.7014874 62.33877482, 48.7014874 62.33968662, 48.6894038 62.33968662, 48.6894038 62.33877482))";

    protected MultiPolygon buildMultiPolygon() {
        try {
            WKTReader reader = new WKTReader();
            MultiPolygon mp = (MultiPolygon) reader.read(MULTIPOLYGONWKT);
            mp.setSRID(4326);
            return mp;
        } catch (ParseException ex) {
            throw new RuntimeException("Unexpected exception: " + ex.getMessage(), ex);
        }
    }

    protected Polygon buildPolygon() {
        try {
            WKTReader reader = new WKTReader();
            Polygon mp = (Polygon) reader.read(POLYGONWKT);
            mp.setSRID(4326);
            return mp;
        } catch (ParseException ex) {
            throw new RuntimeException("Unexpected exception: " + ex.getMessage(), ex);
        }
    }

    //==========================================================================

}
