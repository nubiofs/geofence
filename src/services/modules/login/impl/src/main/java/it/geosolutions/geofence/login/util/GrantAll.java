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
package it.geosolutions.geofence.login.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import it.geosolutions.geofence.api.AuthProvider;
import it.geosolutions.geofence.api.dto.Authority;
import it.geosolutions.geofence.api.dto.GrantedAuths;
import it.geosolutions.geofence.api.exception.AuthException;

import org.apache.log4j.Logger;

/**
 * A dummy AuthProvider which grants all auths to every request.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GrantAll implements AuthProvider {

    private static final Logger LOGGER = Logger.getLogger(GrantAll.class);

    @Override
    public GrantedAuths login(String username, String password, String encryptedPassword) throws AuthException {
        // allow auth to anybody
        LOGGER.warn("Login request from '" + username + "'");

        GrantedAuths ga = new GrantedAuths();

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

        LOGGER.info("encryptedPassword: " + encryptedPassword);
        LOGGER.info("hexString: " + hexString);

        if (hexString.toString().equals(encryptedPassword)) {
            ga.setAuthorities(Arrays.asList(Authority.values()));
        } else {
            ga.setAuthorities(Arrays.asList(Authority.REMOTE));
        }

        return ga;
    }

    @Override
    public void logout(String token) {
        // nothing to do
    }
}
