package co.com.ceiba.adnceibaparking.Exceptions;

import co.com.ceiba.adnceibaparking.Models.Constants;

public class PlateForDay extends Exception {

    public PlateForDay(){
        super(Constants.VEHICLE_CANNOT_ENTER);
    }
}
