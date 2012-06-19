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

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.googlecode.genericdao.search.ISearch;

import it.geosolutions.geofence.core.dao.LayerDetailsDAO;
import it.geosolutions.geofence.core.model.LayerAttribute;
import it.geosolutions.geofence.core.model.LayerDetails;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

/**
 * Public implementation of the RuleLimitsDAO interface
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@Transactional(value = "geofenceTransactionManager")
public class LayerDetailsDAOImpl extends BaseDAO<LayerDetails, Long> implements LayerDetailsDAO {

    private static final Logger LOGGER = Logger.getLogger(LayerDetailsDAOImpl.class);

    @Override
    public void persist(LayerDetails... entities) {
        for (LayerDetails details : entities) {
            if ( details.getRule() == null ) {
                throw new NullPointerException("Rule is not set");
            }
            details.setId(details.getRule().getId());

            for (LayerAttribute attr : details.getAttributes()) {
                if ( attr.getAccess() == null ) {
                    throw new NullPointerException("Null access type for attribute " + attr.getName() + " in " + details);
                }
            }
        }
        super.persist(entities);
    }

//    @Override
//    public LayerDetails find(Long id) {
//        return super.find(id);
//    }
    @Override
    public List<LayerDetails> findAll() {
        return super.findAll();
    }

    @Override
    public List<LayerDetails> search(ISearch search) {
        return super.search(search);
    }

    @Override
    public LayerDetails merge(LayerDetails entity) {
        return super.merge(entity);
    }

    @Override
    public boolean remove(LayerDetails entity) {
        return super.remove(entity);
    }

    @Override
    public boolean removeById(Long id) {
        return super.removeById(id);
    }

    // ==========================================================================

    @Override
    public Set<String> getAllowedStyles(Long id) {
        LayerDetails found = find(id);
        if ( found != null ) {
            Set<String> styles = found.getAllowedStyles();

            if ( (styles != null) && !Hibernate.isInitialized(styles) ) {
                Hibernate.initialize(styles); // fetch the props
            }

            return styles;
        } else {
            throw new IllegalArgumentException("LayerDetails not found");
        }
    }

    @Override
    public void setAllowedStyles(Long id, Set<String> styles) {
        LayerDetails found = find(id);
        if ( found != null ) {
            found.setAllowedStyles(styles);
        } else {
            throw new IllegalArgumentException("LayerDetails not found");
        }
    }
}
