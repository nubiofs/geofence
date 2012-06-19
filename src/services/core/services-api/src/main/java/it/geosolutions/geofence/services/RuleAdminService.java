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

import java.util.List;
import java.util.Set;

import it.geosolutions.geofence.core.model.LayerDetails;
import it.geosolutions.geofence.core.model.Rule;
import it.geosolutions.geofence.core.model.RuleLimits;
import it.geosolutions.geofence.core.model.enums.InsertPosition;
import it.geosolutions.geofence.services.dto.RuleFilter;
import it.geosolutions.geofence.services.dto.ShortRule;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;


/**
 * Operations on {@link Rule Rule}s.
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
public interface RuleAdminService
{

    // ==========================================================================
    // Basic operations

    long insert(Rule rule);

    long insert(Rule rule, InsertPosition position);

    long update(Rule rule) throws NotFoundServiceEx;

    /**
     * Shifts the priority of the rules having <TT>priority &gt;= priorityStart</TT>
     * down by <TT>offset</TT>.
     *
     * @return the number of rules updated.
     */
    int shift(long priorityStart, long offset);

    /**
     * Swaps the priorities of two rules.
     */
    void swap(long id1, long id2);

    boolean delete(long id) throws NotFoundServiceEx;


    // Internal, no REST annotations
    void deleteRulesByUser(long userId) throws NotFoundServiceEx;

    // Internal, no REST annotations
    void deleteRulesByGroup(long groupId) throws NotFoundServiceEx;

    // Internal, no REST annotations
    void deleteRulesByInstance(long instanceId) throws NotFoundServiceEx;

    Rule get(long id) throws NotFoundServiceEx;

    List<ShortRule> getAll();

//    /**
//     * Return the Rules according to the filter.
//     * <UL>
//     * <LI>If a parameter is set to "*", it will match any null or not null value;</LI>
//     * <LI>If a parameter is set to <TT>null</TT>, it will match only null values;</LI>
//     * <LI>If a parameter is set to other values, it will strictly match the related field value;</LI>
//     * </UL>
//     *
//     * @param userId The (Long) id of the GSUser, OR the "*" String, OR null
//     * @param profileId The (Long) id of the Profile, OR the "*" String, OR null
//     * @param instanceId The (Long) id of the GSInstance, OR the "*" String, OR null
//     *
//     * @param page used for retrieving paged data, may be null if not used. If not null, also <TT>entries</TT> should be defined.
//     * @param entries used for retrieving paged data, may be null if not used. If not null, also <TT>page</TT> should be defined.
//     *
//     * @see RuleReaderService#getMatchingRules(String, String, String,  String,String, String,String) RuleReaderService.getMatchingRules(...)
//     * @deprecated Use {@link getList(RuleFilter,Integer,Integer)}
//     */
//    @GET
//    @Path("/rules/user.id/{userId}/profile.id/{profileId}/instance.id/{instanceId}/{service}/{request}/{workspace}/{layer}")
//    List<ShortRule> getList(@PathParam("userId") String userId,
//        @PathParam("profileId") String profileId,
//        @PathParam("instanceId") String instanceId,
//        @PathParam("service") String service,
//        @PathParam("request") String request,
//        @PathParam("workspace") String workspace,
//        @PathParam("layer") String layer,
//        @QueryParam("page") Integer page,
//        @QueryParam("entries") Integer entries);


    /**
     * Return the Rules according to the filter.
     *
     * @param page used for retrieving paged data, may be null if not used. If not null, also <TT>entries</TT> should be defined.
     * @param entries used for retrieving paged data, may be null if not used. If not null, also <TT>page</TT> should be defined.
     *
     * @see RuleReaderService#getMatchingRules(RuleFilter)
     */
    List<ShortRule> getList(RuleFilter filter, Integer page, Integer entries);

    /**
     * Return the Rules according to the filter.
     * Rules will be enriched with all their joined data, so this method may be heavy to execute.
     *
     * @param page used for retrieving paged data, may be null if not used. If not null, also <TT>entries</TT> should be defined.
     * @param entries used for retrieving paged data, may be null if not used. If not null, also <TT>page</TT> should be defined.
     *
     * @see RuleReaderService#getMatchingRules(RuleFilter)
     */
    List<Rule> getListFull(RuleFilter filter, Integer page, Integer entries);

    /**
     * Return the Rules count according to the filter.
     * @param filter
     * @return
     */
    long count(RuleFilter filter);

    long getCountAll();

    // ==========================================================================
    // ==========================================================================

    void setLimits(Long ruleId, RuleLimits limits);

    // ==========================================================================

    /**
     * <P>
     * When setting new Details, old CustomProps will be retained.
     */
    void setDetails(Long ruleId, LayerDetails details);

    Set<String> getAllowedStyles(Long ruleId);

    void setAllowedStyles(Long ruleId, Set<String> styles);

    // ==========================================================================


}
