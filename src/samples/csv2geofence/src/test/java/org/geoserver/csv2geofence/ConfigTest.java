package it.geosolutions.csv2geofence;

import it.geosolutions.csv2geofence.config.model.Configuration;
import it.geosolutions.csv2geofence.config.model.GeofenceConfig;
import it.geosolutions.csv2geofence.config.model.RuleFileConfig;
import it.geosolutions.csv2geofence.config.model.RuleFileConfig.ServiceRequest.Type;
import it.geosolutions.csv2geofence.config.model.UserFileConfig;
import java.io.File;
import javax.xml.bind.JAXB;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class ConfigTest extends BaseTest {

    @Test
    public void marshalTest() {

        GeofenceConfig geofenceConfig = new GeofenceConfig();
        geofenceConfig.setRestUrl("http://localhost:9191/geofence/rest");
        geofenceConfig.setUsername("admin");
        geofenceConfig.setPassword("admin");

        UserFileConfig userFileConfig = new UserFileConfig();
        userFileConfig.setFieldSeparator(',');
        userFileConfig.setStringSeparator('"');
        userFileConfig.setUserNameIndex(1);
        userFileConfig.setValidUsernameRegEx("[A-Z0-9]*");
        userFileConfig.setGroupNameIndex(5);
        userFileConfig.setValidGroupRegEx("ASTRIUM.*|DATA.*");
        
        RuleFileConfig ruleFileConfig = new RuleFileConfig();
        ruleFileConfig.setFieldSeparator(",");

        ruleFileConfig.setLayerNameIndex(1);
        ruleFileConfig.setValidLayernameRegEx(".*");

        ruleFileConfig.addGroup(new RuleFileConfig.Group(2));
        ruleFileConfig.addGroup(new RuleFileConfig.Group(3));
        ruleFileConfig.addGroup(new RuleFileConfig.Group(4));
        ruleFileConfig.addGroup(new RuleFileConfig.Group(5));
        ruleFileConfig.addGroup(new RuleFileConfig.Group(6));
        ruleFileConfig.addGroup(new RuleFileConfig.Group(7));
        ruleFileConfig.addGroup(new RuleFileConfig.Group(8));

        ruleFileConfig.addServiceMapping("F", new RuleFileConfig.ServiceRequest("WMS", null, Type.allow));
        ruleFileConfig.addServiceMapping("F", new RuleFileConfig.ServiceRequest("WFS", null, Type.allow));
        ruleFileConfig.addServiceMapping("V", new RuleFileConfig.ServiceRequest("WMS", null, Type.allow));
        ruleFileConfig.addServiceMapping("V", new RuleFileConfig.ServiceRequest("WFS", "Transaction", Type.deny));
        ruleFileConfig.addServiceMapping("V", new RuleFileConfig.ServiceRequest("WFS", "LockFeature", Type.deny));
        ruleFileConfig.addServiceMapping("V", new RuleFileConfig.ServiceRequest("WFS", null, Type.allow));

        Configuration mainConfig = new Configuration();
        mainConfig.setGeofenceConfig(geofenceConfig);
        mainConfig.setUserFileConfig(userFileConfig);
        mainConfig.setRuleFileConfig(ruleFileConfig);

        JAXB.marshal(mainConfig, System.out);
    }

    @Test
    public void unmarshalTest() {
        File cfgFile = loadFile("config00.xml");
        Configuration cfg = JAXB.unmarshal(cfgFile, Configuration.class);
        assertNotNull(cfg);
        assertNotNull(cfg.getGeofenceConfig());
        assertNotNull(cfg.getRuleFileConfig());
        assertNotNull(cfg.getUserFileConfig());

        assertEquals(7, cfg.getRuleFileConfig().getGroups().size());
        assertEquals(3, cfg.getRuleFileConfig().getRuleMapping().size());
        assertEquals(4, cfg.getRuleFileConfig().getRuleMapping().get("V").size());
        assertNotNull(cfg.getRuleFileConfig().getRuleMapping().get("N"));
        assertEquals(0, cfg.getRuleFileConfig().getRuleMapping().get("N").size());
    }

}
