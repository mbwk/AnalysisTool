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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Karl Birch
 */
public class LogReaderTest {
    
    private File testfile;
    private BufferedWriter ofstream;
    
    public LogReaderTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {  // setup file and writer to allow for test data to be written
        try {
            testfile = new File("test.tmp");
            testfile.createNewFile();
            ofstream = new BufferedWriter(new FileWriter(testfile));
        } catch (IOException ex) {
            Logger.getLogger(LogReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {  // delete temporary test file
        try {
            ofstream.close();
            testfile.delete();
        } catch (IOException ex) {
            Logger.getLogger(LogReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of parseLine method, of class LogReader.
     */
    @Test
    public void testParseLine() {
        System.out.println("parseLine");
        String testString = "MSG, DR, PAVEL, I'M, CIA";
        ArrayList<String> expResult = new ArrayList(Arrays.asList(testString.split(", ")));
//        expResult.add("MSG");
//        expResult.add("DR");
//        expResult.add("PAVEL");
//        expResult.add("I'M");
//        expResult.add("CIA");
        
        try {
            ofstream.append(testString);
            ofstream.newLine();
            ofstream.close();
        } catch (IOException ex) {
            Logger.getLogger(LogReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        LogReader instance = new LogReader(testfile);
        ArrayList<String> result = instance.parseLine();
        assertEquals(expResult, result);
        
        System.out.println("RESULT\tEXPRESULT");
        int arrsize = expResult.size();
        for (int i = 0; i < arrsize; ++i) {
            System.out.println(result.get(i) + "\t" + expResult.get(i));
        }
    }

    /**
     * Test of isFinished method, of class LogReader.
     */
    @Test
    public void testIsFinished() {
        System.out.println("isFinished");
        LogReader instance = null;
        boolean expResult = false;
        boolean result = instance.isFinished();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentLine method, of class LogReader.
     */
    @Test
    public void testGetCurrentLine() {
        System.out.println("getCurrentLine");
        LogReader instance = null;
        long expResult = 0L;
        long result = instance.getCurrentLine();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
