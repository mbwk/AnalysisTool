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

import com.mbwkarl.analysistool.xmltypes.ObjectFactory;
import com.mbwkarl.analysistool.xmltypes.DataLogType;
import com.mbwkarl.analysistool.xmltypes.DataEntryType;
import com.mbwkarl.analysistool.xmltypes.KeyValueMapModeller;
import com.mbwkarl.analysistool.utils.StreamDataType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Karl Birch
 */
public class DataLogContainer {
    
    private ObjectFactory factory = new ObjectFactory();
    private JAXBElement<DataLogType> rootElement = factory.createDataLog(factory.createDataLogType());
    private ArrayList<FormatMap> formatMappings = new ArrayList<>();
    
    public DataLogContainer() {
        addDefaultMappings();
    }
    
    private void addDefaultMappings() {
        FormatMap defaultFmt;
        try {
            defaultFmt = new FormatMap("FMT", "BBnNZ", "Type,Length,Name,Format,Columns");
            formatMappings.add(defaultFmt);
        } catch (FormatMap.AttributeMismatchException ex) {
            System.err.println("DataLogContainer: something went horribly wrong");
            ex.printStackTrace();
            // we are initializing with obviously valid parameters above
            // there should _literally_ never be an exception here
            // if there IS an ex, FormatMap constructor must be faulty
        }
    }
    
    public DataLogContainer(JAXBElement<DataLogType> rootNode) {
        factory = new ObjectFactory();
        rootElement = rootNode;
        formatMappings  = new ArrayList<>();
        addDefaultMappings();
        //getDataEntryList().stream().filter((entry) -> (entry.getID().equals("FMT"))).forEach((entry) -> {
        for (DataEntryType entry : getDataEntryList()) {
            if (entry.getID().equals("FMT")) {
                try {
                    createUpdateFormatMappings(new FormatMap(entry));
                } catch (FormatMap.AttributeMismatchException ex) {
                    System.err.println("DataLogContainer: Skipping malformed FMT entry");
                }
            }
        }
        //});
    }
    
    // <editor-fold desc="Tree Traversal">
    private DataLogType getRoot() {
        return rootElement.getValue();
    }
    
    private List<DataEntryType> getDataEntryList() {
        return getRoot().getDataEntry();
    }
    
    private List<String> getMetaList() {
        return getRoot().getMeta();
    }
    // </editor-fold>
    
    // <editor-fold desc="DataLog Creation/Construction">

    private void createUpdateFormatMappings(FormatMap formatMap) {
        for (int i = 0, limit = formatMappings.size(); i < limit; i++) {
            if (formatMap.getID().equals( formatMappings.get(i).getID() )) {
                formatMappings.set(i, formatMap);
                return;
            }
        }
        formatMappings.add(formatMap);
    }
    
    public int getCorruptionCount() {
        return rootElement.getValue().getCorruptions();
    }
    
    private void incrementCorruptionCount() {
        rootElement.getValue().setCorruptions(getCorruptionCount() + 1);
    }
    
    private void createUpdateFormatMapping(ArrayList<String> fragments) {
        
        try {
            String id = fragments.get(3);
            String formatString = fragments.get(4);
            String namesString = fragments.get(5);
            
            FormatMap fmtmap = new FormatMap(id, formatString, namesString);

            createUpdateFormatMappings(fmtmap);
        } catch (IndexOutOfBoundsException oob) { 
            System.err.println("DataLogContainer: Corrupted entry, skipping");
            incrementCorruptionCount();
        } catch (FormatMap.AttributeMismatchException ame) {
            System.err.println("DataLogContainer: Attribute mismatch, skipping");
            incrementCorruptionCount();
        }
    }
    
    public void addEntry(String metaEntry) {
        addMetaEntry(metaEntry);
    }
    
    public void addEntry(ArrayList<String> fragments) {
        if (fragments.get(0).equals("META") && fragments.size() > 1) {
            addMetaEntry(fragments.get(1));
        } else {
            addDataEntry(fragments);
        }
    }
    
    private void addMetaEntry(String meta_string) {
        getMetaList().add(meta_string);
    }
    
