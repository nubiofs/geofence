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

import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.Profile;
import it.geosolutions.geofence.services.dto.ShortUser;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.WKTReader;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class UserAdminServiceImplTest extends ServiceTestBase {

    public UserAdminServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testInsertDeleteUser() throws NotFoundServiceEx {

        Profile profile = createProfile(getName());
        GSUser user = createUser(getName(), profile);
        userAdminService.get(user.getId()); // will throw if not found
        assertTrue("Could not delete user", userAdminService.delete(user.getId()));
    }

    @Test
    public void testUpdateUser() throws Exception {
        Profile p1 = createProfile("p1");
        GSUser user = createUser("u1", p1);

        Profile p2 = createProfile("p2");
        final String NEWNAME = "NEWNAME";

        {
            GSUser loaded = userAdminService.get(user.getId());
            assertNotNull(loaded);

            assertEquals("u1", loaded.getName());
            assertEquals("p1", loaded.getProfile().getName());

            loaded.setProfile(p2);
            loaded.setName(NEWNAME);

            WKTReader wktReader = new WKTReader();
            String allowedArea = "MULTIPOLYGON (((414699.55 130160.43, 414701.83 130149.9, 414729.2 130155.7, 414729.2 130155.7, 414733.25 130149.8, 414735.1 130140.9, 414743.75 130142.7, 414740.6 130158.15, 414742.15 130158.5, 414739.65 130169.25, 414728.05 130166.65, 414727.77 130167.93, 414724.52 130167.19, 414717.65 130165.63, 414717.85 130164.45, 414699.55 130160.43)))";
            MultiPolygon the_geom = (MultiPolygon) wktReader.read(allowedArea);
            the_geom.setSRID(4326);

            loaded.setAllowedArea(the_geom);
            userAdminService.update(loaded);
        }
        {
            GSUser loaded = userAdminService.get(user.getId());
            assertNotNull(loaded);

            assertEquals(NEWNAME, loaded.getName());
            assertEquals("p2", loaded.getProfile().getName());

            assertNotNull(loaded.getAllowedArea());
            assertEquals(4326, loaded.getAllowedArea().getSRID());
        }
    }

    @Test
    public void testGetAllUsers() {
        assertEquals(0, userAdminService.getList(null,null,null).size());

        createUserAndProfile("u1");
        createUserAndProfile("u2");
        createUserAndProfile("u3");

        assertEquals(3, userAdminService.getList(null,null,null).size());
    }

    @Test
    public void testGetUsersCount() {
        assertEquals(0, userAdminService.getCount(null));

        createUserAndProfile("u10");
        createUserAndProfile("u20");
        createUserAndProfile("u30");
        GSUser u99 = createUserAndProfile("u99");

        assertEquals(4, userAdminService.getCount(null));
        assertEquals(4, userAdminService.getCount("u%"));
        assertEquals(3, userAdminService.getCount("%0"));

        List<ShortUser> users = userAdminService.getList("%9",null,null);
        assertEquals(1, users.size());
        assertEquals("u99", users.get(0).getName());
        assertEquals((Long)u99.getId(), (Long)users.get(0).getId());
    }

}
