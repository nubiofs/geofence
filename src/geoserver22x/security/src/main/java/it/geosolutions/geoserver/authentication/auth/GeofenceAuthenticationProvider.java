/*
 *  Copyright (C) 2007 - 2013 GeoSolutions S.A.S.
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
package it.geosolutions.geoserver.authentication.auth;

import it.geosolutions.geofence.services.RuleReaderService;
import it.geosolutions.geofence.services.dto.AuthUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.geoserver.security.GeoServerAuthenticationProvider;
import org.geoserver.security.config.SecurityNamedServiceConfig;
import org.geoserver.security.config.UsernamePasswordAuthenticationProviderConfig;
import org.geoserver.security.impl.GeoServerRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

/**
 * Authentication provider that delegates to GeoFence
 */
public class GeofenceAuthenticationProvider extends GeoServerAuthenticationProvider {


    private RuleReaderService ruleReaderService;


    @Override
    public void initializeFromConfig(SecurityNamedServiceConfig config) throws IOException {

        UsernamePasswordAuthenticationProviderConfig upAuthConfig =
                (UsernamePasswordAuthenticationProviderConfig) config;

    }

    @Override
    public boolean supports(Class<? extends Object> authentication, HttpServletRequest request) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public Authentication authenticate(Authentication authentication, HttpServletRequest request)
            throws AuthenticationException {

        UsernamePasswordAuthenticationToken  outTok = null;
        LOGGER.warning("AUTH USERNAMEPW for " + authentication);

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken inTok =  (UsernamePasswordAuthenticationToken)authentication;
            AuthUser authUser = ruleReaderService.authorize(
                    inTok.getPrincipal().toString(),
                    inTok.getCredentials().toString());

            if(authUser != null) {
                LOGGER.warning("User " + inTok.getPrincipal() + " authenticated:" + authUser);

                List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
                roles.addAll(inTok.getAuthorities());
                roles.add(GeoServerRole.AUTHENTICATED_ROLE);
                if(authUser.getRole() == AuthUser.Role.ADMIN)
                    roles.add(GeoServerRole.ADMIN_ROLE);

                outTok = new UsernamePasswordAuthenticationToken(
                    inTok.getPrincipal(), inTok.getCredentials(), roles);

            } else {
                LOGGER.warning("User " + inTok.getPrincipal() + " NOT authenticated");
            }

            return outTok;

        } else {
            return null;
        }
    }

    public void setRuleReaderService(RuleReaderService ruleReaderService) {
        this.ruleReaderService = ruleReaderService;
    }

}
