/*
 *  Copyright (C) 2014 GeoSolutions S.A.S.
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

import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Moving base64 de-coding from commons codec to DatatypeConverter.
 * Making sure the results are the same, or we may lose some passwords in the db...
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class Base64EncodersTest {

    @Test
    public void testEq() {

        String msg1 = "this is the message to encode";

        String output_codec = new String(Base64.encodeBase64(msg1.getBytes()));
        String output_dconv = DatatypeConverter.printBase64Binary(msg1.getBytes());

        System.out.println("apache commons:    " + output_codec);
        System.out.println("DatatypeConverter: " + output_dconv);
        assertEquals(output_codec, output_dconv);

        byte[] back_codec = Base64.decodeBase64(output_dconv);
        byte[] back_dconv = DatatypeConverter.parseBase64Binary(output_dconv);



        Assert.assertTrue( Arrays.equals(msg1.getBytes(), back_codec));
        Assert.assertTrue( Arrays.equals(msg1.getBytes(), back_dconv));

    }

}
