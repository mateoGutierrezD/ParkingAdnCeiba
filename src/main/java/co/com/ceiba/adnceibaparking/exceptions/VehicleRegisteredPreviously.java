package co.com.ceiba.adnceibaparking.exceptions;

import co.com.ceiba.adnceibaparking.Models.Constants;

public class VehicleRegisteredPreviously extends Exception {

    public VehicleRegisteredPreviously(){
        super(Constants.VEHICLE_IN_PARKING);
    }
}
