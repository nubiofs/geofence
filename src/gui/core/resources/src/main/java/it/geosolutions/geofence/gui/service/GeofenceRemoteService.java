/*
 * $ Header: it.geosolutions.geofence.gui.service.GeofenceRemoteService,v. 0.1 26-gen-2011 17.25.38 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 26-gen-2011 17.25.38 $
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
package it.geosolutions.geofence.gui.service;

import it.geosolutions.geofence.api.UserRegistry;
import it.geosolutions.geofence.login.LoginService;
import it.geosolutions.geofence.services.GFUserAdminService;
import it.geosolutions.geofence.services.InstanceAdminService;
import it.geosolutions.geofence.services.ProfileAdminService;
import it.geosolutions.geofence.services.RuleAdminService;
import it.geosolutions.geofence.services.UserAdminService;

// TODO: Auto-generated Javadoc
/**
 * The Class GeofenceRemoteService.
 */
public class GeofenceRemoteService {

    /** The login service. */
    private LoginService loginService;

    /** The user admin service. */
    private UserAdminService userAdminService;
    
    /** The Geofence login service */
    private GFUserAdminService gfUserAdminService;

    /** The user provider. */
    private UserRegistry userProvider;

    /** The profile admin service. */
    private ProfileAdminService profileAdminService;
    
    /** The instance admin service. */
    private InstanceAdminService instanceAdminService;
    
    /** The rule admin service. */
    private RuleAdminService ruleAdminService;
    
    /**
     * Gets the login service.
     * 
     * @return the login service
     */
    public LoginService getLoginService() {
        return loginService;
    }

    /**
     * Sets the login service.
     * 
     * @param loginService
     *            the new login service
     */
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Gets the user admin service.
     * 
     * @return the user admin service
     */
    public UserAdminService getUserAdminService() {
        return userAdminService;
    }

    /**
     * Sets the user admin service.
     * 
     * @param userAdminService
     *            the new user admin service
     */
    public void setUserAdminService(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    /**
	 * @param grUserAdminService the grUserAdminService to set
	 */
	public void setGfUserAdminService(GFUserAdminService grUserAdminService) {
		this.gfUserAdminService = grUserAdminService;
	}

	/**
	 * @return the grUserAdminService
	 */
	public GFUserAdminService getGfUserAdminService() {
		return gfUserAdminService;
	}

	/**
     * Gets the user provider.
     * 
     * @return the user provider
     */
    public UserRegistry getUserProvider() {
        return userProvider;
    }

    /**
     * Sets the user provider.
     * 
     * @param userProvider
     *            the new user provider
     */
    public void setUserProvider(UserRegistry userProvider) {
        this.userProvider = userProvider;
    }

    /**
     * Sets the profile admin service.
     * 
     * @param profileAdminService
     *            the new profile admin service
     */
    public void setProfileAdminService(ProfileAdminService profileAdminService) {
        this.profileAdminService = profileAdminService;
    }

    /**
     * Gets the profile admin service.
     * 
     * @return the profile admin service
     */
    public ProfileAdminService getProfileAdminService() {
        return profileAdminService;
    }

    /**
     * Sets the instance admin service.
     * 
     * @param instanceAdminService
     *            the new instance admin service
     */
    public void setInstanceAdminService(InstanceAdminService instanceAdminService) {
        this.instanceAdminService = instanceAdminService;
    }

    /**
     * Gets the instance admin service.
     * 
     * @return the instance admin service
     */
    public InstanceAdminService getInstanceAdminService() {
        return instanceAdminService;
    }

    /**
     * Sets the rule admin service.
     * 
     * @param ruleAdminService
     *            the new rule admin service
     */
    public void setRuleAdminService(RuleAdminService ruleAdminService) {
        this.ruleAdminService = ruleAdminService;
    }

    /**
     * Gets the rule admin service.
     * 
     * @return the rule admin service
     */
    public RuleAdminService getRuleAdminService() {
        return ruleAdminService;
    }

}
