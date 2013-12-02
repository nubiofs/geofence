/*
 *  Copyright (C) 2007 - 2013 GeoSolutions S.A.S.
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
package it.geosolutions.geofence;

import java.io.Serializable;

/**
 * Configuration object for GeofenceAccessManager.
 * 
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class GeofenceAccessManagerConfiguration implements Serializable, Cloneable {
    
    private static final long serialVersionUID = 1L;

    String servicesUrl;
    
    String instanceName;
    
    boolean allowRemoteAndInlineLayers;
    
    boolean allowDynamicStyles;
    
    boolean grantWriteToWorkspacesToAuthenticatedUsers;
    
    boolean useRolesToFilter;
    
    String acceptedRoles;
    
    /**
     * Remote GeoFence services url.
     * @return
     */
    public String getServicesUrl() {
        return servicesUrl;
    }

    /**
     * Remote GeoFence services url.
     * @param servicesUrl
     */
    public void setServicesUrl(String servicesUrl) {
        this.servicesUrl = servicesUrl;
    }

    /**
     * Name of this GeoServer instance for GeoFence rule configuration.
     * 
     * @param instanceName the instanceName to set
     */
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
    
    /**
     * Name of this GeoServer instance for GeoFence rule configuration.
     * 
     * @return the instanceName
     */
    public String getInstanceName() {
        return instanceName;
    }
    
    /**
     * Flag to allow usage of remote and inline layers in SLDs.
     * 
     * @param allowRemoteAndInlineLayers
     */
    public void setAllowRemoteAndInlineLayers(boolean allowRemoteAndInlineLayers) {
        this.allowRemoteAndInlineLayers = allowRemoteAndInlineLayers;
    }
    
    /**
     * Flag to allow usage of SLD and/or SLD_BODY params in GetMap requests.
     * 
     * @param allowDynamicStyles
     */
    public void setAllowDynamicStyles(boolean allowDynamicStyles) {
        this.allowDynamicStyles = allowDynamicStyles;
    }
    
    /**
     * Flag to allow usage of remote and inline layers in SLDs.
     * @return
     */
    public boolean isAllowRemoteAndInlineLayers() {
        return allowRemoteAndInlineLayers;
    }

    /**
     * Flag to allow usage of SLD and/or SLD_BODY params in GetMap requests.
     * 
     * @return
     */
    public boolean isAllowDynamicStyles() {
        return allowDynamicStyles;
    }

    /**
     * Allows write access to resources to authenticated users, if false
     * only ADMINs have write access.
     * 
     * @return the grantWriteToWorkspacesToAuthenticatedUsers
     */
    public boolean isGrantWriteToWorkspacesToAuthenticatedUsers() {
        return grantWriteToWorkspacesToAuthenticatedUsers;
    }
    
    /**
     * Allows write access to resources to authenticated users, if false
     * only ADMINs have write access.
     * 
     * @param grantWriteToWorkspacesToAuthenticatedUsers the
     *        grantWriteToWorkspacesToAuthenticatedUsers to set
     */
    public void setGrantWriteToWorkspacesToAuthenticatedUsers(
            boolean grantWriteToWorkspacesToAuthenticatedUsers) {
        this.grantWriteToWorkspacesToAuthenticatedUsers = grantWriteToWorkspacesToAuthenticatedUsers;
    }
    
    /**
     * Use authenticated users roles to match rules, instead of username.
     * 
     * @return the useRolesToFilter
     */
    public boolean isUseRolesToFilter() {
        return useRolesToFilter;
    }
    
    /**
     * Use authenticated users roles to match rules, instead of username.
     * 
     * @param useRolesToFilter the useRolesToFilter to set
     */
    public void setUseRolesToFilter(boolean useRolesToFilter) {
        this.useRolesToFilter = useRolesToFilter;
    }
    
    /**
     * List of mutually exclusive roles used for rule matching when useRolesToFilter
     * is true.
     * 
     * @return the acceptedRoles
     */
    public String getAcceptedRoles() {
        return acceptedRoles;
    }
    
    /**
     * List of mutually exclusive roles used for rule matching when useRolesToFilter
     * is true.
     * 
     * @param acceptedRoles the acceptedRoles to set
     */
    public void setAcceptedRoles(String acceptedRoles) {
    
        this.acceptedRoles = acceptedRoles;
    }
    
    /**
     * Creates a copy of the configuration object.
     */
    public GeofenceAccessManagerConfiguration clone() {
        GeofenceAccessManagerConfiguration copy = new GeofenceAccessManagerConfiguration();
        copy.setServicesUrl(servicesUrl);
        copy.setInstanceName(instanceName);
        copy.setAcceptedRoles(acceptedRoles);
        copy.setAllowDynamicStyles(allowDynamicStyles);
        copy.setAllowRemoteAndInlineLayers(allowRemoteAndInlineLayers);
        copy.setGrantWriteToWorkspacesToAuthenticatedUsers(grantWriteToWorkspacesToAuthenticatedUsers);
        copy.setUseRolesToFilter(useRolesToFilter);
        return copy;
    }

}
