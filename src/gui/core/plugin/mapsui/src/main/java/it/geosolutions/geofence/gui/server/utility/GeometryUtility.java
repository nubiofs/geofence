/*
 * $ Header: it.geosolutions.geofence.gui.server.utility.GeometryUtility,v. 0.1 25-gen-2011 11.30.33 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.30.33 $
 *
 * ====================================================================
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
package it.geosolutions.geofence.gui.server.utility;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import it.geosolutions.geofence.core.model.adapter.MultiPolygonAdapter;
import it.geosolutions.geofence.core.model.adapter.PolygonAdapter;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Component;


// TODO: Auto-generated Javadoc
/**
 * The Class GeometryUtility.
 */
@Component("geometryUtility")
public class GeometryUtility
{

    /**
     * Project geometry.
     *
     * @param originalGeom
     *            the original geom
     * @param srcCRSCode
     *            the src crs code
     * @param trgCRSCode
     *            the trg crs code
     * @return the geometry
     * @throws NoSuchAuthorityCodeException
     *             the no such authority code exception
     * @throws FactoryException
     *             the factory exception
     * @throws MismatchedDimensionException
     *             the mismatched dimension exception
     * @throws TransformException
     *             the transform exception
     */
    public static Geometry projectGeometry(Geometry originalGeom, String srcCRSCode,
        String trgCRSCode) throws NoSuchAuthorityCodeException, FactoryException, MismatchedDimensionException,
        TransformException
    {

        Geometry projectedGeometry = null;
        final MathTransform transform = CRS.findMathTransform(CRS.decode(srcCRSCode, true), CRS.decode(trgCRSCode, true), true);

        // converting geometries into a linear system
        try
        {
            projectedGeometry = JTS.transform(originalGeom, transform);
        }
        catch (Exception e)
        {
            GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(
                        PrecisionModel.FLOATING), 4326);
            WKTReader reader = new WKTReader(geometryFactory);

            try
            {
                Polygon worldCutPolygon = (Polygon) reader.read("POLYGON((-180 -89.9, -180 89.9, 180 89.9, 180 -89.9, -180 -89.9))");

                projectedGeometry = JTS.transform(originalGeom.intersection(worldCutPolygon),
                        transform);
            }
            catch (Exception ex)
            {
                throw new RuntimeException(ex);
            }
        }

        return projectedGeometry;
    }

    /** The factory. */
    private GeometryFactory factory = new GeometryFactory();

    /** The pol adapter. */
    private PolygonAdapter polAdapter = new PolygonAdapter();

    /** The multi pol adapter. */
    private MultiPolygonAdapter multiPolAdapter = new MultiPolygonAdapter();

    /**
     * Creates the polygon.
     *
     * @param wkt
     *            the wkt
     * @return the polygon
     * @throws ParseException
     *             the parse exception
     */
    public Polygon createPolygon(String wkt) throws ParseException
    {
        Polygon poly = polAdapter.unmarshal(wkt);
        if (poly.getSRID() == 0)
        {
            poly.setSRID(4326);
        }

        return poly;
    }

    /**
     * Creates the multi polygon.
     *
     * @param pol
     *            the pol
     * @return the multi polygon
     */
    public MultiPolygon createMultiPolygon(Polygon[] pol)
    {
        MultiPolygon multiPoly = this.factory.createMultiPolygon(pol);
        if (multiPoly.getSRID() == 0)
        {
            multiPoly.setSRID(4326);
        }

        return multiPoly;
    }

    /**
     * Creates the multi polygon.
     *
     * @param wkt
     *            the wkt
     * @return the multi polygon
     * @throws ParseException
     *             the parse exception
     */
    public MultiPolygon createMultiPolygon(String wkt) throws ParseException
    {
        MultiPolygon multiPoly = this.multiPolAdapter.unmarshal(wkt);
        if (multiPoly.getSRID() == 0)
        {
            multiPoly.setSRID(4326);
        }

        return multiPoly;
    }

    /**
     * Creates the wkt from multi polygon.
     *
     * @param multiPoly
     *            the multi poly
     * @return the string
     * @throws ParseException
     *             the parse exception
     */
    public String createWKTFromMultiPolygon(MultiPolygon multiPoly) throws ParseException
    {
        if (multiPoly.getSRID() == 0)
        {
            multiPoly.setSRID(4326);
        }

        return this.multiPolAdapter.marshal(multiPoly);
    }
}
