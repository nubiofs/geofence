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
package it.geosolutions.geofence.services;

import it.geosolutions.geofence.core.dao.LayerDetailsDAO;
import it.geosolutions.geofence.core.dao.RuleDAO;
import it.geosolutions.geofence.core.dao.RuleLimitsDAO;
import it.geosolutions.geofence.core.model.LayerDetails;
import it.geosolutions.geofence.core.model.Rule;
import it.geosolutions.geofence.core.model.RuleLimits;
import it.geosolutions.geofence.core.model.enums.GrantType;
import it.geosolutions.geofence.services.dto.RuleFilter;
import it.geosolutions.geofence.services.dto.RuleFilter.IdNameFilter;
import it.geosolutions.geofence.services.dto.RuleFilter.NameFilter;
import it.geosolutions.geofence.services.dto.ShortRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.trg.search.Filter;
import com.trg.search.Search;
import it.geosolutions.geofence.services.exception.BadRequestServiceEx;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;

/**
 *
 *
 * <B>Note:</B> <TT>service</TT> and <TT>request</TT> params are usually set by
 * the client, and by OGC specs they are not case sensitive, so we're going to
 * turn all of them uppercase. See also {@link RuleReaderServiceImpl}.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RuleAdminServiceImpl implements RuleAdminService {

    private final static Logger LOGGER = Logger.getLogger(RuleAdminServiceImpl.class);

    private RuleDAO ruleDAO;
    private RuleLimitsDAO limitsDAO;
    private LayerDetailsDAO detailsDAO;

    // =========================================================================
    // Basic operations
    // =========================================================================

    @Override
    public long insert(Rule rule) {
        sanitizeFields(rule);
        ruleDAO.persist(rule);
        return rule.getId();
    }

    @Override
    public long update(Rule rule) throws NotFoundServiceEx {
        Rule orig = ruleDAO.find(rule.getId());
        if (orig == null) {
            throw new NotFoundServiceEx("Rule not found", rule.getId());
        }

        sanitizeFields(rule);
        ruleDAO.merge(rule);
        return orig.getId();
    }

    /**
     * Shifts the priority of the rules having <TT>priority &gt;= priorityStart</TT>
     * down by <TT>offset</TT>.
     * <P/>
     * The shift will not be performed if there are no Rules with priority: <BR/>
     * <tt> startPriority &lt;= priority &lt; startPriority + offset </TT>
     *
     * @return the number of rules updated, or -1 if no need to shift.
     */

    @Override
    public int shift(long priorityStart, long offset) {
        return ruleDAO.shift(priorityStart, offset);
    }

    @Override
    public void swap(long id1, long id2) {
        ruleDAO.swap(id1, id2);
    }


    /**
     * <TT>service</TT> and <TT>request</TT> params are usually set by
     * the client, and by OGC specs they are not case sensitive, so we're going to
     * turn all of them uppercase. See also {@link RuleReaderServiceImpl}.
     */
    protected void sanitizeFields(Rule rule) {
        // read class' javadoc
        if (rule.getService() != null) {
            rule.setService(rule.getService().toUpperCase());
        }
        if (rule.getRequest() != null) {
            rule.setRequest(rule.getRequest().toUpperCase());
        }
    }

    @Override
    public Rule get(long id) throws NotFoundServiceEx {
        Rule rule = ruleDAO.find(id);

        if (rule == null) {
            throw new NotFoundServiceEx("Rule not found", id);
        }

        return rule;
    }

    @Override
    public boolean delete(long id) throws NotFoundServiceEx {
        Rule rule = ruleDAO.find(id);

        if (rule == null) {
            throw new NotFoundServiceEx("Rule not found", id);
        }

        // data on ancillary tables should be deleted by cascading
        return ruleDAO.remove(rule);
    }

    @Override
    public List<ShortRule> getAll() {
        List<Rule> found = ruleDAO.findAll();
        return convertToShortList(found);
    }

