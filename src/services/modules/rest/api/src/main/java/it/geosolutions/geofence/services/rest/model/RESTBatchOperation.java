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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name = "Operation")
@XmlType(propOrder={"payload"})
public class RESTBatchOperation {

    public enum ServiceName {
        users,
        groups,
        instances,
        rules
    }

    private ServiceName service;
    private String type;
    private Long id;
    private String name;
    private Boolean cascade;

    private AbstractRESTPayload payload;

    public RESTBatchOperation() {
    }

    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public ServiceName getService() {
        return service;
    }

    public void setService(ServiceName service) {
        this.service = service;
    }

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute
    public Boolean getCascade() {
        return cascade;
    }

    public void setCascade(Boolean cascade) {
        this.cascade = cascade;
    }

    @XmlElements({
        @XmlElement(name="user",     nillable=true, type=RESTInputUser.class),
        @XmlElement(name="userGroup",    nillable=true, type=RESTInputGroup.class),
        @XmlElement(name="instance", nillable=true, type=RESTInputInstance.class),
        @XmlElement(name="rule",     nillable=true, type=RESTInputRule.class)
    })
    public AbstractRESTPayload getPayload() {
        return payload;
    }

    public void setPayload(AbstractRESTPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" 
                + service + "." + type
                + (id!=null? " id:"+id : "")
                + (name != null? " name:"+name:"")
                + (cascade != null? " cascade:"+cascade:"")
                + (payload!=null? " payload is a " + payload.getClass().getSimpleName() : "")
                + "]";
    }
}
