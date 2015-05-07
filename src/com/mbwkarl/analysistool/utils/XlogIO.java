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
package com.mbwkarl.analysistool.utils;


import com.mbwkarl.analysistool.model.DataLogContainer;
import com.mbwkarl.analysistool.xmltypes.DataLogType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Karl Birch
 */
public class XlogIO {

    public XlogIO() {
    }
    
    private boolean writeToFile(File file, JAXBElement<DataLogType> rootElement) {
        
        // if (file.exists()) return this; // don't overwrite existing file
        FileOutputStream ofstream = null;
        
        try {
            file.createNewFile();
            ofstream = new FileOutputStream(file);
            JAXBContext jaxbCtx = JAXBContext.newInstance(DataLogType.class);
            Marshaller marsh = jaxbCtx.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marsh.marshal(rootElement, ofstream);
            ofstream.close();
            return true;
            // marsh.marshal(rootNode, System.out); // debug
        } catch (JAXBException ex) {
            Logger.getLogger(XlogIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(XlogIO.class.getName()).log(Level.SEVERE, 
                    "IOException with file [" + file.getName() + "] -- "
                    , ex);
            return false;
        }
        
    }
    
    public boolean serializeXmlFile(File file, DataLogContainer dataLog) {
        return writeToFile(file, dataLog.getRootElement());
    }
    
    private JAXBElement<DataLogType> readFromFile(File file) {
        JAXBElement<DataLogType> rootNode = null;
        
        if (!file.exists()  || !file.canRead()) {
            return null;  // can't read if it doesn't exist or we don't have permission
        }
        
        try {
            JAXBContext jaxbCtx = JAXBContext.newInstance(DataLogType.class);
            Unmarshaller unmarsh = jaxbCtx.createUnmarshaller();
            javax.xml.transform.Source source = new StreamSource(new FileInputStream(file));
            rootNode = unmarsh.unmarshal(source, DataLogType.class);
            return rootNode;
        } catch (JAXBException | FileNotFoundException ex) {
            Logger.getLogger(XlogIO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public DataLogContainer deserializeXmlFile(File file) {
        JAXBElement<DataLogType> rootNode = readFromFile(file);
        if (rootNode == null) {
            return null;
        } else {
            return new DataLogContainer(rootNode);
        }
    }
    
}
