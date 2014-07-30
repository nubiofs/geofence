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

import static org.junit.Assert.*;
import it.geosolutions.geofence.core.model.GSUser;

import java.util.List;

import org.junit.Test;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 * 
 */
public class GSUserDAOLdapImplTest extends BaseDAOTest {
    @Test
    public void testFindAll() {
        List<GSUser> users = userDAO.findAll();
        assertTrue(users.size() > 0);
        GSUser user = users.get(0);
        assertTrue(user.getName().length() > 0);
    }

    @Test
    public void testFind() {
        GSUser user = userDAO.find(1l);
        assertNotNull(user);
        assertEquals("admin", user.getName());
    }

    @Test
    public void testGetFullByName() {
        GSUser user = userDAO.getFull("admin");
        assertNotNull(user);
        assertEquals("admin", user.getName());
    }

    @Test
    public void testGetFullById() {
        GSUser user = userDAO.getFull(1l);
        assertNotNull(user);
        assertEquals("admin", user.getName());
    }

    @Test
    public void testCount() {
        assertTrue(userDAO.count(new Search()) > 0);
    }

    @Test
    public void testSearch() {
        Search search = new Search();
        search.addFilter(new Filter("username", "admin"));
        List<GSUser> users = userDAO.search(search);
        assertTrue(users.size() > 0);
        GSUser user = users.get(0);
        assertTrue(user.getName().length() > 0);
    }
}
