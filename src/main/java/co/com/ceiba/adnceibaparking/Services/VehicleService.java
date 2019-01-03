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

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    double value = 0;

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

        vehicle.setDateIn(DateConverter.getCurrentDate());
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

                //this.vehicleRepository.delete(vehicle);
                return new Response<Object>(Constants.VEHICLE_DELETED, "El costo del parqueadero es: " + (valueToPay));
            }
        }
        return new Response<Object>(Constants.VEHICLE_NOT_IN_PARKING);
    }

    public double calculateCarPaymentBill(Vehicle vehicle){
        String currentDate = DateConverter.getCurrentDate();

        Date today = DateConverter.convertStringToDate(currentDate);
        Date dateIn = DateConverter.convertStringToDate(vehicle.getDateIn());

        if (calculateHoursInParking(today.getTime(), dateIn.getTime()) > 9 && calculateHoursInParking(today.getTime(), dateIn.getTime()) < 25 ) {
            value = Constants.CAR_DAY_PRICE;

        } else if (calculateHoursInParking(today.getTime(), dateIn.getTime()) > 24 && calculateHoursInParking(today.getTime(), dateIn.getTime()) > 9)
        {
            long totalDays = calculateHoursInParking(today.getTime(), dateIn.getTime());
            totalDays = totalDays / 24;
            value = Constants.CAR_DAY_PRICE * totalDays;

        } else {
            long totalHours = calculateHoursInParking(today.getTime(), dateIn.getTime());
            value = Constants.CAR_HOUR_PRICE * totalHours;
        }

        return value;
    }

    public double calculateMotorcyclePaymentBill(Vehicle vehicle){
        String currentDate = DateConverter.getCurrentDate();

        Date today = DateConverter.convertStringToDate(currentDate);
        Date dateIn = DateConverter.convertStringToDate(vehicle.getDateIn());


        if(calculateHoursInParking(today.getTime(), dateIn.getTime())  > 9 && calculateHoursInParking(today.getTime(), dateIn.getTime()) < 25 ) {
            value = Constants.MOTORCYCLE_DAY_PRICE;

        } else if (calculateHoursInParking(today.getTime(), dateIn.getTime()) > 24)
        {
            long totalDays = calculateHoursInParking(today.getTime(), dateIn.getTime());
            totalDays = totalDays / 24;
            value = Constants.MOTORCYCLE_DAY_PRICE * totalDays;

        } else {
            long totalHours = calculateHoursInParking(today.getTime(), dateIn.getTime());
            value = Constants.MOTORCYCLE_HOUR_PRICE * totalHours;
        }

        return value;
    }

    public long calculateHoursInParking(long dateIn, long dateOut){

        long hoursIn = TimeUnit.MILLISECONDS.toHours(dateIn);
        dateIn -= TimeUnit.HOURS.toMillis(hoursIn);

        long hoursOut = TimeUnit.MILLISECONDS.toHours(dateOut);
        dateOut -= TimeUnit.HOURS.toMillis(hoursOut);

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
