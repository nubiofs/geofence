/*
 *  Copyright (C) 2007 - 2013 GeoSolutions S.A.S.
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
package it.geosolutions.geofence;

import it.geosolutions.geofence.config.GeoFenceConfiguration;
import it.geosolutions.geofence.config.GeoFenceConfigurationManager;
import it.geosolutions.geofence.config.GeoFencePropertyPlaceholderConfigurer;
import it.geosolutions.geofence.utils.GeofenceTestUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.geoserver.test.GeoServerTestSupport;

public class AccessManagerConfigTest extends GeoServerTestSupport {


//    protected GeofenceAccessManager manager;
//    protected RuleReaderService geofenceService;
    GeoFencePropertyPlaceholderConfigurer configurer;
    GeoFenceConfigurationManager manager;

    @Override
    protected void oneTimeSetUp() throws Exception {
        try{
            super.oneTimeSetUp();
        } catch(Exception e) {
            LOGGER.severe("Error in OneTimeSetup: it may be due to GeoFence not running, please check the logs -- " + e.getMessage());
            LOGGER.log(Level.FINE, "Error in OneTimeSetup: it may be due to GeoFence not running, please check the logs", e);
        }

        Map<String, String> namespaces = new HashMap<String, String>();
        namespaces.put("xlink", "http://www.w3.org/1999/xlink");
        namespaces.put("wfs", "http://www.opengis.net/wfs");
        namespaces.put("wcs", "http://www.opengis.net/wcs/1.1.1");
        namespaces.put("gml", "http://www.opengis.net/gml");
        getTestData().registerNamespaces(namespaces);
        XMLUnit.setXpathNamespaceContext(new SimpleNamespaceContext(namespaces));
    }

    @Override
    protected void setUpInternal() throws Exception {
        super.setUpInternal();

        // get the beans we use for testing
//        manager = (GeofenceAccessManager) applicationContext.getBean("geofenceRuleAccessManager");
//        geofenceService = (RuleReaderService) applicationContext.getBean("ruleReaderService");
        manager = (GeoFenceConfigurationManager) applicationContext.getBean("geofenceConfigurationManager");

        configurer = (GeoFencePropertyPlaceholderConfigurer) applicationContext.getBean("geofence-configurer");
        //configurer.setLocation(new UrlResource(this.getClass().getResource("/geofence.properties")));
    }


    public void testSave() throws IOException, URISyntaxException {
        GeofenceTestUtils.emptyFile(configurer.getConfigFile());
       
        GeoFenceConfiguration config = new GeoFenceConfiguration();
        config.setInstanceName("TEST_INSTANCE");
        config.setServicesUrl("http://fakeservice");
        config.setAllowDynamicStyles(true);
        config.setAllowRemoteAndInlineLayers(true);
        config.setGrantWriteToWorkspacesToAuthenticatedUsers(true);
        config.setUseRolesToFilter(true);
        config.setAcceptedRoles("A,B");
        
        manager.setConfiguration(config);
        manager.storeConfiguration();
        
        String content = GeofenceTestUtils.readConfig(configurer.getConfigFile());
        assertTrue(content.contains("fakeservice"));
        assertTrue(content.contains("TEST_INSTANCE"));

    }

    

}