    private KeyValueMapModeller.Field makeField(String key, String value) {
        KeyValueMapModeller.Field field = factory.createKeyValueMapModellerField();
        field.setKey(key);
        field.setValue(value);
        return field;
    }
    
    private void addDataEntry(ArrayList<String> fragments) {
        
        DataEntryType entry = factory.createDataEntryType();
        
        String id = fragments.get(0);
        
        if (id.equals("FMT")) {
            createUpdateFormatMapping(fragments);
        }
        
        entry.setID(id);
        KeyValueMapModeller data = factory.createKeyValueMapModeller();
        
        try {
            for (int i = 1; i < fragments.size(); ++i) {
                String key = lookup(id).getFieldName(i - 1);
                data.getField().add(makeField(key, fragments.get(i)));
            }

            entry.setFields(data);
            getDataEntryList().add(entry);
            
        } catch (FormatNotFoundException formnotfound) {
            System.err.println("DataLogContainer: FORMAT (" + id + ") not found, skipping...");
            incrementCorruptionCount();
        } catch (IndexOutOfBoundsException oob) {
            System.err.println("DataLogContainer: Corrupted or invalid fragments, skipping...");
            incrementCorruptionCount();
        }
        
    }
    // </editor-fold>
    
    /**
     * Method that looks for the FormatMap that describes entries labeled with
     * "id".  On success, returns the FormatMap object.  On failure, returns
     * null.
     * @param id
     * @return 
     */
    public FormatMap fmtlookup(String id) {
        try {
            return lookup(id);
        } catch (FormatNotFoundException ex) {
            return null;
        }
    }
    
    public int getEntryCountFor(FormatMap fm) {
        int count = 0;
        String id = fm.getID();
        
        for (DataEntryType de : rootElement.getValue().getDataEntry()) {
            if (de.getID().equals(id)) {
                ++count;
            }
        }
        
        return count;
    }
    
    private FormatMap lookup(String id) throws FormatNotFoundException {
        for (FormatMap fm : formatMappings) {
            if (fm.getID().equals(id)) {
                return fm;
            }
        }
        throw new FormatNotFoundException(id);
    }

    protected int getEntryCount() {
        return getRoot().getDataEntry().size();
    }
    
    protected ArrayList<String> getTypesList() {
        ArrayList<String> typesList = new ArrayList<>();
        
        //formatMappings.stream().forEach((map) -> {
        for (FormatMap map : formatMappings) {
            typesList.add(map.getID());
        }
        //});
        
        return typesList;
    }

    public JAXBElement<DataLogType> getRootElement() {
        return rootElement;
    }

    protected DataStream extractDataStream(String streamName, String xAxisName,
                                String yAxisName, FormatMap fm,
                                String keyAttr, String valAttr) {
        String entryID = fm.getID();
        StreamDataType dtype = fm.getTypeOf(fm.getFieldIndex(valAttr));
        
        DataStream stream = new DataStream(streamName, xAxisName, yAxisName, dtype);
        List<DataEntryType> entryList = getDataEntryList();
        //entryList.stream().filter((e) -> (e.getID().equals(entryID))).forEach((e) -> {
        for (DataEntryType e : entryList) {
            if (e.getID().equals(entryID)) {
                KeyValueMapModeller kvmm = e.getFields();
                String findKey = kvmm.find(keyAttr);
                String findVal = kvmm.find(valAttr);

                if (!findKey.isEmpty() && !findVal.isEmpty()) {
                    try {
                        BigDecimal key = new BigDecimal(findKey);
                        BigDecimal value = new BigDecimal(findVal);
                        stream.addDataPoint(key, value);
                    } catch (NumberFormatException nfe) {
                        System.err.println("Number format exception: key(" + findKey + ") | val(" + findVal +")");
                    }
                }
            }
        }
        //});
        
        return stream;
    }

    private static class FormatNotFoundException extends Exception {

        private String id;
        
        public FormatNotFoundException(String id) {
            this.id = id;
        }
        
        public String getID() {
            return id;
        }
    }
    
}
