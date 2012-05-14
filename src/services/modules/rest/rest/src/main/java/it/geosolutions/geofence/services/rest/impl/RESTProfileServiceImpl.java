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

package it.geosolutions.geofence.services.rest.impl;

import java.util.List;
import java.util.Map;

import it.geosolutions.geofence.core.model.Profile;
import it.geosolutions.geofence.services.ProfileAdminService;
import it.geosolutions.geofence.services.dto.ShortProfile;
import it.geosolutions.geofence.services.exception.BadRequestServiceEx;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;
import it.geosolutions.geofence.services.rest.RESTProfileService;
import it.geosolutions.geofence.services.rest.exception.BadRequestRestEx;
import it.geosolutions.geofence.services.rest.exception.InternalErrorRestEx;
import it.geosolutions.geofence.services.rest.exception.NotFoundRestEx;
import it.geosolutions.geofence.services.rest.model.RESTInputProfile;
import it.geosolutions.geofence.services.rest.model.config.RESTFullProfileList;

import org.apache.log4j.Logger;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RESTProfileServiceImpl implements RESTProfileService
{
    private static final Logger LOGGER = Logger.getLogger(RESTProfileServiceImpl.class);

    private ProfileAdminService profileService;

    @Override
    public RESTFullProfileList getProfiles(String nameLike, Integer page, Integer entries)
    {
        List<Profile> profiles = profileService.getFullList(nameLike, page, entries);

        // load lazy data
        for (Profile profile : profiles)
        {
            Map<String, String> props = profileService.getCustomProps(profile.getId());
            profile.setCustomProps(props);
        }

        RESTFullProfileList ret = new RESTFullProfileList();
        ret.setList(profiles);

        return ret;
    }

    @Override
    public long getCount(String nameLike)
    {
        return profileService.getCount(nameLike);
    }

    @Override
    public void delete(Long id) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx
    {
        try
        {
            profileService.delete(id);
        }
        catch (NotFoundServiceEx ex)
        {
            LOGGER.warn(ex);
            throw new NotFoundRestEx("Profile not found");
        }
        catch (Exception ex)
        {
            LOGGER.error(ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }
    }

    @Override
    public Profile get(Long id) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx
    {
        try
        {
            Profile ret = profileService.get(id);

            return ret;
        }
        catch (NotFoundServiceEx ex)
        {
            LOGGER.warn(ex);
            throw new NotFoundRestEx("Profile not found");
        }
        catch (Exception ex)
        {
            LOGGER.error(ex);
            throw new InternalErrorRestEx(ex.getMessage());
        }
    }

    @Override
    public Long insert(RESTInputProfile profile) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx
    {

//        if(profile.getId() != null)
//            throw new BadRequestServiceEx("Id can't be set externally");
//        if(profile.getDateCreation() != null)
//            throw new BadRequestServiceEx("Creation date can't be set externally");

        ShortProfile insert = new ShortProfile();
        insert.setEnabled(profile.getEnabled());
        insert.setExtId(profile.getExtId());
        insert.setName(profile.getName());

        Long id = profileService.insert(insert);

        if (profile.getCustomProps() != null)
        {
            LOGGER.info("examinig custom props: " + profile.getCustomProps().keySet());
            for (Map.Entry<String, String> entry : profile.getCustomProps().entrySet())
            {
                LOGGER.info(" --> prop " + entry.getKey() + ":" + entry.getValue());
            }

            Map<String, String> props = profileService.getCustomProps(id);
            props.keySet().retainAll(profile.getCustomProps().keySet());
            props.putAll(profile.getCustomProps());
            profileService.setCustomProps(id, props);
        }
        else
        {
            LOGGER.info("No custom props to insert");
        }

        return id;
    }

    @Override
    public void update(Long id, RESTInputProfile profile) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx
    {

        try
        {
            Profile old = profileService.get(id);

            if ((profile.getExtId() != null) && !profile.getExtId().equals(old.getExtId()))
            {
                throw new BadRequestServiceEx("ExtId can't be updated");
            }

            ShortProfile shortProfile = new ShortProfile(old);

            if (profile.getEnabled() != null)
            {
                shortProfile.setEnabled(profile.getEnabled());
            }

            if (profile.getName() != null)
            {
                shortProfile.setName(profile.getName());
            }

            profileService.update(shortProfile);

            if (profile.getCustomProps() != null)
            {
                profileService.setCustomProps(id, profile.getCustomProps());
            }

        }
        catch (NotFoundServiceEx ex)
        {
            LOGGER.warn("Profile not found: " + id);
            throw new NotFoundRestEx("Profile not found: " + id);
        }
    }


    // ==========================================================================
    // ==========================================================================


    public void setProfileAdminService(ProfileAdminService profileService)
    {
        this.profileService = profileService;
    }

    // ==========================================================================


//    class ProfileCache {
//        private final Map<String, Profile> cache = new HashMap<String, Profile>();
//        private final ProfileDAO profileDAO;
//
//        public ProfileCache(ProfileDAO profileDAO) {
//            this.profileDAO = profileDAO;
//        }
//
//        public Profile get(String sguId) {
//            Profile profile = cache.get(sguId);
//            if(profile == null) {
//                Search search = new Search(Profile.class).addFilterEqual("extId", sguId);
//                List<Profile> profileList = profileDAO.search(search);
//                if(profileList.isEmpty()) {
//                    LOGGER.warn("Profile sgu:" + sguId + " not found in Geofence. Make sure the profiles are in synch.");
//                    // should we put a null in the cache?
//                } else {
//                    profile = profileList.get(0);
//                    cache.put(sguId, profile);
//                }
//            }
//            return profile;
//        }
//    }
}
