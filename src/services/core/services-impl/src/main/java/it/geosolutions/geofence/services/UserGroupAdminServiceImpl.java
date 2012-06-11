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
package it.geosolutions.geofence.services;

import it.geosolutions.geofence.core.dao.ProfileDAO;
import it.geosolutions.geofence.core.model.Profile;

import java.util.List;

import org.apache.log4j.Logger;

import com.trg.search.Search;
import it.geosolutions.geofence.services.dto.ShortProfile;
import it.geosolutions.geofence.services.exception.BadRequestServiceEx;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class ProfileAdminServiceImpl implements ProfileAdminService {

    private final static Logger LOGGER = Logger.getLogger(ProfileAdminServiceImpl.class);

    private ProfileDAO profileDAO;

    // ==========================================================================
    @Override
    public long insert(ShortProfile profile) {
        Profile p = new Profile();
        p.setName(profile.getName());
        p.setEnabled(profile.isEnabled());
        p.setDateCreation(profile.getDateCreation());
        profileDAO.persist(p);
        return p.getId();
    }

    @Override
    public long update(ShortProfile profile) throws NotFoundServiceEx {
        Profile orig = profileDAO.find(profile.getId());
        if (orig == null) {
            throw new NotFoundServiceEx("Profile not found", profile.getId());
        }

        orig.setName(profile.getName());
        orig.setEnabled(profile.isEnabled());
        orig.setExtId(profile.getExtId());

        profileDAO.merge(orig);
        return orig.getId();
    }

    @Override
    public Profile get(long id) throws NotFoundServiceEx {
        Profile profile = profileDAO.find(id);

        if (profile == null) {
            throw new NotFoundServiceEx("Profile not found", id);
        }

//        return new ShortProfile(profile);
        return profile;
    }

    @Override
    public boolean delete(long id) throws NotFoundServiceEx {
        Profile profile = profileDAO.find(id);

        if (profile == null) {
            throw new NotFoundServiceEx("Profile not found", id);
        }

        // data on ancillary tables should be deleted by cascading
        return profileDAO.remove(profile);
    }

    @Override
    public List<Profile> getFullList(String nameLike, Integer page, Integer entries) {
        Search searchCriteria = buildCriteria(page, entries, nameLike);
        List<Profile> found = profileDAO.search(searchCriteria);
        for (Profile profile : found) {
            profile.setCustomProps(profileDAO.getCustomProps(profile.getId()));
        }
        return found;
    }

    @Override
    public List<ShortProfile> getList(String nameLike, Integer page, Integer entries) {
        Search searchCriteria = buildCriteria(page, entries, nameLike);
        List<Profile> found = profileDAO.search(searchCriteria);
        return convertToShortList(found);
    }

    @Override
    public long getCount(String nameLike) {
        Search searchCriteria = buildCriteria(null, null, nameLike);
        return profileDAO.count(searchCriteria);
    }

    protected Search buildCriteria(Integer page, Integer entries, String nameLike) throws BadRequestServiceEx {
        if( (page != null && entries == null) || (page ==null && entries != null)) {
            throw new BadRequestServiceEx("Page and entries params should be declared together.");
        }
        Search searchCriteria = new Search(Profile.class);
        if(page != null) {
            searchCriteria.setMaxResults(entries);
            searchCriteria.setPage(page);
        }
        searchCriteria.addSortAsc("name");
        if (nameLike != null) {
            searchCriteria.addFilterILike("name", nameLike);
        }
        return searchCriteria;
    }

    // ==========================================================================

    @Override
    public Map<String, String> getCustomProps(Long id) {
        return profileDAO.getCustomProps(id);
    }

    @Override
    public void setCustomProps(Long id, Map<String, String> props) {
        profileDAO.setCustomProps(id, props);
    }

    // ==========================================================================

    private List<ShortProfile> convertToShortList(List<Profile> list) {
        List<ShortProfile> swList = new ArrayList<ShortProfile>(list.size());
        for (Profile profile : list) {
            swList.add(new ShortProfile(profile));
        }

        return swList;
    }

    // ==========================================================================

    public void setProfileDAO(ProfileDAO profileDao) {
        this.profileDAO = profileDao;
    }

}
