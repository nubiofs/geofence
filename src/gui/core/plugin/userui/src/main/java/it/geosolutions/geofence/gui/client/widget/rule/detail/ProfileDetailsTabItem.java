/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.rule.detail.LayerCustomPropsTabItem,v. 0.1 8-feb-2011 15.06.43 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 8-feb-2011 15.06.43 $
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
package it.geosolutions.geofence.gui.client.widget.rule.detail;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.TabItem;

import it.geosolutions.geofence.gui.client.Resources;
import it.geosolutions.geofence.gui.client.model.Profile;
import it.geosolutions.geofence.gui.client.service.ProfilesManagerRemoteServiceAsync;


/**
 * The Class ProfileDetailsTabItem.
 */
public class ProfileDetailsTabItem extends TabItem
{

    /** The profile details widget. */
    private ProfileDetailsWidget profileDetailsWidget;

    private Profile profile;

    /**
     * Instantiates a new profile details tab item.
     *
     * @param tabItemId
     *            the tab item id
     */
    private ProfileDetailsTabItem(String tabItemId)
    {
        // TODO: add I18n message
        // super(I18nProvider.getMessages().profiles());
        super("Profile Details");
        setId(tabItemId);
        setIcon(Resources.ICONS.table());
    }

    /**
     * Instantiates a new Profile Details tab item.
     *
     * @param tabItemId
     *            the tab item id
     * @param model
     * @param rulesService
     *            the rules service
     */
    public ProfileDetailsTabItem(String tabItemId, Profile profile, ProfilesManagerRemoteServiceAsync profilesService)
    {
        this(tabItemId);
        this.profile = profile;

        setProfileDetailsWidget(new ProfileDetailsWidget(this.profile, profilesService));
        add(getProfileDetailsWidget());

        setScrollMode(Scroll.NONE);

        getProfileDetailsWidget().getProfileDetailsInfo().getLoader().load(0, it.geosolutions.geofence.gui.client.Constants.DEFAULT_PAGESIZE);

    }

    /**
     * @return the profileDetailsWidget
     */
    public ProfileDetailsWidget getProfileDetailsWidget()
    {
        return profileDetailsWidget;
    }

    /**
     * @param profileDetailsWidget the profileDetailsWidget to set
     */
    public void setProfileDetailsWidget(ProfileDetailsWidget profileDetailsWidget)
    {
        this.profileDetailsWidget = profileDetailsWidget;
    }

}
