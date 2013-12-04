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


import it.geosolutions.geofence.utils.GeofenceTestUtils;
import it.geosolutions.geofence.web.GeofencePage;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.tester.FormTester;
import org.geoserver.platform.GeoServerExtensions;
import org.geoserver.web.GeoServerHomePage;
import org.geoserver.web.GeoServerWicketTestSupport;

public class GeofencePageTest extends GeoServerWicketTestSupport {
    @Override
    public void oneTimeSetUp() throws Exception {
        super.oneTimeSetUp();
        GeoServerExtensions.bean(GeofenceAccessManager.class)
                .setConfigurationFileName("test-config.properties");
        
    }
    
    

    public void testSave() throws URISyntaxException, IOException {
        GeofenceTestUtils.emptyFile("test-config.properties");
        login();
        tester.startPage(GeofencePage.class);
        FormTester ft = tester.newFormTester("form");
        ft.submit("submit");
        tester.assertRenderedPage(GeoServerHomePage.class);
        assertTrue(GeofenceTestUtils.readConfig("test-config.properties").length() > 0);
    }
    
    public void testCancel() throws URISyntaxException, IOException {
        GeofenceTestUtils.emptyFile("test-config.properties");
        login();
        tester.startPage(GeofencePage.class);
        FormTester ft = tester.newFormTester("form");
        ft.submit("cancel");
        tester.assertRenderedPage(GeoServerHomePage.class);
        assertTrue(GeofenceTestUtils.readConfig("test-config.properties").length() == 0);
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
    
    public void testInvalidateCache()  {
        login();
        tester.startPage(GeofencePage.class);
        
        tester.clickLink("form:invalidate", true);
        String success = new StringResourceModel(GeofencePage.class.getSimpleName() + 
                ".cacheInvalidated", null).getObject();
        tester.assertInfoMessages(new String[] { success });
    }

}
