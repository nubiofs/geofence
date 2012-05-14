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

import com.trg.search.Filter;
import it.geosolutions.geofence.services.dto.AccessInfo;
import it.geosolutions.geofence.services.dto.RuleFilter.IdNameFilter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.trg.search.Search;
import com.vividsolutions.jts.geom.Geometry;
import it.geosolutions.geofence.core.dao.GSUserDAO;
import it.geosolutions.geofence.core.dao.LayerDetailsDAO;
import it.geosolutions.geofence.core.dao.ProfileDAO;
import it.geosolutions.geofence.core.dao.RuleDAO;
import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.LayerDetails;
import it.geosolutions.geofence.core.model.Profile;
import it.geosolutions.geofence.core.model.Rule;
import it.geosolutions.geofence.core.model.RuleLimits;
import it.geosolutions.geofence.core.model.enums.GrantType;
import it.geosolutions.geofence.services.dto.RuleFilter;
import it.geosolutions.geofence.services.dto.RuleFilter.NameFilter;
import it.geosolutions.geofence.services.dto.ShortRule;
import it.geosolutions.geofence.services.exception.BadRequestServiceEx;
import java.util.Collections;

/**
 *
 * <P>
 * <B>Note:</B> <TT>service</TT> and <TT>request</TT> params are usually set by
 * the client, and by OGC specs they are not case sensitive, so we're going to
 * turn all of them uppercase. See also {@link RuleAdminServiceImpl}.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RuleReaderServiceImpl implements RuleReaderService {

    private final static Logger LOGGER = Logger.getLogger(RuleReaderServiceImpl.class);

    private RuleDAO ruleDAO;
    private LayerDetailsDAO detailsDAO;
    private GSUserDAO userDAO;
    private ProfileDAO profileDAO;

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public List<ShortRule> getMatchingRules(
                    String userName, String profileName, String instanceName,
                    String service, String request,
                    String workspace, String layer) {

        return getMatchingRules(new RuleFilter(userName, profileName, instanceName, service, request, workspace, layer));
    }

    @Override
    public List<ShortRule> getMatchingRules(RuleFilter filter) {
        List<Rule> found = getRules(filter);
        return convertToShortList(found);
    }


    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public AccessInfo getAccessInfo(String userName, String profileName, String instanceName, String service, String request, String workspace, String layer) {
        return getAccessInfo(new RuleFilter(userName, profileName, instanceName, service, request, workspace, layer));
    }

    @Override
    public AccessInfo getAccessInfo(RuleFilter filter) {
        LOGGER.info("Requesting access for " + filter);
        List<Rule> found = getRules(filter);

        List<RuleLimits> limits = new ArrayList<RuleLimits>();
        AccessInfo ret = null;

        for (Rule rule : found) {
            if(ret != null)
                break;

            if(LOGGER.isDebugEnabled())
                LOGGER.debug("Rule " + rule + " matches filter " + filter);

            switch(rule.getAccess()) {
                case LIMIT:

                   RuleLimits rl = rule.getRuleLimits();
                   if(rl != null) {
                       LOGGER.info("Collecting limits: " + rl);
                       limits.add(rl);
                    } else
                       LOGGER.warn(rule + " has no associated limits");
                    break;

                case DENY:
                    ret = new AccessInfo(GrantType.DENY);
                    break;

                case ALLOW:
                    ret = buildAllowAccessInfo(rule, limits, filter.getUser());
                    break;

                default:
                    throw new IllegalStateException("Unknown GrantType " + rule.getAccess());
            }
        }

        if(ret == null) {
            LOGGER.warn("No rule matching filter " + filter);
            // Denying by default
            ret = new AccessInfo(GrantType.DENY);
        }

        LOGGER.info("Returning " + ret + " for " + filter);
        return ret;
    }

    private Geometry getUserArea(IdNameFilter userFilter) {
        GSUser user = null;

        if(userFilter.getType() == RuleFilter.FilterType.IDVALUE) {
            user = userDAO.find(userFilter.getId());
        } else if(userFilter.getType() == RuleFilter.FilterType.NAMEVALUE) {
            user = getUserByName(userFilter.getName());
        }

        return user == null ? null :  user.getAllowedArea();
    }

    private GSUser getUserByName(String userName) {
        Search search = new Search(GSUser.class);
        search.addFilterEqual("name", userName);
        List<GSUser> users = userDAO.search(search);
        if(users.size() > 1)
            throw new IllegalStateException("Found more than one user with name '"+userName+"'");

        return users.isEmpty() ? null : users.get(0);
    }

    private GSUser getUser(IdNameFilter filter) {
        Search search = new Search(GSUser.class);

        switch(filter.getType()) {
            case IDVALUE:
                search.addFilterEqual("id", filter.getId());
                break;
            case NAMEVALUE:
                search.addFilterEqual("name", filter.getName());
                break;
            default:
                return null;
        }

        List<GSUser> users = userDAO.search(search);
        if(users.size() > 1)
            throw new IllegalStateException("Found more than one user '"+filter+"'");

        return users.isEmpty() ? null : users.get(0);
    }

    private Profile getProfile(IdNameFilter filter) {
        Search search = new Search(Profile.class);

        switch(filter.getType()) {
            case IDVALUE:
                search.addFilterEqual("id", filter.getId());
                break;
            case NAMEVALUE:
                search.addFilterEqual("name", filter.getName());
                break;
            default:
                return null;
        }

        List<Profile> profiles = profileDAO.search(search);
        if(profiles.size() > 1)
            throw new IllegalStateException("Found more than one profile '"+filter+"'");

        return profiles.isEmpty() ? null : profiles.get(0);
    }



    private AccessInfo buildAllowAccessInfo(Rule rule, List<RuleLimits> limits, IdNameFilter userFilter) {
        AccessInfo accessInfo = new AccessInfo(GrantType.ALLOW);

        Geometry area = intersect(limits);

        Geometry userArea = getUserArea(userFilter);
        area = intersect(area, userArea);

        LayerDetails details = rule.getLayerDetails();
        if(details != null ) {
            area = intersect(area, details.getArea());

            accessInfo.setAttributes(details.getAttributes());
            accessInfo.setCqlFilterRead(details.getCqlFilterRead());
            accessInfo.setCqlFilterWrite(details.getCqlFilterWrite());
            accessInfo.setDefaultStyle(details.getDefaultStyle());
            accessInfo.setAllowedStyles(details.getAllowedStyles());

            accessInfo.setCustomProps(detailsDAO.getCustomProps(rule.getId()));

        }


        //        accessInfo.setArea(area);
        if (area != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Attaching an area to Accessinfo: " + area.getClass().getName() + " " + area.toString());
            }
            accessInfo.setAreaWkt(area.toText());
        }

        return accessInfo;
    }

    private Geometry intersect(List<RuleLimits> limits) {
        Geometry g = null;
        for (RuleLimits limit : limits) {
            Geometry area = limit.getAllowedArea();
            if(area != null) {
                if( g == null) {
                    g = area;
                } else {
                    g = g.intersection(area);
                }
            }
        }
        return g;
    }

    private Geometry intersect(Geometry g1, Geometry g2) {
        if(g1!=null) {
            if(g2==null)
                return g1;
            else
                return g1.intersection(g2);
        } else {
            return g2;
        }
    }

    //==========================================================================

//    protected List<Rule> getRules(String userName, String profileName, String instanceName, String service, String request, String workspace, String layer) throws BadRequestServiceEx {
//        Search searchCriteria = new Search(Rule.class);
//        searchCriteria.addSortAsc("priority");
//
//        addCriteria(searchCriteria, userName, "gsuser");
//        addCriteria(searchCriteria, profileName, "profile");
//        addCriteria(searchCriteria, instanceName, "instance");
//
//        addStringMatchCriteria(searchCriteria, service==null?null:service.toUpperCase(), "service"); // see class' javadoc
//        addStringMatchCriteria(searchCriteria, request==null?null:request.toUpperCase(), "request"); // see class' javadoc
//        addStringMatchCriteria(searchCriteria, workspace, "workspace");
//        addStringMatchCriteria(searchCriteria, layer, "layer");
//
//        List<Rule> found = ruleDAO.search(searchCriteria);
//        return found;
//    }

    protected List<Rule> getRules(RuleFilter filter) throws BadRequestServiceEx {
        Search searchCriteria = new Search(Rule.class);
        searchCriteria.addSortAsc("priority");

        IdNameFilter profileFilter = filter.getProfile();

        GSUser user = getUser(filter.getUser());
        if(user != null) {
            Profile profile = getProfile(profileFilter);
            if(profile != null) {
                if(user.getProfile().getId().longValue() != profile.getId().longValue()) {
                    LOGGER.warn("User profile and given profile differ [User:"+filter.getUser()+"] [Profile:"+profileFilter+"].");
                    return (List<Rule>)Collections.EMPTY_LIST;
                }
            } else {
                // Set user's profile
                profileFilter = new IdNameFilter(RuleFilter.FilterType.IDVALUE);
                profileFilter.setId(user.getProfile().getId());
            }
        }

        addCriteria(searchCriteria, "gsuser", filter.getUser());
        addCriteria(searchCriteria, "profile", profileFilter);
        addCriteria(searchCriteria, "instance", filter.getInstance());

        addStringCriteria(searchCriteria, "service", filter.getService()); // see class' javadoc
        addStringCriteria(searchCriteria, "request", filter.getRequest()); // see class' javadoc
        addStringCriteria(searchCriteria, "workspace", filter.getWorkspace());
        addStringCriteria(searchCriteria, "layer", filter.getLayer());

        List<Rule> found = ruleDAO.search(searchCriteria);
        return found;
    }


    private void addCriteria(Search searchCriteria, String fieldName, IdNameFilter filter) {
        switch (filter.getType()) {
            case ANY:
                break; // no filtering

            case DEFAULT:
                searchCriteria.addFilterNull(fieldName);
                break;

            case IDVALUE:
                searchCriteria.addFilterOr(
                        Filter.isNull(fieldName),
                        Filter.equal(fieldName + ".id", filter.getId()));
                break;

            case NAMEVALUE:
                searchCriteria.addFilterOr(
                        Filter.isNull(fieldName),
                        Filter.equal(fieldName + ".name", filter.getName()));
                break;

            default:
                throw new AssertionError();
        }
    }

    private void addStringCriteria(Search searchCriteria, String fieldName, NameFilter filter) {
        switch (filter.getType()) {
            case ANY:
                break; // no filtering

            case DEFAULT:
                searchCriteria.addFilterNull(fieldName);
                break;

            case NAMEVALUE:
                searchCriteria.addFilterOr(
                        Filter.isNull(fieldName),
                        Filter.equal(fieldName, filter.getName()));
                break;

            case IDVALUE:
            default:
                throw new AssertionError();
        }
    }

//    /**
//     * Add criteria for <B>matching</B> names:
//     * <UL>
//     * <LI><STRIKE>null names will not be accepted: that is: user, profile, instance are required (note you can trick this check by setting empty strings)</STRIKE>a null param will match everything</LI>
//     * <LI>a valid string will match that specific value and any rules with that name set to null</LI>
//     * </UL>
//     * We're dealing with <TT><I>name</I></TT>s here, so <U>we'll suppose that the related object's name field is called "<TT>name</TT>"</U>.
//     */
//    protected void addCriteria(Search searchCriteria, String name, String fieldName) throws BadRequestServiceEx {
//        if (name == null)
//            return; // TODO: check desired behaviour
////            throw new BadRequestServiceEx(fieldName + " is null");
//
//        searchCriteria.addFilterOr(
//                Filter.isNull(fieldName),
//                Filter.equal(fieldName + ".name", name));
//    }
//
//    /**
//     * Add criteria for <B>matching</B>:
//     * <UL>
//     * <LI>null values will not add a constraint criteria</LI>
//     * <LI>any string will match that specific value and any rules with that field set to null</LI>
//     * </UL>
//     */
//    protected void addStringMatchCriteria(Search searchCriteria, String value, String fieldName) throws BadRequestServiceEx {
//        if(value != null) {
//            searchCriteria.addFilterOr(
//                    Filter.isNull(fieldName),
//                    Filter.equal(fieldName, value));
//        }
//    }

    // ==========================================================================

    @Override
    public boolean isAdmin(String userName) {
        if(LOGGER.isDebugEnabled())
            LOGGER.debug("isAdmin " + userName);
        GSUser user = getUserByName(userName);
        return user == null? false : user.isAdmin() && user.getEnabled();
    }

    // ==========================================================================

    private List<ShortRule> convertToShortList(List<Rule> list) {
        List<ShortRule> shortList = new ArrayList<ShortRule>(list.size());
        for (Rule rule : list) {
            shortList.add(new ShortRule(rule));
        }

        return shortList;
    }

    // ==========================================================================

    public void setRuleDAO(RuleDAO ruleDAO) {
        this.ruleDAO = ruleDAO;
    }

    public void setLayerDetailsDAO(LayerDetailsDAO detailsDAO) {
        this.detailsDAO = detailsDAO;
    }

    public void setGsUserDAO(GSUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setProfileDAO(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

}
