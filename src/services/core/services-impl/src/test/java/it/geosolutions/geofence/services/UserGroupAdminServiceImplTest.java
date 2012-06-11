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

import it.geosolutions.geofence.core.model.Profile;
import it.geosolutions.geofence.services.dto.ShortProfile;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class ProfileAdminServiceImplTest extends ServiceTestBase {

    public ProfileAdminServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testInsertDelete() throws NotFoundServiceEx {

        Profile p = createProfile(getName());
        profileAdminService.get(p.getId()); // will throw if not found
        assertTrue("Could not delete profile", profileAdminService.delete(p.getId()));
    }

    @Test
    public void testUpdate() throws Exception {
        removeAllProfiles();

        Profile profile = createProfile("u1");

        final String NEWNAME = "NEWNAME";

        {
            Profile loaded = profileAdminService.get(profile.getId());
            assertNotNull(loaded);

            assertEquals("u1", loaded.getName());

            loaded.setName(NEWNAME);

            ShortProfile sp = new ShortProfile(loaded);
            profileAdminService.update(sp);
        }
        {
            Profile loaded = profileAdminService.get(profile.getId());
            assertNotNull(loaded);

            assertEquals(NEWNAME, loaded.getName());
        }
    }

    @Test
    public void testGetAll() {
        assertEquals(0, profileAdminService.getList(null,null,null).size());

        createProfile("u1");
        createProfile("u2");
        createProfile("u3");

        assertEquals(3, profileAdminService.getList(null,null,null).size());
    }

    @Test
    public void testGetCount() {
        assertEquals(0, profileAdminService.getCount(null));

        createProfile("u10");
        createProfile("u20");
        createProfile("u30");
        Profile u99 = createProfile("u99");

        assertEquals(4, profileAdminService.getCount(null));
        assertEquals(4, profileAdminService.getCount("u%"));
        assertEquals(3, profileAdminService.getCount("%0"));

        List<ShortProfile> plist = profileAdminService.getList("%9",null,null);
        assertEquals(1, plist.size());
        assertEquals("u99", plist.get(0).getName());
        assertEquals((Long)u99.getId(), (Long)plist.get(0).getId());
    }

    @Test
    public void testGetProps() {

        assertEquals(0, profileAdminService.getCount(null));

        Profile p = createProfile(getName());
        Map<String,String> props = new HashMap<String, String>();
        props.put("k1", "v1");
        props.put("k2", "v2");
        profileAdminService.setCustomProps(p.getId(), props);

        assertEquals(1, profileAdminService.getCount(null));
        assertEquals(1, profileAdminService.getList(null, null, null).size());
        assertEquals(1, profileAdminService.getFullList(null, null, null).size());
    }

}
