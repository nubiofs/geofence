/*
 * $ Header: it.geosolutions.geofence.gui.server.gwt.GsUsersManagerServiceImpl,v. 0.1 10-feb-2011 11.08.54 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 11.08.54 $
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
package it.geosolutions.geofence.gui.server.gwt;

import it.geosolutions.geofence.gui.client.ApplicationException;
import it.geosolutions.geofence.gui.client.model.GSUser;
import it.geosolutions.geofence.gui.client.model.data.UserLimitsInfo;
import it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteService;
import it.geosolutions.geofence.gui.server.service.IGsUsersManagerService;
import it.geosolutions.geofence.gui.spring.ApplicationContextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * The Class GsUsersManagerServiceImpl.
 */
public class GsUsersManagerServiceImpl extends RemoteServiceServlet implements GsUsersManagerRemoteService
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6961825619542958052L;

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The gs profile manager service. */
    private IGsUsersManagerService gsUserManagerService;

    /**
     * Instantiates a new gs users manager service impl.
     */
    public GsUsersManagerServiceImpl()
    {
        this.gsUserManagerService = (IGsUsersManagerService) ApplicationContextUtil.getInstance().getBean(
                "gsUsersManagerServiceGWT");
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteService#getGsUsers(com.extjs.gxt.ui.client.data.PagingLoadConfig)
     */
    public PagingLoadResult<GSUser> getGsUsers(int offset, int limit, boolean full) throws ApplicationException
    {
        return gsUserManagerService.getGsUsers(offset, limit, full);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteService#saveGsUser(it.geosolutions.geofence.gui.client.model.GSUser)
     */
    public void saveGsUser(GSUser user) throws ApplicationException
    {
        gsUserManagerService.saveUser(user);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteService#deleteGsUser(it.geosolutions.geofence.gui.client.model.GSUser)
     */
    public void deleteGsUser(GSUser user) throws ApplicationException
    {
        gsUserManagerService.deleteUser(user);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteService#getUserLimitsInfo(it.geosolutions.geofence.gui.client.model.GSUser)
     */
    public UserLimitsInfo getUserLimitsInfo(GSUser user) throws ApplicationException
    {
        return gsUserManagerService.getUserLimitsInfo(user);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteService#saveUserLimitsInfo(it.geosolutions.geofence.gui.client.model.GSUser)
     */
    public UserLimitsInfo saveUserLimitsInfo(UserLimitsInfo userLimitInfo) throws ApplicationException
    {
        return gsUserManagerService.saveUserLimitsInfo(userLimitInfo);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteService#activateUserGroupTabs()
     */
    public boolean activateUserGroupTabs() throws ApplicationException 
    {
        return gsUserManagerService.activateUserGroupTabs();
    }
}
