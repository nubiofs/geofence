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

package it.geosolutions.geofence.services;

import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.LayerDetails;
import it.geosolutions.geofence.core.model.UserGroup;
import it.geosolutions.geofence.core.model.Rule;
import it.geosolutions.geofence.core.model.RuleLimits;
import it.geosolutions.geofence.core.model.enums.CatalogMode;
import it.geosolutions.geofence.core.model.enums.GrantType;
import it.geosolutions.geofence.services.dto.CatalogModeDTO;
import it.geosolutions.geofence.services.dto.RuleFilter;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RuleReaderCatalogModeTest extends ServiceTestBase {

    public RuleReaderCatalogModeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testCatalogModeBothNull() throws NotFoundServiceEx {
        assertEquals(0, ruleAdminService.getCountAll());

        UserGroup g1 = createUserGroup("g1");
        UserGroup g2 = createUserGroup("g2");
        UserGroup g3 = createUserGroup("g3");
        UserGroup g4 = createUserGroup("g4");

        GSUser u1 = createUser("u1", g1, g2, g3, g4);


        insertRule(new Rule(20, u1, null, null,null,      null, null, null, "l1", GrantType.LIMIT), null);
        insertRule(new Rule(20, null, g1, null,null,      null, null, null, "l1", GrantType.ALLOW), null);

        LOGGER.info("SETUP ENDED, STARTING TESTS========================================");

        assertEquals(2, ruleAdminService.getCountAll());

        RuleFilter filterU1;
        filterU1 = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        filterU1.setUser("u1");

        assertEquals(2, ruleReaderService.getMatchingRules(filterU1).size());
        assertEquals(GrantType.ALLOW, ruleReaderService.getAccessInfo(filterU1).getGrant());
        assertEquals(null, ruleReaderService.getAccessInfo(filterU1).getCatalogMode());
    }

    @Test
    public void testCatalogModeOneNull() throws NotFoundServiceEx {
        assertEquals(0, ruleAdminService.getCountAll());

        UserGroup g1 = createUserGroup("g1");
        UserGroup g2 = createUserGroup("g2");
        UserGroup g3 = createUserGroup("g3");
        UserGroup g4 = createUserGroup("g4");

        GSUser u1 = createUser("u1", g1, g2, g3, g4);


        insertRule(new Rule(20, u1, null, null,null,      null, null, null, "l1", GrantType.LIMIT), null);
        insertRule(new Rule(20, null, g1, null,null,      null, null, null, "l1", GrantType.ALLOW), CatalogMode.MIXED);

        LOGGER.info("SETUP ENDED, STARTING TESTS========================================");

        assertEquals(2, ruleAdminService.getCountAll());

        RuleFilter filterU1;
        filterU1 = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        filterU1.setUser("u1");

        assertEquals(2, ruleReaderService.getMatchingRules(filterU1).size());
        assertEquals(CatalogModeDTO.MIXED, ruleReaderService.getAccessInfo(filterU1).getCatalogMode());
    }


    @Test
    public void testCatalogModeNoNull() throws NotFoundServiceEx {
        assertEquals(0, ruleAdminService.getCountAll());

        UserGroup g1 = createUserGroup("g1");
        UserGroup g2 = createUserGroup("g2");
        UserGroup g3 = createUserGroup("g3");
        UserGroup g4 = createUserGroup("g4");

        GSUser u1 = createUser("u1", g1, g2, g3, g4);


        insertRule(new Rule(20, u1, null, null, null,     null, null, null, "l1", GrantType.LIMIT), CatalogMode.HIDE);
        insertRule(new Rule(20, null, g1, null, null,     null, null, null, "l1", GrantType.ALLOW), CatalogMode.MIXED);

        LOGGER.info("SETUP ENDED, STARTING TESTS========================================");

        assertEquals(2, ruleAdminService.getCountAll());

        RuleFilter filterU1;
        filterU1 = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        filterU1.setUser("u1");

        assertEquals(2, ruleReaderService.getMatchingRules(filterU1).size());
        assertEquals(CatalogModeDTO.HIDE, ruleReaderService.getAccessInfo(filterU1).getCatalogMode());
    }

    @Test
    public void testCatalogModeTwoGroups() throws NotFoundServiceEx {
        assertEquals(0, ruleAdminService.getCountAll());

        UserGroup g1 = createUserGroup("g1");
        UserGroup g2 = createUserGroup("g2");
        UserGroup g3 = createUserGroup("g3");
        UserGroup g4 = createUserGroup("g4");

        GSUser u1 = createUser("u1", g1, g2, g3, g4);


        insertRule(new Rule(20, null, g2, null, null,  null, null, null, "l1", GrantType.ALLOW), CatalogMode.HIDE);
        insertRule(new Rule(20, null, g1, null, null,  null, null, null, "l1", GrantType.ALLOW), CatalogMode.MIXED);

        LOGGER.info("SETUP ENDED, STARTING TESTS========================================");

        assertEquals(2, ruleAdminService.getCountAll());

        RuleFilter filterU1;
        filterU1 = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        filterU1.setUser("u1");

        assertEquals(2, ruleReaderService.getMatchingRules(filterU1).size());
        assertEquals(CatalogModeDTO.MIXED, ruleReaderService.getAccessInfo(filterU1).getCatalogMode());
    }



    private void insertRule(Rule rule, CatalogMode mode) {

        ruleAdminService.insert(rule);

        if (GrantType.LIMIT == rule.getAccess()) {
            RuleLimits limits = new RuleLimits();
            limits.setCatalogMode(mode);
            ruleAdminService.setLimits(rule.getId(), limits);
        } else {
            LayerDetails d1 = new LayerDetails();
            d1.setCatalogMode(mode);
            ruleAdminService.setDetails(rule.getId(), d1);
        }
    }

}
