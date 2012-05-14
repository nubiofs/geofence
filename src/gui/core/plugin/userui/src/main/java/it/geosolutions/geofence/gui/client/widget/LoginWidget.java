/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.LoginWidget,v. 0.1 25-feb-2011 16.31.40 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;

import it.geosolutions.geofence.gui.client.GeofenceEvents;
import it.geosolutions.geofence.gui.client.Resources;
import it.geosolutions.geofence.gui.client.i18n.I18nProvider;
import it.geosolutions.geofence.gui.client.widget.LoginStatus.EnumLoginStatus;


// TODO: Auto-generated Javadoc
/**
 * The Class LoginWidget.
 */
public class LoginWidget extends Dialog
{

    /** The user name. */
    protected TextField<String> userName;

    /** The password. */
    protected TextField<String> password;

    /** The reset. */
    protected Button reset;

    /** The login. */
    protected Button login;

    /** The status. */
    protected LoginStatus status;

    /** The USERNAM e_ mi n_ length. */
    private int USERNAME_MIN_LENGTH = 4;

    /** The PASSWOR d_ mi n_ length. */
    private int PASSWORD_MIN_LENGTH = 4;

    /**
     * Instantiates a new login widget.
     */
    public LoginWidget()
    {
        FormLayout layout = new FormLayout();
        layout.setLabelWidth(90);
        layout.setDefaultWidth(175);
        setLayout(layout);

        setButtonAlign(HorizontalAlignment.LEFT);
        setButtons("");
        setIcon(Resources.ICONS.user());
        setHeading(I18nProvider.getMessages().loginWidgetTitle());
        setModal(true);
        setBodyBorder(true);
        setBodyStyle("padding: 8px;background: none");
        setWidth(320);
        setResizable(false);
        setClosable(false);

        KeyListener keyListener = new KeyListener()
            {
                @Override
                public void componentKeyUp(ComponentEvent event)
                {
                    if (userName.isDirty() || password.isDirty())
                    {
                        boolean loginInfoOk = validate();

                        if (loginInfoOk && (event.getKeyCode() == '\r'))
                        {
                            event.cancelBubble();
                            onSubmit();
                        }
                    }
                }
            };

        userName = new TextField<String>();
        userName.setMinLength(USERNAME_MIN_LENGTH);
        userName.setFieldLabel(I18nProvider.getMessages().usernameLabel());
        userName.addKeyListener(keyListener);
        add(userName);

        password = new TextField<String>();
        password.setMinLength(PASSWORD_MIN_LENGTH);
        password.setPassword(true);
        password.setFieldLabel(I18nProvider.getMessages().passwordLabel());
        password.addKeyListener(keyListener);
        add(password);

        setFocusWidget(userName);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.widget.Dialog#createButtons()
     */
    @Override
    protected void createButtons()
    {
        super.createButtons();
        status = new LoginStatus();

        status.setAutoWidth(true);
        getButtonBar().add(status);

        getButtonBar().add(new FillToolItem());

        reset = new Button(I18nProvider.getMessages().resetLabel());
        reset.setIconStyle("x-geofence-reset");
        reset.addSelectionListener(new SelectionListener<ButtonEvent>()
            {
                @Override
                public void componentSelected(ButtonEvent ce)
                {
                    reset();
                }

            });

        login = new Button(I18nProvider.getMessages().loginLabel());
        login.setIconStyle("x-geofence-login");
        login.disable();
        login.addSelectionListener(new SelectionListener<ButtonEvent>()
            {
                @Override
                public void componentSelected(ButtonEvent ce)
                {
                    onSubmit();
                }
            });

        addButton(reset);
        addButton(login);

    }

    /**
     * Reset.
     */
    public void reset()
    {
        userName.reset();
        password.reset();
        validate();
        userName.focus();
        status.clearStatus("");
    }

    /**
     * Reset password.
     */
    public void resetPassword()
    {
        password.reset();
        validate();
        password.focus();
    }

    /**
     * Error connection.
     */
    public void errorConnection()
    {
        userName.reset();
        password.reset();
        validate();
        userName.focus();
        status.clearStatus("");
        getButtonBar().enable();
    }

    /**
     * On submit.
     */
    protected void onSubmit()
    {
        status.setBusy(I18nProvider.getMessages().pleaseWaitMessage());
        getButtonBar().disable();

        Dispatcher.forwardEvent(GeofenceEvents.LOGIN, new String[]
            {
                userName.getValue(),
                password.getValue()
            });
    }

    /**
     * Checks for value.
     *
     * @param field
     *            the field
     * @return true, if successful
     */
    protected boolean hasValue(TextField<String> field)
    {
        return (field.getValue() != null) && (field.getValue().length() > 0);
    }

    /**
     * Validate.
     *
     * @return true, if successful
     */
    protected boolean validate()
    {
        boolean loginInfoOk = hasValue(userName) && hasValue(password) &&
            (password.getValue().length() >= PASSWORD_MIN_LENGTH);
        login.setEnabled(loginInfoOk);

        return loginInfoOk;
    }

    /**
     * Sets the status login finder.
     *
     * @param status
     *            the status
     * @param message
     *            the message
     */
    public void setStatusLoginFinder(EnumLoginStatus status, EnumLoginStatus message)
    {
        this.status.setIconStyle(status.getValue());
        this.status.setText(message.getValue());
        getButtonBar().enable();
    }

}
