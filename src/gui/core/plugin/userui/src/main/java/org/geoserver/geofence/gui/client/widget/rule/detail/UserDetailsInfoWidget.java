/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.rule.detail.UserDetailsInfoWidget,v. 0.1 5-apr-2011 16.30.38 created by tdipisa <tobia.dipisa at geo-solutions.it> $
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

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

import it.geosolutions.geofence.gui.client.model.GSUser;
import it.geosolutions.geofence.gui.client.model.data.UserLimitsInfo;
import it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.widget.GeofenceFormBindingWidget;


/**
 * The Class UserDetailsInfoWidget.
 */
public class UserDetailsInfoWidget extends GeofenceFormBindingWidget<UserLimitsInfo>
{

    /** The user. */
    private GSUser user;

    /** The user service. */
    private GsUsersManagerRemoteServiceAsync usersService;

    /** The user details widget. */
    private UserDetailsWidget userDetailsWidget;

    /** The allowed area. */
    private TextArea allowedArea;

    /**
     * Instantiates a new rule details info widget.
     *
     * @param model
     *            the model
     * @param workspacesService
     *            the workspaces service
     * @param ruleDetailsWidget
     *            the rule details widget
     */
    public UserDetailsInfoWidget(GSUser model, GsUsersManagerRemoteServiceAsync usersService,
        UserDetailsWidget userDetailsWidget)
    {

        super();
        this.user = model;
        this.usersService = usersService;
        this.userDetailsWidget = userDetailsWidget;
        formPanel = createFormPanel();
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.client.widget.GeofenceFormBindingWidget#createFormPanel()
     */
    @Override
    public FormPanel createFormPanel()
    {
        FormPanel fp = new FormPanel();
        fp.setFrame(true);
        fp.setHeaderVisible(false);
        fp.setAutoHeight(true);

        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeading("User Limits");
        fieldSet.setCheckboxToggle(false);
        fieldSet.setCollapsible(true);

        FormLayout layout = new FormLayout();
        fieldSet.setLayout(layout);

        allowedArea = new TextArea();
        allowedArea.setFieldLabel("Allowed Area");
        allowedArea.setPreventScrollbars(true);
        allowedArea.addListener(Events.Change, new Listener<FieldEvent>()
            {

                public void handleEvent(FieldEvent be)
                {
                    userDetailsWidget.enableSaveButton();
                }

            });

        fieldSet.add(allowedArea);

        fp.add(fieldSet);

        return fp;
    }

    /**
     * Gets the model data.
     *
     * @return the model data
     */
    public UserLimitsInfo getModelData()
    {
        UserLimitsInfo userInfo = new UserLimitsInfo();

        String area = allowedArea.getValue();

        String wkt, srid;
        if (area != null)
        {
            if (area.indexOf("SRID=") != -1)
            {
                String[] allowedAreaArray = area.split(";");

                srid = allowedAreaArray[0].split("=")[1];
                wkt = allowedAreaArray[1];
            }
            else
            {
                srid = "4326";
                wkt = area;
            }
        }
        else
        {
            srid = null;
            wkt = null;
        }

        userInfo.setAllowedArea(wkt);
        userInfo.setSrid(srid);
        userInfo.setUserId(user.getId());

        return userInfo;
    }

    /**
     * Bind model data.
     *
     * @param layerDetailsInfo
     *            the layer details info
     */
    public void bindModelData(UserLimitsInfo userInfo)
    {
        this.bindModel(userInfo);

        String area = userInfo.getAllowedArea();
        String srid = userInfo.getSrid();
        if ((area != null) && (srid != null))
        {
            allowedArea.setValue("SRID=" + srid + ";" + area);
        }
        else
        {
            allowedArea.setValue("");
        }
    }

}
