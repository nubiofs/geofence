/*
 *  Copyright (C) 2007 - 2014 GeoSolutions S.A.S.
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

package it.geosolutions.geofence.services.dto;

import it.geosolutions.geofence.core.model.LayerAttribute;
import it.geosolutions.geofence.core.model.enums.GrantType;

import java.io.Serializable;
import java.util.Set;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class AccessInfo implements Serializable {
    
    private static final long serialVersionUID = -9108763358187355342L;

    /**
     * Default "allow everything" AccessInfo
     */
    public static final AccessInfo ALLOW_ALL = new AccessInfo(GrantType.ALLOW);
    
    /**
     * Default "deny everything" AccessInfo
     */
    public static final AccessInfo DENY_ALL = new AccessInfo(GrantType.DENY);

    /**
     * The resulting grant: allow or deny.
     */
    private GrantType grant = GrantType.DENY;

//    private Geometry area;
    private String areaWkt;

    private CatalogModeDTO catalogMode;

    private String defaultStyle;

    private String cqlFilterRead;
    private String cqlFilterWrite;

    private Set<LayerAttribute> attributes;
    private Set<String> allowedStyles;


    public AccessInfo() {
    }

    public AccessInfo(GrantType grant) {
        this.grant = grant;
    }

    public String getAreaWkt() {
        return areaWkt;
    }

    public void setAreaWkt(String areaWkt) {
        this.areaWkt = areaWkt;
    }

    public Set<LayerAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<LayerAttribute> attributes) {
        this.attributes = attributes;
    }

    public CatalogModeDTO getCatalogMode() {
        return catalogMode;
    }

    public void setCatalogMode(CatalogModeDTO catalogMode) {
        this.catalogMode = catalogMode;
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

    public Set<String> getAllowedStyles() {
        return allowedStyles;
    }

    public void setAllowedStyles(Set<String> allowedStyles) {
        this.allowedStyles = allowedStyles;
    }

    public GrantType getGrant() {
        return grant;
    }

    public void setGrant(GrantType grant) {
        if(grant != GrantType.ALLOW && grant != GrantType.DENY)
            throw new IllegalArgumentException("Bad grant type " + grant);
        this.grant = grant;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName())
                .append("[grant:").append(grant);

        if (defaultStyle != null) {
            sb.append(" defSty:").append(defaultStyle);
        }
        if (cqlFilterRead != null) {
            sb.append(" cqlR:").append(cqlFilterRead);
        }
        if (cqlFilterWrite != null) {
            sb.append(" cqlW:").append(cqlFilterWrite);
        }
        if (areaWkt != null) {
            sb.append(" areaWkt:defined");
        }
        if (catalogMode != null) {
            sb.append(" cmode:").append(catalogMode);
        }
        if (allowedStyles != null && ! allowedStyles.isEmpty()) {
            sb.append(" allSty:").append(allowedStyles); // needs decoding?
        }
        if (attributes != null && ! attributes.isEmpty()) {
            sb.append(" attr:").append(attributes); // needs decoding?
        }

        sb.append(']');

        return sb.toString();
    }
}
