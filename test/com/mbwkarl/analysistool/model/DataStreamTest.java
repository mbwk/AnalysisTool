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
    
    private static final String NAME = "TEST", XAXIS = "TestX", YAXIS = "TestY";
    private static final StreamDataType TYPE = StreamDataType.BIGDECIMAL;
    
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
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);
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
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);
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
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);
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
        
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);
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
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);
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
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);
        String expResult = NAME;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getxAxisName method, of class DataStream.
     */
    @Test
    public void testGetxAxisName() {
        System.out.println("getxAxisName");
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);
        String expResult = XAXIS;
        String result = instance.getxAxisName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getyAxisName method, of class DataStream.
     */
    @Test
    public void testGetyAxisName() {
        System.out.println("getyAxisName");
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);
        String expResult = YAXIS;
        String result = instance.getyAxisName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLowestKey method, of class DataStream.
     */
    @Test
    public void testGetLowestKey() {
        System.out.println("getLowestKey");
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);

        instance.addDataPoint(4525, 4524);
        instance.addDataPoint(233, 654);
        instance.addDataPoint(56674, 2348);
        
        BigDecimal expResult = BigDecimal.ONE;
        instance.addDataPoint(expResult, BigDecimal.TEN);
        
        BigDecimal result = instance.getLowestKey();
        assert(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getHighestKey method, of class DataStream.
     */
    @Test
    public void testGetHighestKey() {
        System.out.println("getHighestKey");
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);

        instance.addDataPoint(1, 2);
        instance.addDataPoint(4, 5);
        instance.addDataPoint(6, 3);
        
        BigDecimal expResult = BigDecimal.TEN;
        instance.addDataPoint(expResult, BigDecimal.ONE);
        
        BigDecimal result = instance.getHighestKey();
        assert(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getLowestValue method, of class DataStream.
     */
    @Test
    public void testGetLowestValue() {
        System.out.println("getLowestValue");
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);

        instance.addDataPoint(1, 2);
        instance.addDataPoint(4, 5);
        instance.addDataPoint(6, 3);
        
        BigDecimal expResult = BigDecimal.ONE;
        instance.addDataPoint(expResult, BigDecimal.ONE);
        
        BigDecimal result = instance.getLowestValue();
        assert(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getHighestValue method, of class DataStream.
     */
    @Test
    public void testGetHighestValue() {
        System.out.println("getHighestValue");
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);

        instance.addDataPoint(1, 2);
        instance.addDataPoint(4, 5);
        instance.addDataPoint(6, 3);
        
        BigDecimal expResult = BigDecimal.TEN;
        instance.addDataPoint(expResult, BigDecimal.TEN);
        
        BigDecimal result = instance.getHighestValue();
        assert(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getIntervalForCalls method, of class DataStream.
     */
    @Test
    public void testGetIntervalForCalls() {
        System.out.println("getIntervalForCalls");
        int callCount = 10;
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);
        
        instance.addDataPoint(BigDecimal.valueOf(1), BigDecimal.ZERO);
        instance.addDataPoint(BigDecimal.valueOf(11), BigDecimal.TEN);
        
        BigDecimal expResult = BigDecimal.ONE;
        BigDecimal result = instance.getIntervalForCalls(callCount);
        System.err.println(result + " == " + expResult);
        assert(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getValueAtNIntervals method, of class DataStream.
     */
    @Test
    public void testGetValueAtNIntervals() {
        System.out.println("getValueAtNIntervals");
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);
        
        instance.addDataPoint(BigDecimal.ONE, BigDecimal.ZERO);
        instance.addDataPoint(BigDecimal.valueOf(11), BigDecimal.TEN);
        
        int n = 5;
        BigDecimal interval = instance.getIntervalForCalls(10);
        
        BigDecimal expResult = BigDecimal.valueOf(5);
        BigDecimal result = instance.getValueAtNIntervals(interval, n);
        assert(expResult.compareTo(result) == 0);
    }

    private DataStream[] mkStreams() {
        int limit = 5;
        DataStream[] streams = new DataStream[limit];
        for (int i = 0; i < limit; ++i) {
            streams[i] = new DataStream(NAME, XAXIS, YAXIS, TYPE);
        }
        return streams;
    }
    
    /**
     * Test of getCommonLowestTime method, of class DataStream.
     */
    @Test
    public void testGetCommonLowestTime() {
        System.out.println("getCommonLowestTime");
        DataStream[] streams = mkStreams();
        
        BigDecimal expResult = BigDecimal.TEN;
        streams[3].addDataPoint(expResult, BigDecimal.TEN);
        streams[1].addDataPoint(4, 2324);
        streams[2].addDataPoint(3, 34);
        streams[4].addDataPoint(5, 64);
        streams[0].addDataPoint(2, 3);
        
        BigDecimal result = DataStream.getCommonLowestTime(streams);
        assert(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getCommonHighestTime method, of class DataStream.
     */
    @Test
    public void testGetCommonHighestTime() {
        System.out.println("getCommonHighestTime");
        DataStream[] streams = mkStreams();
        
        BigDecimal expResult = BigDecimal.ONE;
        streams[3].addDataPoint(expResult, BigDecimal.TEN);
        streams[1].addDataPoint(4, 2324);
        streams[2].addDataPoint(3, 34);
        streams[4].addDataPoint(5, 64);
        streams[0].addDataPoint(2, 3);
        
        BigDecimal result = DataStream.getCommonHighestTime(streams);
        assert(expResult.compareTo(result) == 0);
    }

    /**
     * Test of areStreamsTimeCompatibile method, of class DataStream.
     */
    @Test
    public void testAreStreamsTimeCompatibile() {
        System.out.println("areStreamsTimeCompatibile");
        DataStream[] streams = mkStreams();
        
        for (int i = 0, limit = streams.length; i < limit; ++i) {
            streams[i].addDataPoint(BigDecimal.ONE, BigDecimal.ZERO);
            streams[i].addDataPoint(BigDecimal.TEN, BigDecimal.TEN);
        }
        
        boolean expResult = true;
        boolean result = DataStream.areStreamsTimeCompatibile(streams);
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class DataStream.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        DataStream instance = new DataStream(NAME, XAXIS, YAXIS, TYPE);
        StreamDataType expResult = TYPE;
        StreamDataType result = instance.getType();
        assertEquals(expResult, result);
    }
    
}
