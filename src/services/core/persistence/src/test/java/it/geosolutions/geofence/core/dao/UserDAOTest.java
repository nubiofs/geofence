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

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;
import it.geosolutions.geofence.core.model.GSUser;

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
            GSUser user = createUserAndProfile(getName());
            userDAO.persist(user);
            id = user.getId();
        }

        // test save & load
        {
            GSUser loaded = userDAO.find(id);
            assertNotNull("Can't retrieve user", loaded);

            assertNull(loaded.getAllowedArea());
            loaded.setAllowedArea(buildMultiPolygon());
            userDAO.merge(loaded);
        }

        {
            GSUser loaded2 = userDAO.find(id);
            assertNotNull("Can't retrieve user", loaded2);
            assertNotNull(loaded2.getAllowedArea());
        }

        userDAO.removeById(id);
        assertNull("User not deleted", userDAO.find(id));
    }

    @Test
    public void testUpdateSRID() throws Exception {

        final int srid2 = 4326;
        final int srid1 = 8307;

        long id;
        {
            GSUser user = createUserAndProfile(getName());
            MultiPolygon mp = buildMultiPolygon();
            mp.setSRID(srid1);
            user.setAllowedArea(mp);
            userDAO.persist(user);
            id = user.getId();
        }

        // test save & load
        {
            GSUser loaded = userDAO.find(id);
            assertNotNull("Can't retrieve user", loaded);
            assertEquals("bad SRID", srid1, loaded.getAllowedArea().getSRID());

            MultiPolygon mp = buildMultiPolygon();
            mp.setSRID(srid2);
            loaded.setAllowedArea(mp);
            assertEquals("SRID not set", srid2, loaded.getAllowedArea().getSRID());

            userDAO.merge(loaded);
        }

        {
            GSUser loaded = userDAO.find(id);
            assertNotNull("Can't retrieve user", loaded);
            assertEquals("SRID not updated", srid2, loaded.getAllowedArea().getSRID());
        }

        userDAO.removeById(id);
        assertNull("User not deleted", userDAO.find(id));
    }

}