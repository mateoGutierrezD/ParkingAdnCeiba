package co.com.ceiba.adnceibaparking.Exceptions;

import co.com.ceiba.adnceibaparking.Models.Constants;

public class NumberMaxVehicles extends Exception {

    public NumberMaxVehicles(){
        super(Constants.MAX_NUMBER_VEHICLES);
    }
}
