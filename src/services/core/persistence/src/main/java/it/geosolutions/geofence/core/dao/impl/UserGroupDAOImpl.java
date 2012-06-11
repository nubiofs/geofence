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

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.trg.search.ISearch;

import it.geosolutions.geofence.core.dao.ProfileDAO;
import it.geosolutions.geofence.core.model.Profile;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;


/**
 * Public implementation of the ProfileDAO interface
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@Transactional(value = "geofenceTransactionManager")
public class ProfileDAOImpl extends BaseDAO<Profile, Long>
    // extends GenericDAOImpl<User, Long>
    implements ProfileDAO
{

    private static final Logger LOGGER = Logger.getLogger(ProfileDAOImpl.class);

    @Override
    public void persist(Profile... entities)
    {
        Date now = new Date();
        for (Profile profile : entities)
        {
            profile.setDateCreation(now);
        }

        super.persist(entities);
    }

    @Override
    public List<Profile> findAll()
    {
        return super.findAll();
    }

    @Override
    public List<Profile> search(ISearch search)
    {
        return super.search(search);
    }

    @Override
    public Profile merge(Profile entity)
    {
        return super.merge(entity);
    }

    @Override
    public boolean remove(Profile entity)
    {
        return super.remove(entity);
    }

    @Override
    public boolean removeById(Long id)
    {
        return super.removeById(id);
    }

    @Override
    public List<Profile> searchByCustomProp(String key, String value)
    {
        Query q = em().createQuery("FROM Profile p WHERE p.customProps[:key] = :value");
        q.setParameter("key", key);
        q.setParameter("value", value);

        return q.getResultList();
    }

    // ==========================================================================

    @Override
    public Map<String, String> getCustomProps(Long id)
    {
        Profile found = find(id);
        if (found != null)
        {
            Map<String, String> props = found.getCustomProps();

            if ((props != null) && !Hibernate.isInitialized(props))
            {
                Hibernate.initialize(props); // fetch the props
            }

            return props;
        }
        else
        {
            throw new IllegalArgumentException("Profile not found");
        }
    }

    @Override
    public void setCustomProps(Long id, Map<String, String> props)
    {
        Profile profile = find(id);
        if (profile != null)
        {
            profile.setCustomProps(props);
        }
        else
        {
            throw new IllegalArgumentException("Profile not found");
        }
    }
}
