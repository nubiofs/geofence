/*
 * $ Header: it.geosolutions.geofence.gui.client.action.application.LogoutAction,v. 0.1 25-gen-2011 11.30.32 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.30.32 $
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
package it.geosolutions.geofence.gui.client.action.application;

import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;

import it.geosolutions.geogwt.gui.client.widget.map.action.ToolbarMapAction;
import it.geosolutions.geofence.gui.client.GeofenceEvents;
import it.geosolutions.geofence.gui.client.Resources;
import it.geosolutions.geofence.gui.client.i18n.I18nProvider;


// TODO: Auto-generated Javadoc
/**
 * The Class LogoutAction.
 */
public class LogoutAction extends ToolbarMapAction
{

    /**
     *
     */
    private static final long serialVersionUID = -8343538202482412376L;

    /**
     * Instantiates a new logout action.
     */
    public LogoutAction()
    {
        super();
    }

    @Override
    public boolean initialize()
    {
        if (!isInitialized())
        {
            setTooltip(I18nProvider.getMessages().logoutTooltip());
            setIcon(Resources.ICONS.logout());
            this.initialiazed = true;
        }

        return isInitialized();
    }

    @Override
    public void performAction(Button button)
    {
        MessageBox.confirm(I18nProvider.getMessages().logoutDialogTitle(), I18nProvider.getMessages().logoutDialogMessage(), new Listener<MessageBoxEvent>()
            {

                public void handleEvent(MessageBoxEvent be)
                {
                    Button btn = be.getButtonClicked();
                    if (btn.getText().equalsIgnoreCase("YES"))
                    {
                        Dispatcher.forwardEvent(GeofenceEvents.LOGOUT);
                    }
                }
            });
    }
}
