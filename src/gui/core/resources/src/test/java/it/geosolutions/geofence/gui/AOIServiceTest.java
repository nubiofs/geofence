/*
 * $ Header: it.geosolutions.geofence.gui.AOIServiceTest,v. 0.1 14-gen-2011 19.29.11 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.29.11 $
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
package it.geosolutions.geofence.gui;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// TODO: Auto-generated Javadoc
/**
 * The Class AOIServiceTest.
 */
public class AOIServiceTest extends TestCase
// AbstractDependencyInjectionSpringContextTests {
{
    // private final Logger logger = Logger.getLogger(this.getClass());
    //
    // @Autowired
    // private GEOFENCERemoteService geofenceRemoteService;
    //
    // public void testService() {
    // assertNotNull(geofenceRemoteService);
    //
    // UserList aoiList = geofenceRemoteService.getClient().getUsers();
    //
    // if (aoiList.getList() != null)
    // logger.info("******************* TOTAL Users  ***********************"
    // + aoiList.getList().size());
    // }
    //
    // public void testUserSearch() {
    //
    // String username1 = "%user%";
    //
    // SearchRequest srq = new SearchRequest(username1);
    //
    // long userCount = geofenceRemoteService.getClient().getUsersCount(srq);
    //
    // logger.info("USER COUNT FOR %USE% ****************************** "
    // + userCount);
    //
    // PaginatedSearchRequest psr = new PaginatedSearchRequest(username1, 25,
    // 0);
    //
    // UserList ul = geofenceRemoteService.getClient().searchUsers(psr);
    //
    // if (ul.getList() != null)
    // logger.info("FOUND ELEMENTS FOR PAGINATION ************** "
    // + ul.getList().size());
    //
    // }
    //
    // public void testAOISearch() {
    // AOIList aois = geofenceRemoteService.getClient().getAois();
    //
    // if (aois.getList() != null)
    // logger.info("NUMBER OF AOI :***************************** "
    // + aois.getList().size());
    // }

    // public void testInternalService() {
    // List<RegisteredUser> usersRegistered = geofenceRemoteService
    // .getInternalService().getUsers();
    //
    // for (RegisteredUser regUser : usersRegistered) {
    // logger.info("USER_NAME: " + regUser.getUserName()
    // + "  CONNECT_ID: " + regUser.getConnectId());
    // }
    // }

    // protected String[] getConfigLocations() {
    // return new String[] { "classpath:applicationContext.xml" };
    // }

    /**
     * Instantiates a new aOI service test.
     * 
     * @param testName
     *            the test name
     */
    public AOIServiceTest(String testName) {
        super(testName);
    }

    /**
     * Suite.
     * 
     * @return the test
     */
    public static Test suite() {
        return new TestSuite(AOIServiceTest.class);
    }

    /**
     * Test app.
     */
    public void testApp() {
        assertTrue(true);
    }

}
