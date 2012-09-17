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
import it.geosolutions.csv2geofence.config.model.UserFileConfig;
import it.geosolutions.csv2geofence.config.model.internal.OperationType;
import it.geosolutions.csv2geofence.config.model.internal.UserOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 * Loads a user file, converting lines into UserOps.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class UserFileLoader {

    private final static Logger LOGGER = Logger.getLogger(UserFileLoader.class);

    public static final String INSERT = "add";
    public static final String UPDATE = "modify";
    public static final String DELETE = "delete";
    
    private UserFileConfig config;

    public UserFileLoader(UserFileConfig config) {
        this.config = config;
    }

    public List<UserOp> load(File file) throws FileNotFoundException, IOException {

        CSVReader reader = new CSVReader(new FileReader(file), config.getFieldSeparator(), config.getStringSeparator());

        String[] line;

        if(config.isHasHeaders())
            LOGGER.info("Skipping headers line: " + Arrays.asList(reader.readNext())); // skip headers

        Pattern namePattern = Pattern.compile(config.getValidUsernameRegEx());
        Pattern groupPattern = Pattern.compile(config.getValidGroupRegEx());

        List<UserOp> ret = new LinkedList<UserOp>();

        while ((line = reader.readNext()) != null) {

            UserOp userOp = new UserOp();

            if(line.length < config.getUserNameIndex())
                throw new IndexOutOfBoundsException("Line has " + line.length + " elements. "
                        + "User expected at position " + config.getUserNameIndex());

            String username = line[config.getUserNameIndex() - 1];
            if( ! namePattern.matcher(username).matches()) {
                LOGGER.debug("Discarding user '"+username+"' by regexp");
                continue;
            }
            userOp.setUserName(username);

            if(config.getUserFullNameIndex() != null) {
                if(config.getUserFullNameIndex() > line.length)
                    throw new IndexOutOfBoundsException("Line has " + line.length + " elements. "
                            + "User fullname expected at position " + config.getUserFullNameIndex());

                userOp.setFullName(line[config.getUserFullNameIndex()]);
            }

            if(config.getUserMailIndex()!= null) {
                if(config.getUserMailIndex() > line.length)
                    throw new IndexOutOfBoundsException("Line has " + line.length + " elements. "
                            + "User mail address expected at position " + config.getUserMailIndex());

                userOp.setMailAddress(line[config.getUserMailIndex()]);
            }

            if(line.length < config.getGroupNameIndex())
                throw new IndexOutOfBoundsException("Line has " + line.length + " elements. "
                        + "Group list expected at position " + config.getGroupNameIndex());

            String groupList = line[config.getGroupNameIndex() - 1];
            List<String> groups = new ArrayList<String>();
            for (String groupName : groupList.split(",")) {
                if( ! groupPattern.matcher(groupName).matches()) {
                    LOGGER.debug("Discarding group '"+groupName+"' by regexp");
                    continue;
                } else {
                    groups.add(groupName);
                }
            }
            userOp.setGroups(groups);

            String sop = line[config.getOperationTypeIndex() - 1];
            OperationType op = parseOperationType(sop);
            if( op == null ) {
                LOGGER.warn("Skipping user " + username  + " due to unparsable operation '" + sop+ "'");
                continue;
            }
            userOp.setType(op);

//            LOGGER.info(op +" user " + username + " in groups " + groups);
            LOGGER.debug(userOp.toString());
            ret.add(userOp);
        }

        return ret;
    }

    protected OperationType parseOperationType(String s) {
        if(INSERT.equalsIgnoreCase(s))
            return OperationType.INSERT;
        else if(UPDATE.equalsIgnoreCase(s))
            return OperationType.UPDATE;
        else if(DELETE.equalsIgnoreCase(s))
            return OperationType.DELETE;
        else {
            LOGGER.warn("Unparsable operation '"+s+"'");
            return null;
        }
    }

}
