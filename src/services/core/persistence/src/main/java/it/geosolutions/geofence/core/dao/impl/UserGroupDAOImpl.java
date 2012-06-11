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
package it.geosolutions.geofence.core.dao.impl;

import java.util.Date;
import java.util.List;

import com.googlecode.genericdao.search.ISearch;

import it.geosolutions.geofence.core.dao.UserGroupDAO;
import it.geosolutions.geofence.core.model.UserGroup;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;


/**
 * Public implementation of the UserGroupDAO interface
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@Transactional(value = "geofenceTransactionManager")
public class UserGroupDAOImpl extends BaseDAO<UserGroup, Long>
    // extends GenericDAOImpl<User, Long>
    implements UserGroupDAO
{

    private static final Logger LOGGER = Logger.getLogger(UserGroupDAOImpl.class);

    @Override
    public void persist(UserGroup... entities)
    {
        Date now = new Date();
        for (UserGroup e : entities)
        {
            e.setDateCreation(now);
        }

        super.persist(entities);
    }

    @Override
    public List<UserGroup> findAll()
    {
        return super.findAll();
    }

    @Override
    public List<UserGroup> search(ISearch search)
    {
        return super.search(search);
    }

    @Override
    public UserGroup merge(UserGroup entity)
    {
        return super.merge(entity);
    }

    @Override
    public boolean remove(UserGroup entity)
    {
        return super.remove(entity);
    }

    @Override
    public boolean removeById(Long id)
    {
        return super.removeById(id);
    }

    // ==========================================================================

}
