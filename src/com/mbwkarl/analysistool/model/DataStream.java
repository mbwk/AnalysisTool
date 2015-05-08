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
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Karl Birch
 */
public class DataStream {
    
    private static final BigDecimal TIME_COMPATIBILITY_MARGIN = BigDecimal.valueOf(500);
    
    private String dataName = "";
    private String xAxisName = "";
    private String yAxisName = "";
    
    private StreamDataType valueType = StreamDataType.UNKNOWN;
    
    private TreeMap<BigDecimal, BigDecimal> datapoints;
    private BigDecimal lowestValue;
    private BigDecimal highestValue;
    private BigDecimal lastValue;
    
    private static final BigDecimal TIME_SANITATION_MULTIPLIER = BigDecimal.valueOf(2);
    
    public DataStream(String name, String xName, String yName, StreamDataType yType) {
        dataName = name;
        xAxisName = xName;
        yAxisName = yName;
        valueType = yType;
        
        // datapoints = new TreeMap<BigDecimal, BigDecimal>(BigDecimal::compareTo);
        datapoints = new TreeMap<BigDecimal, BigDecimal>(new Comparator<BigDecimal>() {
            @Override
            public int compare(BigDecimal o1, BigDecimal o2) {
                return o1.compareTo(o2);
            }
        });
    }
    
    private BigDecimal getMean(BigDecimal ... values) {
        BigDecimal aggregate = BigDecimal.ZERO;
        int valueCount = values.length;
        for (int i = 0; i < valueCount; ++i) {
            aggregate = aggregate.add(values[i]);
        }
        
        BigDecimal mean = aggregate.divide(BigDecimal.valueOf(valueCount), 10, RoundingMode.HALF_UP);
        
        return mean;
    }
    
    private boolean sanitizeTimes() {
        boolean runAgain = true;
        
        Set<BigDecimal> set = datapoints.keySet();
        BigDecimal[] times = new BigDecimal[set.size()];
        set.toArray(times);
        
        BigDecimal timeMean = getMean(times);
        
        BigDecimal timeMeanDiffHigh = getHighestKey().subtract(timeMean);
        BigDecimal timeMeanDiffLow = timeMean.subtract(getLowestKey());
        
        BigDecimal timeMeanDiffDiff = timeMeanDiffHigh.subtract(timeMeanDiffLow);
        
        if (timeMeanDiffHigh.compareTo( timeMeanDiffLow.multiply(TIME_SANITATION_MULTIPLIER) ) > 0) {
            datapoints.remove(getHighestKey());
        } else if (timeMeanDiffLow.compareTo( timeMeanDiffHigh.multiply(TIME_SANITATION_MULTIPLIER) ) > 0) {
            datapoints.remove(getLowestKey());
        } else {
            runAgain = false;
        }
        
        return runAgain;
    }
    
    private void sanitizeTree() {
        int timeSanitations = 0;
        while (sanitizeTimes()) {
            ++timeSanitations;
        }
        System.err.printf("removed %d erroneous times\n", timeSanitations);
    }
    
    private boolean failSanity() {
        System.err.println("sanity check failed");
        return false;
    }
    
    private boolean passSanityCheck(BigDecimal value) {
        if (!datapoints.isEmpty()) {
//            BigDecimal diff = value.subtract(lastValue);
//            diff = diff.compareTo(BigDecimal.ZERO) < 0 ? diff.negate() : diff;
//            if (diff.compareTo(BigDecimal.valueOf(1000)) > 0) {
//                return failSanity();
//            }
            //sanitizeTree();
        }
        
        if (getType() == StreamDataType.LATLON) {
            BigDecimal limit = BigDecimal.valueOf(180);
            if (value.compareTo(limit) > 0 || value.compareTo(limit.negate()) < 0) {
                return failSanity();
            }
        }
        
        if (getType() == StreamDataType.BIGINTEGER) {
            
        }
        
        return true;
    }
    
    /**
     * Add a data point to the data stream
     * @param time
     * @param value
     * @return 
     */
    public synchronized DataStream addDataPoint(BigDecimal time, BigDecimal value) {
        if (time.compareTo(BigDecimal.ZERO) == 0) {
            // uninitialized value
            return null;
        }
        
        // sanity checking
        if (!passSanityCheck(value)) {
            return null;
        }
        
        if (datapoints.isEmpty()) {
            highestValue = value;
            lowestValue = value;
//            setHighestValue(value);
//            setLowestValue(value);
            lastValue = value;
        } else {
            if (value.compareTo(getHighestValue()) > 0) {
                setHighestValue(value);
            } else if (value.compareTo(getLowestValue()) < 0) {
                setLowestValue(value);
            }
        }
        
        datapoints.put(time, value);
        
        return this;
    }
    
    private void setHighestValue(BigDecimal newHigh) {
        highestValue = newHigh;
    }
    
    private void setLowestValue(BigDecimal newLow) {
        lowestValue = newLow;
    }
    
    public synchronized DataStream addDataPoint(double time, double value) {
        return addDataPoint(BigDecimal.valueOf(time), BigDecimal.valueOf(value));
    }
    
