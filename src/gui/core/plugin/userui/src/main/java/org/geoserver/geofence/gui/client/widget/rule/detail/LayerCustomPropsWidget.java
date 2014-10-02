/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.rule.detail.LayerCustomPropsWidget,v. 0.1 25-feb-2011 16.30.38 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import it.geosolutions.geofence.gui.client.model.Rule;
import it.geosolutions.geofence.gui.client.service.RulesManagerRemoteServiceAsync;


// TODO: Auto-generated Javadoc
/**
 * The Class LayerCustomPropsWidget.
 */
public class LayerCustomPropsWidget extends ContentPanel
{

    /** The layer custom props info. */
    private LayerCustomPropsGridWidget layerCustomPropsInfo;

    /** The model. */
    private Rule model;

    /**
     * Instantiates a new layer custom props widget.
     *
     * @param model
     *            the model
     * @param rulesService
     *            the rules service
     */
    public LayerCustomPropsWidget(Rule model, RulesManagerRemoteServiceAsync rulesService)
    {
        this.model = model;

        setHeaderVisible(false);
        setFrame(true);
        setHeight(330);
        setLayout(new FitLayout());

        setLayerCustomPropsInfo(new LayerCustomPropsGridWidget(model, rulesService));

        add(getLayerCustomPropsInfo().getGrid());

        super.setMonitorWindowResize(true);

        setScrollMode(Scroll.NONE);

        setBottomComponent(this.getLayerCustomPropsInfo().getToolBar());
    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.widget.Component#onWindowResize(int, int)
     */
    @Override
    protected void onWindowResize(int width, int height)
    {
        super.setWidth(width - 5);
        super.layout();
    }

    /**
     * Sets the layer custom props info.
     *
     * @param layerCustomPropsInfo
     *            the new layer custom props info
     */
    public void setLayerCustomPropsInfo(LayerCustomPropsGridWidget layerCustomPropsInfo)
    {
        this.layerCustomPropsInfo = layerCustomPropsInfo;
    }

    /**
     * Gets the layer custom props info.
     *
     * @return the layer custom props info
     */
    public LayerCustomPropsGridWidget getLayerCustomPropsInfo()
    {
        return layerCustomPropsInfo;
    }

}
