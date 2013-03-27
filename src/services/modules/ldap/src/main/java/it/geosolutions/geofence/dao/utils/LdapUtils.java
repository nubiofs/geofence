/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2011, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.geofence.dao.utils;

import it.geosolutions.geofence.dao.ldap.LdapAttributesMapper;

import java.util.List;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

import com.googlecode.genericdao.search.Filter;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class LdapUtils {
	/**
	 * Creates and LDAP filter from the DAO search filter.
	 * Currently only "property = value" filters are supported.
	 * 
	 * @param filter
	 * @return
	 */
	public static String createLdapFilter(Filter filter, AttributesMapper mapper) {
		// TODO add other filter types
		if(filter.getOperator() == Filter.OP_EQUAL) {
			String propertyName = filter.getProperty();
			if(mapper instanceof LdapAttributesMapper) {
				propertyName = ((LdapAttributesMapper) mapper)
						.getLdapAttribute(propertyName);
			}
			return propertyName + "=" + filter.getValue().toString();
		}		
		return null;
	}

	/**
	 * @param searchBase
	 * @param filter
	 * @param attributesMapper
	 * @return
	 */
	public static <T> List<T> search(LdapTemplate ldapTemplate, String searchBase, String filter,
			AttributesMapper attributesMapper) {
		return ldapTemplate.search(searchBase, filter, attributesMapper);
	}
	
	/**
	 * @param searchBase
	 * @param filter
	 * @param attributesMapper
	 * @return
	 */
	public static <T> List<T> search(LdapTemplate ldapTemplate, String searchBase, Filter filter,
			AttributesMapper attributesMapper) {
		return search(ldapTemplate, searchBase, LdapUtils.createLdapFilter(filter, attributesMapper), attributesMapper);
	}
}
