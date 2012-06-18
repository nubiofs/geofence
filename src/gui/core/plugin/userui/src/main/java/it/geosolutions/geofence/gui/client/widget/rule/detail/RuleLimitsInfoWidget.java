/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.rule.detail.RuleDetailsInfoWidget,v. 0.1 25-feb-2011 16.30.38 created by afabiani <alessio.fabiani at geo-solutions.it> $
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

import it.geosolutions.geofence.gui.client.GeofenceEvents;
import it.geosolutions.geofence.gui.client.i18n.I18nProvider;
import it.geosolutions.geofence.gui.client.model.Rule;
import it.geosolutions.geofence.gui.client.model.data.LayerLimitsInfo;
import it.geosolutions.geofence.gui.client.service.RulesManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.widget.GeofenceFormBindingWidget;
import it.geosolutions.geogwt.gui.client.Resources;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;


/**
 * The Class RuleLimitsInfoWidget.
 */
public class RuleLimitsInfoWidget extends GeofenceFormBindingWidget<LayerLimitsInfo>
{

    /** The rule. */
    private Rule theRule;

    /** The rule service. */
    private RulesManagerRemoteServiceAsync rulesService;

    /** The rule details widget. */
    private RuleLimitsWidget ruleLimitsWidget;

    /** The allowed area. */
    private TextArea allowedArea;
    
    private Button draw;

    /**
     * Instantiates a new rule details info widget.
     *
     * @param model
     *            the model
     * @param workspacesService
     *            the workspaces service
     * @param ruleDetailsWidget
     *            the rule details widget
     */
    public RuleLimitsInfoWidget(Rule model, RulesManagerRemoteServiceAsync rulesService,
        RuleLimitsWidget ruleLimitsWidget)
    {

        super();
        this.theRule = model;
        this.rulesService = rulesService;
        this.ruleLimitsWidget = ruleLimitsWidget;
        formPanel = createFormPanel();
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.client.widget.GeofenceFormBindingWidget#createFormPanel()
     */
    @Override
    public FormPanel createFormPanel()
    {
        FormPanel fp = new FormPanel();
        fp.setFrame(true);
        fp.setHeaderVisible(false);
        fp.setHeight(400);
        fp.setWidth(650);

        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeading("Layer Limits");
        fieldSet.setCheckboxToggle(false);
        fieldSet.setCollapsible(false);

        FormLayout layout = new FormLayout();
        fieldSet.setLayout(layout);

        allowedArea = new TextArea();
        allowedArea.setFieldLabel("Allowed Area");
        allowedArea.setWidth(400);
        allowedArea.setPreventScrollbars(true);
        allowedArea.addListener(Events.Change, new Listener<FieldEvent>()
            {

                public void handleEvent(FieldEvent be)
                {
                    ruleLimitsWidget.enableSaveButton();
                }

            });

        fieldSet.add(allowedArea);

        draw = new Button(I18nProvider.getMessages().drawAoiButton(),
                new SelectionListener<ButtonEvent>()
                {
                    @Override
                    public void componentSelected(ButtonEvent ce)
                    {
                        Dispatcher.forwardEvent(GeofenceEvents.ACTIVATE_DRAW_FEATURES,
                                RuleLimitsInfoWidget.this);
                    	Dispatcher.forwardEvent(GeofenceEvents.RULE_EDITOR_DIALOG_HIDE);
                    }
                });

        draw.setIcon(Resources.ICONS.drawFeature());

        
        fp.add(fieldSet);
        ruleLimitsWidget.getToolBar().add(draw);

        return fp;
    }

    /**
     * Gets the model data.
     *
     * @return the model data
     */
    public LayerLimitsInfo getModelData()
    {
        LayerLimitsInfo layerLimitsForm = new LayerLimitsInfo();

        String area = allowedArea.getValue();

        String wkt, srid;
        if (area != null)
        {
            if (area.indexOf("SRID=") != -1)
            {
                String[] allowedAreaArray = area.split(";");

                srid = allowedAreaArray[0].split("=")[1];
                wkt = allowedAreaArray[1];
            }
            else
            {
                srid = "4326";
                wkt = area;
            }
        }
        else
        {
            srid = null;
            wkt = null;
        }

        layerLimitsForm.setAllowedArea(wkt);
        layerLimitsForm.setSrid(srid);
        layerLimitsForm.setRuleId(theRule.getId());

        return layerLimitsForm;
    }

    /**
	 * @param ruleLimitsWidget the ruleLimitsWidget to set
	 */
	public void setRuleLimitsWidget(RuleLimitsWidget ruleLimitsWidget) {
		this.ruleLimitsWidget = ruleLimitsWidget;
	}

	/**
	 * @return the ruleLimitsWidget
	 */
	public RuleLimitsWidget getRuleLimitsWidget() {
		return ruleLimitsWidget;
	}

	/**
	 * @param allowedArea the allowedArea to set
	 */
	public void setAllowedArea(TextArea allowedArea) {
		this.allowedArea = allowedArea;
	}

	/**
	 * @return the allowedArea
	 */
	public TextArea getAllowedArea() {
		return allowedArea;
	}

	/**
     * Bind model data.
     *
     * @param layerDetailsInfo
     *            the layer details info
     */
    public void bindModelData(LayerLimitsInfo layerLimitsInfo)
    {
        this.bindModel(layerLimitsInfo);

        String area = layerLimitsInfo.getAllowedArea();
        String srid = layerLimitsInfo.getSrid();
        if ((area != null) && (srid != null))
        {
            allowedArea.setValue("SRID=" + srid + ";" + area);
        }
        else
        {
            allowedArea.setValue("");
        }
    }

}