//    /**
//     * Creating a filter heuristically
//     * <UL>
//     * <LI>a null id will only match a null field</LI>
//     * <LI>an id=="*" will match everything, so no filter condition is needed</LI>
//     * <LI>a valid numeric id will only match that numeric value</LI>
//     * </UL>
//     */

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public List<ShortRule> getList(String userId, String profileId, String instanceId,
            String service, String request,
            String workspace, String layer,
            Integer page, Integer entries) {

        RuleFilter filter = new RuleFilter(0L, 0L, 0L, service, request, workspace, layer);
        // adjust IDs
        adjustFilterHeuristic(filter.getUser(), userId);
        adjustFilterHeuristic(filter.getProfile(), profileId);
        adjustFilterHeuristic(filter.getInstance(), instanceId);

        return getList(filter, page, entries);
    }

    private void adjustFilterHeuristic(IdNameFilter filter, String id) {
        if (id == null) {
            filter.setType(RuleFilter.SpecialFilterType.DEFAULT);
        } else if (id.equals("*")) {
            filter.setType(RuleFilter.SpecialFilterType.ANY);
        } else {
            try {
                filter.setId(Long.valueOf(id));
            } catch (NumberFormatException ex) {
                throw new BadRequestServiceEx("Bad id '" + id + "'");
            }
        }
    }

    @Override
    public List<ShortRule> getList(RuleFilter filter, Integer page, Integer entries) {
        Search searchCriteria = buildSearch(page, entries, filter);

        List<Rule> found = ruleDAO.search(searchCriteria);
        return convertToShortList(found);
    }

    @Override
    public List<Rule> getListFull(RuleFilter filter, Integer page, Integer entries) {
        Search searchCriteria = buildSearch(page, entries, filter);

        List<Rule> found = ruleDAO.search(searchCriteria);
        for (Rule rule : found) {
            LayerDetails ld = rule.getLayerDetails();
            if(ld != null) {
                Map<String, String> customProps = detailsDAO.getCustomProps(ld.getId());
                ld.setCustomProps(customProps);
            }
        }
        return found;
    }

    protected Search buildSearch(Integer page, Integer entries, RuleFilter filter) throws BadRequestServiceEx {
        if( (page != null && entries == null) || (page ==null && entries != null)) {
            throw new BadRequestServiceEx("Page and entries params should be declared together.");
        }
        Search searchCriteria = buildRuleSearch(filter);
        searchCriteria.addSortAsc("priority");
        LOGGER.info("Searching Rule list " + ( page==null? "(unpaged) " : (" p:"+page + "#:"+entries)));
        if(entries != null) {
            searchCriteria.setMaxResults(entries);
            searchCriteria.setPage(page);
        }
        return searchCriteria;
    }

    @Override
    public long getCountAll() {
        return getCount(new RuleFilter(RuleFilter.SpecialFilterType.ANY));
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public long getCount(String userId, String profileId, String instanceId, String service, String request, String workspace, String layer) {
        RuleFilter filter = new RuleFilter(0L, 0L, 0L, service, request, workspace, layer);
        // adjust IDs
        adjustFilterHeuristic(filter.getUser(), userId);
        adjustFilterHeuristic(filter.getProfile(), profileId);
        adjustFilterHeuristic(filter.getInstance(), instanceId);
        return getCount(filter);
    }

    @Override
    public long getCount(RuleFilter filter) {
        Search searchCriteria = buildRuleSearch(filter);
        return ruleDAO.count(searchCriteria);
    }

    // =========================================================================
    // Search stuff

    private Search buildRuleSearch(RuleFilter filter) {
        Search searchCriteria = new Search(Rule.class);

        if(filter != null) {
            addCriteria(searchCriteria, "gsuser", filter.getUser());
            addCriteria(searchCriteria, "profile", filter.getProfile());
            addCriteria(searchCriteria, "instance", filter.getInstance());

            addStringCriteria(searchCriteria, "service", filter.getService()); // see class' javadoc
            addStringCriteria(searchCriteria, "request", filter.getRequest()); // see class' javadoc
            addStringCriteria(searchCriteria, "workspace", filter.getWorkspace());
            addStringCriteria(searchCriteria, "layer", filter.getLayer());
        }

        return searchCriteria;
    }


    /**
     * Add criteria for <B>searching</B>.
     *
     * We're dealing with IDs here, so <U>we'll suppose that the related object id field is called "id"</U>.
     */
    private void addCriteria(Search searchCriteria, String fieldName, IdNameFilter filter) {
        switch (filter.getType()) {
            case ANY:
                break; // no filtering

            case DEFAULT:
                searchCriteria.addFilterNull(fieldName);
                break;

            case IDVALUE:
                searchCriteria.addFilter(
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
                searchCriteria.addFilter(
                        Filter.equal(fieldName, filter.getName()));
                break;

            case IDVALUE:
            default:
                throw new AssertionError();
        }
    }

    // =========================================================================
    // Limits
    // =========================================================================

    @Override
    public void setLimits(Long ruleId, RuleLimits limits) {
        Rule rule = ruleDAO.find(ruleId);
        if(rule == null)
            throw new NotFoundServiceEx("Rule not found");

        if(rule.getAccess() != GrantType.LIMIT && limits != null)
            throw new BadRequestServiceEx("Rule is not of LIMIT type");

        // remove old limits if any
        if(rule.getRuleLimits() != null) {
            limitsDAO.remove(rule.getRuleLimits());
        }

        if(limits != null) {
            limits.setId(ruleId);
            limits.setRule(rule);
            limitsDAO.persist(limits);
        } else {
            LOGGER.info("Removing limits for " + rule);
            // TODO: remove limits (already removed above?)
        }
    }

    // =========================================================================
    // Details
    // =========================================================================

//    @Override
//    public LayerDetails getDetails(long id) throws ResourceNotFoundFault {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }


    @Override
    public void setDetails(Long ruleId, LayerDetails details) {
        Rule rule = ruleDAO.find(ruleId);
        if(rule == null)
            throw new NotFoundServiceEx("Rule not found");

        if(rule.getLayer() == null && details != null)
            throw new BadRequestServiceEx("Rule does not refer to a fixed layer");

        if(rule.getAccess() != GrantType.ALLOW && details != null)
            throw new BadRequestServiceEx("Rule is not of ALLOW type");

        final Map<String, String> oldProps;
        final Set<String> oldStyles;

        // remove old details if any
        if(rule.getLayerDetails() != null) {
            oldProps = detailsDAO.getCustomProps(ruleId);
            oldStyles = detailsDAO.getAllowedStyles(ruleId);
            detailsDAO.remove(rule.getLayerDetails());
        } else{
            oldProps = null;
            oldStyles = null;
        }

        rule = ruleDAO.find(ruleId);
        if(rule.getLayerDetails() != null)
            throw new IllegalStateException("LayerDetails should be null");

        if(details != null) {
            details.setRule(rule);
            detailsDAO.persist(details);
            // restore old properties
            if(oldProps != null) {
                LOGGER.info("Restoring " + oldProps.size() + " props from older LayerDetails (id:"+ruleId+")");
                //cannot reuse the same Map returned by Hibernate, since it is detached now.
                Map<String, String> newProps = new HashMap<String, String>();
                newProps.putAll(oldProps);
                detailsDAO.setCustomProps(ruleId, newProps);
            }

            if(oldStyles != null){
                LOGGER.info("Restoring " + oldStyles.size() + " styles from older LayerDetails (id:"+ruleId+")");
                //cannot reuse the same Map returned by Hibernate, since it is detached now.
                Set<String> newStyles = new HashSet<String>();
                newStyles.addAll(oldStyles);
                detailsDAO.setAllowedStyles(ruleId, newStyles);
            }
        } else {
            LOGGER.info("Removing details for " + rule);
        }
    }

    @Override
    public void setDetailsProps(Long ruleId, Map<String, String> props) {
        Rule rule = ruleDAO.find(ruleId);
        if(rule == null)
            throw new NotFoundServiceEx("Rule not found");

        if(rule.getLayerDetails() == null) {
            throw new NotFoundServiceEx("Rule has no details associated");
        }

        detailsDAO.setCustomProps(ruleId, props);
    }

    @Override
    public Map<String, String> getDetailsProps(Long ruleId) {
        Rule rule = ruleDAO.find(ruleId);
        if(rule == null)
            throw new NotFoundServiceEx("Rule not found");

        if(rule.getLayerDetails() == null) {
            throw new NotFoundServiceEx("Rule has no details associated");
        }
        return detailsDAO.getCustomProps(ruleId);
    }

    @Override
    public void setAllowedStyles(Long ruleId, Set<String> styles) {
        Rule rule = ruleDAO.find(ruleId);
        if(rule == null)
            throw new NotFoundServiceEx("Rule not found");

        if(rule.getLayerDetails() == null) {
            throw new NotFoundServiceEx("Rule has no details associated");
        }

        detailsDAO.setAllowedStyles(ruleId, styles);
    }

    @Override
    public Set<String> getAllowedStyles(Long ruleId) {
        Rule rule = ruleDAO.find(ruleId);
        if(rule == null)
            throw new NotFoundServiceEx("Rule not found");

        if(rule.getLayerDetails() == null) {
            throw new NotFoundServiceEx("Rule has no details associated");
        }
        return detailsDAO.getAllowedStyles(ruleId);
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

    public void setRuleLimitsDAO(RuleLimitsDAO ruleLimitsDAO) {
        this.limitsDAO = ruleLimitsDAO;
    }

    public void setLayerDetailsDAO(LayerDetailsDAO detailsDAO) {
        this.detailsDAO = detailsDAO;
    }

}
