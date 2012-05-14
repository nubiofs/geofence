/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.UserSearchComponent,v. 0.1 25-feb-2011 16.31.41 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-feb-2011 16.31.41 $
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
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

import it.geosolutions.geofence.gui.client.GeofenceEvents;


// TODO: Auto-generated Javadoc
/**
 * The Class UserSearchComponent.
 */
public class UserSearchComponent
{

    /** The form panel. */
    private FormPanel formPanel;

    /** The search. */
    private Button search;

    /**
     * Instantiates a new user search component.
     */
    public UserSearchComponent()
    {
        this.createFormPanel();
    }

    /**
     * Creates the form panel.
     */
    private void createFormPanel()
    {
        formPanel = new FormPanel();
        formPanel.setFrame(true);
        formPanel.setHeaderVisible(false);
        formPanel.setAutoHeight(true);

        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeading("Search Management");
        fieldSet.setCheckboxToggle(false);
        fieldSet.setCollapsible(false);

        FormLayout layout = new FormLayout();
        fieldSet.setLayout(layout);

        search = new Button("Search", new SelectionListener<ButtonEvent>()
                {

                    @Override
                    public void componentSelected(ButtonEvent ce)
                    {
                        Dispatcher.forwardEvent(GeofenceEvents.SHOW_SEARCH_USER_WIDGET);
                    }
                });

        ButtonBar bar = new ButtonBar();
        bar.setAlignment(HorizontalAlignment.CENTER);

        bar.add(search);

        Button p = new Button("get AOIs");

        Button q = new Button("get Features");

        bar.add(p);
        bar.add(q);

        fieldSet.add(bar);

        formPanel.add(fieldSet);
    }

    /**
     * Gets the form panel.
     *
     * @return the form panel
     */
    public FormPanel getFormPanel()
    {
        return formPanel;
    }

}
