package co.com.ceiba.adnceibaparking.Services;

import co.com.ceiba.adnceibaparking.Exceptions.NumberMaxVehicles;
import co.com.ceiba.adnceibaparking.Exceptions.PlateForDay;
import co.com.ceiba.adnceibaparking.Exceptions.VehicleRegisteredPreviously;
import co.com.ceiba.adnceibaparking.Models.Constants;
import co.com.ceiba.adnceibaparking.Models.Response;

import co.com.ceiba.adnceibaparking.Models.Vehicle;
import co.com.ceiba.adnceibaparking.Repositories.VehicleRepository;
import co.com.ceiba.adnceibaparking.Utilities.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    public Response<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicleList = vehicleRepository.findAll();

        if(vehicleList.size() < 1) {
            return new Response<List<Vehicle>>(Constants.NO_VEHICLES_IN_PARKING);
        } else {
            return new Response<List<Vehicle>>(Constants.SUCCESS, vehicleList);
        }
    }

    public Response<List<Vehicle>> registerVehicle(Vehicle vehicle) throws NumberMaxVehicles, PlateForDay, VehicleRegisteredPreviously {
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        int numberOfVehicles = vehicleList.size();

        if (MaxCapacity(vehicle.getTypeVehicleDescription(), numberOfVehicles)) {
            throw new NumberMaxVehicles();
        }

        if (!VehicleCanPark(vehicle.getPlate())) {
            throw new PlateForDay();
        }

        if (CarIsAlreadyRegistered(vehicle.getPlate())) {
            throw new VehicleRegisteredPreviously();
        }

        vehicle.setDateIn(DateConverter.getCurrentDateAndTime());
        this.vehicleRepository.insert(vehicle);
        return new Response<List<Vehicle>>(Constants.SUCCESS);
    }

    public boolean MaxCapacity(String typeVehicle, int numberVehicles) {
        switch (typeVehicle) {
            case Constants.CAR :
                if(numberVehicles >= Constants.MAX_NUMBER_CARS){
                    return true;
                }
                break;
            case Constants.MOTORCYCLE:
                if(numberVehicles >= Constants.MAX_NUMBER_MOTORCYCLES){
                    return true;
                }
                break;
        }
        return false;
    }

    public boolean VehicleCanPark(String plate) {
        if (plate.toUpperCase().substring(0, 1).equals("A")) {
            return (DateConverter.getCurrentDayOfWeek() == 1 || DateConverter.getCurrentDayOfWeek() == 2);
        }
        return true;
    }

    public boolean CarIsAlreadyRegistered(String plate) {
        Vehicle vehicle = this.vehicleRepository.findByPlate(plate);

        if(vehicle != null){
            String plateFound = vehicle.getPlate();
            if(plateFound.equals(plate)) {
                return true;
            }
        }
        return false;
    }

    public Response<Object> deleteVehicle(String plate){
        Vehicle vehicle = this.vehicleRepository.findByPlate(plate);
        if(vehicle != null){
            String plateFound = vehicle.getPlate();
            if(plateFound.equals(plate)) {
                this.vehicleRepository.delete(vehicle);
                return new Response<Object>(Constants.VEHICLE_DELETED);
            }
        }
        return new Response<Object>(Constants.VEHICLE_NOT_IN_PARKING);
    }
}
