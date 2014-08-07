/*
 *  Copyright (C) 2007 - 2012 GeoSolutions S.A.S.
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
package it.geosolutions.geofence.ldap.dao.impl;

import it.geosolutions.geofence.core.model.UserGroup;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

/**
 * AttributeMapper for UserGroup objects.
 * 
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 * 
 */
public class UserGroupAttributesMapper extends BaseAttributesMapper {

    @Override
    public Object mapFromAttributes(Attributes attrs) throws NamingException {
        UserGroup group = new UserGroup();
        group.setId(Long.parseLong(getAttribute(attrs, "id")));
        group.setExtId((-group.getId()) + "");
        group.setName(getAttribute(attrs, "groupname"));
        group.setEnabled(true);

        return group;
    }

}
