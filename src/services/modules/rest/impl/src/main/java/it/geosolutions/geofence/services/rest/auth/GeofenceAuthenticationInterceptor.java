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
package it.geosolutions.geofence.services.rest.auth;


import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.security.SecurityContext;
import org.apache.log4j.Logger;


/**
 *
 * Starting point was JAASLoginInterceptor
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GeofenceAuthenticationInterceptor extends AbstractPhaseInterceptor<Message>
{

    private static final Logger LOGGER = Logger.getLogger(GeofenceAuthenticationInterceptor.class);

    // TODO: inject user service

    public GeofenceAuthenticationInterceptor()
    {
        super(Phase.UNMARSHAL);
    }

    @Override
    public void handleMessage(Message message) throws Fault
    {

        LOGGER.info("In handleMessage");
        LOGGER.info("Message --> " + message);

        String name = null;
        String password = null;

        AuthUser user = null;

        AuthorizationPolicy policy = (AuthorizationPolicy) message.get(AuthorizationPolicy.class);
        if (policy != null)
        {
            name = policy.getUserName();
            password = policy.getPassword();

            LOGGER.info("Requesting user: " + name);
            // TODO: read user from DB
            // if user and pw do not match, throw new AuthenticationException("Unauthorized");

            user = new AuthUser();
            user.setName(name);

        }
        else
        {
            LOGGER.info("No requesting user -- GUEST access");
        }

        GeofenceSecurityContext securityContext = new GeofenceSecurityContext();
        GeofencePrincipal principal = (user != null) ? new GeofencePrincipal(user) : GeofencePrincipal.createGuest();
        securityContext.setPrincipal(principal);

        message.put(SecurityContext.class, securityContext);
    }
}
