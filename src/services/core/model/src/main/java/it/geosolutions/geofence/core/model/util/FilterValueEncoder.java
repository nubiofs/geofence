/*
 * $ Header: it.geosolutions.geofence.core.model.util.FilterValueEncoder,v. 0.1 4-gen-2011 17.20.14 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
import java.util.List;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * The Class FilterValueEncoder.
 */
public class FilterValueEncoder {

    /** The xml encoded value. */
    Element e = new Element("value");

    /**
     * Adds the.
     * 
     * @param b
     *            the b
     */
    public void add(boolean b) {
        e.addContent(new Element(ValueType.BOOL.name()).setText(Boolean.toString(b)));
    }

    /**
     * Adds the.
     * 
     * @param i
     *            the i
     */
    public void add(int i) {
        e.addContent(new Element(ValueType.INT.name()).setText(Integer.toString(i)));
    }

    /**
     * Adds the.
     * 
     * @param s
     *            the s
     */
    public void add(String s) {
        e.addContent(new Element(ValueType.STRING.name()).setText(s));
    }

    /**
     * Adds the.
     * 
     * @param l
     *            the l
     */
    public void add(List<String> l) {
        Element list = new Element(ValueType.STRINGLIST.name());
        e.addContent(list);
        for (String string : l) {
            list.addContent(new Element(ValueType.STRING.name()).setText(string));
        }
    }

    /**
     * Encode value.
     * 
     * @return the string
     */
    public String encodeValue() {
        XMLOutputter outputter = new XMLOutputter(Format.getRawFormat());
        
        return outputter.outputString(e);
    }

}
