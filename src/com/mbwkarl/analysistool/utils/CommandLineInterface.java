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

import com.mbwkarl.analysistool.model.ModelController;
import com.mbwkarl.analysistool.model.ModelProperty;
import java.io.File;
import java.util.Scanner;

/**
 * Interactive Command Line Interface to the analysis tool.  Included for
 * debugging purposes. Due to limited practicality, most likely not to
 * be of value to the end user.
 * @author Karl Birch
 */
public class CommandLineInterface {
    
    private final ModelController modelController;
    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;
    
    public CommandLineInterface(ModelController mc) {
        modelController = mc;
    }
    
    public void printMessage(String message) {
        System.out.println("\rSYSTEM: " + message);
    }
    
    public void printExtendedMessage(String message) {
        System.out.println("\r        " + message);
    }
    
    private String getInput() {
        return scanner.nextLine();
    }
    
    private void parseCommand(String input) {
        String[] words = input.toLowerCase().split(" ");
        if (words.length == 0) {
            printMessage("Please enter a command");
        }
        
        switch (words[0]) {
            case "help":
            case "h":
                printHelp();
                break;
            
            case "quit":
            case "q":
                running = false;
                break;
                
            case "load":
            case "l":
                if (words.length > 1) {
                    loadFile(words[1]);
                } else {
                    printMessage("Please supply a filename");
                }
                break;
                
            case "save":
            case "s":
                if (words.length > 1) {
                    saveFile(words[1]);
                } else {
                    printMessage("Please supply a filename");
                }
                break;
                
            case "get":
            case "g":
                if (words.length > 1) {
                    getProperty(words[1]);
                } else {
                    printMessage("Please specify a property");
                }
                break;
            
            default:
                printMessage("Dr. Pavel, I'm CIA");
                break;
        }
    }
    
    public void runApp() {
        printMessage("Welcome to AnalysisTool");
        String input;
        do {
            System.out.print("USER: ");
            input = getInput();
            parseCommand(input);
        } while (running);
        printMessage("Goodbye!");
    }
    
    private void printHelp() {
        printMessage("HELP:");
        printExtendedMessage("#####");
        printExtendedMessage("[q]uit - quit the program");
        printExtendedMessage("[h]elp - display this message");
        printExtendedMessage("[l]oad - load a specified log file");
        printExtendedMessage("[s]ave - save to logxl file");
        printExtendedMessage("[g]et  - get a certain property (get list)");
    }
    
    private void loadFile(String filename) {
        File file = new File(filename);
        modelController.loadFile(file);
    }
    
    private void saveFile(String filename) {
        File file = new File(filename);
        modelController.saveFile(file);
    }
    
    private void getProperty(String propertyName) {
        ModelProperty property = ModelProperty.detectProperty(propertyName);
        modelController.pollProperty(property);
    }
    
}
