/*
 *  Copyright (C) 2007 - 2012 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geofence.services.rest.model;

import it.geosolutions.geofence.core.model.LayerAttribute;
import it.geosolutions.geofence.core.model.enums.LayerType;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Etj (etj at geo-solutions.it)
 */
@XmlRootElement(name = "LayerConstraints")
@XmlType(propOrder = {"type", "defaultStyle", "cqlFilterRead", "cqlFilterWrite", "restrictedAreaWkt",
    "allowedStyles", "attributes"})
public class RESTLayerConstraints {

    private LayerType type;
    private String defaultStyle;
    private String cqlFilterRead;
    private String cqlFilterWrite;
    private String restrictedAreaWkt;
    private Set<String> allowedStyles;
    private Set<LayerAttribute> attributes;

    @XmlElementWrapper(name = "allowedStyles")
    @XmlElement(name = "style")
    public Set<String> getAllowedStyles() {
        return allowedStyles;
    }

    public void setAllowedStyles(Set<String> allowedStyles) {
        this.allowedStyles = allowedStyles;
    }

    @XmlElementWrapper(name = "attributes")
    @XmlElement(name = "attribute")
    public Set<LayerAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<LayerAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getCqlFilterRead() {
        return cqlFilterRead;
    }

    public void setCqlFilterRead(String cqlFilterRead) {
        this.cqlFilterRead = cqlFilterRead;
    }

    public String getCqlFilterWrite() {
        return cqlFilterWrite;
    }

    public void setCqlFilterWrite(String cqlFilterWrite) {
        this.cqlFilterWrite = cqlFilterWrite;
    }

    public String getDefaultStyle() {
        return defaultStyle;
    }

    public void setDefaultStyle(String defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

    public String getRestrictedAreaWkt() {
        return restrictedAreaWkt;
    }

    public void setRestrictedAreaWkt(String restrictedAreaWkt) {
        this.restrictedAreaWkt = restrictedAreaWkt;
    }

    public LayerType getType() {
        return type;
    }

    public void setType(LayerType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append('[');
        if(type != null)
            sb.append("type:").append(type);
        if(defaultStyle != null)
            sb.append(" defStyle:").append(defaultStyle);
        if(cqlFilterRead != null)
            sb.append(" cqlR:").append(cqlFilterRead);
        if(cqlFilterWrite != null)
            sb.append(" cqlW:").append(cqlFilterWrite);
        if(restrictedAreaWkt != null)
            sb.append(" wkt:").append(restrictedAreaWkt);
        if(allowedStyles != null)
            sb.append(" styles(").append(allowedStyles.size()).append("):").append(allowedStyles);
        if(attributes != null)
            sb.append(" attrs:").append(attributes);
        sb.append(']');
        return sb.toString();
    }
}
