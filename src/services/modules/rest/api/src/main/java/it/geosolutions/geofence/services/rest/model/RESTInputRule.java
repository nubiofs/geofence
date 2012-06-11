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
import it.geosolutions.geofence.core.model.enums.GrantType;
import it.geosolutions.geofence.core.model.enums.LayerType;
import it.geosolutions.geofence.services.rest.model.util.IdName;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import org.springframework.expression.spel.ast.Identifier;

/**
 * A compact representation of Rule
 *
 * @author Etj (etj at geo-solutions.it)
 */
@XmlRootElement(name = "Rule")
@XmlType(propOrder={"position","grant","user","group","instance","service","request","workspace","layer","constraints"})
public class RESTInputRule implements Serializable {

    private RESTRulePosition position;

    private IdName user;
    private IdName group;

    private IdName instance;

    private String service;
    private String request;

    private String workspace;
    private String layer;

    private GrantType grant;

    private RESTLayerConstraints constraints;

    public RESTInputRule() {
    }

    public void setUserId(Long id) {
        user = new IdName(id);
    }

    public void setUserName(String name) {
        user = new IdName(name);
    }

    public void setGroupId(Long id) {
        group = new IdName(id);
    }

    public void setGroupName(String name) {
        group = new IdName(name);
    }

    public void setInstanceId(Long id) {
        instance = new IdName(id);
    }

    public void setInstanceName(String name) {
        instance = new IdName(name);
    }

    public void setGroup(IdName group) {
        this.group = group;
    }

    public void setInstance(IdName instance) {
        this.instance = instance;
    }

    public void setUser(IdName user) {
        this.user = user;
    }

    public IdName getGroup() {
        return group;
    }

    public IdName getInstance() {
        return instance;
    }

    public IdName getUser() {
        return user;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }



    public RESTLayerConstraints getConstraints() {
        return constraints;
    }

    public void setConstraints(RESTLayerConstraints constraints) {
        this.constraints = constraints;
    }

    public RESTRulePosition getPosition() {
        return position;
    }

    public void setPosition(RESTRulePosition position) {
        this.position = position;
    }

    @XmlAttribute
    public GrantType getGrant() {
        return grant;
    }

    public void setGrant(GrantType grant) {
        this.grant = grant;
    }


    @XmlType(propOrder={"type","defaultStyle","cqlFilterRead","cqlFilterWrite","restrictedAreaWkt",
        "allowedStyles","attributes"})
    public static class RESTLayerConstraints {

        private LayerType type;
        private String defaultStyle;
        private String cqlFilterRead;
        private String cqlFilterWrite;
        private String restrictedAreaWkt;
        private Set<String> allowedStyles = new HashSet<String>();
        private Set<LayerAttribute> attributes = new HashSet<LayerAttribute>();

        @XmlElementWrapper(name="allowedStyles")
        @XmlElement(name="style")
        public Set<String> getAllowedStyles() {
            return allowedStyles;
        }

        public void setAllowedStyles(Set<String> allowedStyles) {
            this.allowedStyles = allowedStyles;
        }

        @XmlElementWrapper(name="attributes")
        @XmlElement(name="attribute")
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
        
    }

    public static class RESTRulePosition {

        public enum RulePosition {

            fixedPriority,
            offsetFromTop,
            offsetFromBottom
        }
        private RulePosition position;
        private long value;

        public RESTRulePosition() {
        }

        public RESTRulePosition(RulePosition position, long value) {
            this.position = position;
            this.value = value;
        }

        @XmlAttribute
        public RulePosition getPosition() {
            return position;
        }

        public void setPosition(RulePosition position) {
            this.position = position;
        }

        @XmlAttribute
        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }
}
