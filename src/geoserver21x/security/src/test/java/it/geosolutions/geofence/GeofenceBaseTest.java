package it.geosolutions.geofence;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import it.geosolutions.geofence.services.RuleReaderService;

import java.util.logging.Level;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.geoserver.data.test.MockData;
import org.geoserver.test.GeoServerTestSupport;

public abstract class GeofenceBaseTest extends GeoServerTestSupport {

    private static Boolean IS_GEOFENCE_AVAILABLE;
    protected GeofenceAccessManager manager;
    protected RuleReaderService geofenceService;

    @Override
    protected void oneTimeSetUp() throws Exception {
        super.oneTimeSetUp();

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
        manager = (GeofenceAccessManager) applicationContext.getBean("geofenceRuleAccessManager");
        geofenceService = (RuleReaderService) applicationContext.getBean("ruleReaderService");
    }

    @Override
    protected void populateDataDirectory(MockData dataDirectory) throws Exception {
        super.populateDataDirectory(dataDirectory);

        // populate the users
        File security = new File(dataDirectory.getDataDirectoryRoot(), "security");
        security.mkdir();

        File users = new File(security, "users.properties");
        Properties props = new Properties();
        props.put("admin", "geoserver,ROLE_ADMINISTRATOR");
        props.put("cite", "cite,ROLE_DUMMY");
        props.put("wmsuser", "wmsuser,ROLE_DUMMY");
        props.put("area", "area,ROLE_DUMMY");
        props.store(new FileOutputStream(users), "");
    }

    @Override
    protected void runTest() throws Throwable {

        if (IS_GEOFENCE_AVAILABLE == null) {
            try {
                geofenceService.getMatchingRules(null, null, null, null, null, null, null);
                IS_GEOFENCE_AVAILABLE = true;
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error connecting to GeoFence", e);
                IS_GEOFENCE_AVAILABLE = false;
            }
        }

        if (IS_GEOFENCE_AVAILABLE) {
            super.runTest();
        } else {
            System.out.println("Skipping test as GeoFence service is down: "
                    + "in order to run this test you need the services to be running on port 9191");
            // TODO: use Assume when using junit >=3
        }

    }


}
