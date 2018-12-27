package co.com.ceiba.adnceibaparking.Controllers;

import co.com.ceiba.adnceibaparking.Models.Vehicle;
import co.com.ceiba.adnceibaparking.Repositories.RegisterVehicleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RegisterVehicleController {

    private RegisterVehicleRepository registerVehicleRepository;


    public RegisterVehicleController(RegisterVehicleRepository registerVehicleRepository) {
        this.registerVehicleRepository = registerVehicleRepository;
    }

    @RequestMapping(value = "/vehicle/allRegisteredVehicles", method=RequestMethod.GET)
    public List<Vehicle> getAll(){
        List<Vehicle> registeredVehicleList = this.registerVehicleRepository.findAll();
        return registeredVehicleList;
    }

    @RequestMapping(value = "/vehicle/register", method=RequestMethod.POST)
    public Vehicle insert(@RequestBody Vehicle registerVehicle) {
        return this.registerVehicleRepository.insert(registerVehicle);
    }

    @RequestMapping(value = "/vehicle/delete", method=RequestMethod.DELETE)
    public void delete(@RequestParam(value="plate") String plate) {
      this.registerVehicleRepository.deleteByPlate(plate);
    }

}
