/*
 * $ Header: it.geosolutions.geofence.gui.client.widget.GridStatus,v. 0.1 25-feb-2011 16.31.41 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-feb-2011 16.31.41 $
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
package it.geosolutions.geofence.gui.client.widget;

import com.extjs.gxt.ui.client.widget.grid.Grid;

import it.geosolutions.geofence.gui.client.model.Rule;


// TODO: Auto-generated Javadoc
/**
 * The Class GridStatus.
 */
public class GridStatus
{


    /** The model. */
    private Rule model;

    /** The grid. */
    private Grid<Rule> grid;

    /**
     * Instantiates a new grid status.
     *
     * @param grid
     *            the grid
     * @param rule
     *            the rule
     */
    public GridStatus(Grid<Rule> grid, Rule rule)
    {
        this.grid = grid;
        this.model = rule;
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public Rule getModel()
    {
        return model;
    }

    /**
     * Sets the model.
     *
     * @param model
     *            the new model
     */
    public void setModel(Rule model)
    {
        this.model = model;
    }

    /**
     * Gets the grid.
     *
     * @return the grid
     */
    public Grid<Rule> getGrid()
    {
        return grid;
    }

    /**
     * Sets the grid.
     *
     * @param grid
     *            the new grid
     */
    public void setGrid(Grid<Rule> grid)
    {
        this.grid = grid;
    }
}
