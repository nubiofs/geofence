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
package it.geosolutions.csv2geofence.config.model.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Info parsed from the command line.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class RunInfo {

    private File configurationFile;

    private List<File> userFiles = new ArrayList<File>();
    private List<File> ruleFiles = new ArrayList<File>();

    private File outputFile;
    private boolean sendRequested;
//    private boolean groupAlignRequested = false;
    private boolean deleteObsoleteRules = false;


    public File getConfigurationFile() {
        return configurationFile;
    }

    public void setConfigurationFile(File configurationFile) {
        this.configurationFile = configurationFile;
    }

    public List<File> getUserFiles() {
        return userFiles;
    }

    public void setUserFiles(List<File> userFiles) {
        this.userFiles = userFiles;
    }

    public List<File> getRuleFiles() {
        return ruleFiles;
    }

    public void setRuleFiles(List<File> ruleFiles) {
        this.ruleFiles = ruleFiles;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public boolean isSendRequested() {
        return sendRequested;
    }

    public void setSendRequested(boolean sendRequested) {
        this.sendRequested = sendRequested;
    }

//    public boolean isGroupAlignRequested() {
//        return groupAlignRequested;
//    }
//
//    public void setGroupAlignRequested(boolean groupAlignRequested) {
//        this.groupAlignRequested = groupAlignRequested;
//    }

    public boolean isDeleteObsoleteRules() {
        return deleteObsoleteRules;
    }

    public void setDeleteObsoleteRules(boolean deleteObsoleteRules) {
        this.deleteObsoleteRules = deleteObsoleteRules;
    }

}
