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
package it.geosolutions.geofence.services.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.services.rest.exception.BadRequestRestEx;
import it.geosolutions.geofence.services.rest.exception.NotFoundRestEx;
import it.geosolutions.geofence.services.rest.model.config.RESTFullRuleList;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;


/**
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */

@Path("/")
public interface RESTRuleService
{

    /**
     * @return a sample user list
     * */

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    RESTFullRuleList getRules(
        @QueryParam("page") Integer page,
        @QueryParam("entries") Integer entries);

    @GET
    @Path("/count/{nameLike}")
    long getCount(@PathParam("nameLike") String nameLike);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    GSUser get(@PathParam("id") Long id) throws BadRequestRestEx, NotFoundRestEx;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    Long insert(@Multipart("user") GSUser user) throws BadRequestRestEx, NotFoundRestEx;


    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    void update(@PathParam("id") Long id,
        @Multipart("user") GSUser user) throws BadRequestRestEx, NotFoundRestEx;

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    void delete(@PathParam("id") Long id) throws BadRequestRestEx, NotFoundRestEx;

}
