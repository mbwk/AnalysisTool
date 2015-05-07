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

import com.mbwkarl.analysistool.utils.StreamDataType;
import java.util.ArrayList;
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
public class FormatMapTest {
    
    public FormatMapTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getID method, of class FormatMap.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        FormatMap instance;
        try {
            instance = new FormatMap("Test", "TI", "Time,Int");
            String expResult = "Test";
            String result = instance.getID();
            assert(expResult.equals(result));
        } catch (FormatMap.AttributeMismatchException ex) {
            fail("AttributeMismatchException");
        }
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getCopyOfAttributes method, of class FormatMap.
     */
    @Test
    public void testGetCopyOfAttributes() {
        System.out.println("getCopyOfAttributes");
        FormatMap instance;
        try {
            instance = new FormatMap("Test", "TI", "Time,Int");
            ArrayList<EntryAttribute> expResult = new ArrayList<>();
            expResult.add(new EntryAttribute("Time", StreamDataType.TIME));
            expResult.add(new EntryAttribute("Int", StreamDataType.BIGINTEGER));
            
            ArrayList<EntryAttribute> result = instance.getCopyOfAttributes();
            for (int i = 0, limit = expResult.size(); i < limit; ++i) {
                EntryAttribute expect = expResult.get(i), actual = result.get(i);
                if (!expect.getName().equals(actual.getName()) || expect.getType() != actual.getType()) {
                    fail("Doesn't match");
                }
            }
            
        } catch (FormatMap.AttributeMismatchException ex) {
            fail("AttributeMismatchException");
        }
    }

    /**
     * Test of getFilteredCopyOfAttributes method, of class FormatMap.
     */
    @Test
    public void testGetFilteredCopyOfAttributes() {
        System.out.println("getFilteredCopyOfAttributes");
        StreamDataType[] datatypes = null;
        FormatMap instance = null;
        ArrayList<EntryAttribute> expResult = null;
        ArrayList<EntryAttribute> result = instance.getFilteredCopyOfAttributes(datatypes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFieldName method, of class FormatMap.
     */
    @Test
    public void testGetFieldName() {
        System.out.println("getFieldName");
        int i = 0;
        FormatMap instance = null;
        String expResult = "";
        String result = instance.getFieldName(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTypeOf method, of class FormatMap.
     */
    @Test
    public void testGetTypeOf() {
        System.out.println("getTypeOf");
        int i = 0;
        FormatMap instance = null;
        StreamDataType expResult = null;
        StreamDataType result = instance.getTypeOf(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFieldIndex method, of class FormatMap.
     */
    @Test
    public void testGetFieldIndex() {
        System.out.println("getFieldIndex");
        String name = "";
        FormatMap instance = null;
        int expResult = 0;
        int result = instance.getFieldIndex(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAttributeCount method, of class FormatMap.
     */
    @Test
    public void testGetAttributeCount() {
        System.out.println("getAttributeCount");
        FormatMap instance = null;
        int expResult = 0;
        int result = instance.getAttributeCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
