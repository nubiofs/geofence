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

import it.geosolutions.geofence.services.dto.ShortUser;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.geosolutions.geofence.core.model.GFUser;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GFUserAdminServiceImplTest extends ServiceTestBase {

    public GFUserAdminServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testInsertDeleteUser() throws NotFoundServiceEx {

        GFUser user = createGFUser(getName());
        gfUserAdminService.get(user.getId()); // will throw if not found
        assertTrue("Could not delete user", gfUserAdminService.delete(user.getId()));
    }

    @Test
    public void testUpdateUser() throws Exception {
        GFUser user = createGFUser("u1");

        final String NEWNAME = "NEWNAME";

        {
            GFUser loaded = gfUserAdminService.get(user.getId());
            assertNotNull(loaded);

            assertEquals("u1", loaded.getName());

            loaded.setName(NEWNAME);

            gfUserAdminService.update(loaded);
        }
        {
            GFUser loaded = gfUserAdminService.get(user.getId());
            assertNotNull(loaded);

            assertEquals(NEWNAME, loaded.getName());
        }
    }

    @Test
    public void testGetAllUsers() {
        assertEquals(0, gfUserAdminService.getList(null,null,null).size());

        createGFUser("u1");
        createGFUser("u2");
        createGFUser("u3");

        assertEquals(3, gfUserAdminService.getList(null,null,null).size());
    }

    @Test
    public void testGetUsersCount() {
        assertEquals(0, gfUserAdminService.getCount(null));

        createGFUser("u10");
        createGFUser("u20");
        createGFUser("u30");
        GFUser u99 = createGFUser("u99");

        assertEquals(4, gfUserAdminService.getCount(null));
        assertEquals(4, gfUserAdminService.getCount("u%"));
        assertEquals(3, gfUserAdminService.getCount("%0"));

        List<ShortUser> users = gfUserAdminService.getList("%9",null,null);
        assertEquals(1, users.size());
        assertEquals("u99", users.get(0).getName());
        assertEquals((Long)u99.getId(), (Long)users.get(0).getId());
    }

}
