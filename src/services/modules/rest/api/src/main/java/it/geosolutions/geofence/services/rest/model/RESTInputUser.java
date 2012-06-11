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

import it.geosolutions.geofence.services.rest.model.util.IdName;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@XmlRootElement(name = "User")
@XmlType(propOrder = {"extId", "name", "password", "fullName", "emailAddress", "groups"})
public class RESTInputUser {

    private String extId;
    private String name;
    private String password;
    private String fullName;
    private String emailAddress;
    private Boolean enabled;
    private Boolean admin;
    private List<IdName> groups;

//    @XmlAttribute(name = "extid")
    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    @XmlAttribute(name = "admin")
    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @XmlAttribute(name = "enabled")
    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @XmlElementWrapper(name = "groups")
    @XmlElement(name = "group")
    public List<IdName> getGroups() {
        return groups;
    }

    public void setGroups(List<IdName> groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @Override
//    public String toString()
//    {
//        return this.getClass().getSimpleName() +
//            '[' +
//            "sguId=" + extId +
//            " userName=" + userName +
//            " profile=" + profile +
//            (enabled ? " enabled" : " disabled") +
//            ']';
//    }
}
