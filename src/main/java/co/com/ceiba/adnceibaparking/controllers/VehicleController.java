package co.com.ceiba.adnceibaparking.controllers;

import co.com.ceiba.adnceibaparking.Models.Constants;
import co.com.ceiba.adnceibaparking.Models.ResponseController;
import co.com.ceiba.adnceibaparking.Models.Vehicle;
import co.com.ceiba.adnceibaparking.exceptions.GeneralException;
import co.com.ceiba.adnceibaparking.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class VehicleController {

    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @RequestMapping(value = "/vehicle/allRegisteredVehicles", method=RequestMethod.GET)
    public ResponseController<List<Vehicle>> listVehicles(){
        return vehicleService.getAllVehicles();
    }

    @RequestMapping(value = "/vehicle/register", method=RequestMethod.POST)
    public ResponseEntity<ResponseController<Vehicle>> insert(@RequestBody Vehicle vehicle) throws GeneralException {
        vehicleService.registerVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseController<Vehicle>(Constants.VEHICLE_ENTERED));
    }

    @RequestMapping(value = "/vehicle/delete", method=RequestMethod.DELETE)
    public ResponseController<Object> delete(@RequestParam(value="plate") String plate) throws ParseException {
        return vehicleService.deleteVehicle(plate);
    }
}
