/*
 * $ Header: it.geosolutions.geofence.gui.server.service.impl.ProfilesManagerServiceImpl,v. 0.1 10-feb-2011 17.07.06 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 17.07.06 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.geofence.gui.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.extjs.gxt.ui.client.data.PagingLoadResult;

import it.geosolutions.geofence.gui.client.ApplicationException;
import it.geosolutions.geofence.gui.client.model.Profile;
import it.geosolutions.geofence.gui.client.model.data.ProfileCustomProps;
import it.geosolutions.geofence.gui.client.model.data.rpc.RpcPageLoadResult;
import it.geosolutions.geofence.gui.server.service.IProfilesManagerService;
import it.geosolutions.geofence.gui.service.GeofenceRemoteService;
import it.geosolutions.geofence.services.dto.ShortGroup;
import it.geosolutions.geofence.services.exception.NotFoundServiceEx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


// TODO: Auto-generated Javadoc
/**
 * The Class ProfilesManagerServiceImpl.
 */
@Component("profilesManagerServiceGWT")
public class ProfilesManagerServiceImpl implements IProfilesManagerService
{

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The geofence remote service. */
    @Autowired
    private GeofenceRemoteService geofenceRemoteService;

    /*
     * (non-Javadoc)
     *
     * @see
     * it.geosolutions.geofence.gui.server.service.IFeatureService#loadFeature(com.extjs.gxt.ui.
     * client.data.PagingLoadConfig, java.lang.String)
     */
    public PagingLoadResult<Profile> getProfiles(int offset, int limit, boolean full) throws ApplicationException
    {

        int start = offset;

        List<Profile> profileListDTO = new ArrayList<Profile>();

        if (full)
        {
            Profile all_profile = new Profile();
            all_profile.setId(-1);
            all_profile.setName("*");
            all_profile.setEnabled(true);
            all_profile.setDateCreation(null);
            profileListDTO.add(all_profile);
        }

        long profilesCount = geofenceRemoteService.getProfileAdminService().getCount(null) + 1;

        Long t = new Long(profilesCount);

        int page = (start == 0) ? start : (start / limit);

        List<ShortGroup> profilesList = geofenceRemoteService.getProfileAdminService().getList(
                null, page, limit);

        if (profilesList == null)
        {
            if (logger.isErrorEnabled())
            {
                logger.error("No profile found on server");
            }
            throw new ApplicationException("No profile found on server");
        }

        Iterator<ShortGroup> it = profilesList.iterator();

        while (it.hasNext())
        {
            ShortGroup short_profile = it.next();

            it.geosolutions.geofence.core.model.UserGroup remote_profile;
            try
            {
                remote_profile = geofenceRemoteService.getProfileAdminService().get(
                        short_profile.getId());
            }
            catch (NotFoundServiceEx e)
            {
                if (logger.isErrorEnabled())
                {
                    logger.error("Details for profile " + short_profile.getName() +
                        " not found on Server!");
                }
                throw new ApplicationException("Details for profile " + short_profile.getName() +
                    " not found on Server!");
            }

            Profile local_profile = new Profile();

            local_profile.setId(short_profile.getId());
            local_profile.setName(remote_profile.getName());
            local_profile.setDateCreation(remote_profile.getDateCreation());
            local_profile.setEnabled(remote_profile.getEnabled());
            // TODO: use specific API methods in order to load UserGroup custom props
            // local_profile.setCustomProps(remote_profile.getCustomProps());

            profileListDTO.add(local_profile);
        }

        return new RpcPageLoadResult<Profile>(profileListDTO, offset, t.intValue());
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.server.service.IProfilesManagerService#deleteProfile(it.geosolutions.geofence.gui.client.model.UserGroup)
     */
    public void deleteProfile(Profile profile)
    {
        it.geosolutions.geofence.core.model.UserGroup remote_profile = null;
        try
        {
            remote_profile = geofenceRemoteService.getProfileAdminService().get(profile.getId());
            geofenceRemoteService.getProfileAdminService().delete(remote_profile.getId());
        }
        catch (NotFoundServiceEx e)
        {
            logger.error(e.getLocalizedMessage(), e.getCause());
            throw new ApplicationException(e.getLocalizedMessage(), e.getCause());
        }
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.server.service.IProfilesManagerService#saveProfile(it.geosolutions.geofence.gui.client.model.UserGroup)
     */
    public void saveProfile(Profile profile)
    {
        it.geosolutions.geofence.core.model.UserGroup remote_profile = null;
        if (profile.getId() >= 0)
        {
            try
            {
                remote_profile = geofenceRemoteService.getProfileAdminService().get(profile.getId());

                ShortGroup short_profile = new ShortGroup();
                short_profile.setId(remote_profile.getId());
                short_profile.setName(profile.getName());
                short_profile.setEnabled(profile.isEnabled());
                geofenceRemoteService.getProfileAdminService().update(short_profile);
            }
            catch (NotFoundServiceEx e)
            {
                logger.error(e.getLocalizedMessage(), e.getCause());
                throw new ApplicationException(e.getLocalizedMessage(), e.getCause());
            }
        }
        else
        {
            try
            {
                remote_profile = new it.geosolutions.geofence.core.model.UserGroup();

                ShortGroup short_profile = new ShortGroup();
                short_profile.setName(profile.getName());
                short_profile.setEnabled(profile.isEnabled());
                short_profile.setDateCreation(profile.getDateCreation());
                geofenceRemoteService.getProfileAdminService().insert(short_profile);
            }
            catch (Exception e)
            {
                logger.error(e.getLocalizedMessage(), e.getCause());
                throw new ApplicationException(e.getLocalizedMessage(), e.getCause());
            }
        }
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.server.service.IProfilesManagerService#getProfileCustomProps(com.extjs.gxt.ui.client.data.PagingLoadConfig, it.geosolutions.geofence.gui.client.model.Rule)
     */
    public PagingLoadResult<ProfileCustomProps> getProfileCustomProps(int offset, int limit, Profile profile)
    {
        int start = offset;
        Long t = new Long(0);

        List<ProfileCustomProps> customPropsDTO = new ArrayList<ProfileCustomProps>();

        if ((profile != null) && (profile.getId() >= 0))
        {
            try
            {
                logger.error("TODO: profile refactoring!!! custom props have been removed");
                Map<String, String> customProperties = new HashMap<String, String>();
                customProperties.put("NOTE", "Custom properties are no longer supported. Code is unstable");
//                Map<String, String> customProperties = geofenceRemoteService.getProfileAdminService().getCustomProps(profile.getId());

                if (customProperties == null)
                {
                    if (logger.isErrorEnabled())
                    {
                        logger.error("No property found on server");
                    }
                    throw new ApplicationException("No rule found on server");
                }

                long rulesCount = customProperties.size();

                t = new Long(rulesCount);

                int page = (start == 0) ? start : (start / limit);

                SortedSet<String> sortedset = new TreeSet<String>(customProperties.keySet());
                Iterator<String> it = sortedset.iterator();

                while (it.hasNext())
                {
                    String key = it.next();
                    ProfileCustomProps property = new ProfileCustomProps();
                    property.setPropKey(key);
                    property.setPropValue(customProperties.get(key));
                    customPropsDTO.add(property);
                }

//                for (String key : customProperties.keySet()) {
//                    ProfileCustomProps property = new ProfileCustomProps();
//                    property.setPropKey(key);
//                    property.setPropValue(customProperties.get(key));
//                    customPropsDTO.add(property);
//                }
            }
            catch (Exception e)
            {
                // do nothing!
            }
        }

        return new RpcPageLoadResult<ProfileCustomProps>(customPropsDTO, offset, t.intValue());
    }

    /** (non-Javadoc)
     * @deprecated custom props have been removed
     */
    public void setProfileProps(Long profileId, List<ProfileCustomProps> customProps)
    {
        Map<String, String> props = new HashMap<String, String>();

        for (ProfileCustomProps prop : customProps)
        {
            props.put(prop.getPropKey(), prop.getPropValue());
        }

//        LayerDetails details = null;
//        try {
//            details = geofenceRemoteService.getRuleAdminService().getDetails(ruleId);
//        } catch (Exception e) {
//            details = new LayerDetails();
//            geofenceRemoteService.getRuleAdminService().setDetails(ruleId, details);
//        }

        logger.error("TODO: profile refactoring!!! custom props have been removed");
//        geofenceRemoteService.getProfileAdminService().setCustomProps(profileId, props);
    }
}
