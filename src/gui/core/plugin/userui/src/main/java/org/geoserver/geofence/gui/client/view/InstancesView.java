/*
 * $ Header: it.geosolutions.geofence.gui.client.view.UsersView,v. 0.1 10-feb-2011 11.50.15 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 10-feb-2011 11.50.15 $
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
package it.geosolutions.geofence.gui.client.view;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;

import it.geosolutions.geofence.gui.client.GeofenceEvents;
import it.geosolutions.geofence.gui.client.service.InstancesManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.widget.AddInstanceWidget;


// TODO: Auto-generated Javadoc
/**
 * The Class UsersView.
 */
public class InstancesView extends View
{

    /** The instances manager service remote. */
    private InstancesManagerRemoteServiceAsync instancesManagerServiceRemote =
        InstancesManagerRemoteServiceAsync.Util.getInstance();

    private AddInstanceWidget addInstance;

    /**
     * Instantiates a new users view.
     *
     * @param controller
     *            the controller
     */
    public InstancesView(Controller controller)
    {
        super(controller);

        this.addInstance = new AddInstanceWidget(GeofenceEvents.SAVE_INSTANCE, true);
        this.addInstance.setInstanceService(instancesManagerServiceRemote);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.mvc.View#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)
     */
    @Override
    protected void handleEvent(AppEvent event)
    {
        if (event.getType() == GeofenceEvents.CREATE_NEW_INSTANCE)
        {
            onCreateNewInstance(event);
        }

    }

    /**
     * On create new instance.
     *
     * @param event
     *            the event
     */
    private void onCreateNewInstance(AppEvent event)
    {
        this.addInstance.show();
    }

//    /**
//     * On edit instance.
//     *
//     * @param event
//     *            the event
//     */
//    private void onEditUser(AppEvent event) {
//        if (event.getData() != null && event.getData() instanceof GSUser) {
//            this.userEditorDialog.reset();
//            this.userEditorDialog.setModel((GSUser) event.getData());
//            this.userEditorDialog.show();
//        } else {
//            // TODO: i18n!!
//            Dispatcher.forwardEvent(GeofenceEvents.SEND_ERROR_MESSAGE, new String[] {
//                    "Users Editor", "Could not found any associated instance!" });
//        }
//    }

//    /**
//     * On rule custom prop add.
//     *
//     * @param event
//     *            the event
//     */
//    private void onRuleCustomPropAdd(AppEvent event) {
//        if (event.getData() != null) {
//            LayerCustomPropsTabItem layersCustomPropsItem = (LayerCustomPropsTabItem) this.userEditorDialog
//                    .getTabWidget().getItemByItemId(
//                            RuleDetailsEditDialog.RULE_LAYER_CUSTOM_PROPS_DIALOG_ID);
//            final LayerCustomPropsGridWidget layerCustomPropsInfo = layersCustomPropsItem
//                    .getLayerCustomPropsWidget().getLayerCustomPropsInfo();
//            LayerCustomProps model = new LayerCustomProps();
//            model.setPropKey("_new");
//            layerCustomPropsInfo.getStore().add(model);
//
//            layerCustomPropsInfo.getGrid().repaint();
//        } else {
//            // TODO: i18n!!
//            Dispatcher.forwardEvent(GeofenceEvents.SEND_ERROR_MESSAGE, new String[] {
//                    "Rules Details Editor", "Could not found any associated rule!" });
//        }
//    }
//
//    /**
//     * On rule custom prop remove.
//     *
//     * @param event
//     *            the event
//     */
//    private void onRuleCustomPropRemove(AppEvent event) {
//        if (event.getData() != null) {
//            Map<Long, LayerCustomProps> updateDTO = event.getData();
//
//            LayerCustomPropsTabItem layersCustomPropsItem = (LayerCustomPropsTabItem) this.userEditorDialog
//                    .getTabWidget().getItemByItemId(
//                            RuleDetailsEditDialog.RULE_LAYER_CUSTOM_PROPS_DIALOG_ID);
//            final LayerCustomPropsGridWidget layerCustomPropsInfo = layersCustomPropsItem
//                    .getLayerCustomPropsWidget().getLayerCustomPropsInfo();
//
//            for (Long ruleId : updateDTO.keySet()) {
//                LayerCustomProps dto = updateDTO.get(ruleId);
//
//                for (LayerCustomProps prop : layerCustomPropsInfo.getStore().getModels()) {
//                    if (prop.getPropKey().equals(dto.getPropKey()))
//                        layerCustomPropsInfo.getStore().remove(prop);
//                }
//            }
//
//            layerCustomPropsInfo.getGrid().repaint();
//
//        } else {
//            // TODO: i18n!!
//            Dispatcher.forwardEvent(GeofenceEvents.SEND_ERROR_MESSAGE, new String[] {
//                    "Rules Details Editor", "Could not found any associated rule!" });
//        }
//    }
//
//    /**
//     * On rule custom prop update key.
//     *
//     * @param event
//     *            the event
//     */
//    private void onRuleCustomPropUpdateKey(AppEvent event) {
//        if (event.getData() != null) {
//            LayerCustomPropsTabItem layersCustomPropsItem = (LayerCustomPropsTabItem) this.userEditorDialog
//                    .getTabWidget().getItemByItemId(
//                            RuleDetailsEditDialog.RULE_LAYER_CUSTOM_PROPS_DIALOG_ID);
//            final LayerCustomPropsGridWidget layerCustomPropsInfo = layersCustomPropsItem
//                    .getLayerCustomPropsWidget().getLayerCustomPropsInfo();
//
//            Map<String, LayerCustomProps> updateDTO = event.getData();
//
//            for (String key : updateDTO.keySet()) {
//                for (LayerCustomProps prop : layerCustomPropsInfo.getStore().getModels()) {
//                    if (prop.getPropKey().equals(key)) {
//                        layerCustomPropsInfo.getStore().remove(prop);
//                        LayerCustomProps newModel = updateDTO.get(key);
//                        layerCustomPropsInfo.getStore().add(newModel);
//                    }
//                }
//            }
//
//            layerCustomPropsInfo.getGrid().repaint();
//        } else {
//            // TODO: i18n!!
//            Dispatcher.forwardEvent(GeofenceEvents.SEND_ERROR_MESSAGE, new String[] {
//                    "Rules Details Editor", "Could not found any associated rule!" });
//        }
//    }
//
//    /**
//     * On rule custom prop update value.
//     *
//     * @param event
//     *            the event
//     */
//    private void onRuleCustomPropUpdateValue(AppEvent event) {
//        if (event.getData() != null) {
//            LayerCustomPropsTabItem layersCustomPropsItem = (LayerCustomPropsTabItem) this.userEditorDialog
//                    .getTabWidget().getItemByItemId(
//                            RuleDetailsEditDialog.RULE_LAYER_CUSTOM_PROPS_DIALOG_ID);
//            final LayerCustomPropsGridWidget layerCustomPropsInfo = layersCustomPropsItem
//                    .getLayerCustomPropsWidget().getLayerCustomPropsInfo();
//
//            Map<String, LayerCustomProps> updateDTO = event.getData();
//
//            for (String key : updateDTO.keySet()) {
//                for (LayerCustomProps prop : layerCustomPropsInfo.getStore().getModels()) {
//                    if (prop.getPropKey().equals(key)) {
//                        layerCustomPropsInfo.getStore().remove(prop);
//                        LayerCustomProps newModel = updateDTO.get(key);
//                        layerCustomPropsInfo.getStore().add(newModel);
//                    }
//                }
//            }
//
//            layerCustomPropsInfo.getGrid().repaint();
//        } else {
//            // TODO: i18n!!
//            Dispatcher.forwardEvent(GeofenceEvents.SEND_ERROR_MESSAGE, new String[] {
//                    "Rules Details Editor", "Could not found any associated rule!" });
//        }
//    }
//
//    /**
//     * On rule custom prop save.
//     *
//     * @param event
//     *            the event
//     */
//    private void onRuleCustomPropSave(AppEvent event) {
//        Long ruleId = event.getData();
//
//        LayerCustomPropsTabItem layersCustomPropsItem = (LayerCustomPropsTabItem) this.userEditorDialog
//                .getTabWidget().getItemByItemId(
//                        RuleDetailsEditDialog.RULE_LAYER_CUSTOM_PROPS_DIALOG_ID);
//        final LayerCustomPropsGridWidget layerCustomPropsInfo = layersCustomPropsItem
//                .getLayerCustomPropsWidget().getLayerCustomPropsInfo();
//
//        rulesManagerServiceRemote.setDetailsProps(ruleId, layerCustomPropsInfo.getStore()
//                .getModels(), new AsyncCallback<PagingLoadResult<LayerCustomProps>>() {
//
//            public void onFailure(Throwable caught) {
//
//                Dispatcher.forwardEvent(GeofenceEvents.SEND_ERROR_MESSAGE, new String[] {
//                        I18nProvider.getMessages().ruleServiceName(),
//                        "Error occurred while saving Rule Custom Properties!" });
//            }
//
//            public void onSuccess(PagingLoadResult<LayerCustomProps> result) {
//
//                // grid.getStore().sort(BeanKeyValue.PRIORITY.getValue(),
//                // SortDir.ASC);
//                layerCustomPropsInfo.getStore().getLoader().load();
//                layerCustomPropsInfo.getGrid().repaint();
//
//                Dispatcher.forwardEvent(GeofenceEvents.BIND_MEMBER_DISTRIBUTION_NODES, result);
//                Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE, new String[] {
//                        I18nProvider.getMessages().ruleServiceName(),
//                        I18nProvider.getMessages().ruleFetchSuccessMessage() });
//            }
//        });
//    }
}
