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

import java.security.Principal;

import org.apache.cxf.security.SecurityContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GeofenceSecurityContext implements SecurityContext
{

    private static final Logger LOGGER = LogManager.getLogger(GeofenceSecurityContext.class);

    private GeofencePrincipal principal;

    public void setPrincipal(GeofencePrincipal principal)
    {
        this.principal = principal;
    }

    @Override
    public Principal getUserPrincipal()
    {
        return principal;
    }


    @Override
    public boolean isUserInRole(String role)
    {
        boolean ret = isUserInRoleAux(role);
        LOGGER.info("User" + principal.getName() + " in " + role + " : " + ret);

        return ret;
    }

    public boolean isUserInRoleAux(String role)
    {
        // TODO pls use an enum here
        if ("*".equals(role))
        {
            return true;
        }

        if ("guest".equalsIgnoreCase(role))
        {
            return true;
        }

        if ("user".equalsIgnoreCase(role)) // so user is registered
        {
            return true;
        }

        if ("admin".equalsIgnoreCase(principal.getName()) && "admin".equalsIgnoreCase(role))
        {
            return true;
        }

        return false;
    }


}
