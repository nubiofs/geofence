/*
 * $ Header: it.geosolutions.geofence.gui.server.gwt.LoginRemoteImpl,v. 0.1 25-gen-2011 11.23.49 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.23.49 $
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

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import it.geosolutions.geofence.gui.client.model.User;
import it.geosolutions.geofence.gui.client.service.LoginRemote;
import it.geosolutions.geofence.gui.server.GeofenceKeySessionValues;
import it.geosolutions.geofence.gui.server.service.ILoginService;
import it.geosolutions.geofence.gui.spring.ApplicationContextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class LoginRemoteImpl.
 */
public class LoginRemoteImpl extends RemoteServiceServlet implements LoginRemote
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6763250533126295210L;

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The login service. */
    private ILoginService loginService;

    /**
     * Instantiates a new login remote impl.
     */
    public LoginRemoteImpl()
    {
        this.loginService = (ILoginService) ApplicationContextUtil.getInstance().getBean(
                "loginService");
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geofence.gui.client.service.LoginRemote#authenticate(java .lang.String,
     * java.lang.String)
     */
    public User authenticate(String userName, String password)
    {
        HttpSession session = getThreadLocalRequest().getSession();

        return loginService.authenticate(userName, password, session);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geofence.gui.client.service.LoginRemote#logout()
     */
    public void logout()
    {
        HttpSession session = getThreadLocalRequest().getSession();
        if (session != null)
        {
            session.removeAttribute(GeofenceKeySessionValues.USER_LOGGED_TOKEN.getValue());
            session.setAttribute(GeofenceKeySessionValues.USER_LOGGED_TOKEN.getValue(), "");
        }

    }

    public Boolean isAuthenticated()
    {
        boolean authenticated = false;

        HttpSession session = getThreadLocalRequest().getSession();
        if ((session.getAttribute(GeofenceKeySessionValues.USER_LOGGED_TOKEN.getValue()) != null) &&
                !((String) session.getAttribute(GeofenceKeySessionValues.USER_LOGGED_TOKEN.getValue())).isEmpty())
        {
            authenticated = true;
        }

        return authenticated;
    }

}
