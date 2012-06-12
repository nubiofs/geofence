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

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import it.geosolutions.geofence.core.model.LayerDetails;
import it.geosolutions.geofence.core.model.Rule;
import it.geosolutions.geofence.core.model.RuleLimits;
import it.geosolutions.geofence.services.dto.RuleFilter;
import it.geosolutions.geofence.services.dto.ShortRule;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;


/**
 * Operations on {@link Rule Rule}s.
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@WebService(name = "ProfileAdminService", targetNamespace = "http://geosolutions.it/geofence")
public interface RuleAdminService
{

    // ==========================================================================
    // Basic operations

    // TODO: check how POST work in pure REST
    @POST
    @Path("/rules")
    long insert(@FormParam("rule") Rule rule);

    @PUT
    @Path("/rules/{id}")
    long update(@PathParam("rule") Rule rule) throws NotFoundServiceEx;

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


    @DELETE
    @Path("/rules/{id}")
    boolean delete(@PathParam("id") long id) throws NotFoundServiceEx;


    // Internal, no REST annotations
    void deleteRulesByUser(long userId) throws NotFoundServiceEx;

    // Internal, no REST annotations
    void deleteRulesByGroup(long groupId) throws NotFoundServiceEx;

    @GET
    @Path("/rules/{id}")
    Rule get(@PathParam("id") long id) throws NotFoundServiceEx;

    @GET
    @Path("/rules")
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
     * The same filtering policy as {@link getList(String,String,String,String,String,String,String,Integer,Integer) getList()} is applied.
     * @deprecated Use {@link count(RuleFilter)}
     */
    @GET
    @Path("/rulescount/user.id/{userId}/profile.id/{profileId}/instance.id/{instanceId}/{service}/{request}/{workspace}/{layer}")
    long getCount(@PathParam("userId") String userId,
        @PathParam("profileId") String profileId,
        @PathParam("instanceId") String instanceId,
        @PathParam("service") String service,
        @PathParam("request") String request,
        @PathParam("workspace") String workspace,
        @PathParam("layer") String layer);


    /**
     * Return the Rules count according to the filter.
     * @param filter
     * @return
     */
    long count(RuleFilter filter);

    long getCountAll();

    // ==========================================================================

//    @GET
//    @Path("/rules/{id}/details")
//    LayerDetails getDetails(@PathParam("id") long id) throws ResourceNotFoundFault;

    // ==========================================================================

//    /**
//     * Return the Rules according to the filter.
//     * <P>
//     * Differently from {@link getList(String,String,String,String,String,String,String,Integer,Integer) getList()},
//     *  when a param is set, it will match
//     * all the rules with the corresponding matching field,
//     * plus all the rules having that field set to null.
//     * <BR>Null params will always match.
//     */
//    @GET
//    @Path("/rules/user.id/{userId}/profile.id/{profileId}/instance.id/{instanceId}/{service}/{request}/{workspace}/{layer}")
//    List<ShortRule> getMatchingRules(
//            @PathParam("userId") Long userId,
//            @PathParam("profileId") Long profileId,
//            @PathParam("instanceId") Long instanceId,
//
//            @PathParam("service") String service,
//            @PathParam("request") String request,
//            @PathParam("workspace") String workspace,
//            @PathParam("layer") String layer
//
////            ,@QueryParam("page") Integer page
////            ,@QueryParam("entries") Integer entries
//            );

    // ==========================================================================

    void setLimits(Long ruleId, RuleLimits limits);

    // ==========================================================================

    /**
     * <P>
     * When setting new Details, old CustomProps will be retained.
     */
    void setDetails(Long ruleId, LayerDetails details);

    Map<String, String> getDetailsProps(Long ruleId);

    void setDetailsProps(Long ruleId, Map<String, String> props);

    Set<String> getAllowedStyles(Long ruleId);

    void setAllowedStyles(Long ruleId, Set<String> styles);

    // ==========================================================================


}
