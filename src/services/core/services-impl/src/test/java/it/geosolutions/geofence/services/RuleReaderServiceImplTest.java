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

package it.geosolutions.geofence.services;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.LayerAttribute;
import it.geosolutions.geofence.core.model.LayerDetails;
import it.geosolutions.geofence.core.model.Profile;
import it.geosolutions.geofence.core.model.Rule;
import it.geosolutions.geofence.core.model.RuleLimits;
import it.geosolutions.geofence.core.model.adapter.GeometryAdapter;
import it.geosolutions.geofence.core.model.enums.AccessType;
import it.geosolutions.geofence.core.model.enums.GrantType;
import it.geosolutions.geofence.services.dto.AccessInfo;
import it.geosolutions.geofence.services.dto.RuleFilter;
import it.geosolutions.geofence.services.dto.ShortRule;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RuleReaderServiceImplTest extends ServiceTestBase {

    public RuleReaderServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }


    @Test
    public void testGetRulesForUsersAndProfile() {

        RuleFilter filter;

        filter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        assertEquals(0, ruleAdminService.getCount(filter));

        Profile p1 = createProfile("p1");
        Profile p2 = createProfile("p2");

        GSUser user1 = new GSUser();
        user1.setName("TestUser1");
        user1.setProfile(p1);

        GSUser user2 = new GSUser();
        user2.setName("TestUser2");
        user2.setProfile(p2);

        userAdminService.insert(user1);
        userAdminService.insert(user2);

        Rule r1 = new Rule(10, user1, p1, null,      "s1", "r1", "w1", "l1", GrantType.ALLOW);
        Rule r2 = new Rule(20, user2, p2, null,      "s1", "r2", "w2", "l2", GrantType.ALLOW);
        Rule r3 = new Rule(30, user1, p1, null,      "s3", "r3", "w3", "l3", GrantType.ALLOW);
        Rule r4 = new Rule(40, user1, p1, null,      null, null, null, null, GrantType.ALLOW);

        ruleAdminService.insert(r1);
        ruleAdminService.insert(r2);
        ruleAdminService.insert(r3);
        ruleAdminService.insert(r4);

        assertEquals(1, ruleReaderService.getMatchingRules(user1.getName(),"","",            null, null,null,null).size());
        assertEquals(3, ruleReaderService.getMatchingRules(user1.getName(),p1.getName(),"",  "*", "*","*","*").size());
        assertEquals(1, ruleReaderService.getMatchingRules(user2.getName(),p2.getName(),"",  "*", "*","*","*").size());
        assertEquals(2, ruleReaderService.getMatchingRules(user1.getName(),p1.getName(),"",  "s1", "*","*","*").size());
        assertEquals(1, ruleReaderService.getMatchingRules(user1.getName(),"","",            "ZZ", "*","*","*").size());


        filter = createFilter(user1.getId(), p1.getId(), null);
        assertEquals(3, ruleReaderService.getMatchingRules(filter).size());

        filter = createFilter(user1.getName(), p1.getName(), null);
        assertEquals(3, ruleReaderService.getMatchingRules(filter).size());

        filter = createFilter(user1.getId(), p1.getId(), "s1");
        assertEquals(2, ruleReaderService.getMatchingRules(filter).size());

        filter = createFilter(user1.getId(), null, "s3");
        assertEquals(2, ruleReaderService.getMatchingRules(filter).size());

    }

    private static RuleFilter createFilter(Long userId, Long profileId, String service) {
        RuleFilter filter;
        filter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        if(userId != null)
            filter.setUser(userId);
        if(profileId != null)
            filter.setProfile(profileId);
        if(service != null)
            filter.setService(service);
        return filter;
    }
    private static RuleFilter createFilter(String userName, String profileName, String service) {
        RuleFilter filter;
        filter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        if(userName != null)
            filter.setUser(userName);
        if(profileName != null)
            filter.setProfile(profileName);
        if(service != null)
            filter.setService(service);
        return filter;
    }


    @Test
    public void testGetRulesForProfileOnly() {

        RuleFilter filter;
        filter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        assertEquals(0, ruleAdminService.getCount(filter));

        Profile p1 = createProfile("p1");
        Profile p2 = createProfile("p2");

        Rule r1 = new Rule(10, null, p1, null,      "s1", "r1", "w1", "l1", GrantType.ALLOW);
        Rule r2 = new Rule(20, null, p2, null,      "s1", "r2", "w2", "l2", GrantType.ALLOW);
        Rule r3 = new Rule(30, null, p1, null,      "s3", "r3", "w3", "l3", GrantType.ALLOW);
        Rule r4 = new Rule(40, null, p1, null,      null, null, null, null, GrantType.ALLOW);

        ruleAdminService.insert(r1);
        ruleAdminService.insert(r2);
        ruleAdminService.insert(r3);
        ruleAdminService.insert(r4);

        assertEquals(0, ruleReaderService.getMatchingRules("","","",            null, null,null,null).size());
        assertEquals(3, ruleReaderService.getMatchingRules("",p1.getName(),"",  "*", "*","*","*").size());
        assertEquals(1, ruleReaderService.getMatchingRules("",p2.getName(),"",  "*", "*","*","*").size());
        assertEquals(2, ruleReaderService.getMatchingRules("",p1.getName(),"",  "s1", "*","*","*").size());
        assertEquals(0, ruleReaderService.getMatchingRules("","","",            "ZZ", "*","*","*").size());


        filter = createFilter(null, p1.getId(), null);
        assertEquals(3, ruleReaderService.getMatchingRules(filter).size());

        filter = createFilter(null, p1.getName(), null);
        assertEquals(3, ruleReaderService.getMatchingRules(filter).size());

        filter = createFilter(null, p1.getId(), "s1");
        assertEquals(2, ruleReaderService.getMatchingRules(filter).size());

        filter = createFilter((String)null, null, "s3");
        assertEquals(2, ruleReaderService.getMatchingRules(filter).size());
    }

    @Test
    public void testGetInfo() {
        assertEquals(0, ruleAdminService.getCount(new RuleFilter(RuleFilter.SpecialFilterType.ANY)));

        int pri = -1;
        List<Rule> rules = new ArrayList<Rule>();

        rules.add(new Rule(100+rules.size(), null, null, null,   "WCS", null, null, null, GrantType.ALLOW));
        rules.add(new Rule(100+rules.size(), null, null, null,   "s1", "r2", "w2", "l2", GrantType.ALLOW));
        rules.add(new Rule(100+rules.size(), null, null, null,   "s3", "r3", "w3", "l3", GrantType.ALLOW));
        rules.add(new Rule(100+rules.size(), null, null, null,    null, null, null, null, GrantType.DENY));

        for (Rule rule : rules) {
            if(rule != null)
                ruleAdminService.insert(rule);
        }

        assertEquals(4, ruleAdminService.getCount(new RuleFilter(RuleFilter.SpecialFilterType.ANY)));

        AccessInfo accessInfo;

        {
            RuleFilter ruleFilter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
            ruleFilter.setUser("u0");
            ruleFilter.setProfile("p0");
            ruleFilter.setInstance("i0");
            ruleFilter.setService("WCS");
            ruleFilter.setRequest(RuleFilter.SpecialFilterType.ANY);
            ruleFilter.setWorkspace("W0");
            ruleFilter.setLayer("l0");

            assertEquals(4, ruleReaderService.getMatchingRules(new RuleFilter(RuleFilter.SpecialFilterType.ANY)).size());
            List<ShortRule> matchingRules = ruleReaderService.getMatchingRules(ruleFilter);
            LOGGER.info("Matching rules: " + matchingRules);
            assertEquals(2, matchingRules.size());
            accessInfo = ruleReaderService.getAccessInfo(ruleFilter);
            assertEquals(GrantType.ALLOW, accessInfo.getGrant());
            assertNull(accessInfo.getAreaWkt());
        }
        {
            RuleFilter ruleFilter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
            ruleFilter.setUser("u0");
            ruleFilter.setProfile("p0");
            ruleFilter.setInstance("i0");
            ruleFilter.setService("UNMATCH");
            ruleFilter.setRequest(RuleFilter.SpecialFilterType.ANY);
            ruleFilter.setWorkspace("W0");
            ruleFilter.setLayer("l0");

            assertEquals(1, ruleReaderService.getMatchingRules(ruleFilter).size());
            accessInfo = ruleReaderService.getAccessInfo(ruleFilter);
            assertEquals(GrantType.DENY, accessInfo.getGrant());
        }
    }

    @Test
    public void testResolveLazy() {
        assertEquals(0, ruleAdminService.getCount(new RuleFilter(RuleFilter.SpecialFilterType.ANY)));

        List<Rule> rules = new ArrayList<Rule>();

        rules.add(new Rule(100+rules.size(), null, null, null,   "WCS", null, null, null, GrantType.ALLOW));
        rules.add(new Rule(100+rules.size(), null, null, null,   "s1", "r2", "w2", "l2", GrantType.ALLOW));

        for (Rule rule : rules) {
            if(rule != null)
                ruleAdminService.insert(rule);
        }

        LayerDetails details = new LayerDetails();
        details.setRule(rules.get(1));
        details.getCustomProps().put("k1", "v1");
        details.getCustomProps().put("k2", "v2");
        ruleAdminService.setDetails(rules.get(1).getId(), details);

        assertEquals(2, ruleAdminService.getCount(new RuleFilter(RuleFilter.SpecialFilterType.ANY)));

        AccessInfo accessInfo;

        {
            RuleFilter ruleFilter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
            ruleFilter.setService("s1");
            ruleFilter.setLayer("l2");

            assertEquals(2, ruleReaderService.getMatchingRules(new RuleFilter(RuleFilter.SpecialFilterType.ANY)).size());
            List<ShortRule> matchingRules = ruleReaderService.getMatchingRules(ruleFilter);
            LOGGER.info("Matching rules: " + matchingRules);
            assertEquals(1, matchingRules.size());
            accessInfo = ruleReaderService.getAccessInfo(ruleFilter);
            assertEquals(GrantType.ALLOW, accessInfo.getGrant());
            assertNull(accessInfo.getAreaWkt());

            assertEquals(2, accessInfo.getCustomProps().size());
        }
    }

    @Test
    public void testNoDefault() {
        assertEquals(0, ruleAdminService.getCount("*","*","*", "*","*", "*","*"));

        int pri = -1;
        Rule rules[] = new Rule[100];

        pri++; rules[pri] = new Rule(pri, null, null, null,   "WCS", null, null, null, GrantType.ALLOW);

        for (Rule rule : rules) {
            if(rule != null)
                ruleAdminService.insert(rule);
        }

        AccessInfo accessInfo;

        assertEquals(1, ruleReaderService.getMatchingRules("u0","p0","i0", "WCS", null,"W0","l0").size());
        accessInfo = ruleReaderService.getAccessInfo("u0","p0","i0", "WCS", null,"W0","l0");
        assertEquals(GrantType.ALLOW, accessInfo.getGrant());

        assertEquals(0, ruleReaderService.getMatchingRules("u0","p0","i0", "UNMATCH", null,"W0","l0").size());
        accessInfo = ruleReaderService.getAccessInfo("u0","p0","i0", "UNMATCH", null,"W0","l0");
        assertEquals(GrantType.DENY, accessInfo.getGrant());
    }

    @Test
    public void testProfiles() {
        assertEquals(0, ruleAdminService.getCountAll());

        Profile p1 = createProfile("p1");
        Profile p2 = createProfile("p2");

        GSUser u1 = createUser("u1", p1);
        GSUser u2 = createUser("u2", p2);

        List<Rule> rules = new ArrayList<Rule>();
        rules.add(new Rule(rules.size()+10, null, p1, null,      "s1", "r1", "w1", "l1", GrantType.ALLOW));
        rules.add(new Rule(rules.size()+10, null, p1, null,      null, null, null, null, GrantType.DENY));

        for (Rule rule : rules) {
            ruleAdminService.insert(rule);
        }

        LOGGER.info("SETUP ENDED, STARTING TESTS");
        //===

        assertEquals(rules.size(), ruleAdminService.getCountAll());

        {
            RuleFilter filter;
            filter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
            filter.setUser(u1.getId());
            assertEquals(2, ruleReaderService.getMatchingRules(filter).size());
            filter.setService("s1");
            assertEquals(2, ruleReaderService.getMatchingRules(filter).size());
            assertEquals(GrantType.ALLOW, ruleReaderService.getAccessInfo(filter).getGrant());

            filter.setService("s2");
            assertEquals(1, ruleReaderService.getMatchingRules(filter).size());
            assertEquals(GrantType.DENY, ruleReaderService.getAccessInfo(filter).getGrant());
        }

        {
            RuleFilter filter;
            filter = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
            filter.setUser(u2.getId());
            filter.setProfile(p1.getId());
            assertEquals(0, ruleReaderService.getMatchingRules(filter).size());
            assertEquals(GrantType.DENY, ruleReaderService.getAccessInfo(filter).getGrant());

        }
    }

    @Test
    public void testProfilesOrder01() throws UnknownHostException {
        assertEquals(0, ruleAdminService.getCountAll());

        Profile p1 = createProfile("p1");
        Profile p2 = createProfile("p2");

        GSUser u1 = createUser("u1", p1);
        GSUser u2 = createUser("u2", p2);

        List<Rule> rules = new ArrayList<Rule>();
        rules.add(new Rule(rules.size()+10, null, p1, null,      null, null, null, null, GrantType.ALLOW));
        rules.add(new Rule(rules.size()+10, null, p2, null,      null, null, null, null, GrantType.DENY));

        for (Rule rule : rules) {
            ruleAdminService.insert(rule);
        }

        LOGGER.info("SETUP ENDED, STARTING TESTS");
        //===

        assertEquals(rules.size(), ruleAdminService.getCountAll());

        RuleFilter filterU1 = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        filterU1.setUser(u1.getId());

        RuleFilter filterU2 = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        filterU2.setUser(u2.getId());

        filterU1.setSourceAddress(Inet4Address.getByAddress(new byte[]{42,43,44,45}));
        filterU2.setSourceAddress(Inet4Address.getByAddress(new byte[]{0xe, 0xf, 0x10, 0x11}));

        assertEquals(1, ruleReaderService.getMatchingRules(filterU1).size());
        assertEquals(1, ruleReaderService.getMatchingRules(filterU2).size());

        assertEquals(GrantType.ALLOW, ruleReaderService.getAccessInfo(filterU1).getGrant());
        assertEquals(GrantType.DENY, ruleReaderService.getAccessInfo(filterU2).getGrant());
    }

    @Test
    public void testProfilesOrder02() {
        assertEquals(0, ruleAdminService.getCountAll());

        Profile p1 = createProfile("p1");
        Profile p2 = createProfile("p2");

        GSUser u1 = createUser("u1", p1);
        GSUser u2 = createUser("u2", p2);

        List<Rule> rules = new ArrayList<Rule>();
        rules.add(new Rule(rules.size()+10, null, p2, null,      null, null, null, null, GrantType.DENY));
        rules.add(new Rule(rules.size()+10, null, p1, null,      null, null, null, null, GrantType.ALLOW));

        for (Rule rule : rules) {
            ruleAdminService.insert(rule);
        }

        LOGGER.info("SETUP ENDED, STARTING TESTS");
        //===

        assertEquals(rules.size(), ruleAdminService.getCountAll());

        RuleFilter filterU1;
        filterU1 = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        filterU1.setUser(u1.getId());

        RuleFilter filterU2;
        filterU2 = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        filterU2.setUser(u2.getId());


        assertEquals(1, ruleReaderService.getMatchingRules(filterU1).size());
        assertEquals(1, ruleReaderService.getMatchingRules(filterU2).size());

        assertEquals(GrantType.ALLOW, ruleReaderService.getAccessInfo(filterU1).getGrant());
        assertEquals(GrantType.DENY, ruleReaderService.getAccessInfo(filterU2).getGrant());
    }

    protected MultiPolygon buildMultiPolygon(String multip) {
        try {
            WKTReader reader = new WKTReader();
            MultiPolygon mp = (MultiPolygon) reader.read(multip);
            mp.setSRID(4326);
            return mp;
        } catch (ParseException ex) {
            throw new RuntimeException("Unexpected exception: " + ex.getMessage(), ex);
        }
    }

    @Test
    public void testArea() throws NotFoundServiceEx, ParseException {
        assertEquals(0, ruleAdminService.getCountAll());

        final String MULTIPOLYGONWKT0 = "MULTIPOLYGON(((10 0, 0 -10, -10 0, 0 10, 10 0)))";
        final String MULTIPOLYGONWKT1 = "MULTIPOLYGON(((6 6, 6 -6, -6 -6 , -6 6, 6 6)))";

        Profile p1 = createProfile("p1");
        GSUser u1 = createUser("u1", p1);
        u1.setAllowedArea(buildMultiPolygon(MULTIPOLYGONWKT0));
        userAdminService.update(u1);

        Rule r0 = new Rule(10, u1, p1, null,      null, null, null, null, GrantType.LIMIT);
        Rule r1 = new Rule(20, null, p1, null,      null, null, null, null, GrantType.ALLOW);


        ruleAdminService.insert(r0);
        ruleAdminService.insert(r1);

        RuleLimits limits = new RuleLimits();
        limits.setAllowedArea(buildMultiPolygon(MULTIPOLYGONWKT1));
        ruleAdminService.setLimits(r0.getId(), limits);

        LOGGER.info("SETUP ENDED, STARTING TESTS");

        assertEquals(2, ruleAdminService.getCountAll());

        //===

        RuleFilter filterU1;
        filterU1 = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        filterU1.setUser(u1.getId());


        assertEquals(2, ruleReaderService.getMatchingRules(filterU1).size());

        AccessInfo accessInfo = ruleReaderService.getAccessInfo(filterU1);
        assertEquals(GrantType.ALLOW, accessInfo.getGrant());
//        assertNotNull(accessInfo.getArea());
//        assertEquals(9, accessInfo.getArea().getNumPoints());

        assertNotNull(accessInfo.getAreaWkt());
        GeometryAdapter ga = new GeometryAdapter();
        Geometry area = ga.unmarshal(accessInfo.getAreaWkt());
        assertEquals(9, area.getNumPoints());        
    }

    @Test
    public void testAttrib() throws NotFoundServiceEx {
        assertEquals(0, ruleAdminService.getCountAll());

        Profile p1 = createProfile("p1");
        GSUser u1 = createUser("u1", p1);

        Rule r1 = new Rule(20, null, p1, null,      null, null, null, "l1", GrantType.ALLOW);
        ruleAdminService.insert(r1);

        LayerDetails details = new LayerDetails();
        details.getAllowedStyles().add("style01");
        details.getAllowedStyles().add("style02");
        details.getAttributes().add(new LayerAttribute("att1", "String", AccessType.NONE));
        details.getAttributes().add(new LayerAttribute("att2", "String", AccessType.READONLY));
        details.getAttributes().add(new LayerAttribute("att3", "String", AccessType.READWRITE));

        ruleAdminService.setDetails(r1.getId(), details);

        LOGGER.info("SETUP ENDED, STARTING TESTS========================================");

        assertEquals(1, ruleAdminService.getCountAll());

        //===

        RuleFilter filterU1;
        filterU1 = new RuleFilter(RuleFilter.SpecialFilterType.ANY);
        filterU1.setUser(u1.getId());

        LOGGER.info("getMatchingRules ========================================");
        assertEquals(1, ruleReaderService.getMatchingRules(filterU1).size());

        LOGGER.info("getAccessInfo ========================================");
        AccessInfo accessInfo = ruleReaderService.getAccessInfo(filterU1);
        assertEquals(GrantType.ALLOW, accessInfo.getGrant());
    }

}
