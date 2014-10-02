/*
 * $ Header: it.geosolutions.geofence.gui.client.service.LoginRemote,v. 0.1 25-gen-2011 11.23.48 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.23.48 $
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
package it.geosolutions.geofence.gui.client.service;

import it.geosolutions.geofence.gui.client.ApplicationException;
import it.geosolutions.geofence.gui.client.model.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * The Interface LoginRemote.
 */
@RemoteServiceRelativePath("LoginRemoteService")
public interface LoginRemoteService extends RemoteService
{

    /**
     * Authenticate.
     *
     * @param userName
     *            the profile name
     * @param password
     *            the password
     * @return the profile
     * @throws ApplicationException
     *             the application exception
     */
    public User authenticate(String userName, String password) throws ApplicationException;

    /**
     * Logout.
     */
    public void logout();

    public Boolean isAuthenticated();

}
