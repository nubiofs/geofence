/*
 * $ Header: it.geosolutions.geofence.gui.server.service.IGsUsersManagerService,v. 0.1 10-feb-2011 11.09.23 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 11.09.23 $
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
package it.geosolutions.geofence.gui.server.service;

import com.extjs.gxt.ui.client.data.PagingLoadResult;

import it.geosolutions.geofence.gui.client.ApplicationException;
import it.geosolutions.geofence.gui.client.model.GSUser;
import it.geosolutions.geofence.gui.client.model.data.UserLimitsInfo;


/**
 * The Interface IGsUsersManagerService.
 */
public interface IGsUsersManagerService
{

    /**
     * Gets the gs users.
     *
     * @param config
     *            the config
     * @param full
     *            the full
     * @return the gs users
     * @throws ApplicationException
     *             the application exception
     */
    public PagingLoadResult<GSUser> getGsUsers(int offset, int limit, boolean full) throws ApplicationException;

    /**
     * Save profile.
     *
     * @param profile
     *            the profile
     * @throws ApplicationException
     *             the application exception
     */
    public void saveUser(GSUser user) throws ApplicationException;

    /**
     * Delete profile.
     *
     * @param profile
     *            the profile
     */
    public void deleteUser(GSUser user);


    /**
     * @param user
     * @return UserLimitInfo
     */
    public UserLimitsInfo getUserLimitsInfo(GSUser user);

    /**
     * @param user
     * @return UserLimitInfo
     */
    public UserLimitsInfo saveUserLimitsInfo(UserLimitsInfo userLimitInfo);
    
    /**
     * @return
     * @throws ApplicationException
     */
    public boolean activateUserGroupTabs() throws ApplicationException;
}
