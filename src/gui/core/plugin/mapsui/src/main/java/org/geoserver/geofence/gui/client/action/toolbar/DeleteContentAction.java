/*
 * $ Header: it.geosolutions.geofence.gui.client.action.toolbar.DeleteContentAction,v. 0.1 25-gen-2011 11.30.33 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.30.33 $
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
package it.geosolutions.geofence.gui.client.action.toolbar;

import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;

import it.geosolutions.geogwt.gui.client.widget.map.action.ToolbarMapAction;
import it.geosolutions.geofence.gui.client.GeofenceEvents;


// TODO: Auto-generated Javadoc
/**
 * The Class DeleteContentAction.
 */
public class DeleteContentAction extends ToolbarMapAction
{

    /**
     *
     */
    private static final long serialVersionUID = 3253090668327732004L;

    /**
     * Instantiates a new delete content action.
     */
    public DeleteContentAction()
    {
        super();
    }

    @Override
    public boolean initialize()
    {
        // TODO Auto-generated method stub

        // I18nProvider.getMessages().deleteContentToolTip();

        return false;
    }

    @Override
    public void performAction(Button button)
    {
        Dispatcher.forwardEvent(GeofenceEvents.DELETE_CONTENT);
    }
}
