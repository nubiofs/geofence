/*
 * $ Header: it.geosolutions.geofence.gui.client.model.BeanKeyValue,v. 0.1 25-gen-2011 16.50.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 16.50.11 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. 
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.geofence.gui.client.model;

// TODO: Auto-generated Javadoc
/**
 * The Enum BeanKeyValue.
 */
public enum BeanKeyValue {

    /** The USER name. */
    USER_NAME("userName"),

    /** The FULL name. */
    FULL_NAME("fullName"),

    /** The USER. */
    USER("user"),

    /** The PROFILE. */
    PROFILE("profile"),
    
    /** The RULE. */
    RULE("rule"),
    
    /** The SERVICE. */
    SERVICE("service"),

    /** The REQUEST. */
    REQUEST("request"),

    /** The WORKSPACE. */
    WORKSPACE("workspace"),

    /** The LAYER. */
    LAYER("layer"),
    
    /** The GRANT. */
    GRANT("grant"),

    CATALOG_MODE("catalogMode"),

    /** The DATE creation. */
    DATE_CREATION("dateCreation"),

    /** The EMAIL. */
    EMAIL("emailAddress"),

    /** The USER enabled. */
    USER_ENABLED("userEnabled"),

    /** The PROFILE enabled. */
    PROFILE_ENABLED("profileEnabled"),

    /** The NAME. */
    NAME("name"),

    /** The DESCRIPTION. */
    DESCRIPTION("description"),
    
    /** The PRIORITY. */
    PRIORITY("rulePriority"),
    
    /** The BASE url. */
    BASE_URL("baseUrl"),
    
    /** The INSTANCE. */
    INSTANCE("instance"),
    
    /** The PATH. */
    PATH("path"), 
    
    /** The password. */
    PASSWORD("password"),
    
    PROP_KEY("prop_key"),
    
    PROP_VALUE("prop_value"),

    ATTR_NAME("attr_name"),
    
    ATTR_TYPE("attr_type"),
    
    ATTR_ACCESS("attr_access"),
    
    STYLES_COMBO("styles_combo"),
    
    STYLE_ENABLED("style_enable"),
    
    ALLOWED_AREA("allowed_area"),
    
    CQL_READ("cql_read"),
    
    CQL_WRITE("cql_read"),
    
    PROFILE_PROP_VALUE("profile_value"),
    
    PROFILE_PROP_KEY("profile_key"),
    
    USER_ADMIN("user_admin"),
    
    USER_ALLOWED_AREA("user_allowed_area"),
    
    USER_ALLOWED_AREA_SRID("user_allowed_area_srid"),
    
    LAYER_ALLOWED_AREA_SRID("layer_allowed_area_srid"),
    
    RULE_ALLOWED_AREA_SRID("rule_allowed_area_srid");
    
    
    /** The value. */
    private String value;

    /**
     * Instantiates a new bean key value.
     * 
     * @param value
     *            the value
     */
    BeanKeyValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }

}
