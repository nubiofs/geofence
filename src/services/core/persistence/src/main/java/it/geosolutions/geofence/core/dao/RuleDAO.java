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

package it.geosolutions.geofence.core.dao;

import it.geosolutions.geofence.core.model.Rule;
import it.geosolutions.geofence.core.model.enums.InsertPosition;

/**
 * Public interface to define operations on Rule
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */

public interface RuleDAO extends RestrictedGenericDAO<Rule> {

    /**
     * Shifts the priority of the rules having <TT>priority &gt;= priorityStart</TT>
     * down by <TT>offset</TT>.
     * <P/>
     * The shift will not be performed if there are no Rules with priority: <BR/>
     * <tt> startPriority &lt;= priority &lt; startPriority + offset </TT>
     *
     * @return the number of rules updated, or -1 if no need to shift.
     */
    public int shift(long priorityStart, long offset);

    /**
     * Swaps the priorities of the two rules.
     */
    public void swap(long id1, long id2);

    long persist(Rule entity, InsertPosition position);

}
