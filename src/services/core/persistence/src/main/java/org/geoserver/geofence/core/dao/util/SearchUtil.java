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

package it.geosolutions.geofence.core.dao.util;

import com.googlecode.genericdao.search.Search;
import it.geosolutions.geofence.core.model.IPAddressRange;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class SearchUtil {

    /**
     * Adds a search field for finding the precise addressrange instance.
     * Does <b>NOT</b> search for an address in a range.
     */
    public static void addAddressRangeSearch(Search search, IPAddressRange addressRange) {
        if(addressRange != null ) {
            // it's embedded
            addSearchField(search, "addressRange.low", addressRange.getLow());
            addSearchField(search, "addressRange.high", addressRange.getHigh());
            addSearchField(search, "addressRange.size", addressRange.getSize());
        } else {
            addSearchField(search, "addressRange.low", null);
            addSearchField(search, "addressRange.high", null);
            addSearchField(search, "addressRange.size", null);
        }
    }

    public static void addSearchField(Search search, String field, Object o) {
        if ( o == null ) {
            search.addFilterNull(field);
        } else {
            search.addFilterEqual(field, o);
        }
    }
}
