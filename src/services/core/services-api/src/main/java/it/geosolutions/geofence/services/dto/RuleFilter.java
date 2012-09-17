/*
 *  Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
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

import java.io.Serializable;
import java.net.InetAddress;

import it.geosolutions.geofence.core.model.Rule;

import org.apache.log4j.Logger;

/**
 * A Filter for selecting {@link Rule}s. <P> For every given field, you may choose to select <LI>a given value</LI> <LI>any values
 * (no filtering)</LI> <LI>only default rules (null value in a field) </LI> </UL>
 *
 * For users, groups, instances (i.e., classes represented by DB entities) you may specify either the ID or the name.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RuleFilter implements Serializable {

    private static final long serialVersionUID = 5629211135629700041L;
    private static final Logger LOGGER = Logger.getLogger(RuleFilter.class);

    public enum FilterType {

        NAMEVALUE,
        IDVALUE,
        ANY,
        DEFAULT;
    }

    public enum SpecialFilterType {

        ANY(FilterType.ANY),
        DEFAULT(FilterType.DEFAULT);
        private FilterType relatedType;

        private SpecialFilterType(FilterType relatedType) {
            this.relatedType = relatedType;
        }

        public FilterType getRelatedType() {
            return relatedType;
        }
    }
    private final IdNameFilter user;
    private final IdNameFilter userGroup;
    private final IdNameFilter instance;
    private final NameFilter service;
    private final NameFilter request;
    private final NameFilter workspace;
    private final NameFilter layer;
    private InetAddress sourceAddress;

    public RuleFilter() {
        this(SpecialFilterType.DEFAULT);
    }

    /**
     * Creates a RuleFilter by setting all fields filtering either to ANY or DEFAULT. <BR>
     * If no other field is set, you will get <UL>
     * <LI>with <B>ANY</B>, all Rules will be returned</LI>
     * <LI>with <B>DEFAULT</B>, only the default Rule will be returned</LI>
     * </UL>
     */
    public RuleFilter(SpecialFilterType type) {
        FilterType ft = type.getRelatedType();

        user = new IdNameFilter(ft);
        userGroup = new IdNameFilter(ft);
        instance = new IdNameFilter(ft);
        service = new NameFilter(ft, true);
        request = new NameFilter(ft, true);
        workspace = new NameFilter(ft);
        layer = new NameFilter(ft);
    }

    public RuleFilter(SpecialFilterType type, boolean includeDefault) {
        FilterType ft = type.getRelatedType();

        user = new IdNameFilter(ft, includeDefault);
        userGroup = new IdNameFilter(ft, includeDefault);
        instance = new IdNameFilter(ft, includeDefault);
        service = new NameFilter(ft, true);
        service.setIncludeDefault(includeDefault);
        request = new NameFilter(ft, true);
        request.setIncludeDefault(includeDefault);
        workspace = new NameFilter(ft);
        workspace.setIncludeDefault(includeDefault);
        layer = new NameFilter(ft);
        layer.setIncludeDefault(includeDefault);
    }

    /**
     * Creates a RuleFilter by heuristically converting special string values into Fitler behaviour:<UL> <LI>a null value will
     * match only with null</LI> <LI>a '*' value will match everything (no filter condition on that given field)</LI> <LI>any
     * other string will match literally</LI> </UL>
     *
     * @deprecated Please use plain setters if you want to handle by hand special values or filter conditions.
     */
    public RuleFilter(String userName, String groupName, String instanceName, String service, String request,
            String workspace, String layer) {
        this(SpecialFilterType.DEFAULT);

        LOGGER.warn("Creating a RuleFilter heuristically");

        this.user.setHeuristically(userName);
        this.userGroup.setHeuristically(groupName);
        this.instance.setHeuristically(instanceName);

        this.service.setHeuristically(service);
        this.request.setHeuristically(request);
        this.workspace.setHeuristically(workspace);
        this.layer.setHeuristically(layer);
    }

    /**
     * Creates a RuleFilter by heuristically converting special string values into Fitler behaviour:<UL> <LI>a null value will
     * match only with null</LI> <LI>a '*' value will match everything (no filter condition on that given field)</LI> <LI>any
     * other string will match literally</LI> </UL>
     *
     * @deprecated Please use plain setters if you want to handle by hand special values or filter conditions.
     */
    public RuleFilter(Long userId, Long groupId, Long instanceId, String service, String request, String workspace,
            String layer) {
        this(SpecialFilterType.DEFAULT);

        LOGGER.warn("Creating a RuleFilter heuristically");

        this.user.setHeuristically(userId);
        this.userGroup.setHeuristically(groupId);
        this.instance.setHeuristically(instanceId);

        this.service.setHeuristically(service);
        this.request.setHeuristically(request);
        this.workspace.setHeuristically(workspace);
        this.layer.setHeuristically(layer);
    }

    public RuleFilter setUser(Long id) {
        user.setId(id);
        return this;
    }

    public RuleFilter setUser(String name) {
        user.setName(name);
        return this;
    }

    public RuleFilter setUser(SpecialFilterType type) {
        user.setType(type);
        return this;
    }

    public RuleFilter setUserGroup(Long id) {
        userGroup.setId(id);
        return this;
    }

    public RuleFilter setUserGroup(String name) {
        userGroup.setName(name);
        return this;
    }

    public RuleFilter setUserGroup(SpecialFilterType type) {
        userGroup.setType(type);
        return this;
    }

    public RuleFilter setInstance(Long id) {
        instance.setId(id);
        return this;
    }

    public RuleFilter setInstance(String name) {
        instance.setName(name);
        return this;
    }

    public RuleFilter setInstance(SpecialFilterType type) {
        instance.setType(type);
        return this;
    }

    public RuleFilter setService(String name) {
        service.setName(name);
        return this;
    }

    public RuleFilter setService(SpecialFilterType type) {
        service.setType(type);
        return this;
    }

    public RuleFilter setRequest(String name) {
        request.setName(name);
        return this;
    }

    public RuleFilter setRequest(SpecialFilterType type) {
        request.setType(type);
        return this;
    }

    public RuleFilter setWorkspace(String name) {
        workspace.setName(name);
        return this;
    }

    public RuleFilter setWorkspace(SpecialFilterType type) {
        workspace.setType(type);
        return this;
    }

    public RuleFilter setLayer(String name) {
        layer.setName(name);
        return this;
    }

    public RuleFilter setLayer(SpecialFilterType type) {
        layer.setType(type);
        return this;
    }

    public IdNameFilter getInstance() {
        return instance;
    }

    public NameFilter getLayer() {
        return layer;
    }

    public IdNameFilter getUserGroup() {
        return userGroup;
    }

    public NameFilter getRequest() {
        return request;
    }

    public NameFilter getService() {
        return service;
    }

    public IdNameFilter getUser() {
        return user;
    }

    public NameFilter getWorkspace() {
        return workspace;
    }

    public InetAddress getSourceAddress() {
        return sourceAddress;
    }

    public RuleFilter setSourceAddress(InetAddress sourceAddress) {
        this.sourceAddress = sourceAddress;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RuleFilter other = (RuleFilter) obj;
        if (this.user != other.user && (this.user == null || !this.user.equals(other.user))) {
            return false;
        }
        if (this.userGroup != other.userGroup && (this.userGroup == null || !this.userGroup.equals(other.userGroup))) {
            return false;
        }
        if (this.instance != other.instance && (this.instance == null || !this.instance.equals(other.instance))) {
            return false;
        }
        if (this.service != other.service && (this.service == null || !this.service.equals(other.service))) {
            return false;
        }
        if (this.request != other.request && (this.request == null || !this.request.equals(other.request))) {
            return false;
        }
        if (this.workspace != other.workspace && (this.workspace == null || !this.workspace.equals(other.workspace))) {
            return false;
        }
        if (this.layer != other.layer && (this.layer == null || !this.layer.equals(other.layer))) {
            return false;
        }
        //NOTE: ipaddress not in equals() bc it is not used for caching
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.user != null ? this.user.hashCode() : 0);
        hash = 37 * hash + (this.userGroup != null ? this.userGroup.hashCode() : 0);
        hash = 37 * hash + (this.instance != null ? this.instance.hashCode() : 0);
        hash = 37 * hash + (this.service != null ? this.service.hashCode() : 0);
        hash = 37 * hash + (this.request != null ? this.request.hashCode() : 0);
        hash = 37 * hash + (this.workspace != null ? this.workspace.hashCode() : 0);
        hash = 37 * hash + (this.layer != null ? this.layer.hashCode() : 0);
        //NOTE: ipaddress not in hashcode bc it is not used for caching
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        sb.append("user:").append(user);
        sb.append(" grp:").append(userGroup);
        sb.append(" inst:").append(instance);
        sb.append(" serv:").append(service);
        sb.append(" req:").append(request);
        sb.append(" ws:").append(workspace);
        sb.append(" layer:").append(layer);
        sb.append(" srcaddr:");
        if ( sourceAddress == null ) {
            sb.append('-');
        } else {
            sb.append(sourceAddress);
        }
        sb.append(']');

        return sb.toString();
    }

    public static class IdNameFilter implements Serializable {

        private static final long serialVersionUID = -5984311150423659545L;
        private Long id;
        private String name;
        private FilterType type;
        /**
         * Only used in TYPE_ID and TYPE_NAME, tells if also default Rules should be matched. Old code automatically included
         * default rules.
         */
        private boolean includeDefault = true;

        public IdNameFilter(FilterType type) {
            this.type = type;
        }

        public IdNameFilter(FilterType type, boolean includeDefault) {
            this.type = type;
            this.includeDefault = includeDefault;
        }

        public IdNameFilter(long id) {
            setId(id);
        }

        public IdNameFilter(long id, boolean includeDefault) {
            setId(id);
            this.includeDefault = includeDefault;
        }

        public IdNameFilter(String name, boolean includeDefault) {
            setName(name);
            this.includeDefault = includeDefault;
        }

        public void setHeuristically(String name) {
            if ( name == null ) {
                this.type = FilterType.DEFAULT;
            } else if ( name.equals("*") ) {
                this.type = FilterType.ANY;
            } else {
                this.type = FilterType.NAMEVALUE;
                this.name = name;
            }
        }

        public void setHeuristically(Long id) {
            if ( id == null ) {
                this.type = FilterType.DEFAULT;
            } else {
                this.type = FilterType.IDVALUE;
                this.id = id;
            }
        }

        public void setId(Long id) {
            this.id = id;
            this.type = FilterType.IDVALUE;
        }

        public void setName(String name) {
            this.name = name;
            this.type = FilterType.NAMEVALUE;
        }

        public void setType(SpecialFilterType type) {
            this.type = type.getRelatedType();
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public FilterType getType() {
            return type;
        }

        public boolean isIncludeDefault() {
            return includeDefault;
        }

        public void setIncludeDefault(boolean includeDefault) {
            this.includeDefault = includeDefault;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final IdNameFilter other = (IdNameFilter) obj;
            if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
                return false;
            }
            if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
                return false;
            }
            if (this.type != other.type) {
                return false;
            }
            if (this.includeDefault != other.includeDefault) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
            hash = 83 * hash + (this.name != null ? this.name.hashCode() : 0);
            hash = 83 * hash + (this.type != null ? this.type.hashCode() : 0);
            hash = 83 * hash + (this.includeDefault ? 1 : 0);
            return hash;
        }

        @Override
        public String toString() {
            switch (type) {
                case ANY:
                case DEFAULT:
                    return type.toString();

                case IDVALUE:
                    return "id" + (includeDefault?"+:":":") + (id==null?"(null)":id.toString());

                case NAMEVALUE:
                    return "name" + (includeDefault?"+:":":") + name == null ? "(null)" : name.isEmpty() ? "(empty)" : name;

                default:
                    throw new AssertionError();
            }
        }
    }

    public static class NameFilter implements Serializable {

        private static final long serialVersionUID = 6565336016075974626L;
        private String name;
        private FilterType type;
        private boolean forceUppercase = false;
        /**
         * Only used in TYPE_NAME, tells if also default Rules should be matched. 
         */
        private boolean includeDefault = false;

        public NameFilter(FilterType type) {
            this.type = type;
        }

        public NameFilter(FilterType type, boolean forceUppercase) {
            this.type = type;
            this.forceUppercase = forceUppercase;
        }

        public void setHeuristically(String name) {
            if ( name == null ) {
                this.type = FilterType.DEFAULT;
            } else if ( name.equals("*") ) {
                this.type = FilterType.ANY;
            } else {
                this.type = FilterType.NAMEVALUE;
                this.name = forceUppercase ? name.toUpperCase() : name;
            }
        }

        public void setName(String name) {
            this.name = forceUppercase ? name.toUpperCase() : name;
            this.type = FilterType.NAMEVALUE;
        }

        public void setType(SpecialFilterType type) {
            this.type = type.getRelatedType();
        }

        public String getName() {
            return name;
        }

        public FilterType getType() {
            return type;
        }

        public boolean isIncludeDefault() {
            return includeDefault;
        }

        public void setIncludeDefault(boolean includeDefault) {
            this.includeDefault = includeDefault;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final NameFilter other = (NameFilter) obj;
            if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
                return false;
            }
            if (this.type != other.type) {
                return false;
            }
            if (this.forceUppercase != other.forceUppercase) {
                return false;
            }
            if (this.includeDefault != other.includeDefault) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 61 * hash + (this.name != null ? this.name.hashCode() : 0);
            hash = 61 * hash + (this.type != null ? this.type.hashCode() : 0);
            hash = 61 * hash + (this.forceUppercase ? 1 : 0);
            hash = 61 * hash + (this.includeDefault ? 1 : 0);
            return hash;
        }

        @Override
        public String toString() {
            switch (type) {
                case ANY:
                case DEFAULT:
                    return type.toString();

                case NAMEVALUE:
                    return "name" + (includeDefault?"+:":":") + name == null ? "(null)" : name.isEmpty() ? "(empty)" : name;

                case IDVALUE:
                default:
                    throw new AssertionError();
            }
        }
    }
}
