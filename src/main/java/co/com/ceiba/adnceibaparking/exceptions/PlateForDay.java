package co.com.ceiba.adnceibaparking.exceptions;

import co.com.ceiba.adnceibaparking.Models.Constants;

public class PlateForDay extends GeneralException {

    public PlateForDay(){
        super(Constants.VEHICLE_CANNOT_ENTER);
    }
}
