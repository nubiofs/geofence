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
package it.geosolutions.geofence.ldap.dao.impl;

import static it.geosolutions.geofence.core.dao.util.SearchUtil.addSearchField;

import java.util.List;

import it.geosolutions.geofence.core.dao.GSUserDAO;
import it.geosolutions.geofence.core.dao.UserGroupDAO;
import it.geosolutions.geofence.core.dao.impl.RuleDAOImpl;
import it.geosolutions.geofence.core.model.GSUser;
import it.geosolutions.geofence.core.model.Rule;
import it.geosolutions.geofence.core.model.UserGroup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.googlecode.genericdao.search.Search;

/**
 * Implementation of RuleDAO compatible with ldap user and group daos.
 * 
 * It persists user and groups to db when a rule is bound to a new user or group.
 * 
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 * 
 */
public class RuleDAOLdapImpl extends RuleDAOImpl {
    
    private static final Logger LOGGER = LogManager.getLogger(RuleDAOLdapImpl.class);

    private GSUserDAO userDao;

    private UserGroupDAO userGroupDao;

    /**
     * Original JPA GSUserDAO. Used to persist new users from ldap to db.
     * 
     * @param userDao the userDao to set
     */
    public void setUserDao(GSUserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * Original JPA UserGroupDAO. Used to persist new groups from ldap to db.
     * 
     * @param userGroupDao the userGroupDao to set
     */
    public void setUserGroupDao(UserGroupDAO userGroupDao) {
        this.userGroupDao = userGroupDao;
    }

    @Override
    public void persist(Rule... entities) {
        for (Rule rule : entities) {
            checkUserAndGroup(rule);
        }
        super.persist(entities);
    }

    /**
     * Checks a rule, to identify users or groups not persisted on db. If any is found, they are persisted before the rule, to avoid referential
     * integrity issues.
     * 
     * @param rule
     */
    private void checkUserAndGroup(Rule entity) {
        if (notPersistedUser(entity)) {
            // create a new persistable user, persist it and update the rule
            GSUser user = copyUser(entity.getGsuser());
            userDao.persist(user);
            entity.setGsuser(user);
        }
        if (notPersistedGroup(entity)) {
            // create a new persistable group, persist it and update the rule
            UserGroup group = copyGroup(entity.getUserGroup());
            userGroupDao.persist(group);
            entity.setUserGroup(group);
        }
        else {
            UserGroup group = getGroup(entity.getUserGroup());
            entity.setUserGroup(group);
        }
    }

    private UserGroup getGroup(UserGroup userGroup) {
        UserGroup group = userGroupDao.find(userGroup.getId());
        if (group == null)
        {
            Search search = new Search(UserGroup.class);
            addSearchField(search, "name", userGroup.getName());
            List<UserGroup> found = userGroupDao.search(search);
            int numFound = found.size();
            LOGGER.debug("[getGroup] -> ('name' = "+userGroup.getName()+") : " + numFound);
            if (numFound > 0) {
                group = found.get(0);
            }
        }
        
        return group;
    }

    /**
     * Checks if the rule has a group defined, and if it is persisted.
     * 
     * @param rule
     * @return
     */
    private boolean notPersistedGroup(Rule rule) {
        if (rule.getUserGroup() != null)
        {
            if (userGroupDao.find(rule.getUserGroup().getId()) == null)
            {
                Search search = new Search(UserGroup.class);
                addSearchField(search, "name", rule.getUserGroup().getName());
                int numFound = userGroupDao.search(search).size();
                LOGGER.debug("[notPersistedGroup] -> ('name' = "+rule.getUserGroup().getName()+") : " + numFound);
    
                return numFound <= 0;
            }
            
            return true;
        }
        
        return true;
    }

    /**
     * Checks if the rule has a user defined, and if it is persisted.
     * 
     * @param rule
     * @return
     */
    private boolean notPersistedUser(Rule rule) {
        return rule.getGsuser() != null && userDao.find(rule.getGsuser().getId()) == null;
    }

    @Override
    public Rule merge(Rule entity) {
        checkUserAndGroup(entity);
        return super.merge(entity);

    }

    /**
     * Creates a persistable copy of the given user.
     * 
     * @param user
     */
    private GSUser copyUser(GSUser user) {
        GSUser newUser = new GSUser();
        newUser.setId(user.getId());
        newUser.setName(user.getName());
        newUser.setFullName(user.getFullName());
        newUser.setEmailAddress(user.getEmailAddress());
        newUser.setEnabled(true);
        newUser.setAdmin(user.isAdmin());
        newUser.setPassword(user.getPassword());
        // set external id to negative ldap id, so that it's easily identifiable in
        // searches
        newUser.setExtId(-user.getId() + "");
        newUser.setDateCreation(user.getDateCreation());
        return newUser;
    }

    /**
     * Creates a persistable copy of the given group.
     * 
     * @param user
     */
    private UserGroup copyGroup(UserGroup group) {
        UserGroup newGroup = new UserGroup();
        newGroup.setId(group.getId());
        newGroup.setName(group.getName());
        newGroup.setExtId(-group.getId() + "");
        newGroup.setEnabled(true);
        return newGroup;
    }

}
