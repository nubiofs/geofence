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
package it.geosolutions.geofence.services;

import com.googlecode.genericdao.search.Search;
import it.geosolutions.geofence.services.dto.ShortUser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.geosolutions.geofence.core.dao.GFUserDAO;
import it.geosolutions.geofence.core.model.GFUser;
import it.geosolutions.geofence.services.exception.BadRequestServiceEx;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GFUserAdminServiceImpl implements GFUserAdminService {

    private final static Logger LOGGER = Logger.getLogger(GFUserAdminServiceImpl.class);

    private GFUserDAO gfUserDAO;

    // ==========================================================================
    @Override
    public long insert(GFUser user) {
        gfUserDAO.persist(user);
        return user.getId();
    }

    @Override
    public long update(GFUser user) throws NotFoundServiceEx {
        GFUser orig = gfUserDAO.find(user.getId());
        if (orig == null) {
            throw new NotFoundServiceEx("User not found", user.getId());
        }

        gfUserDAO.merge(user);
        return orig.getId();
    }

    @Override
    public GFUser get(long id) throws NotFoundServiceEx {
        GFUser user = gfUserDAO.find(id);

        if (user == null) {
            throw new NotFoundServiceEx("User not found", id);
        }

        return user;
    }

    @Override
    public boolean delete(long id) throws NotFoundServiceEx {
        // data on ancillary tables should be deleted by cascading
        return gfUserDAO.removeById(id);
    }

    @Override
    public List<GFUser> getFullList(String nameLike, Integer page, Integer entries) {

        if( (page != null && entries == null) || (page ==null && entries != null)) {
            throw new BadRequestServiceEx("Page and entries params should be declared together.");
        }

        Search searchCriteria = new Search(GFUser.class);

        if(page != null) {
            searchCriteria.setMaxResults(entries);
            searchCriteria.setPage(page);
        }

        searchCriteria.addSortAsc("name");

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        List<GFUser> found = gfUserDAO.search(searchCriteria);
        return found;
    }

    @Override
    public List<ShortUser> getList(String nameLike, Integer page, Integer entries) {
        return convertToShortList(getFullList(nameLike, page, entries));
    }

    @Override
    public long getCount(String nameLike) {
        Search searchCriteria = new Search(GFUser.class);

        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }

        return gfUserDAO.count(searchCriteria);
    }

    // ==========================================================================

    private List<ShortUser> convertToShortList(List<GFUser> list) {
        List<ShortUser> swList = new ArrayList<ShortUser>(list.size());
        for (GFUser user : list) {
            swList.add(new ShortUser(user));
        }

        return swList;
    }

    // ==========================================================================

    public void setGfUserDAO(GFUserDAO userDao) {
        this.gfUserDAO = userDao;
    }

}
