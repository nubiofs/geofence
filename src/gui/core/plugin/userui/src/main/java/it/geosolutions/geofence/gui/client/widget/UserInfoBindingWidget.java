/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.UserInfoBindingWidget,v. 0.1 25-feb-2011 16.31.40 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-feb-2011 16.31.40 $
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
package it.geosolutions.geofence.gui.client.widget;

import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

import it.geosolutions.geofence.gui.client.GeofenceEvents;
import it.geosolutions.geofence.gui.client.Resources;
import it.geosolutions.geofence.gui.client.model.BeanKeyValue;
import it.geosolutions.geofence.gui.client.model.User;


// TODO: Auto-generated Javadoc
/**
 * The Class UserInfoBindingWidget.
 */
public class UserInfoBindingWidget extends GeofenceBindingWidget<User>
{

    /** The form data. */
    private FormData formData;

    /** The user name. */
    private LabelField userName;

    /** The email. */
    private LabelField email;

    /** The email enable. */
    private CheckBox emailEnable;

    /** The rss enable. */
    private CheckBox rssEnable;

    /** The reduced content. */
    private CheckBox reducedContent;

    /** The user enabled. */
    private CheckBox userEnabled;

    /** The new user. */
    private Button newUser;

    /** The update user. */
    private Button updateUser;

    /** The delete user. */
    private Button deleteUser;

    /** The search. */
    private Button search;

    /** The get ao is. */
    private Button getAOIs;

    /** The get features. */
    private Button getFeatures;

    /**
     * Instantiates a new user info binding widget.
     */
    public UserInfoBindingWidget()
    {
        this.init();
    }

    /**
     * Inits the.
     */
    private void init()
    {
        formData = new FormData("-20");
        formPanel = createFormPanel();
        formBinding = new FormBinding(formPanel, true);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geofence.gui.client.widget.GEOFENCEBindingWidget# createFormPanel()
     */
    @Override
    public FormPanel createFormPanel()
    {
        FormPanel fp = new FormPanel();
        fp.setFrame(true);
        fp.setHeaderVisible(false);

        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeading("User Info");
        fieldSet.setCheckboxToggle(false);
        fieldSet.setCollapsible(false);

        FormLayout layout = new FormLayout();
        fieldSet.setLayout(layout);

        userName = new LabelField();
        userName.setId(BeanKeyValue.USER_NAME.getValue());
        userName.setName(BeanKeyValue.USER_NAME.getValue());
        userName.setWidth(150);
        userName.setFieldLabel("User name");

        fieldSet.add(userName, formData);

        email = new LabelField();
        email.setId(BeanKeyValue.EMAIL.getValue());
        email.setName(BeanKeyValue.EMAIL.getValue());
        email.setWidth(150);
        email.setFieldLabel("Mail");

        fieldSet.add(email, formData);

//        emailEnable = new CheckBox();
//        emailEnable.setId(BeanKeyValue.EMAIL_ENABLE.getValue());
//        emailEnable.setName(BeanKeyValue.EMAIL_ENABLE.getValue());
//        emailEnable.setFieldLabel("Email enable");
//        emailEnable.setWidth(150);
//        emailEnable.setEnabled(false);
//
//        fieldSet.add(emailEnable, formData);

//        rssEnable = new CheckBox();
//        rssEnable.setId(BeanKeyValue.RSS_ENABLE.getValue());
//        rssEnable.setName(BeanKeyValue.RSS_ENABLE.getValue());
//        rssEnable.setFieldLabel("Rss enable");
//        rssEnable.setWidth(150);
//        rssEnable.setEnabled(false);
//
//        fieldSet.add(rssEnable, formData);

//        reducedContent = new CheckBox();
//        reducedContent.setId(BeanKeyValue.REDUCED_CONTENT.getValue());
//        reducedContent.setName(BeanKeyValue.REDUCED_CONTENT.getValue());
//        reducedContent.setFieldLabel("Hide Attributions");
//        reducedContent.setWidth(150);
//        reducedContent.setEnabled(false);
//
//        fieldSet.add(reducedContent, formData);

        userEnabled = new CheckBox();
        userEnabled.setId(BeanKeyValue.USER_ENABLED.getValue());
        userEnabled.setName(BeanKeyValue.USER_ENABLED.getValue());
        userEnabled.setFieldLabel("User enabled");
        userEnabled.setWidth(150);
        userEnabled.setEnabled(false);

        fieldSet.add(userEnabled, formData);

        fp.add(fieldSet);

        FlexTable table = new FlexTable();
        table.getElement().getStyle().setProperty("marginLeft", "60px");

        table.setCellSpacing(8);
        table.setCellPadding(4);

        this.newUser = new Button("New", new SelectionListener<ButtonEvent>()
                {

                    @Override
                    public void componentSelected(ButtonEvent ce)
                    {
                        Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE, new String[]
                            {
                                "Add User",
                                "Add User botton pressed."
                            });
                        Dispatcher.forwardEvent(GeofenceEvents.SHOW_ADD_USER_WIDGET);
                    }
                });

        this.newUser.setIcon(Resources.ICONS.userAdd());

        table.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(1, 1, this.newUser);

        this.deleteUser = new Button("Delete", new SelectionListener<ButtonEvent>()
                {

                    @Override
                    public void componentSelected(ButtonEvent ce)
                    {
                        Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE, new String[]
                            {
                                "Delete User", "Delete User button pressed."
                            });
                        MessageBox.confirm("Delete User", "Are you sure to delete profile " +
                            getModel().getName() + " ?", new Listener<MessageBoxEvent>()
                            {

                                public void handleEvent(MessageBoxEvent be)
                                {
                                    Button btn = be.getButtonClicked();
                                    if (btn.getText().equalsIgnoreCase("YES"))
                                    {
                                        Dispatcher.forwardEvent(GeofenceEvents.DELETE_USER, getModel());
                                    }
                                }
                            });
                    }
                });

