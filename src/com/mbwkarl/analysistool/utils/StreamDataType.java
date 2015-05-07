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

/**
 *
 * @author Karl Birch
 */
public enum StreamDataType {
    TIME,
    STRING,
    BIGINTEGER,
    BIGDECIMAL,
    LATLON,
    UNKNOWN;
    
    public static StreamDataType detectType(char ch) {
        switch (ch) {
            case 'I':
                return StreamDataType.TIME;

            case 'N':
            case 'n':
            case 'Z':
            case 'M':
                return StreamDataType.STRING;
                
            case 'B':
            case 'H':
            case 'h':
                return StreamDataType.BIGINTEGER;
                
            case 'f':
            case 'C':
            case 'c':
            case 'E':
            case 'e':
                return StreamDataType.BIGDECIMAL;
                
            case 'L':
                return StreamDataType.LATLON;

            // error
            default:
                return StreamDataType.UNKNOWN;
        }
    }
}
