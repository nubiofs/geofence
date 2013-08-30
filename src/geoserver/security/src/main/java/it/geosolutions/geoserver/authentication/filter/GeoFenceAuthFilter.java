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
package it.geosolutions.geoserver.authentication.filter;

import it.geosolutions.geofence.services.RuleReaderService;
import it.geosolutions.geofence.services.dto.AuthUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.geoserver.security.config.SecurityNamedServiceConfig;
import org.geoserver.security.filter.GeoServerAuthenticationFilter;
import org.geoserver.security.filter.GeoServerSecurityFilter;
import org.geoserver.security.impl.GeoServerRole;
import org.geotools.util.logging.Logging;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GeoFenceAuthFilter extends GeoServerSecurityFilter
    implements GeoServerAuthenticationFilter {

    static final Logger LOGGER = Logging.getLogger(GeoFenceAuthFilter.class);

    private RuleReaderService ruleReaderService;

    static final String ROOT_ROLE = "ROLE_ADMINISTRATOR";
    static final String ANONYMOUS_ROLE = "ROLE_ANONYMOUS";
    static final String USER_ROLE = "ROLE_USER";

    public static enum OnBadAuth {
        SEND_401,
        LOGIN_AS_ANON
    }

    private OnBadAuth onBadAuth = OnBadAuth.LOGIN_AS_ANON;

    @Override
    public void initializeFromConfig(SecurityNamedServiceConfig config) throws IOException {
        super.initializeFromConfig(config);

        GeoFenceAuthFilterConfig cfg = (GeoFenceAuthFilterConfig) config;
        // anything to set here? maybe the cache config
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            doAuth(request, response);
        } else {
            LOGGER.fine("Found existing Authentication in context: " + auth);
        }

        chain.doFilter(request, response);
    }

    private void doAuth(ServletRequest request, ServletResponse response) {

        BasicUser basicUser = getBasicAuth(request);
        AuthUser authUser = null;

        if(basicUser != null) {
            LOGGER.fine("Checking auth for user " + basicUser.name);
            authUser = ruleReaderService.authorize(basicUser.name, basicUser.pw);

            if(authUser == null) {
                LOGGER.info("Could not authenticate user " + basicUser.name);
            }

        } else {
            LOGGER.fine("No basicauth");
        }
        
        if(authUser != null) {
            LOGGER.fine("Found user " + authUser);
            
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(GeoServerRole.AUTHENTICATED_ROLE);

            if(authUser.getRole() == AuthUser.Role.ADMIN) {
                authorities.add(GeoServerRole.ADMIN_ROLE);
                authorities.add(new SimpleGrantedAuthority("ADMIN")); // needed for REST?!?
            } else {
                authorities.add(new SimpleGrantedAuthority(USER_ROLE)); // ??
            }

            UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(basicUser.name, basicUser.pw, authorities);
            SecurityContextHolder.getContext().setAuthentication(upa); 
            
        } else {
            LOGGER.fine("Anonymous access");
//
//            Authentication authentication = new AnonymousAuthenticationToken("geoserver", "null",
//                    Arrays.asList(new GrantedAuthority[] { new SimpleGrantedAuthority(ANONYMOUS_ROLE) }));
//            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
    }

    /**
     * Simple username+password container
     */
    class BasicUser {
        String name;
        String pw;

        public BasicUser(String name, String pw) {
            this.name = name;
            this.pw = pw;
        }
    }

    /**
     * Reads username and password from Basic auth headers.
     * @return a BasicUser instance, or null if no basic auth detected.
     */
    private BasicUser getBasicAuth(ServletRequest request) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String header = ((httpRequest.getHeader("Authorization") != null) ?
                httpRequest.getHeader("Authorization") :
                httpRequest.getHeader("X-CUSTOM-USERID"));

        if (header != null)
        {
            String base64Token = header.startsWith("Basic ") ?
                    header.substring(6) : header;
            String token = new String(Base64.decodeBase64(base64Token.getBytes()));

            int delim = token.indexOf(":");

            String username = null;
            String password = null;

            if (delim != -1)
            {
                username = token.substring(0, delim);
                password = token.substring(delim + 1);
            }
            else
            {
                username = header;
                password = null;
            }

            return new BasicUser(username, password);
        } else {
            return null;
        }

    }

    /**
     * @see org.geoserver.security.filter.GeoServerAuthenticationFilter#applicableForHtml()
     */
//    @Override
    public boolean applicableForHtml() {
        return true;
    }


    /**
     * @see org.geoserver.security.filter.GeoServerAuthenticationFilter#applicableForServices()
     */
//    @Override
    public boolean applicableForServices() {
        return true;
    }

    public void setOnBadAuth(OnBadAuth onBadAuth) {
        this.onBadAuth = onBadAuth;
    }

    public void setOnBadAuth(String onBadAuth) {
        this.onBadAuth = OnBadAuth.valueOf(onBadAuth);
    }

    public void setRuleReaderService(RuleReaderService ruleReaderService) {
        this.ruleReaderService = ruleReaderService;
    }

}
