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
package it.geosolutions.geofence.core.model;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashSet;
import javax.xml.bind.JAXB;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GSUserTest {

    @Test
    public void jaxb() {
        GSUser user = new GSUser();
        user.setAdmin(Boolean.FALSE);
        user.setDateCreation(new Date());
        user.setEmailAddress("a@b.c");
        user.setEnabled(Boolean.TRUE);
        user.setExtId("extid");
        user.setFullName("fullname");
        user.setId(42l);
        user.setName("name");
        user.setPassword("pwd");
        user.setGroups(new HashSet<UserGroup>());

        {
            UserGroup ug = new UserGroup();
            ug.setId(10l);
            ug.setExtId("this_is_a_group");
            ug.setEnabled(Boolean.TRUE);
            ug.setDateCreation(new Date());
            ug.setName("groupname");
            user.getGroups().add(ug);
        }
        {
            UserGroup ug = new UserGroup();
            ug.setId(11l);
            ug.setExtId("this_is_another_group");
            ug.setEnabled(Boolean.TRUE);
            ug.setDateCreation(new Date());
            ug.setName("groupname2");
            user.getGroups().add(ug);
        }

        assertEquals(2, user.getGroups().size());
        assertNotNull(user.getGroups().iterator().next());

        StringWriter w = new StringWriter();
        JAXB.marshal(user, w);
        String xml = w.toString();
        System.out.println(xml);

        StringReader r = new StringReader(xml);
        GSUser user2 = JAXB.unmarshal(r, GSUser.class);

        System.out.println("2nd marshalling:");
        JAXB.marshal(user2, System.out);

        assertNotNull(user2);
        assertEquals(user.getDateCreation(), user2.getDateCreation());
        assertEquals(user.getEmailAddress(), user2.getEmailAddress());
        assertEquals(user.getEnabled(), user2.getEnabled());
        assertEquals(user.getExtId(), user2.getExtId());
        assertEquals(user.getFullName(), user2.getFullName());
        assertEquals(user.getId(), user2.getId());
        assertEquals(user.getName(), user2.getName());
        assertEquals(user.getPassword(), user2.getPassword());

        assertEquals(user.getGroups().size(), user2.getGroups().size());

        for (UserGroup ug1 : user.getGroups()) {
            boolean found = false;
            for (UserGroup ug2 : user2.getGroups()) {
                if (ug2.getId().equals(ug1.getId())) {
                    found = true;
                    break;
                }
            }            
            assertTrue("Group " + ug1 + " not found in unmarshalled GSUser" , found);
        }
        for (UserGroup ug2 : user2.getGroups()) {
            boolean found = false;
            for (UserGroup ug1 : user.getGroups()) {
                if (ug2.getId().equals(ug1.getId())) {
                    found = true;
                    break;
                }
            }
            assertTrue("Group " + ug2 + " not found in unmarshalled GSUser" , found);
        }

    }
}
