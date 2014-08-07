/*
 *  Copyright (C) 2007 - 2014 GeoSolutions S.A.S.
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

package it.geosolutions.geofence.services.util;

import java.util.regex.Pattern;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class IPUtils {

    private static final String IPV4_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
    private static final String SLASH_FORMAT = IPV4_ADDRESS + "/(\\d{1,3})";
    private static final Pattern cidrPattern = Pattern.compile(SLASH_FORMAT);
    private static final Pattern ipv4Pattern = Pattern.compile(IPV4_ADDRESS);

    public static boolean isAddressValid(String ipAddress) {
        if(ipAddress == null) {
            return false;
        }

        return (ipv4Pattern.matcher(ipAddress).matches() || "0:0:0:0:0:0:0:1".equals(ipAddress));
    }

    public static boolean isRangeValid(String ipAddressRange) {
        if(ipAddressRange == null) {
            return false;
        }

        return cidrPattern.matcher(ipAddressRange).matches();
    }


}
