/*
 * ====================================================================
 *
 * Copyright (C) 2007 - 2014 GeoSolutions S.A.S.
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
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import it.geosolutions.geofence.gui.client.model.BeanKeyValue;
import it.geosolutions.geofence.gui.client.model.data.ClientCatalogMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    private ComboBox<ClientCatalogMode> catalogModeBox;

    private Button draw;

    private Map<String, ClientCatalogMode> nameMode = new HashMap<String, ClientCatalogMode>();

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

        initModeMap();
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
        allowedArea.addListener(Events.Change,
                new Listener<FieldEvent>()
                {
                    public void handleEvent(FieldEvent be)
                    {
                        ruleLimitsWidget.enableSaveButton();
                    }
                });

        fieldSet.add(allowedArea);

        catalogModeBox = getCatalogModeCombo();
        catalogModeBox.addListener(Events.Select,
                new Listener<FieldEvent>()
                    {
                        public void handleEvent(FieldEvent be) {
                            ruleLimitsWidget.enableSaveButton();
                        }
                    }
                );

        fieldSet.add(catalogModeBox);

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
        LayerLimitsInfo layerLimitsInfo = new LayerLimitsInfo();

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

        layerLimitsInfo.setAllowedArea(wkt);
        layerLimitsInfo.setSrid(srid);
        layerLimitsInfo.setRuleId(theRule.getId());

        layerLimitsInfo.setCatalogMode(catalogModeBox.getValue());

        Dispatcher.forwardEvent(
                GeofenceEvents.SEND_INFO_MESSAGE, new String[] {
                        "Info", "Returning CatalogMode " + catalogModeBox.getValue()});

        return layerLimitsInfo;
    }

	public void setRuleLimitsWidget(RuleLimitsWidget ruleLimitsWidget) {
		this.ruleLimitsWidget = ruleLimitsWidget;
	}

	public RuleLimitsWidget getRuleLimitsWidget() {
		return ruleLimitsWidget;
	}


    public void setArea(String wkt, String srid) {

        String area = "";
        if (wkt != null) {
            area = wkt;
            if( srid != null && ! wkt.startsWith("SRID")) {
                area = "SRID=" + srid + ";" + wkt;
            }
        }

        allowedArea.setValue(area);

        // set the backend model (is it really used?!?)

        LayerLimitsInfo modelData = getModelData();
        this.bindModel(modelData);
    }

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

        if(layerLimitsInfo.getCatalogMode() != null) {

            // get local instance
            ClientCatalogMode lcm = nameMode.get(layerLimitsInfo.getCatalogMode().getCatalogMode());
            catalogModeBox.setValue(lcm);

        } else {
            catalogModeBox.setValue(ClientCatalogMode.DEFAULT);

            Dispatcher.forwardEvent(
                GeofenceEvents.SEND_INFO_MESSAGE, new String[] {
                        "Info", "CatalogMode is null"});
        }

    }

    private ComboBox<ClientCatalogMode> getCatalogModeCombo() {

        final ComboBox<ClientCatalogMode> combo = new ComboBox<ClientCatalogMode>();
        combo.setId("limitCatalogMode");
        combo.setName("limitCatalogMode");

        combo.setFieldLabel("Catalog Mode");

        combo.setDisplayField(BeanKeyValue.CATALOG_MODE.getValue());
        combo.setEditable(false);
        combo.setStore(getAvailableCatalogModes());
        combo.setTriggerAction(ComboBox.TriggerAction.ALL);
        combo.setWidth(70);

        return combo;
    }

    private ListStore<ClientCatalogMode> getAvailableCatalogModes()
    {
        ListStore<ClientCatalogMode> ret = new ListStore<ClientCatalogMode>();
        List<ClientCatalogMode> list = new ArrayList<ClientCatalogMode>();

        list.add(ClientCatalogMode.DEFAULT);
        list.add(ClientCatalogMode.HIDE);
        list.add(ClientCatalogMode.MIXED);
        list.add(ClientCatalogMode.CHALLENGE);

        ret.add(list);

        return ret;
    }

    private void initModeMap() {
        nameMode.put(ClientCatalogMode.NAME_DEFAULT, ClientCatalogMode.DEFAULT);
        nameMode.put(ClientCatalogMode.NAME_HIDE, ClientCatalogMode.HIDE);
        nameMode.put(ClientCatalogMode.NAME_MIXED, ClientCatalogMode.MIXED);
        nameMode.put(ClientCatalogMode.NAME_CHALLENGE, ClientCatalogMode.CHALLENGE);
    }

}
