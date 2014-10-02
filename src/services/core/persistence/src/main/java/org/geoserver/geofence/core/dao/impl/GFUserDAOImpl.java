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
package it.geosolutions.geofence.core.dao.impl;


import it.geosolutions.geofence.core.dao.GFUserDAO;
import it.geosolutions.geofence.core.model.GFUser;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.ISearch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Public implementation of the GSUserDAO interface
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@Transactional(value = "geofenceTransactionManager")
public class GFUserDAOImpl extends BaseDAO<GFUser, Long> implements GFUserDAO
{

    private static final Logger LOGGER = LogManager.getLogger(GFUserDAOImpl.class);

    @Override
    public void persist(GFUser... entities)
    {
        Date now = new Date();
        for (GFUser user : entities)
        {
            user.setDateCreation(now);
        }
        super.persist(entities);
    }

    @Override
    public List<GFUser> findAll()
    {
        return super.findAll();
    }

    @Override
    public List<GFUser> search(ISearch search)
    {
        return super.search(search);
    }

    @Override
    public GFUser merge(GFUser entity)
    {
        return super.merge(entity);
    }

    @Override
    public boolean remove(GFUser entity)
    {
        return super.remove(entity);
    }

    @Override
    public boolean removeById(Long id)
    {
        return super.removeById(id);
    }

}
