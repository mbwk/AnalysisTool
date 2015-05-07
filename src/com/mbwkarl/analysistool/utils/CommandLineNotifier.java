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

import com.mbwkarl.analysistool.utils.CommandLineInterface;
import com.mbwkarl.analysistool.model.DataStream;
import com.mbwkarl.analysistool.model.EntryAttribute;
import com.mbwkarl.analysistool.utils.UserInterfaceNotifier;
import java.util.ArrayList;

/**
 *
 * @author Karl Birch
 */
public class CommandLineNotifier implements UserInterfaceNotifier {

    private final CommandLineInterface cli;
    
    public CommandLineNotifier(CommandLineInterface cli) {
        this.cli = cli;
    }
    
    @Override
    public void loadedFile(String filename) {
        cli.printMessage("Loaded file: " + filename);
    }

    @Override
    public void failedLoadFile(String filename) {
        cli.printMessage("Failed to load file: " + filename);
    }

    @Override
    public void savedFile(String filename) {
        cli.printMessage("Saved file: " + filename);
    }

    @Override
    public void failedSaveFile(String filename) {
        cli.printMessage("Failed to save file: " + filename);
    }

    @Override
    public void mustLoadFile() {
        cli.printMessage("Please load a file first");
    }

    @Override
    public void broadcastEntryCount(int entryCount) {
        cli.printMessage("Number of entries is " + entryCount);
    }

    @Override
    public void broadcastFormats(ArrayList<String> formatsList) {
        cli.printMessage("FORMATS:");
        cli.printExtendedMessage("--------");
        for (String s : formatsList) {
            cli.printExtendedMessage(s);
        }
        cli.printExtendedMessage("--------");
    }

    @Override
    public void broadcastPropertyList(String[] proplist) {
        cli.printMessage("PROPERTIES:");
        cli.printExtendedMessage("-----------");
        for (String s : proplist) {
            cli.printExtendedMessage(s);
        }
        cli.printExtendedMessage("-----------");
    }

    @Override
    public void broadcastDataStreams(ArrayList<DataStream> streams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void broadcastCorruptions(int corruptionCount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void broadcastFormatMap(String formatID, ArrayList<EntryAttribute> xAttributes, ArrayList<EntryAttribute> yAttributes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
