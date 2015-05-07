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

import com.mbwkarl.analysistool.xmltypes.KeyValueMapModeller;
import com.mbwkarl.analysistool.xmltypes.DataEntryType;
import com.mbwkarl.analysistool.utils.StreamDataType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Karl Birch
 */
public class FormatMap {
    
    private String typeName;
    private ArrayList<EntryAttribute> attributesList = new ArrayList<>();;
    
    public FormatMap(String type, String attrTypesFmtString, String attrNamesString) throws AttributeMismatchException {
        typeName = type;
        initAttributes(attrTypesFmtString, attrNamesString);
    }

    public FormatMap(DataEntryType entry) throws AttributeMismatchException {
        List<KeyValueMapModeller.Field> fieldsList = entry.getFields().getField();
        typeName = fieldsList.get(2).getValue();
        if (fieldsList.size() > 4) {
            initAttributes(fieldsList.get(3).getValue(), fieldsList.get(4).getValue());
        }
    }
    
    public String getID() {
        return typeName;
    }
    
    public ArrayList<EntryAttribute> getCopyOfAttributes() {
        return new ArrayList<>(attributesList);
    }

    public ArrayList<EntryAttribute> getFilteredCopyOfAttributes(StreamDataType ... datatypes) {
        ArrayList<EntryAttribute> filteredAttributesList = new ArrayList<>();
        for (int i = 0, attrListSize = attributesList.size(); i < attrListSize; ++i) {
            EntryAttribute ea = attributesList.get(i);
            for (int j = 0, filterLen = datatypes.length; j < filterLen; ++j) {
                if (ea.getType().compareTo(datatypes[j]) == 0) {
                    filteredAttributesList.add(ea);
                    break;
                }
            }
        }
        return filteredAttributesList;
    }
    
    // <editor-fold desc="Initializtion">
    private void initAttributes(String attrTypesFmtString, String attrNamesString) throws AttributeMismatchException {
        // precaution to prevent method being called after initialization
        if (!attributesList.isEmpty()) {
            return;
        }
        
        ArrayList<String> attributeNames = splitNamesString(attrNamesString);
        ArrayList<StreamDataType> attributeTypes = toDataTypes(attrTypesFmtString);
        
        // TODO: handle this
        int attrNamesSize = attributeNames.size();
        int attrTypesSize = attributeTypes.size();
        if (attrNamesSize != attrTypesSize) {
            throw new AttributeMismatchException();
        } else {
            for (int i = 0; i < attrNamesSize; ++i) {
                String name = attributeNames.get(i);
                StreamDataType type = attributeTypes.get(i);
                attributesList.add(new EntryAttribute(name, type));
            }
        }
        
    }
    
    private ArrayList<String> splitNamesString(String attrNamesString) {
        ArrayList<String> nameList = new ArrayList<>();
        String[] names = attrNamesString.split(",");
        nameList.addAll(Arrays.asList(names));
        return nameList;
    }
    
    // convert to datatypes
    private ArrayList<StreamDataType> toDataTypes(String typeStringList) {
        ArrayList<StreamDataType> typeEnumList = new ArrayList<>();
        
        for (char s : typeStringList.toCharArray()) {
            typeEnumList.add(StreamDataType.detectType(s));
        }
        
        return typeEnumList;
    }
    // </editor-fold>

    public String getFieldName(int i) throws IndexOutOfBoundsException {
        return attributesList.get(i).getName();
    }
    
    public StreamDataType getTypeOf(int i) {
        return attributesList.get(i).getType();
    }
    
    public int getFieldIndex(String name) {
        for (int i = 0, limit = attributesList.size(); i < limit; ++i) {
            if (attributesList.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
    
    public int getAttributeCount() {
        return attributesList.size();
    }

    protected static class AttributeMismatchException extends Exception {

        public AttributeMismatchException() {
        }
        
    }
    
}
