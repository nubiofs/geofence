/*
 *  Copyright (C) 2007 - 2010 GeoSolutions S.A.S.
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

package it.geosolutions.geofence.util;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class CategorizedCircularBufferTest extends TestCase {

    @Test
    public void testAdd() {
        CategorizedCircularBuffer<String, Long> ccb = new CategorizedCircularBuffer<String, Long>(4);

        ccb.add(1l, "e0");
        assertEquals(1, ccb.size());
        assertEquals(1, ccb.sizeByKey(1l));
        assertEquals(0, ccb.sizeByKey(999l));

        ccb.add(1l, "e1");
        assertEquals(2, ccb.size());
        assertEquals(2, ccb.sizeByKey(1l));
        assertEquals(0, ccb.sizeByKey(999l));

        ccb.add(2l, "e2");
        assertEquals(3, ccb.size());
        assertEquals(2, ccb.sizeByKey(1l));
        assertEquals(1, ccb.sizeByKey(2l));
        assertEquals(0, ccb.sizeByKey(999l));

        ccb.add(3l, "e2");
        assertEquals(4, ccb.size());

        // size limit reached

        ccb.add(4l, "e0");
        assertEquals(4, ccb.size()); // size should be still 4
        assertEquals(1, ccb.sizeByKey(1l)); // th first entry should have been removed
    }

    @Test
    public void testSubList() {
        CategorizedCircularBuffer<String, Long> ccb = new CategorizedCircularBuffer<String, Long>(4);

        ccb.add(0l, "e0"); // 3
        ccb.add(1l, "e2"); // 2
        ccb.add(1l, "e3"); // 1
        ccb.add(3l, "e4"); // idx 0

        {
            List l = ccb.subList(2, 4);
            assertEquals(2, l.size());
            assertEquals("e2", l.get(0));
            assertEquals("e0", l.get(1));

            List kl = ccb.subListByKey(1l, 0, 2);
            assertEquals(2, kl.size());
            assertEquals("e3", kl.get(0));
            assertEquals("e2", kl.get(1));
        }

        ccb.add(3l, "e5"); // idx 0; other will scroll up

        {
            List l = ccb.subList(2, 4);
            assertEquals(2, l.size());
            assertEquals("e3", l.get(0));
            assertEquals("e2", l.get(1));
        }

        ccb.add(3l, "e6"); // idx 0; other will scroll up

        {
            List kl = ccb.subListByKey(1l, 0, 1);
            assertEquals(1, kl.size());
            assertEquals("e3", kl.get(0));
        }
    }

    @Test
    public void testSubListByKey() {
        CategorizedCircularBuffer<String, Long> ccb = new CategorizedCircularBuffer<String, Long>(6);

        ccb.add(1l, "e0"); // 5 ibk2
        ccb.add(2l, "e1"); // 4
        ccb.add(1l, "e2"); // 3 ibk1
        ccb.add(2l, "e3"); // 2
        ccb.add(1l, "e4"); // 1 ibk0
        ccb.add(2l, "e5"); // 0

        List l = ccb.subListByKey(1l, 1, 3);
        assertEquals(2, l.size());
        assertEquals("e2", l.get(0));
        assertEquals("e0", l.get(1));
    }

}