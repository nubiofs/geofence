/*
 * $ Header: it.geosolutions.geofence.gui.client.controller.RulesController,v. 0.1 31-gen-2011 13.41.27 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 31-gen-2011 13.41.27 $
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
package it.geosolutions.geofence.gui.client.controller;

import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.geosolutions.geofence.gui.client.GeofenceEvents;
import it.geosolutions.geofence.gui.client.model.GSInstance;
import it.geosolutions.geofence.gui.client.service.InstancesManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.view.InstancesView;
import it.geosolutions.geofence.gui.client.widget.InstanceGridWidget;
import it.geosolutions.geofence.gui.client.widget.tab.InstancesTabItem;
import it.geosolutions.geofence.gui.client.widget.tab.TabWidget;


// TODO: Auto-generated Javadoc
/**
 * The Class RulesController.
 */
public class InstanceController extends Controller
{

    /** The Constant INSTANCES_TAB_ITEM_ID. */
    private static final String INSTANCES_TAB_ITEM_ID = "InstancesTabItem";

    /** The instances manager service remote. */
    private InstancesManagerRemoteServiceAsync instancesManagerServiceRemote =
        InstancesManagerRemoteServiceAsync.Util.getInstance();

    /** The tab widget. */
    private TabWidget tabWidget;

    /** The users view. */
    private InstancesView instancesView;

    /**
     * Instantiates a new uSERS controller.
     */
    public InstanceController()
    {
        registerEventTypes(
            GeofenceEvents.INIT_MAPS_UI_MODULE,




            GeofenceEvents.UPDATE_INSTANCE,
            GeofenceEvents.DELETE_INSTANCE,
            GeofenceEvents.SAVE_INSTANCE,




            GeofenceEvents.CREATE_NEW_INSTANCE,

            GeofenceEvents.TEST_CONNECTION,


            GeofenceEvents.ATTACH_BOTTOM_TAB_WIDGETS);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.mvc.Controller#initialize()
     */
    @Override
    protected void initialize()
    {
        initWidget();
        this.instancesView = new InstancesView(this);
    }

    /**
     * Inits the widget.
     */
    private void initWidget()
    {
    }

    /*
     * (non-Javadoc)
     *
     * @see com.extjs.gxt.ui.client.mvc.Controller#handleEvent(com.extjs.gxt.ui.client
     * .mvc.AppEvent)
     */
    @Override
    public void handleEvent(AppEvent event)
    {
        if (event.getType() == GeofenceEvents.ATTACH_BOTTOM_TAB_WIDGETS)
        {
            onAttachTabWidgets(event);
        }

        if ((event.getType() == GeofenceEvents.UPDATE_INSTANCE) ||
                (event.getType() == GeofenceEvents.SAVE_INSTANCE))
        {
            onSaveInstance(event);
        }

        if (event.getType() == GeofenceEvents.DELETE_INSTANCE)
        {
            onDeleteInstance(event);
        }
        
        if (event.getType() == GeofenceEvents.TEST_CONNECTION)
        {
            onTestConnection(event);
        }

        forwardToView(instancesView, event);
    }

    /**
     * On attach tab widgets.
     *
     * @param event
     *            the event
     */
    private void onAttachTabWidgets(AppEvent event)
    {
        if (tabWidget == null)
        {
            tabWidget = (TabWidget) event.getData();
            tabWidget.add(new InstancesTabItem(INSTANCES_TAB_ITEM_ID, instancesManagerServiceRemote));
        }
    }

    /**
     *
     * @param event
     */
    private void onSaveInstance(AppEvent event)
    {
        if (tabWidget != null)
        {

            InstancesTabItem instancesTabItem = (InstancesTabItem) tabWidget.getItemByItemId(INSTANCES_TAB_ITEM_ID);
            final InstanceGridWidget instancesInfoWidget =
                instancesTabItem.getInstanceManagementWidget().getInstancesInfo();
            final Grid<GSInstance> grid = instancesInfoWidget.getGrid();

            if ((grid != null) && (grid.getStore() != null) && (event.getData() != null) && (event.getData() instanceof GSInstance))
            {

                GSInstance instance = event.getData();

                instancesManagerServiceRemote.saveInstance(instance, new AsyncCallback<Void>()
                    {

                        public void onFailure(Throwable caught)
                        {

                            Dispatcher.forwardEvent(GeofenceEvents.SEND_ERROR_MESSAGE,
                                new String[] {  /* I18nProvider.getMessages().ruleServiceName() */"Instance Service",
                                    /* I18nProvider.getMessages().ruleFetchFailureMessage() */ "Error occurred while saving instance!" });
                        }

                        public void onSuccess(Void result)
                        {

                            // grid.getStore().sort(BeanKeyValue.NAME.getValue(), SortDir.ASC);<<-- ric mod 20100215
                            grid.getStore().getLoader().load();
                            grid.repaint();

                            Dispatcher.forwardEvent(
                                GeofenceEvents.BIND_MEMBER_DISTRIBUTION_NODES, result);
                            Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE,
                                new String[] {  /* TODO: I18nProvider.getMessages().ruleServiceName()*/"Instance Service",
                                    /* TODO: I18nProvider.getMessages().ruleFetchSuccessMessage() */ "Instance saved successfully!" });
                        }
                    });

            }
        }
    }

