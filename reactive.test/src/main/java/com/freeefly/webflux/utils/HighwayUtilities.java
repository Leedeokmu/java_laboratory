package com.freeefly.webflux.utils;

import com.freeefly.webflux.model.Vehicle;
import org.apache.commons.lang3.RandomStringUtils;

public class HighwayUtilities {
    public static Vehicle simulateTraffic(){
        Vehicle vehicle = new Vehicle();
        vehicle.setCarPateNumber(RandomStringUtils.randomAlphanumeric(7));
        vehicle.setSpeed(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicle.setWeight(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicle.setModelYear(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicle.setGasType(RandomStringUtils.randomAscii(5));
        return vehicle;
    }
}
