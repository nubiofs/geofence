/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.LoginStatus,v. 0.1 25-feb-2011 16.31.40 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-feb-2011 16.31.40 $
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
package it.geosolutions.geofence.gui.client.widget;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginStatus.
 */
public class LoginStatus extends StatusWidget
{

    /**
     * The Enum EnumLoginStatus.
     */
    public enum EnumLoginStatus
    {

        /** The STATU s_ login. */
        STATUS_LOGIN("x-status-ok"),

        /** The STATU s_ n o_ login. */
        STATUS_NO_LOGIN("x-status-not-ok"),

        /** The STATU s_ logi n_ error. */
        STATUS_LOGIN_ERROR("x-status-error"),

        /** The STATU s_ messag e_ login. */
        STATUS_MESSAGE_LOGIN("Login OK"),

        /** The STATU s_ messag e_ no t_ login. */
        STATUS_MESSAGE_NOT_LOGIN("Login Failed"),

        /** The STATU s_ messag e_ logi n_ error. */
        STATUS_MESSAGE_LOGIN_ERROR("Login Service Error");

        /** The value. */
        private String value;

        /**
         * Instantiates a new enum login status.
         *
         * @param value
         *            the value
         */
        EnumLoginStatus(String value)
        {
            this.value = value;
        }

        /**
         * Gets the value.
         *
         * @return the value
         */
        public String getValue()
        {
            return value;
        }
    }

}
