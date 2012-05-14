/*
 * $ Header: it.geosolutions.geofence.gui.client.configuration.UserBeanManager,v. 0.1 25-gen-2011 11.23.48 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
package it.geosolutions.geofence.gui.client.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import it.geosolutions.geofence.gui.client.model.User;

import org.springframework.stereotype.Component;


// TODO: Auto-generated Javadoc
/**
 * The Class UserBeanManager.
 */
@Component("userBeanManager")
public class UserBeanManager implements IUserBeanManager
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 575619421702010379L;

    /** The users. */
    private List<User> users = new ArrayList<User>();

    /**
     * Sets the users.
     *
     * @param users
     *            the new users
     */
    public void setUsers(List<User> users)
    {
        this.users = users;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geofence.gui.client.configuration.IUserBeanManager#getUsers ()
     */
    public List<User> getUsers()
    {
        // TODO Auto-generated method stub
        return users;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geofence.gui.client.configuration.IUserBeanManager#configureUsers()
     */
    @PostConstruct
    public void configureUsers()
    {
        for (int i = 0; i < 200; i++)
        {
            User user = new User();
            user.setPath("geofence/resources/images/userChoose.jpg");
            user.setName("TEST" + i);
            user.setFullName("profile" + i);
            user.setPassword("password" + i);
            user.setEmailAddress("profile" + i + "@test.it");
            this.users.add(user);
        }
    }

}
