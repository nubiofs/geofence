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
package it.geosolutions.csv2geofence.impl;

import au.com.bytecode.opencsv.CSVReader;
import it.geosolutions.csv2geofence.config.model.RuleFileConfig;
import it.geosolutions.csv2geofence.config.model.internal.RuleOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 * Loads a rule file, converting lines into RuleOps.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RuleFileLoader {

    private final static Logger LOGGER = Logger.getLogger(RuleFileLoader.class);
    
    private RuleFileConfig config;
//    private List<String> groupNames;

    public RuleFileLoader(RuleFileConfig config) {
        this.config = config;
    }

    public List<RuleOp> load(File file) throws FileNotFoundException, IOException {

        CSVReader reader = new CSVReader(new FileReader(file), config.getFieldSeparator().charAt(0));

        // fetch layer names at given columns
        String[] headers = reader.readNext();
//        groupNames = new ArrayList<String>();

        for (RuleFileConfig.Group group : config.getGroups()) {
            int idx = group.getIndex() - 1; // indices are 1-based
            if(headers.length <= idx)
                throw new IndexOutOfBoundsException("Header has " + headers.length + " elements. "
                        + "Group column expected at position " + (idx+1) );

            String realName = headers[idx];
            LOGGER.debug(" Group name in rule file : '"+realName+"'");
//            groupNames.add(realName);
        }

        final Pattern namePattern = config.getValidLayernameRegEx() != null ?
                Pattern.compile(config.getValidLayernameRegEx()) :
                null;

        List<RuleOp> ret = new LinkedList<RuleOp>();

        String[] line;
        while ((line = reader.readNext()) != null) {

            if(line.length < config.getLayerNameIndex())
                throw new IndexOutOfBoundsException("Line has " + line.length + " elements. "
                        + "Layer expected at position " + config.getLayerNameIndex());

            String layername = line[config.getLayerNameIndex() - 1];
            if( namePattern != null && ! namePattern.matcher(layername).matches()) {
                LOGGER.debug("Discarding layer '"+layername+"' by regexp");
                continue;
            }


            for (RuleFileConfig.Group group : config.getGroups()) {
                int idx = group.getIndex() - 1; // indices are 1-based in config
                String groupName = headers[idx];
                if(line.length <= idx)
                    throw new IndexOutOfBoundsException("Line has " + line.length + " elements. "
                            + "Rule for group '"+groupName+"' expected at position " + (idx+1) );

                String verb = line[idx];

//                List<RuleFileConfig.ServiceRequest> configRules = config.getRuleMapping().get(verb);
//                if(configRules == null) {
//                    LOGGER.warn("Unknown verb '"+verb+"' for layer '" + layername + "' and group '"+groupName+"'");
//                    continue;
//                }
//                if(! configRules.isEmpty()) {
                    final RuleOp ruleOp = new RuleOp();
                    ruleOp.setLayerName(layername);
                    ruleOp.setGroupName(groupName);
                    ruleOp.setVerb(verb);

                    ret.add(ruleOp);
//                }
            }
        }

        return ret;
    }

}
