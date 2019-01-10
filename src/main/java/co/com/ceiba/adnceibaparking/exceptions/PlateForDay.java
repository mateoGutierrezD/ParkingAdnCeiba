package co.com.ceiba.adnceibaparking.exceptions;

import co.com.ceiba.adnceibaparking.Models.Constants;

public class PlateForDay extends Exception {

    public PlateForDay(){
        super(Constants.VEHICLE_CANNOT_ENTER);
    }
}
