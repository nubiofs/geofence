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

import com.vividsolutions.jts.geom.MultiPolygon;
import it.geosolutions.geofence.core.model.adapter.MultiPolygonAdapter;
import it.geosolutions.geofence.core.model.enums.GrantType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

/**
 * Defines general limits (such as an Area ) for a {@link Rule}.
 * <BR>RuleLimits may be set only for rules with a {@link GrantType#LIMIT} access type.
 *
 * @author ETj (etj at geo-solutions.it)
 */
@Entity(name = "RuleLimits")
@Table(name = "gf_rule_limits",
    uniqueConstraints= @UniqueConstraint(columnNames="rule_id"))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "RuleLimits")
@XmlRootElement(name = "RuleLimits")
public class RuleLimits implements Serializable {

    private static final long serialVersionUID = 2829839552804345725L;

    /** The id. */
    @Id
//    @GeneratedValue
    @Column
    private Long id;

    @OneToOne(optional=false)
    @Check(constraints="rule.access='LIMIT'") // ??? check this 
    @ForeignKey(name="fk_limits_rule")
    private Rule rule;

	@Type(type = "org.hibernatespatial.GeometryUserType")
	@Column(name = "area")
	private MultiPolygon allowedArea;

    @XmlJavaTypeAdapter(MultiPolygonAdapter.class)
    public MultiPolygon getAllowedArea() {
        return allowedArea;
    }

    public void setAllowedArea(MultiPolygon allowedArea) {
        this.allowedArea = allowedArea;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return "RuleLimits{" + "id=" + id + " rule=" + rule + " allowedArea=" + allowedArea + '}';
    }
}
