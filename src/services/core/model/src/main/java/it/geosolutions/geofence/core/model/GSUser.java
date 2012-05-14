/* ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */

package it.geosolutions.geofence.core.model;

import com.vividsolutions.jts.geom.MultiPolygon;
import it.geosolutions.geofence.core.model.adapter.FK2ProfileAdapter;
import it.geosolutions.geofence.core.model.adapter.MultiPolygonAdapter;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

/**
 * A User that can access GeoServer resources.
 *
 * <P>A GSUser is <B>not</B> in the domain of the users which can log into Geofence.
 *
 */
@Entity(name = "GSUser")
@Table(name = "gf_gsuser")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "GSUser")
@XmlRootElement(name = "GSUser")
@XmlType(propOrder={"id","extId","name","fullName","password","emailAddress","dateCreation","profile","allowedArea"})
public class GSUser implements Identifiable, Serializable {

    private static final long serialVersionUID = 7718458156939088033L;

    /** The id. */
    @Id
    @GeneratedValue
    @Column
    private Long id;

    /** External Id. An ID used in an external systems.
     * This field should simplify Geofence integration in complex systems.
     */
    @Column(nullable=true, updatable=false, unique=true)
    private String extId;

    /** The name. */
    @Column(nullable=false, unique=true)
    private String name;

    /** The user name. */
    @Column(nullable=true)
    private String fullName;

    /** The password. */
    @Column(nullable=true)
    private String password;

    /** The email address. */
    @Column(nullable=true)
    private String emailAddress;

    /** The date of creation of this user */
    @Column(updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    /** Is the GSUser Enabled or not in the system? */
    @Column(nullable=false)
    private boolean enabled = true;

    /** Is the GSUser a GS admin? */
    @Column(nullable=false)
    private boolean admin = false;

    /** The user. */
    @ManyToOne(optional = false)
    @ForeignKey(name="fk_user_profile")
    private Profile profile;

	@Type(type = "org.hibernatespatial.GeometryUserType")
	@Column(name = "allowedArea")
	private MultiPolygon allowedArea;

    /**
     * Instantiates a new user.
     */
    public GSUser() {
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    @XmlAttribute
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean isAdmin) {
        this.admin = isAdmin;
    }

    /**
     * @return the dateCreation
     */
    public Date getDateCreation() {
        return dateCreation;
    }

    /**
     * @param dateCreation
     *            the dateCreation to set
     */
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * @return the enabled
     */
    @XmlAttribute
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the profile
     */
    @XmlJavaTypeAdapter(FK2ProfileAdapter.class)
    public Profile getProfile() {
        return profile;
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @XmlJavaTypeAdapter(MultiPolygonAdapter.class)
    public MultiPolygon getAllowedArea() {
        return allowedArea;
    }

    public void setAllowedArea(MultiPolygon allowedArea) {
        this.allowedArea = allowedArea;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
        result = prime * result + (Boolean.valueOf(enabled).hashCode());
        result = prime * result + (Boolean.valueOf(admin).hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((profile == null) ? 0 : profile.hashCode());
        result = prime * result + ((allowedArea == null) ? 0 : allowedArea.hashCode());
        result = prime * result + ((allowedArea == null) ? 0 : allowedArea.getSRID());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GSUser)) {
            return false;
        }
        GSUser other = (GSUser) obj;
        if (dateCreation == null) {
            if (other.dateCreation != null) {
                return false;
            }
        } else if (!dateCreation.equals(other.dateCreation)) {
            return false;
        }
        if(enabled != other.enabled) {
            return false;
        }
        if(admin != other.admin) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (profile == null) {
            if (other.profile != null) {
                return false;
            }
        } else if (!profile.equals(other.profile)) {
            return false;
        }
        if (allowedArea == null) {
            if (other.allowedArea != null) {
                return false;
            }
        } else if (!allowedArea.equals(other.allowedArea)) {
            return false;
        } else if(other.allowedArea != null && other.allowedArea.getSRID() != allowedArea.getSRID()) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [");
        builder.append("id=").append(id).append(", ");
        if (name != null)
            builder.append("name=").append(name).append(", ");
        if (profile != null)
            builder.append("profile=").append(profile);
        builder.append("enabled=").append(enabled).append(", ");
        builder.append("admin=").append(admin).append(", ");
        if (dateCreation != null)
            builder.append("dateCreation=").append(dateCreation).append(", ");
        builder.append("]");
        return builder.toString();
    }

}
