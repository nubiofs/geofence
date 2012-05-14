/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.rule.detail.RuleDetailsWidget,v. 0.1 25-feb-2011 16.30.38 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.geofence.gui.client.GeofenceEvents;
import it.geosolutions.geofence.gui.client.Resources;
import it.geosolutions.geofence.gui.client.i18n.I18nProvider;
import it.geosolutions.geofence.gui.client.model.Rule;
import it.geosolutions.geofence.gui.client.model.data.LayerLimitsInfo;
import it.geosolutions.geofence.gui.client.service.RulesManagerRemoteServiceAsync;


/**
 * The Class RuleLimitsWidget.
 *
 */
public class RuleLimitsWidget extends ContentPanel
{

    /** The rule. */
    private Rule theRule;

    /** The rule details info. */
    private RuleLimitsInfoWidget ruleLimitsInfo;

    /** The tool bar. */
    private ToolBar toolBar;

    /** The save layer details button. */
    private Button saveLayerDetailsButton;

    private Button cancelButton;

    /**
    * Instantiates a new rule limits widget.
    *
    * @param model
    *            the model
    * @param rulesService
    *            the rule service
    */
    public RuleLimitsWidget(Rule model, RulesManagerRemoteServiceAsync rulesService)
    {
        this.theRule = model;

        setHeaderVisible(false);
        setFrame(true);
        setHeight(330);
        setWidth(690);
        setLayout(new FitLayout());

        ruleLimitsInfo = new RuleLimitsInfoWidget(this.theRule, rulesService, this);
        add(ruleLimitsInfo.getFormPanel());

        super.setMonitorWindowResize(true);

        setScrollMode(Scroll.AUTOY);

        this.toolBar = new ToolBar();

        this.saveLayerDetailsButton = new Button("Save");
        saveLayerDetailsButton.setIcon(Resources.ICONS.save());
        saveLayerDetailsButton.disable();

        saveLayerDetailsButton.addListener(Events.OnClick, new Listener<ButtonEvent>()
            {

                public void handleEvent(ButtonEvent be)
                {

                    disableSaveButton();

                    LayerLimitsInfo limitsInfoModel = ruleLimitsInfo.getModelData();
                    Dispatcher.forwardEvent(GeofenceEvents.SAVE_LAYER_LIMITS, limitsInfoModel);

                    Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE,
                        new String[] { "GeoServer Rules: Layer Limits", "Apply Changes" });

                }
            });

        cancelButton = new Button("Cancel");
        cancelButton.addListener(Events.OnClick, new Listener<ButtonEvent>()
            {
                public void handleEvent(ButtonEvent be)
                {
                    // /////////////////////////////////////////////////////////
                    // Getting the Rule limits edit dialogs and hiding this
                    // /////////////////////////////////////////////////////////
                    ComponentManager.get().get(I18nProvider.getMessages().ruleDialogId()).hide();
                }
            });

        this.toolBar.add(new FillToolItem());
        this.toolBar.add(saveLayerDetailsButton);
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
     * Sets the rule limits info.
     *
     * @param ruleLimitsInfoWidget
     *            the new rule limits info
     */
    public void setRuleDetailsInfo(RuleLimitsInfoWidget ruleLimitsInfoWidget)
    {
        this.ruleLimitsInfo = ruleLimitsInfoWidget;
    }

    /**
     * Gets the rule limits info.
     *
     * @return the rule limits info
     */
    public RuleLimitsInfoWidget getRuleLimitsInfo()
    {
        return ruleLimitsInfo;
    }

    /**
     * Disable save button.
     */
    public void disableSaveButton()
    {
        if (this.saveLayerDetailsButton.isEnabled())
        {
            this.saveLayerDetailsButton.disable();
        }
    }

    /**
     * Enable save button.
     */
    public void enableSaveButton()
    {
        if (!this.saveLayerDetailsButton.isEnabled())
        {
            this.saveLayerDetailsButton.enable();
        }
    }

}
