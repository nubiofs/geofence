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
package it.geosolutions.geofence.ldap.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.geosolutions.geofence.core.model.UserGroup;

import java.util.List;

import org.junit.Test;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 * 
 */
public class UserGroupDAOLdapImplTest extends BaseDAOTest {
    @Test
    public void testFindAll() {
        List<UserGroup> groups = userGroupDAO.findAll();
        assertTrue(groups.size() > 0);
        UserGroup group = groups.get(0);
        assertTrue(group.getName().length() > 0);
    }

    @Test
    public void testFind() {
        UserGroup group = userGroupDAO.find(1l);
        assertNotNull(group);
        assertNotNull(group.getName());
        assertEquals("admin", group.getName());
    }

    @Test
    public void testSearch() {
        Search search = new Search();
        search.addFilter(new Filter("groupname", "admin"));

        List<UserGroup> groups = userGroupDAO.search(search);
        assertTrue(groups.size() > 0);
        UserGroup group = groups.get(0);
        assertTrue(group.getName().length() > 0);
    }

    @Test
    public void testCount() {
        assertTrue(userGroupDAO.count(new Search()) > 0);
    }
}
