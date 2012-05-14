/*
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S. http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geofence.services.rest.utils;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;

import jaitools.jts.Utils;

import org.apache.log4j.Logger;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class MultiPolygonUtils
{
    private static final Logger LOGGER = Logger.getLogger(MultiPolygonUtils.class);

    /**
     * Simplifies a MultiPolygon.
     * <BR/><BR/>
     * Simplification is performed by first removing collinear points, then
     * by applying DouglasPeucker simplification.
     * <BR/>Order <B>is</B> important, since it's more likely to have collinear
     * points before applying any other simplification.
     */
    public static MultiPolygon simplifyMultiPolygon(final MultiPolygon mp)
    {

        final Polygon[] simpPolys = new Polygon[mp.getNumGeometries()];

        for (int i = 0; i < mp.getNumGeometries(); i++)
        {
            Polygon p = (Polygon) mp.getGeometryN(i);
            Polygon s1 = Utils.removeCollinearVertices(p);
            TopologyPreservingSimplifier tps = new TopologyPreservingSimplifier(s1);
            Polygon s2 = (Polygon) tps.getResultGeometry();
            simpPolys[i] = s2;

            if (LOGGER.isInfoEnabled())
            {
                LOGGER.info("RCV: simplified poly " + getPoints(p) +
                    " --> " + getPoints(s1) +
                    " --> " + getPoints(s2));
            }
        }

        // reuse existing factory
        final GeometryFactory gf = mp.getFactory();

        return gf.createMultiPolygon(simpPolys);
    }

    /**
     * Return the number of points of a polygon in the format
     * E+I0+I1+...+In
     * where E is the number of points of the exterior ring and I0..In are
     * the number of points of the Internal rings.
     */
    public static String getPoints(final Polygon p)
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(p.getExteriorRing().getNumPoints());
        for (int i = 0; i < p.getNumInteriorRing(); i++)
        {
            LineString ir = p.getInteriorRingN(i);
            sb.append('+').append(ir.getNumPoints());
        }

        return sb.toString();
    }

}