    private BigDecimal getMidValue(BigDecimal lookupKey, BigDecimal lowKey, BigDecimal highKey, BigDecimal lowValue, BigDecimal highValue) {
        BigDecimal lowToMy = lookupKey.subtract(lowKey);
        BigDecimal lowToHigh = highKey.subtract(lowKey);
        
        BigDecimal ratio = lowToMy.divide(lowToHigh, 2, RoundingMode.HALF_UP);
        
        BigDecimal valueDiff = highValue.subtract(lowValue);
        BigDecimal ratioValueDiff = valueDiff.multiply(ratio);
        
        return lowValue.add(ratioValueDiff);
    }
    
    /**
     * Takes a key parameter, and returns a value for that time.  If no such
     * entry exists for the given key, and the given key lies between two
     * existing keys, an approximation is made of the value that would be at
     * this point.
     * @param time
     * @return 
     */
    public BigDecimal getValueAtTime(BigDecimal time) {
        if (datapoints.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal ck = datapoints.ceilingKey(time);
        BigDecimal fk = datapoints.floorKey(time);
        
        if (ck == null || fk == null) {
            return BigDecimal.ZERO;
        } else if (time.compareTo(ck) == 0 || time.compareTo(fk) == 0) {
            return datapoints.get(time);
        } else {
            BigDecimal cv = datapoints.get(ck);
            BigDecimal fv = datapoints.get(fk);
            return getMidValue(time, fk, ck, fv, cv);
        }
        
    }
    
    /**
     * Convenience method wrapping around getValueAtTime() that permits a using
     * a double as the argument.
     * @param time
     * @return 
     */
    public BigDecimal getValueAtTime(double time) {
        return getValueAtTime(BigDecimal.valueOf(time));
    }
    
    /**
     * Returns the lowest key in the DataStream
     * @return 
     */
    public BigDecimal getLowestKey() {
        try {
            return datapoints.firstKey();
        } catch (NoSuchElementException nse) {
            return BigDecimal.ZERO;
        }
    }
    
    /**
     * Returns the highest key in the DataStream
     * @return 
     */
    public BigDecimal getHighestKey() {
        try {
            return datapoints.lastKey();
        } catch (NoSuchElementException nse) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Returns the lowest recorded value in the DataStream
     * @return 
     */
    public BigDecimal getLowestValue() {
        if (datapoints.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return lowestValue;
    }
    
    /**
     * Returns the highest recorded value in the DataStream
     * @return 
     */
    public BigDecimal getHighestValue() {
        if (datapoints.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return highestValue;
    }

    /**
     * Accessor for DataStream name
     * @return 
     */
    public String getName() {
        return dataName;
    }
    
    /**
     * Accessor for keys name
     * @return 
     */
    public String getxAxisName() {
        return xAxisName;
    }

    /**
     * Accessor for values name
     * @return 
     */
    public String getyAxisName() {
        return yAxisName;
    }
    
    /**
     * Calculates the time interval for a given number of lookups
     * @param callCount
     * @return 
     */
    public BigDecimal getIntervalForCalls(int callCount) {
        return getHighestKey().subtract(getLowestKey()).divide(BigDecimal.valueOf(callCount), 2, RoundingMode.HALF_DOWN);
    }
    
    public BigDecimal getValueAtNIntervals(BigDecimal interval, int n) {
        return getValueAtTime(interval.multiply(BigDecimal.valueOf(n)).add(getLowestKey()));
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dataName);
        sb.append(" - (");
        sb.append(yAxisName);
        sb.append('/');
        sb.append(xAxisName);
        sb.append(')');
        return sb.toString();
    }
    
    public static BigDecimal getCommonLowestTime(DataStream ... streams) {
        BigDecimal lowest = streams[0].getLowestKey();
        if (streams.length != 0) {
            lowest = streams[0].getLowestKey();
            for (DataStream ds : streams) {
                BigDecimal dsLowKey = ds.getLowestKey();
                if (dsLowKey.compareTo(lowest) > 0) {
                    lowest = dsLowKey;
                }
            }
        }
        return lowest;
    }
    
    public static BigDecimal getCommonHighestTime(DataStream ... streams) {
        BigDecimal highest = streams[0].getHighestKey();
        for (DataStream ds : streams) {
            BigDecimal dsHighKey = ds.getHighestKey();
            if (dsHighKey.compareTo(highest) < 0) {
                highest = dsHighKey;
            }
        }
        return highest;
    }
    
    public static BigDecimal[] getCommonTimeRange(DataStream ... streams) {
        BigDecimal[] range = new BigDecimal[2];
        range[0] = getCommonLowestTime(streams);
        range[1] = getCommonHighestTime(streams);
        
        return range;
    }
    
    public static boolean areStreamsTimeCompatibile(DataStream ... streams) {
        BigDecimal min = streams[0].getLowestKey();
        BigDecimal max = streams[0].getHighestKey();
        
        for (int i = 1, limit = streams.length; i < limit; ++i) {
            DataStream stream = streams[i];
            
            BigDecimal minMargin = stream.getLowestKey().subtract(min);
            if (minMargin.compareTo(BigDecimal.ZERO) < 0) {
                minMargin = minMargin.negate();
            }
            if (minMargin.compareTo(TIME_COMPATIBILITY_MARGIN) > 0) {
                return false;
            }
                
            BigDecimal maxMargin = stream.getHighestKey().subtract(max);
            if (maxMargin.compareTo(BigDecimal.ZERO) < 0) {
                maxMargin = maxMargin.negate();
            }
            if (maxMargin.compareTo(TIME_COMPATIBILITY_MARGIN) > 0) {
                return false;
            }
        }
        
        return true;
    }
    
    public StreamDataType getType() {
        return valueType;
    }
    
}
