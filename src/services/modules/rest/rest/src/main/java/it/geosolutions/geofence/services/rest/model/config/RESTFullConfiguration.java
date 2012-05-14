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

package it.geosolutions.geofence.services.rest.model.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */

@XmlRootElement(name = "GeofenceConfiguration")
@XmlType(propOrder = { "profileList", "userList", "grUserList", "gsInstanceList", "ruleList" })
public class RESTFullConfiguration
{

    private RESTFullProfileList profileList;
    private RESTFullUserList userList;
    private RESTFullGRUserList grUserList;
    private RESTFullGSInstanceList gsInstanceList;
    private RESTFullRuleList ruleList;

    public RESTFullConfiguration()
    {
    }

    @XmlElement(name = "GSInstances")
    public RESTFullGSInstanceList getGsInstanceList()
    {
        return gsInstanceList;
    }

    public void setGsInstanceList(RESTFullGSInstanceList gsInstanceList)
    {
        this.gsInstanceList = gsInstanceList;
    }

    @XmlElement(name = "Profiles")
    public RESTFullProfileList getProfileList()
    {
        return profileList;
    }

    public void setProfileList(RESTFullProfileList profileList)
    {
        this.profileList = profileList;
    }

    @XmlElement(name = "Rules")
    public RESTFullRuleList getRuleList()
    {
        return ruleList;
    }

    public void setRuleList(RESTFullRuleList ruleList)
    {
        this.ruleList = ruleList;
    }

    @XmlElement(name = "Users")
    public RESTFullUserList getUserList()
    {
        return userList;
    }

    public void setUserList(RESTFullUserList userList)
    {
        this.userList = userList;
    }

    @XmlElement(name = "InternalUsers")
    public RESTFullGRUserList getGrUserList()
    {
        return grUserList;
    }

    public void setGrUserList(RESTFullGRUserList grUserList)
    {
        this.grUserList = grUserList;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append('[');
        if (profileList != null)
        {
            sb.append(profileList);
        }
        if (userList != null)
        {
            sb.append(", ").append(userList);
        }
        if (gsInstanceList != null)
        {
            sb.append(", ").append(gsInstanceList);
        }
        if (ruleList != null)
        {
            sb.append(", ").append(ruleList);
        }
        if ((grUserList != null) && (grUserList.getList() != null))
        {
            sb.append(", ").append(grUserList.getList().size()).append(" internal users");
        }
        sb.append(']');

        return sb.toString();
    }

}