        this.deleteUser.disable();

        this.deleteUser.setIcon(Resources.ICONS.userDelete());

        table.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(1, 2, this.deleteUser);

        this.updateUser = new Button("Update", new SelectionListener<ButtonEvent>()
                {

                    @Override
                    public void componentSelected(ButtonEvent ce)
                    {
                        Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE, new String[]
                            {
                                "Update User", "Update User button pressed."
                            });
                        Dispatcher.forwardEvent(GeofenceEvents.SHOW_UPDATE_USER_WIDGET, getModel());
                    }
                });

        this.updateUser.disable();

        this.updateUser.setIcon(Resources.ICONS.editUser());

        table.getCellFormatter().setHorizontalAlignment(1, 3, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(1, 3, this.updateUser);

        this.search = new Button("Search", new SelectionListener<ButtonEvent>()
                {

                    @Override
                    public void componentSelected(ButtonEvent ce)
                    {
                        Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE, new String[]
                            {
                                "Search User", "Search User button pressed."
                            });
                        Dispatcher.forwardEvent(GeofenceEvents.SHOW_SEARCH_USER_WIDGET);
                    }
                });

        search.setIcon(Resources.ICONS.search());

        table.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(2, 1, this.search);

        this.getAOIs = new Button("Get AOIs", new SelectionListener<ButtonEvent>()
                {

                    @Override
                    public void componentSelected(ButtonEvent ce)
                    {
                        Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE, new String[]
                            {
                                "Get AOI",
                                "Get AOI button pressed."
                            });
                        Dispatcher.forwardEvent(GeofenceEvents.SEARCH_USER_AOI, getModel());
                    }
                });

        this.getAOIs.disable();
        this.getAOIs.setIcon(Resources.ICONS.getAOIS());

        table.getCellFormatter().setHorizontalAlignment(2, 2, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(2, 2, this.getAOIs);

        this.getFeatures = new Button("Get Features", new SelectionListener<ButtonEvent>()
                {

                    @Override
                    public void componentSelected(ButtonEvent ce)
                    {
                        Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE, new String[]
                            {
                                "Get Features", "Get Features button pressed."
                            });
                        Dispatcher.forwardEvent(GeofenceEvents.SEARCH_USER_GEORSS, getModel());
                    }
                });

        this.getFeatures.disable();
        this.getFeatures.setIcon(Resources.ICONS.getFeatures());

        table.getCellFormatter().setHorizontalAlignment(2, 3, HasHorizontalAlignment.ALIGN_CENTER);

        table.setWidget(2, 3, this.getFeatures);

        fp.add(table);

        return fp;
    }

    /**
     * Enable buttons.
     */
    public void enableButtons()
    {
        this.deleteUser.enable();
        this.updateUser.enable();
        this.getAOIs.enable();
        this.getFeatures.enable();
    }

    /**
     * Disable buttons.
     */
    public void disableButtons()
    {
        this.deleteUser.disable();
        this.updateUser.disable();
        this.getAOIs.disable();
        this.getFeatures.disable();
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geofence.gui.client.widget.GEOFENCEBindingWidget#unBindModel()
     */
    @Override
    public void unBindModel()
    {
        super.unBindModel();
        disableButtons();
    }

}
