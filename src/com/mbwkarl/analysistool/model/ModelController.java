/*
 * Copyright 2015 Karl Birch.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mbwkarl.analysistool.model;

/**
 *
 * @author Karl Birch
 */
public class ModelController {
    
    private DataModel model;
    
    public ModelController(DataModel model) {
        this.model = model;
    }
    
    public void loadFile(java.io.File file) {
        model.loadFromFile(file);
        
        if ( file.getName().endsWith(".atx") ) {
            return;
        }
        
//        CodeSource codeSource = com.mbwkarl.analysistool.AnalysisTool.class.getProtectionDomain().getCodeSource();
//        File jarFile = new File(codeSource.getLocation().toURI().getPath());
//        String jarDir = jarFile.getParentFile().getPath();
//        
//        File programDir = new File(jarDir);
//        programDir;
        
        String newLogFileDirName = System.getProperty("user.home") + "/AnalysisToolLogFiles/";
        
        java.io.File newLogFileDir = new java.io.File(newLogFileDirName);
        newLogFileDir.mkdir();
        
        int fileExtension = file.getName().lastIndexOf('.');
        String fileName = file.getName().substring(0, fileExtension) + ".atx";
        java.io.File newFile = new java.io.File(newLogFileDirName + fileName);
        
        model.writeToFile(newFile);
    }
    
    public void saveFile(java.io.File file) {
        model.writeToFile(file);
    }
    
    public void pollProperty(ModelProperty property) {
        model.pollProperty(property);
    }
    
    public void requestFormatAttributes(String formatID) {
        model.requestFormatAttributes(formatID);
    }
    
    public void requestDataStreamExtraction(String streamName,
                    String xAxisName, String yAxisName,
                    String formatType, String keyAttr, String valAttr) {
        model.requestDataStreamExtraction(streamName, xAxisName, yAxisName,
                                            formatType, keyAttr, valAttr);
    }
    
}
