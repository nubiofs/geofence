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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * A dummy AuthProvider which grants all auths to every request.
 *
 * TODO: this class used to provide a GrantAll grant, but since long an MD5 check
 * was implemented here. Naming should be fixed
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GrantAll implements AuthProvider {

    private static final Logger LOGGER = LogManager.getLogger(GrantAll.class);

    @Override
    public GrantedAuths login(String username, String password, String pwFromDb) throws AuthException {
        // allow auth to anybody
        LOGGER.warn("Login request from '" + username + "'");

        GrantedAuths ga = new GrantedAuths();
        String hashedPw = MD5Util.getHash(password);

//        LOGGER.info("storedPassword: " + pwFromDb);
        LOGGER.info("hashedPw: " + hashedPw);

        if (hashedPw.equals(pwFromDb)) {
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
