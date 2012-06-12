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
package it.geosolutions.geofence.services.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import it.geosolutions.geofence.services.rest.exception.BadRequestRestEx;
import it.geosolutions.geofence.services.rest.exception.NotFoundRestEx;
import it.geosolutions.geofence.services.rest.model.RESTInputRule;
import it.geosolutions.geofence.services.rest.model.RESTOutputRule;
import it.geosolutions.geofence.services.rest.model.config.RESTFullRuleList;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;


/**
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */

@Path("/")
public interface RESTRuleService
{

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    RESTFullRuleList get(
        @QueryParam("page") Integer page,
        @QueryParam("entries") Integer entries,
        @QueryParam("full")@DefaultValue("false")  boolean full,

        @QueryParam("userId")   Integer userId,
        @QueryParam("userName") String userName,
        @QueryParam("userAny")  Boolean userAny,

        @QueryParam("groupId")   Integer groupId,
        @QueryParam("groupName") String groupName,
        @QueryParam("groupAny")  Boolean groupAny,

        @QueryParam("instanceId")   Integer instanceId,
        @QueryParam("instanceName") String  instanceName,
        @QueryParam("instanceAny")  Boolean instanceAny,

        @QueryParam("service")     String  serviceName,
        @QueryParam("serviceAny")  Boolean serviceAny,

        @QueryParam("request")     String  requestName,
        @QueryParam("requestAny")  Boolean requestAny,

        @QueryParam("workspace") String  workspace,
        @QueryParam("workspaceAny")  Boolean workspaceAny,

        @QueryParam("layer") String  layer,
        @QueryParam("layerAny")  Boolean layerAny
    );

    @GET
    @Path("/count")
    long count(
        @QueryParam("userId")   Integer userId,
        @QueryParam("userName") String userName,
        @QueryParam("userAny")  Boolean userAny,

        @QueryParam("groupId")   Integer groupId,
        @QueryParam("groupName") String groupName,
        @QueryParam("groupAny")  Boolean groupAny,

        @QueryParam("instanceId")   Integer instanceId,
        @QueryParam("instanceName") String  instanceName,
        @QueryParam("instanceAny")  Boolean instanceAny,

        @QueryParam("service")     String  serviceName,
        @QueryParam("serviceAny")  Boolean serviceAny,

        @QueryParam("request")     String  requestName,
        @QueryParam("requestAny")  Boolean requestAny,

        @QueryParam("workspace") String  workspace,
        @QueryParam("workspaceAny")  Boolean workspaceAny,

        @QueryParam("layer") String  layer,
        @QueryParam("layerAny")  Boolean layerAny
    );

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    RESTOutputRule get(@PathParam("id") Long id) throws BadRequestRestEx, NotFoundRestEx;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    Long insert(@Multipart("rule") RESTInputRule rule) throws BadRequestRestEx, NotFoundRestEx;


    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    void update(@PathParam("id") Long id,
        @Multipart("rule") RESTInputRule rule) throws BadRequestRestEx, NotFoundRestEx;

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    void delete(@PathParam("id") Long id) throws BadRequestRestEx, NotFoundRestEx;

}
