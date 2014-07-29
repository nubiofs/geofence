/*
 * $ Header: it.geosolutions.geofence.gui.server.UploadServlet,v. 0.1 25-gen-2011 11.30.32 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.30.32 $
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
package it.geosolutions.geofence.gui.server;

import it.geosolutions.geofence.gui.server.utility.IoUtility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.geotools.data.shapefile.files.ShpFiles;
import org.geotools.data.shapefile.shp.ShapefileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;


// TODO: Auto-generated Javadoc
/**
 * The Class UploadServlet.
 */
public class UploadServlet extends HttpServlet
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4619230349225062484L;

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        super.doGet(req, resp);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        // process only multipart requests
        if (ServletFileUpload.isMultipartContent(req))
        {

            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            File uploadedFile = null;
            // Parse the request
            try
            {
                List<FileItem> items = upload.parseRequest(req);

                for (FileItem item : items)
                {
                    // process only file upload - discard other form item types
                    if (item.isFormField())
                    {
                        continue;
                    }

                    String fileName = item.getName();
                    // get only the file name not whole path
                    if (fileName != null)
                    {
                        fileName = FilenameUtils.getName(fileName);
                    }

                    uploadedFile = File.createTempFile(fileName, "");
                    // if (uploadedFile.createNewFile()) {
                    item.write(uploadedFile);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.flushBuffer();

                    // uploadedFile.delete();
                    // } else
                    // throw new IOException(
                    // "The file already exists in repository.");
                }
            }
            catch (Exception e)
            {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An error occurred while creating the file : " + e.getMessage());
            }

            try
            {
                String wkt = calculateWKT(uploadedFile);
                resp.getWriter().print(wkt);
            }
            catch (Exception exc)
            {
                resp.getWriter().print("Error : " + exc.getMessage());
                logger.error("ERROR ********** " + exc);
            }

            uploadedFile.delete();

        }
        else
        {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                "Request contents type is not supported by the servlet.");
        }
    }

    /**
     * Calculate wkt.
     *
     * @param file
     *            the file
     * @return the string
     * @throws Exception
     *             the exception
     */
    private String calculateWKT(File file) throws Exception
    {
        ShapefileReader reader = null;
        Polygon[] geomArray = null;
        File shpDir = null;
        try
        {
            shpDir = IoUtility.decompress("GEOFENCE", file, File.createTempFile(
                        "geofence-temp", ".tmp"));

            if (shpDir == null)
            {
                return "Error : File zip doesn't contains file shp.";
            }

            reader = new ShapefileReader(new ShpFiles(shpDir), false, false, new GeometryFactory());

            List<Geometry> geomList = new ArrayList<Geometry>();
            while (reader.hasNext())
            {
                Geometry geometry = (Geometry) reader.nextRecord().shape();
                if ((geometry != null) && (geometry instanceof Polygon))
                {
                    geomList.add(geometry);
                }
                else if ((geometry != null) && (geometry instanceof MultiPolygon))
                {
                    for (int g = 0; g < geometry.getNumGeometries(); g++)
                    {
                        if ((geometry.getGeometryN(g) != null) &&
                                (geometry.getGeometryN(g) instanceof Polygon))
                        {
                            geomList.add(geometry.getGeometryN(g));
                        }
                    }
                }
            }

            geomArray = new Polygon[geomList.size()];
            for (int i = 0; i < geomArray.length; i++)
            {
                Geometry geometry = geomList.get(i);
                geomArray[i] = (Polygon) geometry;
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (shpDir != null)
            {
                shpDir.delete();
            }
            if (reader != null)
            {
                reader.close();
            }
        }

        if (geomArray.length == 0)
        {
            return "Error : The file shape must contains only Polygon or Multi Polygon geometries.";
        }

        MultiPolygon polygon = new MultiPolygon(geomArray, new GeometryFactory());

        return polygon.toText();
    }

}
