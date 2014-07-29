/*
 *  Copyright (C) 2007 - 2014 GeoSolutions S.A.S.
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


import it.geosolutions.geofence.config.GeoFencePropertyPlaceholderConfigurer;
import it.geosolutions.geofence.services.RuleReaderService;
import it.geosolutions.geofence.utils.GeofenceTestUtils;
import it.geosolutions.geofence.web.GeofencePage;
import java.io.File;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.tester.FormTester;
import org.geoserver.platform.GeoServerExtensions;
import org.geoserver.web.GeoServerHomePage;
import org.geoserver.web.GeoServerWicketTestSupport;
import org.springframework.core.io.UrlResource;

public class GeofencePageTest extends GeoServerWicketTestSupport {
    
    GeoFencePropertyPlaceholderConfigurer configurer;

    @Override
    protected void setUpInternal() throws Exception {
        super.setUpInternal();
    
        // get the beans we use for testing
        configurer = (GeoFencePropertyPlaceholderConfigurer) applicationContext.getBean("geofence-configurer");
        configurer.setLocation(new UrlResource(this.getClass().getResource("/test-config.properties")));
    }
    
    public void testSave() throws URISyntaxException, IOException {
        GeofenceTestUtils.emptyFile("test-config.properties");
        login();
        tester.startPage(GeofencePage.class);
        FormTester ft = tester.newFormTester("form");
        ft.submit("submit");
        tester.assertRenderedPage(GeoServerHomePage.class);

        File configFile = configurer.getConfigFile();
        LOGGER.info("Config file is " + configFile);

        assertTrue(GeofenceTestUtils.readConfig(configFile).length() > 0);
    }
    
    public void testCancel() throws URISyntaxException, IOException {
        GeofenceTestUtils.emptyFile("test-config.properties");
//        GeofenceTestUtils.emptyFile("test-cache-config.properties");
        login();
        tester.startPage(GeofencePage.class);
        FormTester ft = tester.newFormTester("form");
        ft.submit("cancel");
        tester.assertRenderedPage(GeoServerHomePage.class);
        assertTrue(GeofenceTestUtils.readConfig("test-config.properties").length() == 0);
//        assertTrue(GeofenceTestUtils.readConfig("test-cache-config.properties").length() == 0);
    }
    
    public void testErrorEmptyInstance()  {
        login();
        tester.startPage(GeofencePage.class);
        FormTester ft = tester.newFormTester("form");
        ft.setValue("instanceName", "");
        ft.submit("submit");
        tester.assertRenderedPage(GeofencePage.class);
        
        tester.assertContains("is required");
    }
    
    public void testErrorEmptyURL()  {
        login();
        tester.startPage(GeofencePage.class);
        FormTester ft = tester.newFormTester("form");
        ft.setValue("servicesUrl", "");
        ft.submit("submit");
        tester.assertRenderedPage(GeofencePage.class);
        
        tester.assertContains("is required");
    }
    
    public void testErrorWrongURL()  {
        login();
        tester.startPage(GeofencePage.class);
        
        TextField servicesUrl = ((TextField)tester.getComponentFromLastRenderedPage("form:servicesUrl"));
        servicesUrl.setDefaultModel(new Model("fakeurl"));
        
        tester.clickLink("form:test", true);
        
        tester.assertContains("RemoteAccessException");
    }
    
    public void testErrorEmptyCacheSize()  {
        login();
        tester.startPage(GeofencePage.class);
        FormTester ft = tester.newFormTester("form");
        ft.setValue("cacheSize", "");
        ft.submit("submit");
        tester.assertRenderedPage(GeofencePage.class);
        
        tester.assertContains("is required");
    }
    
    public void testErrorWrongCacheSize()  {
        login();
        tester.startPage(GeofencePage.class);
        FormTester ft = tester.newFormTester("form");
        ft.setValue("cacheSize", "A");
        ft.submit("submit");
        tester.assertRenderedPage(GeofencePage.class);
        
        tester.assertContains("long");
    }
    
    public void testInvalidateCache()  {
        login();
        tester.startPage(GeofencePage.class);
        
        tester.clickLink("form:invalidate", true);
        String success = new StringResourceModel(GeofencePage.class.getSimpleName() + 
                ".cacheInvalidated", null).getObject();
        tester.assertInfoMessages(new String[] { success });
    }

}
