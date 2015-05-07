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
import java.math.BigDecimal;
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
public class DataStreamTest {
    
    DataStream instance;
    
    public DataStreamTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new DataStream("Test", "TestX", "TestY", StreamDataType.BIGDECIMAL);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addDataPoint method, of class DataStream.
     */
    @Test
    public void testAddDataPoint_BigDecimal_BigDecimal() {
        System.out.println("addDataPoint");
        BigDecimal time = new BigDecimal(3000);
        BigDecimal value = new BigDecimal(5.5);
        BigDecimal expValue = new BigDecimal(5.5);
        BigDecimal result = instance.addDataPoint(time, value).getValueAtTime(time);
        assertEquals(expValue, result);
    }

    /**
     * Test of addDataPoint method, of class DataStream.
     */
    @Test
    public void testAddDataPoint_double_double() {
        System.out.println("addDataPoint");
        double time = 3000.0;
        double value = 40.0;
        BigDecimal expResult = new BigDecimal(40.0);
        BigDecimal result = instance.addDataPoint(time, value).getValueAtTime(time);
        assert(result.compareTo(expResult) == 0);
    }

    /**
     * Test of getValueAtTime method, of class DataStream.
     */
    @Test
    public void testGetValueAtTime_BigDecimal() {
        System.out.println("getValueAtTime");
        
        BigDecimal key1 = new BigDecimal(3000.0), value1 = new BigDecimal(400.0);
        BigDecimal key2 = new BigDecimal(4000.0), value2 = new BigDecimal(450.0);
        BigDecimal midtime = new BigDecimal(3500.0), midvalue = new BigDecimal(425.0);
        
        instance.addDataPoint(key1, value1);
        instance.addDataPoint(key2, value2);
        
        BigDecimal result = instance.getValueAtTime(midtime);
        assert(midvalue.compareTo(result) == 0);
    }

    /**
     * Test of getValueAtTime method, of class DataStream.
     */
    @Test
    public void testGetValueAtTime_double() {
        System.out.println("getValueAtTime");
        double key1 = 3000.0, value1 = 400.0;
        double key2 = 4000.0, value2 = 450.0;
        double midtime = 3500.0;
        BigDecimal midvalue = new BigDecimal(425.0);
        
        instance.addDataPoint(key1, value1);
        instance.addDataPoint(key2, value2);
        
        BigDecimal result = instance.getValueAtTime(midtime);
        assert(midvalue.compareTo(result) == 0);
    }

    /**
     * Test of getName method, of class DataStream.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "Test";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getxAxisName method, of class DataStream.
     */
    @Test
    public void testGetxAxisName() {
        System.out.println("getxAxisName");
        String expResult = "TestX";
        String result = instance.getxAxisName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getyAxisName method, of class DataStream.
     */
    @Test
    public void testGetyAxisName() {
        System.out.println("getyAxisName");
        String expResult = "TestY";
        String result = instance.getyAxisName();
        assertEquals(expResult, result);
    }
    
}
