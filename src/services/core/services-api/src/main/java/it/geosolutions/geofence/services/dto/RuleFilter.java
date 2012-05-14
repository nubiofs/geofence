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
 * A Filter for selecting {@link Rule}s.
 * <P>
 * For every given field, you may choose to select
 * <LI>a given value</LI>
 * <LI>any values (no filtering)</LI>
 * <LI>only default rules (null value in a field) </LI>
 * </UL>
 *
 * For users, profiles, instances (i.e., classes represented by DB entities)
 * you may specify either the ID or the name.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RuleFilter implements Serializable
{
    private static final long serialVersionUID = 5629211135629700041L;
    private static final Logger LOGGER = Logger.getLogger(RuleFilter.class);

    public enum FilterType
    {
        NAMEVALUE,
        IDVALUE,
        ANY,
        DEFAULT;
    }

    public enum SpecialFilterType
    {
        ANY(FilterType.ANY),
        DEFAULT(FilterType.DEFAULT);

        private FilterType relatedType;

        private SpecialFilterType(FilterType relatedType)
        {
            this.relatedType = relatedType;
        }

        public FilterType getRelatedType()
        {
            return relatedType;
        }
    }

    private final IdNameFilter user;
    private final IdNameFilter profile;
    private final IdNameFilter instance;

    private final NameFilter service;
    private final NameFilter request;
    private final NameFilter workspace;
    private final NameFilter layer;

    private InetAddress sourceAddress;


    public RuleFilter()
    {
        this(SpecialFilterType.DEFAULT);
    }

    /**
     * Creates a RuleFilter by setting all fields filtering either to ANY or DEFAULT.
     * <BR>If no other field is set, you will get
     * <LI>with <B>ANY</B>, all Rules will be returned</LI>
     * <LI>with <B>DEFAULT</B>, only the default Rule will be returned</LI>
     * </UL>
     */
    public RuleFilter(SpecialFilterType type)
    {
        FilterType ft = type.getRelatedType();

        user = new IdNameFilter(ft);
        profile = new IdNameFilter(ft);
        instance = new IdNameFilter(ft);
        service = new NameFilter(ft, true);
        request = new NameFilter(ft, true);
        workspace = new NameFilter(ft);
        layer = new NameFilter(ft);
    }

    /**
     * Creates a RuleFilter by heuristically converting special string values into
     * Fitler behaviour:<UL>
     * <LI>a null value will match only with null</LI>
     * <LI>a '*' value will match everything (no filter condition on that given field)</LI>
     * <LI>any other string will match literally</LI>
     * </UL>
     * @deprecated Please use plain setters if you want to handle by hand special values or filter conditions.
     */
    public RuleFilter(String userName, String profileName, String instanceName, String service, String request,
        String workspace, String layer)
    {
        this(SpecialFilterType.DEFAULT);

        LOGGER.warn("Creating a RuleFilter heuristically");

        this.user.setHeuristically(userName);
        this.profile.setHeuristically(profileName);
        this.instance.setHeuristically(instanceName);

        this.service.setHeuristically(service);
        this.request.setHeuristically(request);
        this.workspace.setHeuristically(workspace);
        this.layer.setHeuristically(layer);
    }

    /**
     * Creates a RuleFilter by heuristically converting special string values into
     * Fitler behaviour:<UL>
     * <LI>a null value will match only with null</LI>
     * <LI>a '*' value will match everything (no filter condition on that given field)</LI>
     * <LI>any other string will match literally</LI>
     * </UL>
     * @deprecated Please use plain setters if you want to handle by hand special values or filter conditions.
     */
    public RuleFilter(Long userId, Long profileId, Long instanceId, String service, String request, String workspace,
        String layer)
    {
        this(SpecialFilterType.DEFAULT);

        LOGGER.warn("Creating a RuleFilter heuristically");

        this.user.setHeuristically(userId);
        this.profile.setHeuristically(profileId);
        this.instance.setHeuristically(instanceId);

        this.service.setHeuristically(service);
        this.request.setHeuristically(request);
        this.workspace.setHeuristically(workspace);
        this.layer.setHeuristically(layer);
    }

    public void setUser(Long id)
    {
        user.setId(id);
    }

    public void setUser(String name)
    {
        user.setName(name);
    }

    public void setUser(SpecialFilterType type)
    {
        user.setType(type);
    }

    public void setProfile(Long id)
    {
        profile.setId(id);
    }

    public void setProfile(String name)
    {
        profile.setName(name);
    }

    public void setProfile(SpecialFilterType type)
    {
        profile.setType(type);
    }

    public void setInstance(Long id)
    {
        instance.setId(id);
    }

    public void setInstance(String name)
    {
        instance.setName(name);
    }

    public void setInstance(SpecialFilterType type)
    {
        instance.setType(type);
    }

    public void setService(String name)
    {
        service.setName(name);
    }

    public void setService(SpecialFilterType type)
    {
        service.setType(type);
    }

    public void setRequest(String name)
    {
        request.setName(name);
    }

    public void setRequest(SpecialFilterType type)
    {
        request.setType(type);
    }

    public void setWorkspace(String name)
    {
        workspace.setName(name);
    }

    public void setWorkspace(SpecialFilterType type)
    {
        workspace.setType(type);
    }

    public void setLayer(String name)
    {
        layer.setName(name);
    }

    public void setLayer(SpecialFilterType type)
    {
        layer.setType(type);
    }

    public IdNameFilter getInstance()
    {
        return instance;
    }

    public NameFilter getLayer()
    {
        return layer;
    }

    public IdNameFilter getProfile()
    {
        return profile;
    }

    public NameFilter getRequest()
    {
        return request;
    }

    public NameFilter getService()
    {
        return service;
    }

    public IdNameFilter getUser()
    {
        return user;
    }

    public NameFilter getWorkspace()
    {
        return workspace;
    }

    public InetAddress getSourceAddress()
    {
        return sourceAddress;
    }

    public void setSourceAddress(InetAddress sourceAddress)
    {
        this.sourceAddress = sourceAddress;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        sb.append("user:").append(user);
        sb.append(" prof:").append(profile);
        sb.append(" inst:").append(instance);
        sb.append(" serv:").append(service);
        sb.append(" req:").append(request);
        sb.append(" ws:").append(workspace);
        sb.append(" layer:").append(layer);
        sb.append(" srcaddr:");
        if (sourceAddress == null)
        {
            sb.append('-');
        }
        else
        {
            sb.append(sourceAddress);
        }
        sb.append(']');

        return sb.toString();
    }


    public static class IdNameFilter implements Serializable
    {
        private static final long serialVersionUID = -5984311150423659545L;

        private Long id;
        private String name;
        private FilterType type;

        public IdNameFilter(FilterType type)
        {
            this.type = type;
        }

        public void setHeuristically(String name)
        {
            if (name == null)
            {
                this.type = FilterType.DEFAULT;
            }
            else if (name.equals("*"))
            {
                this.type = FilterType.ANY;
            }
            else
            {
                this.type = FilterType.NAMEVALUE;
                this.name = name;
            }
        }

        public void setHeuristically(Long id)
        {
            if (id == null)
            {
                this.type = FilterType.DEFAULT;
            }
            else
            {
                this.type = FilterType.IDVALUE;
                this.id = id;
            }
        }

        public void setId(Long id)
        {
            this.id = id;
            this.type = FilterType.IDVALUE;
        }

        public void setName(String name)
        {
            this.name = name;
            this.type = FilterType.NAMEVALUE;
        }

        public void setType(SpecialFilterType type)
        {
            this.type = type.getRelatedType();
        }

        public Long getId()
        {
            return id;
        }

        public String getName()
        {
            return name;
        }

        public FilterType getType()
        {
            return type;
        }

        @Override
        public String toString()
        {
            switch (type)
            {
            case ANY:
            case DEFAULT:
                return type.toString();

            case IDVALUE:
                return new StringBuilder("id:").append(id.toString()).toString();

            case NAMEVALUE:
                return name;

            default:
                throw new AssertionError();
            }
        }
    }


    public static class NameFilter implements Serializable
    {
        private static final long serialVersionUID = 6565336016075974626L;
        private String name;
        private FilterType type;
        private boolean forceUppercase = false;

        public NameFilter(FilterType type)
        {
            this.type = type;
        }

        public NameFilter(FilterType type, boolean forceUppercase)
        {
            this.type = type;
            this.forceUppercase = forceUppercase;
        }

        public void setHeuristically(String name)
        {
            if (name == null)
            {
                this.type = FilterType.DEFAULT;
            }
            else if (name.equals("*"))
            {
                this.type = FilterType.ANY;
            }
            else
            {
                this.type = FilterType.NAMEVALUE;
                this.name = forceUppercase ? name.toUpperCase() : name;
            }
        }

        public void setName(String name)
        {
            this.name = forceUppercase ? name.toUpperCase() : name;
            this.type = FilterType.NAMEVALUE;
        }

        public void setType(SpecialFilterType type)
        {
            this.type = type.getRelatedType();
        }

        public String getName()
        {
            return name;
        }

        public FilterType getType()
        {
            return type;
        }

        @Override
        public String toString()
        {
            switch (type)
            {
            case ANY:
            case DEFAULT:
                return type.toString();

            case NAMEVALUE:
                return name;

            case IDVALUE:
            default:
                throw new AssertionError();
            }
        }

    }
}
