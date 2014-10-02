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
import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.UserGroup;
import java.util.Set;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class UserDAOTest extends BaseDAOTest {

    @Test
    public void testPersistUser() throws Exception {

        long id;
        {
            GSUser user = createUserAndGroup(name.getMethodName());
            userDAO.persist(user);
            id = user.getId();
        }

        // test save & load
        {
            GSUser loaded = userDAO.find(id);
            assertNotNull("Can't retrieve user", loaded);

//            assertNull(loaded.getAllowedArea());
//            loaded.setAllowedArea(buildMultiPolygon());
//            userDAO.merge(loaded);
        }

//        {
//            GSUser loaded2 = userDAO.find(id);
//            assertNotNull("Can't retrieve user", loaded2);
//            assertNotNull(loaded2.getAllowedArea());
//        }

        userDAO.removeById(id);
        assertNull("User not deleted", userDAO.find(id));
    }

//    @Test
//    public void testUpdateSRID() throws Exception {
//
//        final int srid2 = 4326;
//        final int srid1 = 8307;
//
//        long id;
//        {
//            GSUser user = createUserAndGroup(getName());
//            MultiPolygon mp = buildMultiPolygon();
//            mp.setSRID(srid1);
//            user.setAllowedArea(mp);
//            userDAO.persist(user);
//            id = user.getId();
//        }
//
//        // test save & load
//        {
//            GSUser loaded = userDAO.find(id);
//            assertNotNull("Can't retrieve user", loaded);
//            assertEquals("bad SRID", srid1, loaded.getAllowedArea().getSRID());
//
//            MultiPolygon mp = buildMultiPolygon();
//            mp.setSRID(srid2);
//            loaded.setAllowedArea(mp);
//            assertEquals("SRID not set", srid2, loaded.getAllowedArea().getSRID());
//
//            userDAO.merge(loaded);
//        }
//
//        {
//            GSUser loaded = userDAO.find(id);
//            assertNotNull("Can't retrieve user", loaded);
//            assertEquals("SRID not updated", srid2, loaded.getAllowedArea().getSRID());
//        }
//
//        userDAO.removeById(id);
//        assertNull("User not deleted", userDAO.find(id));
//    }


    @Test
    public void testGroups() throws Exception {

        Long gid1, gid2;
        Long uid1;
        {
            UserGroup g1 = createUserGroup(name.getMethodName()+"1");
            gid1 = g1.getId();
            
            UserGroup g2 = createUserGroup(name.getMethodName()+"2");
            gid2 = g2.getId();

            GSUser u1 = createUser("u0", g1);
            userDAO.persist(u1);
            uid1= u1.getId();
            assertNotNull(uid1);
        }

        {
            GSUser loaded = userDAO.find(uid1);
            assertNotNull("Can't retrieve user", loaded);
            Set<UserGroup> grps = userDAO.getGroups(uid1);
            assertEquals("Bad number of usergroups", 1, grps.size());
            assertEquals("Bad assigned usergroup", gid1, grps.iterator().next().getId());

            // add another group
            UserGroup g2 = userGroupDAO.find(gid2);
            assertNotNull(g2);
            loaded.setGroups(grps);
            loaded.getGroups().add(g2);
            userDAO.merge(loaded);
        }

        {
            GSUser loaded = userDAO.find(uid1);
            assertNotNull("Can't retrieve user", loaded);
            Set<UserGroup> grps = userDAO.getGroups(uid1);
            assertEquals("Bad number of usergroups", 2, grps.size());
        }
    }

}