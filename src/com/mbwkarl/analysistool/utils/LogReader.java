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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Karl Birch
 */
public class LogReader {
    
    private File logfile;
    private BufferedReader reader;
    private long currentLine = 0;
    private boolean finished = false;
    
    public LogReader(File file) {
        logfile = file;
        if (logfile.exists() && logfile.canRead()) {
            try {
                reader = this.openFileInReader(logfile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LogReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private BufferedReader openFileInReader(File logfile) throws FileNotFoundException {
        BufferedReader buffread = new BufferedReader(new FileReader(logfile)); // fnf exception
        return buffread;
    }
    
    private String getNextLine() {
        try {
            return reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(LogReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ArrayList<String> parseLine() {
        String line = null;
        ArrayList<String> fragmentArrayList = new ArrayList<>();
        
        
        // attempt read
        do { 
            line = getNextLine();
            ++currentLine;
        } while (line != null && (line.length() == 0 || isDirty(line))); // skip empty/bad lines
        
        
        // check if hit eof
        if (null == line) {
            System.err.println("Hit end of file");
            fragmentArrayList.add("EOF");
            finished = true;
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(LogReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            return fragmentArrayList;
        }
        
        if (isDirty(line)) {
            line = cleanLine(line);
        }
        
        if (line.contains(",")) {
            fragmentArrayList = splitLine(fragmentArrayList, line);
        } else {
            fragmentArrayList.add("META");
            fragmentArrayList.add(line);
        }
        
        return fragmentArrayList;
    }
    
    private boolean isIllegalCharacter(char c) {
        // return true if char isn't a legal character
        return (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)
                && (c < 32 || c > 90) && (c != 0));
    }
    
    private boolean isDirty(String line) {
        for (char c : line.toCharArray()) {
            if (isIllegalCharacter(c)) {
                return true;
            }
        }
        return false;
    }
    
    private String cleanLine(String line) {
        StringBuilder sb = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (!isIllegalCharacter(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    private ArrayList<String> splitLine(ArrayList<String> dest, String line) {
        dest.addAll(Arrays.asList(line.split(", ")));
        return dest;
    }
    
    public boolean isFinished() {
        return finished;
    }
    
    public long getCurrentLine() {
        return currentLine;
    }
    
}
