package co.com.ceiba.adnceibaparking.Controllers;

import co.com.ceiba.adnceibaparking.Exceptions.NumberMaxVehicles;
import co.com.ceiba.adnceibaparking.Exceptions.PlateForDay;
import co.com.ceiba.adnceibaparking.Exceptions.VehicleRegisteredPreviously;
import co.com.ceiba.adnceibaparking.Models.Constants;
import co.com.ceiba.adnceibaparking.Models.Response;
import co.com.ceiba.adnceibaparking.Models.Vehicle;
import co.com.ceiba.adnceibaparking.Repositories.VehicleRepository;
import co.com.ceiba.adnceibaparking.Services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VehicleController {

    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @RequestMapping(value = "/vehicle/allRegisteredVehicles", method=RequestMethod.GET)
    public Response<?> listVehicles(){
        return vehicleService.getAllVehicles();
    }

    @RequestMapping(value = "/vehicle/register", method=RequestMethod.POST)
    public ResponseEntity<Response<?>> insert(@RequestBody Vehicle vehicle) {
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
    public Response<?> delete(@RequestParam(value="plate") String plate) {
        return vehicleService.deleteVehicle(plate);
    }

}
