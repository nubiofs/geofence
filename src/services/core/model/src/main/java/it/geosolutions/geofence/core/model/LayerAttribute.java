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

package it.geosolutions.geofence.core.model;

import it.geosolutions.geofence.core.model.enums.AccessType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@Embeddable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "LayerAttribute")
@XmlRootElement(name = "LayerAttribute")
public class LayerAttribute implements Serializable {

    private static final long serialVersionUID = -4739817113509675752L;

    @Column(nullable=false)
    private String name;

    @Column(name="data_type")
    private String datatype; // should be an enum?

    /** 
     * Tells if the attribute can be read, written, or not accessed at all.
     * <P>
     * This field should be notnull, but making it so, hibernate will insist to
     * put it into the PK.
     * We'll making it notnull in the {@link LayerDetails#attributes parent class},
     * but this seems not to work. We're enforncing the notnull at the DAO level.
     *
     */
    @Enumerated(EnumType.STRING)
    @Column(name="access_type", nullable = true /*false*/)
    private AccessType access;

    public LayerAttribute() {
    }

    public LayerAttribute(String name, AccessType access) {
        this.name = name;
        this.access = access;
    }

    public LayerAttribute(String name, String datatype, AccessType access) {
        this.name = name;
        this.datatype = datatype;
        this.access = access;
    }

    @XmlAttribute
    public AccessType getAccess() {
        return access;
    }

    public void setAccess(AccessType access) {
        this.access = access;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LayerAttribute other = (LayerAttribute) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.datatype == null) ? (other.datatype != null) : !this.datatype.equals(other.datatype)) {
            return false;
        }
        if (this.access != other.access) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 29 * hash + (this.datatype != null ? this.datatype.hashCode() : 0);
        hash = 29 * hash + (this.access != null ? this.access.hashCode() : 0);
        return hash;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName())
                .append("[name:").append(name)
                .append(" access:").append(access);

        if (datatype != null) {
            sb.append(" type:").append(datatype);
        }
        sb.append("]");

        return sb.toString();

    }

}
