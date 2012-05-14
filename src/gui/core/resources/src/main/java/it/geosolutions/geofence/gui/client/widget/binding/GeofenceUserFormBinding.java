/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.binding.GeofenceUserFormBinding,v. 0.1 25-gen-2011 11.24.45 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.24.45 $
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
package it.geosolutions.geofence.gui.client.widget.binding;

import it.geosolutions.geofence.gui.client.model.BeanKeyValue;

import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class GeofenceUserFormBinding.
 */
public class GeofenceUserFormBinding extends FormBinding {

    /**
     * Instantiates a new geo repo user form binding.
     * 
     * @param panel
     *            the panel
     * @param autoBind
     *            the auto bind
     */
    public GeofenceUserFormBinding(FormPanel panel, boolean autoBind) {
        super(panel, autoBind);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.binding.FormBinding#autoBind()
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void autoBind() {
        for (Field f : panel.getFields()) {
            if (!bindings.containsKey(f.getId())) {
                String name = f.getName();
                if (name != null) {
                    FieldBinding b;
//                    if (f.getId().equals(BeanKeyValue.REDUCED_CONTENT_UPDATE.getValue()))
//                        b = new ReducedContentFieldBinding(f, f.getName());
//                    else
                        b = new GeofenceFieldBinding(f, f.getName());
                    bindings.put(f.getId(), b);
                }
            }
        }
    }

}
