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

package it.geosolutions.geofence.ldap.dao.impl;

import static org.junit.Assert.assertNotNull;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;

import it.geosolutions.geofence.core.dao.GSUserDAO;
import it.geosolutions.geofence.core.dao.UserGroupDAO;

import org.apache.directory.server.core.schema.SchemaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.test.LdapTestUtils;



/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public abstract class BaseDAOTest {
    protected final Logger LOGGER;

    protected static GSUserDAO userDAO;
    protected static UserGroupDAO userGroupDAO;
    
    protected static ClassPathXmlApplicationContext ctx = null;

    public BaseDAOTest() {
        LOGGER = LogManager.getLogger(getClass());

       
        
        synchronized(BaseDAOTest.class) {
            if(ctx == null) {
                String[] paths = {
                        "applicationContext.xml"
//                         ,"applicationContext-test.xml"
                };
                ctx = new ClassPathXmlApplicationContext(paths);

                userDAO = (GSUserDAO)ctx.getBean("gsLdapUserDAO");               
                userGroupDAO = (UserGroupDAO)ctx.getBean("ldapUserGroupDAO");
            }
            
        }
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        // Start an LDAP server and import test data
    	LdapTestUtils.startApacheDirectoryServer(10389, "dc=example,dc=com", "test", LdapTestUtils.DEFAULT_PRINCIPAL, LdapTestUtils.DEFAULT_PASSWORD, null);
    }
   
    @AfterClass
    public static void tearDownClass() throws Exception {
        LdapTestUtils.destroyApacheDirectoryServer(LdapTestUtils.DEFAULT_PRINCIPAL, LdapTestUtils.DEFAULT_PASSWORD);        
    }

    @Before
    public void setUp() throws Exception {
    	// Bind to the directory
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://127.0.0.1:10389");
        contextSource.setUserDn(LdapTestUtils.DEFAULT_PRINCIPAL);
        contextSource.setPassword(LdapTestUtils.DEFAULT_PASSWORD);
        contextSource.setPooled(false);
        //contextSource.setDirObjectFactory(null);
        contextSource.afterPropertiesSet();                

        // Create the Sprint LDAP template
        LdapTemplate template = new LdapTemplate(contextSource);

        // Clear out any old data - and load the test data
        LdapTestUtils.cleanAndSetup(template.getContextSource(), new DistinguishedName("dc=example,dc=com"), new ClassPathResource("data.ldif"));
        LOGGER.info("################ Running " + getClass().getSimpleName() );
        

        LOGGER.info("##### Ending setup for " + getClass().getSimpleName() + " ###----------------------");
    }

    @Test
    public void testCheckDAOs() {

        assertNotNull(userDAO);        
    }

}
