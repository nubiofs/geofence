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
import it.geosolutions.geofence.gui.client.service.WorkspacesManagerRemoteServiceAsync;

// TODO: Auto-generated Javadoc
/**
 * The Class RuleDetailsTabItem.
 */
public class RuleDetailsTabItem extends TabItem {

	/** The rule details widget. */
	private RuleDetailsWidget ruleDetailsWidget;

	/** The rule. */
	private Rule theRule;

	/**
	 * Instantiates a new rule details tab item.
	 * 
	 * @param tabItemId
	 *            the tab item id
	 */
	private RuleDetailsTabItem(String tabItemId) {
		// TODO: add I18n message
		// super(I18nProvider.getMessages().profiles());
		super("Layer Details");
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
	 * @param loadModel
	 */
	public RuleDetailsTabItem(String tabItemId, Rule model,
			WorkspacesManagerRemoteServiceAsync workspacesService,
			boolean loadModel) {
		this(tabItemId);
		this.theRule = model;

		setRuleDetailsWidget(new RuleDetailsWidget(this.theRule,
				workspacesService));
		add(getRuleDetailsWidget());

		setScrollMode(Scroll.NONE);

		if (loadModel) {
			this.addListener(Events.Select, new Listener<BaseEvent>() {

				public void handleEvent(BaseEvent be) {
					if (ruleDetailsWidget.getRuleDetailsInfo().getModel() == null) {
						Dispatcher.forwardEvent(
								GeofenceEvents.LOAD_LAYER_DETAILS, theRule);
					}

					if (ruleDetailsWidget.getRuleDetailsGrid().getStore()
							.getCount() < 1) {
						ruleDetailsWidget.getRuleDetailsGrid().getLoader()
								.load();
					}
				}

			});
		}
		// getLayerCustomPropsWidget().getLayerCustomPropsInfo().getLoader().load(0,
		// it.geosolutions.geofence.gui.client.Constants.DEFAULT_PAGESIZE);

	}

	/**
	 * Sets the rule details widget.
	 * 
	 * @param ruleDetailsWidget
	 *            the new rule details widget
	 */
	public void setRuleDetailsWidget(RuleDetailsWidget ruleDetailsWidget) {
		this.ruleDetailsWidget = ruleDetailsWidget;
	}

	/**
	 * Gets the rule details widget.
	 * 
	 * @return the rule details widget
	 */
	public RuleDetailsWidget getRuleDetailsWidget() {
		return ruleDetailsWidget;
	}

}
