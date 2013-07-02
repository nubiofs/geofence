/*
 *  Copyright (C) 2007 - 2010 GeoSolutions S.A.S.
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
import it.geosolutions.geofence.services.dto.ShortUser;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.codehaus.jra.Delete;
import org.codehaus.jra.Get;
import org.codehaus.jra.HttpResource;
import org.codehaus.jra.Post;
import org.codehaus.jra.Put;


/**
 * Operations on {@link GSUser GSUser}s.
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@WebService(name = "ConfigAdminService", targetNamespace = "http://geosolutions.it/geofence")
public interface ConfigAdminService
{

    // ==========================================================================
    // Basic operations

    @Put
    @HttpResource(location = "/users")
    long insert(@WebParam(name = "user") GSUser user);

    @Post
    @HttpResource(location = "/users")
    long update(@WebParam(name = "user") GSUser user) throws NotFoundServiceEx;

    @Delete
    @HttpResource(location = "/users/{id}")
    boolean delete(@WebParam(name = "id") long id) throws NotFoundServiceEx;

    @Get
    @HttpResource(location = "/users/{id}")
    GSUser get(@WebParam(name = "id") long id) throws NotFoundServiceEx;

    @Get
    @HttpResource(location = "/users")
    List<ShortUser> getAll();

    @Get
    @HttpResource(location = "/users/{nameLike}/{page}/{entries}")
    List<ShortUser> getList(@WebParam(name = "nameLike") String nameLike,
        @WebParam(name = "page") Integer page,
        @WebParam(name = "entries") Integer entries);

    @Get
    @HttpResource(location = "/userscount/{nameLike}")
    long getCount(@WebParam(name = "nameLike") String nameLike);
}
