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

package it.geosolutions.geofence.services.util;

import java.io.Serializable;
import java.util.Set;

import com.vividsolutions.jts.geom.Geometry;

import it.geosolutions.geofence.core.model.LayerAttribute;
import it.geosolutions.geofence.core.model.enums.CatalogMode;
import it.geosolutions.geofence.core.model.enums.GrantType;
import it.geosolutions.geofence.services.dto.AccessInfo;
import it.geosolutions.geofence.services.dto.CatalogModeDTO;
import java.util.HashSet;


/**
 * This class is used internally when merging restrictedArea, because it deals with Geometries and not with WKT strings.
 * Also note the collections here can be null, where {@link AccessInfo AccessInfo}
 * collections are initt'ed.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class AccessInfoInternal implements Serializable {
    
    private static final long serialVersionUID = -9108763358187355342L;

//    /**
//     * Default "allow everything" AccessInfo
//     */
//    public static final AccessInfoInternal ALLOW_ALL = new AccessInfoInternal(GrantType.ALLOW);
//
//    /**
//     * Default "deny everything" AccessInfo
//     */
//    public static final AccessInfoInternal DENY_ALL = new AccessInfoInternal(GrantType.DENY);

    /**
     * The resulting grant: allow or deny.
     */
    private GrantType grant = GrantType.DENY;

//    private Geometry area;
    private Geometry area;

    private CatalogMode catalogMode;

    private String defaultStyle;

    private String cqlFilterRead;
    private String cqlFilterWrite;

    private Set<LayerAttribute> attributes;
    private Set<String> allowedStyles;


    public AccessInfoInternal() {
    }

    public AccessInfoInternal(GrantType grant) {
        this.grant = grant;
    }

//    public Geometry getArea() {
//        return area;
//    }
//
//    public void setArea(Geometry area) {
//        this.area = area;
//    }

    public Geometry getArea() {
        return area;
    }

    public void setArea(Geometry area) {
        this.area = area;
    }

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

    public Set<String> getAllowedStyles() {
        return allowedStyles;
    }

    public void setAllowedStyles(Set<String> allowedStyles) {
        this.allowedStyles = allowedStyles;
    }

    public CatalogMode getCatalogMode() {
        return catalogMode;
    }

    public void setCatalogMode(CatalogMode catalogMode) {
        this.catalogMode = catalogMode;
    }

    public GrantType getGrant() {
        return grant;
    }

    public void setGrant(GrantType grant) {
        if(grant != GrantType.ALLOW && grant != GrantType.DENY)
            throw new IllegalArgumentException("Bad grant type " + grant);
        this.grant = grant;
    }

    public AccessInfo toAccessInfo() {
        AccessInfo ret = new AccessInfo();

        ret.setGrant(grant);
        ret.setDefaultStyle(defaultStyle);
        ret.setAllowedStyles(allowedStyles == null? null : new HashSet(allowedStyles)); // new Set will prevent marshalling hibernate classes
        ret.setAttributes(attributes == null ? null : new HashSet<LayerAttribute>(attributes));
        ret.setCqlFilterRead(cqlFilterRead);
        ret.setCqlFilterWrite(cqlFilterWrite);
        if(area != null)
            ret.setAreaWkt(area.toText());
        ret.setCatalogMode(mapCatalogModeDTO(catalogMode));

        return ret;
    }

    protected static CatalogModeDTO mapCatalogModeDTO(CatalogMode cm) {
        if(cm == null)
            return null;
        switch (cm) {
            case CHALLENGE:
                return CatalogModeDTO.CHALLENGE;
            case HIDE:
                return CatalogModeDTO.HIDE;
            case MIXED:
                return CatalogModeDTO.MIXED;
            default:
                return null;
        }
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
//        if (area != null) {
//            sb.append(" area: [")
//                    .append(area.getNumPoints()).append(" vertices, (")
//                    .append(area.getCoordinate().x).append(',').append(area.getCoordinate().y).append(')');
//        }
        if (area != null) {
            sb.append(" area:defined");
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
