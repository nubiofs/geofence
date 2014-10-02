/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.rule.detail.LayerAttributesTabItem,v. 0.1 25-feb-2011 16.30.38 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-feb-2011 16.30.38 $
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

import it.geosolutions.geofence.gui.client.Resources;
import it.geosolutions.geofence.gui.client.model.Rule;
import it.geosolutions.geofence.gui.client.service.RulesManagerRemoteServiceAsync;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.TabItem;


// TODO: Auto-generated Javadoc
/**
 * The Class LayerAttributesTabItem.
 */
public class LayerAttributesTabItem extends TabItem
{

    /** The layer attributes widget. */
    private LayerAttributesWidget layerAttributesWidget;

    /** The model. */
    private Rule model;

    /**
     * Instantiates a new layer attributes tab item.
     *
     * @param tabItemId
     *            the tab item id
     */
    private LayerAttributesTabItem(String tabItemId)
    {
        // TODO: add I18n message
        // super(I18nProvider.getMessages().profiles());
        super("Layer Attributes");
        setId(tabItemId);
        setIcon(Resources.ICONS.table());
    }

    /**
     * Instantiates a new layer attributes tab item.
     *
     * @param tabItemId
     *            the tab item id
     * @param model
     *            the model
     * @param rulesService
     *            the rules service
     */
    public LayerAttributesTabItem(String tabItemId, Rule model, RulesManagerRemoteServiceAsync rulesService)
    {
        this(tabItemId);
        this.model = model;

        setLayerAttributesWidget(new LayerAttributesWidget(model, rulesService));
        add(getLayerAttributesWidget());

        setScrollMode(Scroll.NONE);

        this.addListener(Events.Select, new Listener<BaseEvent>() {

			public void handleEvent(BaseEvent be) {
				getLayerAttributesWidget().getLayerAttributesInfo().getLoader().load();
			}

		});
    }

    /**
     * Sets the layer attributes widget.
     *
     * @param layerAttributesWidget
     *            the new layer attributes widget
     */
    public void setLayerAttributesWidget(LayerAttributesWidget layerAttributesWidget)
    {
        this.layerAttributesWidget = layerAttributesWidget;
    }

    /**
     * Gets the layer attributes widget.
     *
     * @return the layer attributes widget
     */
    public LayerAttributesWidget getLayerAttributesWidget()
    {
        return layerAttributesWidget;
    }

}
