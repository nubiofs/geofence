/*
 * $ Header: it.geosolutions.geofence.gui.client.SendType,v. 0.1 25-gen-2011 11.24.45 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.24.45 $
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
package it.geosolutions.geofence.gui.client;

import com.extjs.gxt.ui.client.data.BeanModel;

// TODO: Auto-generated Javadoc
/**
 * The Class SendType.
 */
public class SendType extends BeanModel {

    /**
     * The Enum SendTypeEnum.
     */
    public enum SendTypeEnum {

        /** The TYPE. */
        TYPE("type");

        /** The value. */
        private String value;

        /**
         * Instantiates a new send type enum.
         * 
         * @param value
         *            the value
         */
        SendTypeEnum(String value) {
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

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6754712996337001119L;

    /**
     * Instantiates a new send type.
     * 
     * @param type
     *            the type
     */
    public SendType(String type) {
        setType(type);
    }

    /**
     * Sets the type.
     * 
     * @param type
     *            the new type
     */
    public void setType(String type) {
        set(SendTypeEnum.TYPE.getValue(), type);
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public String getType() {
        return get(SendTypeEnum.TYPE.getValue());
    }

}
