/*
 *  Copyright (C) 2007 - 2014 GeoSolutions S.A.S.
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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class IPAddressRangeTest {

    public IPAddressRangeTest() {
    }


    @Test
    public void testCIDRConstructorV4() {

        IPAddressRange r = new IPAddressRange("1.2.0.0/16");

        assertEquals(16, r.getSize());

        assertEquals((long)(1<<24 | 2 << 16), (long)r.getLow());
        assertNull(r.getHigh());
    }

    @Test
    public void testMatch() {

        IPAddressRange r = new IPAddressRange("1.2.0.0/16");

        assertTrue(r.match("1.2.3.4"));
        assertFalse(r.match("1.1.3.4"));
    }

    @Test
    public void testToString() {

        String s = "1.2.0.0/16";

        IPAddressRange r = new IPAddressRange(s);

        assertEquals(s, r.getCidrSignature());
        assertEquals("1.2.0.0", r.getAddress());
    }

    @Test
    public void testHigestBitV4() {

        IPAddressRange r = new IPAddressRange("255.2.127.0/20");

        assertEquals(20, r.getSize());

        assertEquals((255<<24 | 2 << 16 | 127 << 8)&0x0ffffffff, (long)r.getLow());
        assertNull(r.getHigh());
    }

}
