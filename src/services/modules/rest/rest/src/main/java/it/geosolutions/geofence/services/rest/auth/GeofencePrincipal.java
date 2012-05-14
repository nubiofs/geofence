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


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GeofencePrincipal implements Principal
{

    static GeofencePrincipal createGuest()
    {
        return new GeofencePrincipal();
    }

    private AuthUser user;

    public GeofencePrincipal()
    {
    }

    public GeofencePrincipal(AuthUser user)
    {
        if (user == null)
        {
            throw new NullPointerException("Null user");
        }
        this.user = user;
    }

    @Override
    public String getName()
    {
        return (user != null) ? user.getName() : "GUEST";
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }

        final GeofencePrincipal other = (GeofencePrincipal) obj;
        if ((this.user != other.user) && ((this.user == null) || !this.user.equals(other.user)))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = (97 * hash) + ((this.user != null) ? this.user.hashCode() : 0);

        return hash;
    }


}
