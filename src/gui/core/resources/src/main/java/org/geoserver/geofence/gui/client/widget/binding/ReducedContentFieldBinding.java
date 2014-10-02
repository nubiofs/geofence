/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.binding.ReducedContentFieldBinding,v. 0.1 25-gen-2011 11.24.44 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.24.44 $
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

import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.Field;

// TODO: Auto-generated Javadoc
/**
 * The Class ReducedContentFieldBinding.
 */
public class ReducedContentFieldBinding extends FieldBinding {

    /** The old value. */
    private Object oldValue;

    /**
     * Instantiates a new reduced content field binding.
     * 
     * @param field
     *            the field
     * @param property
     *            the property
     */
    @SuppressWarnings("rawtypes")
    public ReducedContentFieldBinding(Field field, String property) {
        super(field, property);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.binding.FieldBinding#updateField(boolean)
     */
    @Override
    public void updateField(boolean updateOriginalValue) {
        Object val = onConvertModelValue(model.get(property));

        if (oldValue == null)
            oldValue = val;

        ((CheckBox) field).setValue((Boolean) val);

        if (updateOriginalValue) {
            ((CheckBox) field).setValue((Boolean) val);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.binding.FieldBinding#updateModel()
     */
    @Override
    @SuppressWarnings("unchecked")
    public void updateModel() {
        Object val = onConvertFieldValue(field.getValue());
        if (store != null) {
            Record r = store.getRecord(model);
            if (r != null) {
                r.setValid(property, field.isValid());
                r.set(property, val);
            }
        } else {
            // model.set(property, ((UpdateInterval) val).getTime());
            //TODO
//            ((User) model).setReducedContent((Boolean) val);
        }
    }

    /**
     * Reset value.
     */
    public void resetValue() {
        oldValue = onConvertFieldValue(field.getValue());

        ((CheckBox) field).setValue((Boolean) oldValue);

        // model.set(property, oldValue.toString());
        // TODO
//        ((User) model).setReducedContent((Boolean) oldValue);
    }

}
