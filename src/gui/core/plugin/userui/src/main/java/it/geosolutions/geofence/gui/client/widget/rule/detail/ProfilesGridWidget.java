/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.rule.detail.RuleDetailsGridWidget,v. 0.1 25-feb-2011 16.30.38 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
import it.geosolutions.geofence.gui.client.model.BeanKeyValue;
import it.geosolutions.geofence.gui.client.model.GSUser;
import it.geosolutions.geofence.gui.client.model.UserGroup;
import it.geosolutions.geofence.gui.client.service.GsUsersManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.service.ProfilesManagerRemoteServiceAsync;
import it.geosolutions.geofence.gui.client.widget.GeofenceGridWidget;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;


// TODO: Auto-generated Javadoc
/**
 * The Class ProfilesGridWidget.
 */
public class ProfilesGridWidget extends GeofenceGridWidget<UserGroup>
{
	/** The users service */
	private final GsUsersManagerRemoteServiceAsync usersService;

    /** The profiles service. */
    private final ProfilesManagerRemoteServiceAsync profilesService;

    /** The user details widget. */
    private UserDetailsWidget userDetailsWidget;
    
    /** The proxy. */
    private RpcProxy<PagingLoadResult<UserGroup>> proxy;

    /** The loader. */
    private BaseListLoader<ListLoadResult<ModelData>> loader;

	private GSUser user;
	private Set<UserGroup> selectedGroups = new HashSet<UserGroup>();

    /**
     * Instantiates a new rule details grid widget.
     * @param model 
     *
     * @param model
     *            the model
     * @param usersService 
     * @param workspacesService
     *            the workspaces service
     * @param ruleDetailsWidget
     *            the rule details widget
     */
    public ProfilesGridWidget(GSUser model,
    		GsUsersManagerRemoteServiceAsync usersService, ProfilesManagerRemoteServiceAsync profilesService, UserDetailsWidget userDetailsWidget)
    {
        super();
        this.user = model;
        this.usersService = usersService;
        this.profilesService = profilesService;
        this.userDetailsWidget = userDetailsWidget;
    }

    /*
     * (non-Javadoc)
     * @see it.geosolutions.geofence.gui.client.widget.GEOFENCEGridWidget#setGridProperties ()
     */
    @Override
    public void setGridProperties()
    {
        grid.setLoadMask(true);
        grid.setAutoWidth(true);
        //grid.setHeight(300);
        grid.setAutoHeight(true);
        grid.setAutoWidth(true);
    }

    /**
     * Clear grid elements.
     */
    public void clearGridElements()
    {
        this.store.removeAll();
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geofence.gui.client.widget.GEOFENCEGridWidget#createStore()
     */
    @Override
    public void createStore()
    {

        // /////////////////////////////
        // Loader for rulesService
        // /////////////////////////////

        this.proxy = new RpcProxy<PagingLoadResult<UserGroup>>()
            {
                @Override
                protected void load(Object loadConfig, AsyncCallback<PagingLoadResult<UserGroup>> callback)
                {
                    profilesService.getProfiles(-1,-1,false, callback);
                }
            };

        loader = new BaseListLoader<ListLoadResult<ModelData>>(proxy);
        loader.setRemoteSort(false);
        store = new ListStore<UserGroup>(loader);
        store.sort(BeanKeyValue.STYLES_COMBO.getValue(), SortDir.ASC);

        setUpLoadListener();
    }

    /**
     * Sets the up load listener.
     */
    private void setUpLoadListener()
    {
        loader.addLoadListener(new LoadListener()
            {

                @Override
                public void loaderLoad(LoadEvent le)
                {

                    // TODO: change messages here!!

                    BasePagingLoadResult<?> result = le.getData();
                    if (!result.getData().isEmpty())
                    {
                        int size = result.getData().size();
                        String message = "";
                        if (size == 1)
                        {
                            message = I18nProvider.getMessages().recordLabel();
                        }
                        else
                        {
                            message = I18nProvider.getMessages().recordPluralLabel();
                        }
                        Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE,
                            new String[]
                            {
                                I18nProvider.getMessages().remoteServiceName(),
                                I18nProvider.getMessages().foundLabel() + " " + result.getData().size() +
                                " " + message
                            });
                    }
                    else
                    {
                        Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE,
                            new String[]
                            {
                                I18nProvider.getMessages().remoteServiceName(),
                                I18nProvider.getMessages().recordNotFoundMessage()
                            });
                    }
                }

            });
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geofence.gui.client.widget.GeofenceGridWidget#prepareColumnModel()
     */
    @Override
    public ColumnModel prepareColumnModel()
    {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        ColumnConfig attributeProfileColumn = new ColumnConfig();
        attributeProfileColumn.setId(BeanKeyValue.STYLES_COMBO.getValue());
        attributeProfileColumn.setHeader("Profile");
        attributeProfileColumn.setWidth(180);
        attributeProfileColumn.setRenderer(this.createProfileTextBox());
        attributeProfileColumn.setMenuDisabled(true);
        attributeProfileColumn.setSortable(false);

        configs.add(attributeProfileColumn);

        ColumnConfig attributeEnableColumn = new ColumnConfig();
        attributeEnableColumn.setId(BeanKeyValue.STYLE_ENABLED.getValue());
        attributeEnableColumn.setHeader("Enable");
        attributeEnableColumn.setWidth(80);
        attributeEnableColumn.setRenderer(this.createEnableCheckBox());
        attributeEnableColumn.setMenuDisabled(true);
        attributeEnableColumn.setSortable(false);
        configs.add(attributeEnableColumn);

        return new ColumnModel(configs);
    }

