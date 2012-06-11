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

import javax.persistence.Query;

import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;

import it.geosolutions.geofence.core.dao.RuleDAO;
import it.geosolutions.geofence.core.model.Rule;

import it.geosolutions.geofence.core.model.enums.GrantType;
import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Public implementation of the GSUserDAO interface
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@Transactional(value = "geofenceTransactionManager")
public class RuleDAOImpl extends BaseDAO<Rule, Long> implements RuleDAO {

    private static final Logger LOGGER = Logger.getLogger(RuleDAOImpl.class);

    @Override
    public void persist(Rule... entities) {

        for (Rule rule : entities) {
            // check there are no dups for the rules received
            if ( rule.getAccess() != GrantType.LIMIT ) { // there may be as many LIMIT rules as desired
                Search search = getDupSearch(rule);
                List<Rule> dups = search(search);
                for (Rule dup : dups) {
                    if ( dup.getAccess() != GrantType.LIMIT ) {
                        LOGGER.warn(" ORIG: " + dup);
                        LOGGER.warn(" DUP : " + rule);
                        throw new DuplicateKeyException("Duplicate Rule " + rule);
                    }
                }
//                if (count(search) > 0)
//                {
//                    throw new DuplicateKeyException("Duplicate Rule " + rule);
//                }
            }
        }
        super.persist(entities);
    }

    private Search getDupSearch(Rule rule) {
        Search search = new Search(Rule.class);
        addSearchField(search, "gsuser", rule.getGsuser());
        addSearchField(search, "userGroup", rule.getUserGroup());
        addSearchField(search, "instance", rule.getInstance());
        addSearchField(search, "service", rule.getService());
        addSearchField(search, "request", rule.getRequest());
        addSearchField(search, "workspace", rule.getWorkspace());
        addSearchField(search, "layer", rule.getLayer());

        return search;
    }

    private void addSearchField(Search search, String field, Object o) {
        if ( o == null ) {
            search.addFilterNull(field);
        } else {
            search.addFilterEqual(field, o);
        }
    }

    @Override
    public List<Rule> findAll() {
        return super.findAll();
    }

    @Override
    public List<Rule> search(ISearch search) {
        return super.search(search);
    }

    @Override
    public Rule merge(Rule entity) {
        Search search = getDupSearch(entity);

        // check if we are dup'ing some other Rule.
        List<Rule> existent = search(search);
        switch (existent.size()) {
            case 0:
                break;

            case 1:
                // We may be updating some other fields in this Rule
                if ( !existent.get(0).getId().equals(entity.getId()) ) {
                    throw new DuplicateKeyException("Duplicating Rule " + existent.get(0) + " with " + entity);
                }
                break;

            default:
                throw new IllegalStateException("Too many rules duplicating " + entity);
        }

        return super.merge(entity);
    }

    @Override
    public int shift(long priorityStart, long offset) {
        if ( offset <= 0 ) {
            throw new IllegalArgumentException("Positive offset required");
        }

        Search search = new Search(Rule.class);
        search.addFilterGreaterOrEqual("priority", priorityStart);
        search.addFilterLessThan("priority", priorityStart + offset);
        if ( super.count(search) == 0 ) {
            return -1;
        }

        String hql = "UPDATE Rule SET priority=priority+ :offset WHERE priority >= :priorityStart";

        Query query = em().createQuery(hql);
        query.setParameter("offset", offset);
        query.setParameter("priorityStart", priorityStart);

        return query.executeUpdate();
    }

    @Override
    public void swap(long id1, long id2) {
        Rule rule1 = super.find(id1);
        Rule rule2 = super.find(id2);

        if ( (rule1 == null) || (rule2 == null) ) {
            throw new IllegalArgumentException("Rule not found");
        }

        Long tmp = rule1.getPriority();
        rule1.setPriority(rule2.getPriority());
        rule2.setPriority(tmp);
        super.merge(rule1, rule2);
    }

    @Override
    public boolean remove(Rule entity) {
        return super.remove(entity);
    }

    @Override
    public boolean removeById(Long id) {
        return super.removeById(id);
    }
}
