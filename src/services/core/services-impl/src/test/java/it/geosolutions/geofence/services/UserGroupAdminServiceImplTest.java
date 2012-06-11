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

package it.geosolutions.geofence.services;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.geosolutions.geofence.core.model.UserGroup;
import it.geosolutions.geofence.services.dto.ShortGroup;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class UserGroupAdminServiceImplTest extends ServiceTestBase {

    public UserGroupAdminServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testInsertDelete() throws NotFoundServiceEx {

        UserGroup p = createUserGroup(getName());
        userGroupAdminService.get(p.getId()); // will throw if not found
        assertTrue("Could not delete group", userGroupAdminService.delete(p.getId()));
    }

    @Test
    public void testUpdate() throws Exception {
        removeAllUserGroups();

        UserGroup group = createUserGroup("u1");

        final String NEWNAME = "NEWNAME";

        {
            UserGroup loaded = userGroupAdminService.get(group.getId());
            assertNotNull(loaded);

            assertEquals("u1", loaded.getName());

            loaded.setName(NEWNAME);

            ShortGroup sg = new ShortGroup(loaded);
            userGroupAdminService.update(sg);
        }
        {
            UserGroup loaded = userGroupAdminService.get(group.getId());
            assertNotNull(loaded);

            assertEquals(NEWNAME, loaded.getName());
        }
    }

    @Test
    public void testGetAll() {
        assertEquals(0, userGroupAdminService.getList(null,null,null).size());

        createUserGroup("u1");
        createUserGroup("u2");
        createUserGroup("u3");

        assertEquals(3, userGroupAdminService.getList(null,null,null).size());
    }

    @Test
    public void testGetCount() {
        assertEquals(0, userGroupAdminService.getCount(null));

        createUserGroup("u10");
        createUserGroup("u20");
        createUserGroup("u30");
        UserGroup u99 = createUserGroup("u99");

        assertEquals(4, userGroupAdminService.getCount(null));
        assertEquals(4, userGroupAdminService.getCount("u%"));
        assertEquals(3, userGroupAdminService.getCount("%0"));

        List<ShortGroup> glist = userGroupAdminService.getList("%9",null,null);
        assertEquals(1, glist.size());
        assertEquals("u99", glist.get(0).getName());
        assertEquals((Long)u99.getId(), (Long)glist.get(0).getId());
    }

//    @Test
//    public void testGetProps() {
//
//        assertEquals(0, userGroupAdminService.getCount(null));
//
//        UserGroup p = createUserGroup(getName());
//        Map<String,String> props = new HashMap<String, String>();
//        props.put("k1", "v1");
//        props.put("k2", "v2");
//        userGroupAdminService.setCustomProps(p.getId(), props);
//
//        assertEquals(1, userGroupAdminService.getCount(null));
//        assertEquals(1, userGroupAdminService.getList(null, null, null).size());
//        assertEquals(1, userGroupAdminService.getFullList(null, null, null).size());
//    }

}