    /**
     * Creates the style text box.
     *
     * @return the grid cell renderer
     */
    private GridCellRenderer<UserGroup> createProfileTextBox()
    {

        GridCellRenderer<UserGroup> textRendered = new GridCellRenderer<UserGroup>()
            {

                private boolean init;

                public Object render(final UserGroup model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<UserGroup> store, Grid<UserGroup> grid)
                {

                    if (!init)
                    {
                        init = true;
                        grid.addListener(Events.ColumnResize, new Listener<GridEvent<UserGroup>>()
                            {

                                public void handleEvent(GridEvent<UserGroup> be)
                                {
                                    for (int i = 0; i < be.getGrid().getStore().getCount(); i++)
                                    {
                                        if ((be.getGrid().getView().getWidget(i, be.getColIndex()) != null) &&
                                                (be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent))
                                        {
                                            ((BoxComponent) be.getGrid().getView().getWidget(i,
                                                    be.getColIndex())).setWidth(be.getWidth() - 10);
                                        }
                                    }
                                }
                            });
                    }

                    LabelField profileName = new LabelField();
                    profileName.setWidth(150);
                    profileName.setReadOnly(true);
                    profileName.setValue(model.getName());

                    return profileName;
                }
            };

        return textRendered;
    }

    /**
     * Creates the enable check box.
     *
     * @return the grid cell renderer
     */
    private GridCellRenderer<UserGroup> createEnableCheckBox()
    {

        GridCellRenderer<UserGroup> textRendered = new GridCellRenderer<UserGroup>()
            {

                private boolean init;

                public Object render(final UserGroup model, String property, ColumnData config,
                    int rowIndex, int colIndex, ListStore<UserGroup> store, Grid<UserGroup> grid)
                {

                    if (!init)
                    {
                        init = true;
                        grid.addListener(Events.ColumnResize, new Listener<GridEvent<UserGroup>>()
                            {

                                public void handleEvent(GridEvent<UserGroup> be)
                                {
                                    for (int i = 0; i < be.getGrid().getStore().getCount(); i++)
                                    {
                                        if ((be.getGrid().getView().getWidget(i, be.getColIndex()) != null) &&
                                                (be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent))
                                        {
                                            ((BoxComponent) be.getGrid().getView().getWidget(i,
                                                    be.getColIndex())).setWidth(be.getWidth() - 10);
                                        }
                                    }
                                }
                            });
                    }

                    CheckBox available = new CheckBox();
                    for( UserGroup group : user.getUserGroups()) {
                    	if (group.getName().equals(model.getName())) {
                    		available.setValue(true);
                    		break;
                    	}
                    }

                    available.addListener(Events.Change, new Listener<FieldEvent>()
                        {

                            public void handleEvent(FieldEvent be)
                            {
                                Boolean enable = (Boolean) be.getField().getValue();

                                if (enable.booleanValue())
                                {
                                    Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE,
                                        new String[] { "User Details", "Group " + model.getName() + ": enabled" });

                                    model.setEnabled(enable);

                                    boolean groupAllowed = false;
                                    for( UserGroup group : selectedGroups) {
                                    	if (group.getName().equals(model.getName())) {
                                    		groupAllowed = true;
                                    		break;
                                    	}
                                    }
                                    if (!groupAllowed)
                                    {
                                    	//user.getUserGroups().add(model);
                                    	selectedGroups.add(model);
                                    }

                                    /*logger.error("TODO: profile refactoring!!!");*/
                                    userDetailsWidget.enableSaveButton();

                                }
                                else
                                {
                                    Dispatcher.forwardEvent(GeofenceEvents.SEND_INFO_MESSAGE,
                                        new String[] { "User Details", "Group " + model.getName() + ": disabled" });

                                    model.setEnabled(enable);

                                    for( UserGroup group : selectedGroups) {
                                    	if (group.getName().equals(model.getName())) {
                                    		//user.getUserGroups().remove(group);
                                    		selectedGroups.remove(group);
                                    		break;
                                    	}
                                    }

                                    /*logger.error("TODO: profile refactoring!!!");*/
                                    userDetailsWidget.enableSaveButton();
                                }
                            }
                        });

                    return available;
                }
            };

        return textRendered;
    }

    /**
     * Gets the loader.
     *
     * @return the loader
     */
    public BaseListLoader<ListLoadResult<ModelData>> getLoader()
    {
        return loader;
    }

	/**
	 * @return the selectedGroups
	 */
	public Set<UserGroup> getSelectedGroups() {
		return selectedGroups;
	}

}
