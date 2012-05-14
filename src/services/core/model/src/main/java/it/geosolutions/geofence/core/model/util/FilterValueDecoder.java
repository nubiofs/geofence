/*
 * $ Header: it.geosolutions.geofence.core.model.util.FilterValueDecoder,v. 0.1 4-gen-2011 17.20.14 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 4-gen-2011 17.20.14 $
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

package it.geosolutions.geofence.core.model.util;

import it.geosolutions.geofence.core.model.enums.ValueType;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * The Class FilterValueDecoder.
 */
public class FilterValueDecoder {

    /** The string encoded value. */
    private String value;

    /** The xml decoded value. */
    Element e;

    /**
     * Instantiates a new filter value decoder.
     * 
     * @param value
     *            the value
     * @throws IOException
     * @throws JDOMException
     */
    public FilterValueDecoder(String value) throws JDOMException, IOException {
        this.value = value;

        SAXBuilder saxBuilder = new SAXBuilder();
        Document jdomDocument = saxBuilder.build(new StringReader(this.value));
        e = jdomDocument.getRootElement();
    }

    /**
     * Gets the value count.
     * 
     * @return the value count
     */
    public int getValueCount() {
        return e.getContentSize();
    }

    /**
     * Gets the type.
     * 
     * @param index
     *            the index
     * @return the type
     */
    public ValueType getType(int index) {
        return ValueType.valueOf(((Element) e.getContent(index)).getName());
    }

    /**
     * Gets the boolean.
     * 
     * @param index
     *            the index
     * @return the boolean
     */
    public boolean getBoolean(int index) {
        return Boolean.valueOf(((Element) e.getContent(index)).getText());
    }

    /**
     * Gets the int.
     * 
     * @param index
     *            the index
     * @return the int
     */
    public int getInt(int index) {
        return Integer.valueOf(((Element) e.getContent(index)).getText());
    }

    /**
     * Gets the string.
     * 
     * @param index
     *            the index
     * @return the string
     */
    public String getString(int index) {
        return String.valueOf(((Element) e.getContent(index)).getText());
    }

    /**
     * Gets the string list.
     * 
     * @param index
     *            the index
     * @return the string list
     */
    @SuppressWarnings("unchecked")
    public List<String> getStringList(int index) {
        List<String> values = new ArrayList<String>();

        Iterator<Element> itr = (((Element) e.getContent(index)).getChildren()).iterator();
        while (itr.hasNext()) {
            Element oneLevelDeep = (Element) itr.next();
            values.add(oneLevelDeep.getText());
        }

        return values;
    }

}
