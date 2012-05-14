/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.rule.detail.RuleDetailsTabItem,v. 0.1 25-feb-2011 16.30.38 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.TabItem;

import it.geosolutions.geofence.gui.client.GeofenceEvents;
import it.geosolutions.geofence.gui.client.Resources;
import it.geosolutions.geofence.gui.client.model.Rule;
import it.geosolutions.geofence.gui.client.service.RulesManagerRemoteServiceAsync;


/**
 * The Class RuleLimitsTabItem.
 */
public class RuleLimitsTabItem extends TabItem
{

    /** The rule details widget. */
    private RuleLimitsWidget ruleLimitsWidget;

    /** The rule. */
    private Rule theRule;

    /**
     * Instantiates a new rule limits tab item.
     *
     * @param tabItemId
     *            the tab item id
     */
    private RuleLimitsTabItem(String tabItemId)
    {
        // TODO: add I18n message
        // super(I18nProvider.getMessages().profiles());
        super("Layer Limits");
        setId(tabItemId);
        setIcon(Resources.ICONS.addAOI());
    }

    /**
     * Instantiates a new rule details tab item.
     *
     * @param tabItemId
     *            the tab item id
     * @param model
     *            the model
     * @param workspacesService
     *            the workspaces service
     */
    public RuleLimitsTabItem(String tabItemId, Rule model, RulesManagerRemoteServiceAsync rulesService)
    {
        this(tabItemId);
        this.theRule = model;

        setRuleLimitsWidget(new RuleLimitsWidget(this.theRule, rulesService));
        add(getRuleLimitsWidget());

        setScrollMode(Scroll.NONE);

        this.addListener(Events.Select, new Listener<BaseEvent>()
            {

                public void handleEvent(BaseEvent be)
                {
                    if (ruleLimitsWidget.getRuleLimitsInfo().getModel() == null)
                    {
                        Dispatcher.forwardEvent(GeofenceEvents.LOAD_LAYER_LIMITS, theRule);
                    }
                }

            });

    }

    /**
     * Sets the rule details widget.
     *
     * @param ruleLimitsWidget
     *            the new rule details widget
     */
    public void setRuleLimitsWidget(RuleLimitsWidget ruleDetailsWidget)
    {
        this.ruleLimitsWidget = ruleDetailsWidget;
    }

    /**
     * Gets the rule limits widget.
     *
     * @return the rule limits widget
     */
    public RuleLimitsWidget getRuleLimitsWidget()
    {
        return ruleLimitsWidget;
    }

}
