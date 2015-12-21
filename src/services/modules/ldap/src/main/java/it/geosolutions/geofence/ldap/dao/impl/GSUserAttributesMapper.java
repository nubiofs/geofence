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
 */package it.geosolutions.geofence.ldap.dao.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.dao.utils.IdConverter;
/**
 * AttributeMapper for GSUser objects.
 * 
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class GSUserAttributesMapper extends BaseAttributesMapper {

        @Autowired
	private IdConverter idConverter;

	@Override
	public Object mapFromAttributes(Attributes attrs) throws NamingException {

	        GSUser user = new GSUser();
	        Long sanitizedId = sanitizeId(getAttribute(attrs, "id"));
	        user.setId(sanitizedId);
		user.setExtId(-sanitizedId+"");
		user.setName(getAttribute(attrs, "username"));
		user.setEmailAddress(getAttribute(attrs, "email"));
		user.setEnabled(true);
		user.setFullName(getAttribute(attrs, "name") + " "
				+ getAttribute(attrs, "surname"));
		user.setPassword(getAttribute(attrs, "password"));
				
		return user;
	}
	
	/**
	 * This method transforms a non-numeric id to a numeric id using a heuristics
	 * This is needed when geofence is integrated with foreign system (es. LDAP) which may use alphanumeric codes for the id
	 *   
	 * @return a Long
	 */
	public Long sanitizeId(String id){
	    if(id == null || id.isEmpty()){
	        return null;
	    }
	    if(!StringUtils.isNumeric(id)){ //This accept empty String!
	        return idConverter.convertId(id);
	    }
	    return Long.parseLong(id);
	}

}
