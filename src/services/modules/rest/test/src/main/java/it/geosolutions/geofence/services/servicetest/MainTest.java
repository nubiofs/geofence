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
package it.geosolutions.geofence.services.servicetest;

import it.geosolutions.geofence.core.model.GFUser;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.geosolutions.geofence.core.model.GSInstance;
import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.LayerAttribute;
import it.geosolutions.geofence.core.model.LayerDetails;
import it.geosolutions.geofence.core.model.UserGroup;
import it.geosolutions.geofence.core.model.Rule;
import it.geosolutions.geofence.core.model.enums.AccessType;
import it.geosolutions.geofence.core.model.enums.GrantType;
import it.geosolutions.geofence.services.GFUserAdminService;
import it.geosolutions.geofence.services.InstanceAdminService;
import it.geosolutions.geofence.services.UserGroupAdminService;
import it.geosolutions.geofence.services.RuleAdminService;
import it.geosolutions.geofence.services.UserAdminService;
import it.geosolutions.geofence.services.dto.ShortGroup;
import it.geosolutions.geofence.services.rest.utils.InstanceCleaner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class MainTest implements InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(MainTest.class);
    private RuleAdminService ruleAdminService;
    private UserGroupAdminService userGroupAdminService;
    private UserAdminService userAdminService;
    private GFUserAdminService gfUserAdminService;
    private InstanceAdminService instanceAdminService;
    private InstanceCleaner instanceCleaner;

    public void afterPropertiesSet() throws Exception {
        LOGGER.info("===== Starting Geofence REST test services =====");

        instanceCleaner.removeAll();
        instanceCleaner.removeAllGFUsers();

        setUpTestRule();
    }

    private void setUpTestRule() {

        ShortGroup sp1 = new ShortGroup();
        sp1.setName("test_profile");

        long p1id = userGroupAdminService.insert(sp1);

        ShortGroup sp2 = new ShortGroup();
        sp2.setName("test_profile2");

        long p2id = userGroupAdminService.insert(sp2);
        UserGroup p2 = userGroupAdminService.get(p2id);

        GFUser u0 = new GFUser();
        u0.setName("admin");
        u0.setPassword("password");
        u0.setEnabled(true);
        u0.setFullName("Sample G.F. Admin");
        u0.setEmailAddress("gf.admin@geofence.net");
        u0.setExtId("sample_geoserver_user");
        gfUserAdminService.insert(u0);

        GSUser u1 = new GSUser();
        u1.setAdmin(true);
        u1.setName("admin");
        u1.setPassword("password");
        u1.getGroups().add(userGroupAdminService.get(p1id));
        u1.setEnabled(true);
        u1.setFullName("Sample G.S. Admin");
        u1.setEmailAddress("gs.admin@geofence.net");
        u1.setExtId("sample_geoserver_user");
        userAdminService.insert(u1);


        GSInstance gs1 = new GSInstance();
        gs1.setName("geoserver01");
        gs1.setUsername("admin");
        gs1.setPassword("geoserver");
        gs1.setBaseURL("http://localhost/geoserver");
        gs1.setDescription("A sample instance");
        instanceAdminService.insert(gs1);

        Rule r0 = new Rule(5, u1, p2, gs1, "s0", "r0", null, null, GrantType.ALLOW);
        ruleAdminService.insert(r0);


        final Long r1id;

        {
            Rule r1 = new Rule(10, null, null, null, "s1", "r1", "w1", "l1", GrantType.ALLOW);
            ruleAdminService.insert(r1);
            r1id = r1.getId();
        }

        // save details and check it has been saved
        final Long lid1;
        {
            LayerDetails details = new LayerDetails();
            details.getAllowedStyles().add("FIRST_style1");
            details.getAttributes().add(new LayerAttribute("FIRST_attr1", AccessType.NONE));
            ruleAdminService.setDetails(r1id, details);
            lid1 = details.getId();
            assert lid1 != null;
        }

        // check details have been set in Rule
        {
            Rule loaded = ruleAdminService.get(r1id);
            LayerDetails details = loaded.getLayerDetails();
            assert details != null;
            assert lid1.equals(details.getId());
            assert 1 == details.getAttributes().size();
            assert 1 == details.getAllowedStyles().size();
            LOGGER.info("Found " + loaded + " --> " + loaded.getLayerDetails());
        }

        // set new details
        final Long lid2;
        {
            LayerDetails details = new LayerDetails();
            details.getAttributes().add(new LayerAttribute("attr1", AccessType.NONE));
            details.getAttributes().add(new LayerAttribute("attr2", AccessType.READONLY));
            details.getAttributes().add(new LayerAttribute("attr3", AccessType.READWRITE));

            assert 3 == details.getAttributes().size();

            Set<String> styles = new HashSet<String>();
            styles.add("style1");
            styles.add("style2");
            ruleAdminService.setAllowedStyles(r1id, styles);

            ruleAdminService.setDetails(r1id, details);
            lid2 = details.getId();
            assert lid2 != null;
        }

        // check details
        {
            Rule loaded = ruleAdminService.get(r1id);
            LayerDetails details = loaded.getLayerDetails();
            assert details != null;
            for (LayerAttribute layerAttribute : details.getAttributes()) {
                LOGGER.error(layerAttribute);
            }

            assert 3 == details.getAttributes().size();
            assert 2 == details.getAllowedStyles().size();
            assert details.getAllowedStyles().contains("style1");
        }

    }

    // ==========================================================================
    public void setInstanceCleaner(InstanceCleaner instanceCleaner) {
        this.instanceCleaner = instanceCleaner;
    }

    // ==========================================================================
    public void setUserGroupAdminService(UserGroupAdminService service) {
        this.userGroupAdminService = service;
    }

    public void setUserAdminService(UserAdminService service) {
        this.userAdminService = service;
    }

    public void setInstanceAdminService(InstanceAdminService service) {
        this.instanceAdminService = service;
    }

    public void setRuleAdminService(RuleAdminService service) {
        this.ruleAdminService = service;
    }

    public void setGfUserAdminService(GFUserAdminService service) {
        this.gfUserAdminService = service;
    }
}
