/*
 *  Copyright (C) 2007 - 2012 GeoSolutions S.A.S.
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
package it.geosolutions.geofence.services.rest.impl;

import it.geosolutions.geofence.services.dto.ShortGroup;
import it.geosolutions.geofence.services.rest.RESTRuleService;
import it.geosolutions.geofence.services.rest.RESTUserGroupService;
import it.geosolutions.geofence.services.rest.RESTUserService;
import it.geosolutions.geofence.services.rest.model.RESTOutputRule;
import it.geosolutions.geofence.services.rest.model.RESTOutputRuleList;
import it.geosolutions.geofence.services.rest.model.RESTShortUser;
import it.geosolutions.geofence.services.rest.model.RESTShortUserList;
import it.geosolutions.geofence.services.rest.model.config.RESTFullUserGroupList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;
import org.junit.rules.TestName;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public abstract class RESTBaseTest {
    private static final Logger LOGGER = LogManager.getLogger(RESTBaseTest.class);

    @org.junit.Rule public TestName name = new TestName();

    protected static ClassPathXmlApplicationContext ctx = null;

    protected static RESTUserService restUserService;
    protected static RESTUserGroupService restUserGroupService;
    protected static RESTRuleService restRuleService;

    public RESTBaseTest() {

        synchronized(RESTBaseTest.class) {
            if(ctx == null) {
                String[] paths = {
                        "classpath*:applicationContext.xml"
//                         ,"applicationContext-test.xml"
                };
                ctx = new ClassPathXmlApplicationContext(paths);


                for(String name : ctx.getBeanDefinitionNames()) {
                    if(name.startsWith("rest") )
                        LOGGER.warn("  BEAN ===> " + name);                    
                }

                restUserService       = (RESTUserService)ctx.getBean("restUserService");
                restUserGroupService  = (RESTUserGroupService)ctx.getBean("restUserGroupService");
                restRuleService       = (RESTRuleService)ctx.getBean("restRuleService");
            }
            
            assertNotNull(restUserService);
            assertNotNull(restUserGroupService);
            assertNotNull(restRuleService);
        }
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void before() throws Exception {
        LOGGER.info("");
        LOGGER.info("============================== TEST " + name.getMethodName());
        LOGGER.info("");

        RESTOutputRuleList rules = restRuleService.get(null, null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        for (RESTOutputRule rule : rules) {
            LOGGER.warn("Removing " + rule);
            restRuleService.delete(rule.getId());
        }


        RESTShortUserList users = restUserService.getList(null, null, null);
        for (RESTShortUser user : users) {
            LOGGER.warn("Removing " + user);
            restUserService.delete(user.getId(), true);            
        }
        RESTFullUserGroupList userGroups = restUserGroupService.getList(null, null, null);
        for (ShortGroup group : userGroups) {
            LOGGER.warn("Removing " + group);
            restUserGroupService.delete(group.getId(), true);
        }

        LOGGER.info("----------------- ending cleaning tasks ------------- ");

    }


}
