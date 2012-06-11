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

import com.trg.search.Filter;
import com.trg.search.Search;
import it.geosolutions.geofence.core.dao.impl.ProfileDAOImpl;
import it.geosolutions.geofence.core.model.Profile;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Priority;

import org.junit.Test;

/**
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class ProfileDAOTest extends BaseDAOTest {

    @Test
    public void testPersist() throws Exception {

        long id;
        {
            Profile profile = createProfile(getName());
            id = profile.getId();
        }

        // test save & load
        {
            Profile loaded = profileDAO.find(id);
            assertNotNull("Can't retrieve profile", loaded);
            assertEquals(getName(), loaded.getName());
        }

        profileDAO.removeById(id);
        assertNull("User not deleted", profileDAO.find(id));
    }

    @Test
    public void testMerge() throws Exception {

        long id;
        {
            Profile profile = createProfile(getName());
            id = profile.getId();
        }

        {
            Profile loaded = profileDAO.find(id);
            assertNotNull("Can't retrieve profile", loaded);
            assertEquals(getName(), loaded.getName());

            loaded.setName("aNewName");
            profileDAO.merge(loaded);
        }

        {
            Profile loaded = profileDAO.find(id);
            assertEquals("aNewName", loaded.getName());
        }

        profileDAO.removeById(id);
        assertNull("User not deleted", profileDAO.find(id));
    }



    @Test
    public void testProps() throws Exception {

        long id;
        {
            Profile profile = createProfile(getName());
            id = profile.getId();
        }

        {
            Map<String, String> props = profileDAO.getCustomProps(id);
            assertTrue(props.isEmpty());
            props.put("k1", "v1");
            props.put("k2", "v2");
            profileDAO.setCustomProps(id, props);
        }

        {
            Map<String, String> props = profileDAO.getCustomProps(id);
            assertFalse(props.isEmpty());
            assertEquals(2, props.size());
            assertEquals("v1", props.remove("k1"));
            profileDAO.setCustomProps(id, props);
        }
        {
            Map<String, String> props = profileDAO.getCustomProps(id);
            assertEquals(1, props.size());
            assertEquals("v2", props.remove("k2"));
            profileDAO.setCustomProps(id, props);
        }
        {
            Map<String, String> props = profileDAO.getCustomProps(id);
            assertTrue(props.isEmpty());
        }

        profileDAO.removeById(id);
        assertNull("User not deleted", profileDAO.find(id));
    }

    @Test
    public void testDeletePropsOnCascade() throws Exception {

        long id;
        {
            Profile profile = createProfile(getName());
            id = profile.getId();
        }

        // test save & load
        {
            Profile loaded = profileDAO.find(id);
            assertNotNull("Can't retrieve profile", loaded);
        }

        {
            Map<String, String> props = profileDAO.getCustomProps(id);
            assertTrue(props.isEmpty());
            props.put("k1", "v1");
            props.put("k2", "v2");
            profileDAO.setCustomProps(id, props);
        }

        profileDAO.removeById(id);
        assertNull("User not deleted", profileDAO.find(id));
    }


    @Test
    public void testSearchByProp() throws Exception {

        final long id1;
        {
            Profile profile = createProfile("p1");
            profile.getCustomProps().put("xid", "one");
            profileDAO.merge(profile);
            id1 = profile.getId();
        }

        {
            Profile profile = createProfile("p2");
            profile.getCustomProps().put("xid", "two");
            profileDAO.merge(profile);
        }
        {
            Profile profile = createProfile("p3");
            profile.getCustomProps().put("xid", "two");
            profileDAO.merge(profile);
        }

        {
            // make sure props are in there
            Map<String, String> customProps = profileDAO.getCustomProps(id1);
            assertEquals("one", customProps.get("xid"));
        }

        // none of these will work
//        Search search = new Search(Profile.class);
//        search.addFilterEqual("customProps.xid", "one");
//        search.addFilterEqual("customProps.key", "xid");
//        search.addFilterEqual("customProps.index", "xid");
//        search.addFilterEqual("customProps.propkey", "xid");
//        search.addFilterSome("customProps", Filter.equal("index", "xid"));
//        search.addFilterSome("customProps", Filter.equal("key", "xid"));
//        search.addFilterSome("customProps", Filter.equal("propkey", "xid"));
//        search.addFilterEqual("propvalue", "xid");  // Could not find property 'propvalue' on class class it.geosolutions.geofence.core.model.Profile
//        search.addFilterSome("customProps", Filter.equal("xid", "one"));
//        search.addFilterEqual("index(customProps)", "xid");

        {
            List<Profile> f1 = profileDAO.searchByCustomProp("xid", "one");
            assertEquals(1, f1.size());
            assertEquals("p1", f1.get(0).getName());
        }

        {
            List<Profile> f1 = profileDAO.searchByCustomProp("xid", "two");
            assertEquals(2, f1.size());
        }

//        List found = profileDAO.search(search);
//        LOGGER.info("Found " + found);
//        assertEquals(1, found.size());

    }

}