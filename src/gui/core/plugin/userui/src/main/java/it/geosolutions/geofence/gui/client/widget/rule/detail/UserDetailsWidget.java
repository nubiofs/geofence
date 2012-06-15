/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.rule.detail.UserDeatilsWidget,v. 0.1 5-apr-2011 16.30.38 created by tdipisa <tobia.dipisa at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 5-apr-2011 16.30.38 $
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

import it.geosolutions.geofence.gui.client.GeofenceEvents;
import it.geosolutions.geofence.gui.client.Resources;
import it.geosolutions.geofence.gui.client.i18n.I18nProvider;
import it.geosolutions.geofence.gui.client.model.GSUser;
import it.geosolutions.geofence.gui.client.model.data.UserLimitsInfo;
import it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.service.ProfilesManagerRemoteServiceAsync;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;


/**
 * The Class UserDetailsWidget.
 *
 */
public class UserDetailsWidget extends ContentPanel
{

    /** The user. */
    private GSUser user;

    /** The user details info. */
    private UserDetailsInfoWidget userDeatilsInfo;
    
    /** The profiles info. */
    private ProfilesGridWidget profilesInfo;

    /** The tool bar. */
    private ToolBar toolBar;

    /** The save user details button. */
    private Button saveUserDetailsButton;

    private Button cancelButton;

    /**
     * Instantiates a new user limits widget.
     *
     * @param model
     *            the model
     * @param usersService
     *            the user service
     * @param profilesManagerServiceRemote 
     */
    public UserDetailsWidget(GSUser model, GsUsersManagerRemoteServiceAsync usersService, ProfilesManagerRemoteServiceAsync profilesManagerServiceRemote)
    {
        this.user = model;

        setHeaderVisible(false);
        setFrame(true);
        setHeight(330);
        setWidth(690);
        setLayout(new FitLayout());

        userDeatilsInfo = new UserDetailsInfoWidget(this.user, usersService, this);
        //add(userDeatilsInfo.getFormPanel());

        profilesInfo = new ProfilesGridWidget(this.user, usersService, profilesManagerServiceRemote, this);
        add(profilesInfo.getGrid());
        
        super.setMonitorWindowResize(true);

        setScrollMode(Scroll.AUTOY);

        this.toolBar = new ToolBar();

        this.saveUserDetailsButton = new Button("Save");
        saveUserDetailsButton.setIcon(Resources.ICONS.save());
        saveUserDetailsButton.disable();

        saveUserDetailsButton.addListener(Events.OnClick, new Listener<ButtonEvent>()
            {

                public void handleEvent(ButtonEvent be)
                {

                    disableSaveButton();

                    /*UserLimitsInfo userInfoModel = userDeatilsInfo.getModelData();
                    Dispatcher.forwardEvent(GeofenceEvents.SAVE_USER_LIMITS, userInfoModel);*/

                    user.setUserGroups(profilesInfo.getSelectedGroups());
                    Dispatcher.forwardEvent(GeofenceEvents.SAVE_USER_GROUPS, user);
                    
                    Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE,
                        new String[] { "GeoServer Users: Users Limits", "Apply Changes" });

                }
            });

        cancelButton = new Button("Cancel");
        cancelButton.addListener(Events.OnClick, new Listener<ButtonEvent>()
            {
                public void handleEvent(ButtonEvent be)
                {
                    // /////////////////////////////////////////////////////////
                    // Getting the GS USER limits edit dialogs and hiding this
                    // /////////////////////////////////////////////////////////
                    ComponentManager.get().get(I18nProvider.getMessages().userDialogId()).hide();
                }
            });

        this.toolBar.add(new FillToolItem());
        this.toolBar.add(saveUserDetailsButton);
        this.toolBar.add(cancelButton);
        setBottomComponent(this.toolBar);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.widget.Component#onWindowResize(int, int)
     */
    @Override
    protected void onWindowResize(int width, int height)
    {
        super.layout();
    }

    /**
     * Sets the user limits info.
     *
     * @param userDetailsInfoWidget
     *            the new user limits info
     */
    public void setUserDetailsInfo(UserDetailsInfoWidget userDetailsInfoWidget)
    {
        this.userDeatilsInfo = userDetailsInfoWidget;
    }

    /**
     * Gets the user limits info.
     *
     * @return the user limits info
     */
    public UserDetailsInfoWidget getUserDetailsInfo()
    {
        return userDeatilsInfo;
    }

    /**
     * Disable save button.
     */
    public void disableSaveButton()
    {
        if (this.saveUserDetailsButton.isEnabled())
        {
            this.saveUserDetailsButton.disable();
        }
    }

    /**
     * Enable save button.
     */
    public void enableSaveButton()
    {
        if (!this.saveUserDetailsButton.isEnabled())
        {
            this.saveUserDetailsButton.enable();
        }
    }

	/**
	 * @param profilesInfo the profilesInfo to set
	 */
	public void setProfilesInfo(ProfilesGridWidget profilesInfo) {
		this.profilesInfo = profilesInfo;
	}

	/**
	 * @return the profilesInfo
	 */
	public ProfilesGridWidget getProfilesInfo() {
		return profilesInfo;
	}

}
