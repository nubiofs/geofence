/*
 * $ Header: it.geosolutions.geofence.gui.client.DistribUpdateInterval,v. 0.1 25-gen-2011 11.24.44 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.24.44 $
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
 * The Class DistribUpdateInterval.
 */
public class DistribUpdateInterval extends BeanModel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2604147167853598222L;

    /**
     * The Enum DistribUpdateIntervalEnum.
     */
    public enum DistribUpdateIntervalEnum {

        /** The TIME. */
        TIME("distribTime");

        /** The value. */
        private String value;

        /**
         * Instantiates a new distrib update interval enum.
         * 
         * @param value
         *            the value
         */
        DistribUpdateIntervalEnum(String value) {
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

    /**
     * Instantiates a new distrib update interval.
     * 
     * @param time
     *            the time
     */
    public DistribUpdateInterval(String time) {
        setTime(time);
    }

    /**
     * Sets the time.
     * 
     * @param time
     *            the new time
     */
    public void setTime(String time) {
        set(DistribUpdateIntervalEnum.TIME.getValue(), time);
    }

    /**
     * Gets the time.
     * 
     * @return the time
     */
    public String getTime() {
        return get(DistribUpdateIntervalEnum.TIME.getValue());
    }
}
