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

package it.geosolutions.geofence.core.dao;

import it.geosolutions.geofence.core.model.UserGroup;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class UserGroupDAOTest extends BaseDAOTest {

    @Test
    public void testPersist() throws Exception {

        long id;
        {
            UserGroup group = createUserGroup(name.getMethodName());
            id = group.getId();
        }

        // test save & load
        {
            UserGroup loaded = userGroupDAO.find(id);
            assertNotNull("Can't retrieve userGroup", loaded);
            assertEquals(name.getMethodName(), loaded.getName());
        }

        userGroupDAO.removeById(id);
        assertNull("User not deleted", userGroupDAO.find(id));
    }

    @Test
    public void testMerge() throws Exception {

        long id;
        {
            UserGroup group = createUserGroup(name.getMethodName());
            id = group.getId();
        }

        {
            UserGroup loaded = userGroupDAO.find(id);
            assertNotNull("Can't retrieve userGroup", loaded);
            assertEquals(name.getMethodName(), loaded.getName());

            loaded.setName("aNewName");
            userGroupDAO.merge(loaded);
        }

        {
            UserGroup loaded = userGroupDAO.find(id);
            assertEquals("aNewName", loaded.getName());
        }

        userGroupDAO.removeById(id);
        assertNull("User not deleted", userGroupDAO.find(id));
    }



//    @Test
//    public void testSearchByProp() throws Exception {
//
//        final long id1;
//        {
//            UserGroup profile = createUserGroup("p1");
//            profile.getCustomProps().put("xid", "one");
//            userGroupDAO.merge(profile);
//            id1 = profile.getId();
//        }
//
//        {
//            UserGroup profile = createUserGroup("p2");
//            profile.getCustomProps().put("xid", "two");
//            userGroupDAO.merge(profile);
//        }
//        {
//            UserGroup profile = createUserGroup("p3");
//            profile.getCustomProps().put("xid", "two");
//            userGroupDAO.merge(profile);
//        }
//
//        {
//            // make sure props are in there
//            Map<String, String> customProps = userGroupDAO.getCustomProps(id1);
//            assertEquals("one", customProps.get("xid"));
//        }
//
//        // none of these will work
////        Search search = new Search(UserGroup.class);
////        search.addFilterEqual("customProps.xid", "one");
////        search.addFilterEqual("customProps.key", "xid");
////        search.addFilterEqual("customProps.index", "xid");
////        search.addFilterEqual("customProps.propkey", "xid");
////        search.addFilterSome("customProps", Filter.equal("index", "xid"));
////        search.addFilterSome("customProps", Filter.equal("key", "xid"));
////        search.addFilterSome("customProps", Filter.equal("propkey", "xid"));
////        search.addFilterEqual("propvalue", "xid");  // Could not find property 'propvalue' on class class it.geosolutions.geofence.core.model.UserGroup
////        search.addFilterSome("customProps", Filter.equal("xid", "one"));
////        search.addFilterEqual("index(customProps)", "xid");
//
//        {
//            List<UserGroup> f1 = userGroupDAO.searchByCustomProp("xid", "one");
//            assertEquals(1, f1.size());
//            assertEquals("p1", f1.get(0).getName());
//        }
//
//        {
//            List<UserGroup> f1 = userGroupDAO.searchByCustomProp("xid", "two");
//            assertEquals(2, f1.size());
//        }
//
////        List found = userGroupDAO.search(search);
////        LOGGER.info("Found " + found);
////        assertEquals(1, found.size());
//
//    }

}