package co.com.ceiba.adnceibaparking.controllers;

import co.com.ceiba.adnceibaparking.exceptions.NumberMaxVehicles;
import co.com.ceiba.adnceibaparking.exceptions.PlateForDay;
import co.com.ceiba.adnceibaparking.exceptions.VehicleRegisteredPreviously;
import co.com.ceiba.adnceibaparking.Models.Constants;
import co.com.ceiba.adnceibaparking.Models.Response;
import co.com.ceiba.adnceibaparking.Models.Vehicle;
import co.com.ceiba.adnceibaparking.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleController {

    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @RequestMapping(value = "/vehicle/allRegisteredVehicles", method=RequestMethod.GET)
    public Response<List<Vehicle>> listVehicles(){
        return vehicleService.getAllVehicles();
    }

    @RequestMapping(value = "/vehicle/register", method=RequestMethod.POST)
    public ResponseEntity<Response<Vehicle>> insert(@RequestBody Vehicle vehicle) {
        try {

            vehicleService.registerVehicle(vehicle);
        } catch (NumberMaxVehicles e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response<Vehicle>(e.getMessage()));
        } catch (PlateForDay e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response<Vehicle>(e.getMessage()));
        } catch (VehicleRegisteredPreviously e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response<Vehicle>(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Response<Vehicle>(Constants.VEHICLE_ENTERED));
    }

    @RequestMapping(value = "/vehicle/delete", method=RequestMethod.DELETE)
    public Response<Object> delete(@RequestParam(value="plate") String plate) throws Exception {
        return vehicleService.deleteVehicle(plate);
    }
}
