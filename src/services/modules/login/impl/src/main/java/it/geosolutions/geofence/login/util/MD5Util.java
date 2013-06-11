/*
 *  Copyright (C) 2013 GeoSolutions S.A.S.
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
package it.geosolutions.geofence.login.util;

import it.geosolutions.geofence.api.exception.AuthException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class MD5Util {
    private static final Logger LOGGER = LogManager.getLogger(MD5Util.class);

    public static String getHash(String password) throws AuthException {
        byte[] passwordByteArr;
        try {
            passwordByteArr = password.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new AuthException(e.getLocalizedMessage(), e);
        }
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.reset();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new AuthException(e.getLocalizedMessage(), e);
        }
        byte[] thedigest = md.digest(passwordByteArr);
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < thedigest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & thedigest[i]));
        }
        return hexString.toString();
    }
}
