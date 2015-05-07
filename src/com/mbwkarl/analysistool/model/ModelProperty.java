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
public enum ModelProperty {
    LIST,
    ENTRY_COUNT,
    FORMATS,
    UNKNOWN;
    
    public static ModelProperty detectProperty(String instr) {
        switch (instr) {
            case "list": case "l":
                return LIST;
                
            case "entry-count": case "ec":
                return ENTRY_COUNT;
                
            case "formats": case "f":
                return FORMATS;
                
            default:
                return UNKNOWN;
        }
    }
}
