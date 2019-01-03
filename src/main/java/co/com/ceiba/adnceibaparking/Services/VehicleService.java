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

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    double value = 0;
    String currentDate = DateConverter.getCurrentDate();


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

        if (maxCapacity(vehicle.getTypeVehicleDescription(), numberOfVehicles)) {
            throw new NumberMaxVehicles();
        }

        if (!vehicleCanPark(vehicle.getPlate())) {
            throw new PlateForDay();
        }

        if (carIsAlreadyRegistered(vehicle.getPlate())) {
            throw new VehicleRegisteredPreviously();
        }

        vehicle.setDateIn(DateConverter.getCurrentDate());
        this.vehicleRepository.insert(vehicle);
        return new Response<List<Vehicle>>(Constants.SUCCESS);
    }

    public boolean maxCapacity(String typeVehicle, int numberVehicles) {
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

    public boolean vehicleCanPark(String plate) {
        if (plate.toUpperCase().substring(0, 1).equals("A")) {
            return (DateConverter.getCurrentDayOfWeek() == 1 || DateConverter.getCurrentDayOfWeek() == 2);
        }
        return true;
    }

    public boolean carIsAlreadyRegistered(String plate) {
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
        double valueToPay = 0;
        Vehicle vehicle = this.vehicleRepository.findByPlate(plate);
        if(vehicle != null){
            String plateFound = vehicle.getPlate();
            if(plateFound.equals(plate)) {

                switch (vehicle.getTypeVehicleDescription()) {
                    case Constants.CAR :
                        valueToPay = calculateCarPaymentBill(vehicle);
                        break;
                    case Constants.MOTORCYCLE :
                        valueToPay = calculateMotorcyclePaymentBill(vehicle);
                }

                boolean vehicleIshighCylinder = validateCylinder(vehicle.getCylinder());

                if(vehicleIshighCylinder) {
                    valueToPay = valueToPay + Constants.MOTORCYCLE_EXTRA_PRICE;
                }

                this.vehicleRepository.delete(vehicle);
                return new Response<Object>(Constants.VEHICLE_DELETED, (int)valueToPay);
            }
        }
        return new Response<Object>(Constants.VEHICLE_NOT_IN_PARKING);
    }

    public double calculateCarPaymentBill(Vehicle vehicle){

        double totalHours = getCurrentDateAndVehicleDateIn(vehicle);

        if (totalHours > 9 && totalHours < 25 ) {
            value = Constants.CAR_DAY_PRICE;

        } else if (totalHours > 24 && totalHours > 9)
        {
            totalHours = totalHours / 24;
            value = Constants.CAR_DAY_PRICE * (int) Math.ceil(totalHours);

        } else {
            value = Constants.CAR_HOUR_PRICE * totalHours;
        }

        return value;
    }

    public double calculateMotorcyclePaymentBill(Vehicle vehicle){

        double totalHours = getCurrentDateAndVehicleDateIn(vehicle);

        if (totalHours > 9 && totalHours < 25 ) {
            value = Constants.MOTORCYCLE_DAY_PRICE;

        } else if (totalHours > 24 && totalHours > 9)
        {
            totalHours = totalHours / 24;
            value = Constants.MOTORCYCLE_DAY_PRICE * (int) Math.ceil(totalHours);

        } else {
            value = Constants.MOTORCYCLE_HOUR_PRICE * totalHours;
        }

        return value;
    }

    public double getCurrentDateAndVehicleDateIn(Vehicle vehicle){
        Date today = DateConverter.convertStringToDate(currentDate);
        Date dateIn = DateConverter.convertStringToDate(vehicle.getDateIn());
        double totalHours = calculateHoursInParking(today.getTime(), dateIn.getTime());
        totalHours = (int) Math.ceil(totalHours);
        return totalHours;
    }

    public double calculateHoursInParking(long dateIn, long dateOut){

        double hoursIn = TimeUnit.MILLISECONDS.toMinutes(dateIn);
        double hoursOut = TimeUnit.MILLISECONDS.toMinutes(dateOut);

        hoursOut = hoursOut / 60;
        hoursIn = hoursIn / 60;

        return hoursIn - hoursOut;
    }

    public boolean validateCylinder(int cylinder) {
        if(cylinder > Constants.MOTORCYCLE_CC_RULE) {
            return true;
        } else {
            return false;
        }
    }


}