    /**
     *
     * @param event
     */
    private void onDeleteInstance(AppEvent event)
    {
        if (tabWidget != null)
        {

            InstancesTabItem instancesTabItem = (InstancesTabItem) tabWidget.getItemByItemId(INSTANCES_TAB_ITEM_ID);
            final InstanceGridWidget instancesInfoWidget =
                instancesTabItem.getInstanceManagementWidget().getInstancesInfo();
            final Grid<GSInstance> grid = instancesInfoWidget.getGrid();

            if ((grid != null) && (grid.getStore() != null) && (event.getData() != null) && (event.getData() instanceof GSInstance))
            {

                GSInstance instance = event.getData();

                instancesManagerServiceRemote.deleteInstance(instance,
                    new AsyncCallback<Void>()
                    {

                        public void onFailure(Throwable caught)
                        {

                            Dispatcher.forwardEvent(GeofenceEvents.SEND_ERROR_MESSAGE,
                                new String[] {  /* TODO: I18nProvider.getMessages().ruleServiceName() */"Instance Service",
                                    /* TODO: I18nProvider.getMessages().ruleFetchFailureMessage() */ "Error occurred while removing Instance!" });
                        }

                        public void onSuccess(Void result)
                        {

                            // grid.getStore().sort(BeanKeyValue.USER_NAME.getValue(), SortDir.ASC);<<-- ric mod 20100215
                            grid.getStore().getLoader().load();
                            grid.repaint();

                            Dispatcher.forwardEvent(
                                GeofenceEvents.BIND_MEMBER_DISTRIBUTION_NODES, result);
                            Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE,
                                new String[] {  /* TODO: I18nProvider.getMessages().ruleServiceName()*/"Instance Service",
                                    /* TODO: I18nProvider.getMessages().ruleFetchSuccessMessage() */ "Instance removed successfully!" });
                        }
                    });

            }
        }
    }
    
    /**
    *
    * @param event
    */
   private void onTestConnection(AppEvent event)
   {
       if (tabWidget != null)
       {

           InstancesTabItem instancesTabItem = (InstancesTabItem) tabWidget.getItemByItemId(INSTANCES_TAB_ITEM_ID);
           final InstanceGridWidget instancesInfoWidget =
               instancesTabItem.getInstanceManagementWidget().getInstancesInfo();
           final Grid<GSInstance> grid = instancesInfoWidget.getGrid();

           if ((grid != null) && (grid.getStore() != null) && (event.getData() != null) && (event.getData() instanceof GSInstance))
           {

               GSInstance instance = event.getData();
               
               instancesManagerServiceRemote.testConnection(instance, new AsyncCallback<Void>()
                       {

                   public void onFailure(Throwable caught)
                   {

                       Dispatcher.forwardEvent(GeofenceEvents.SEND_ERROR_MESSAGE,
                           new String[] {  /* TODO: I18nProvider.getMessages().ruleServiceName() */"Instance Service",
                               /* TODO: I18nProvider.getMessages().ruleFetchFailureMessage() */ "Error occurred testing connection to Instance ("+caught.getMessage()+")!" });
                   }

                   public void onSuccess(Void result)
                   {

                	   Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE,
                               new String[] {  /* TODO: I18nProvider.getMessages().ruleServiceName() */"Instance Service",
                                   /* TODO: I18nProvider.getMessages().ruleFetchFailureMessage() */ "Connection to Instance succesful!" });
                   }
               });
               
           }
       }
   }

    /**
     * Forward to tab widget.
     *
     * @param event
     *            the event
     */
    @SuppressWarnings("unused")
    private void forwardToTabWidget(AppEvent event)
    {
        this.tabWidget.fireEvent(event.getType(), event);
    }

}
