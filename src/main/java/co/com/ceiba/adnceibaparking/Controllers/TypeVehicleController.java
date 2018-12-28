package co.com.ceiba.adnceibaparking.Controllers;

import co.com.ceiba.adnceibaparking.Models.Response;
import co.com.ceiba.adnceibaparking.Models.TypeVehicle;
import co.com.ceiba.adnceibaparking.Repositories.TypeVehicleRepository;
import co.com.ceiba.adnceibaparking.Services.TypeVehicleService;
import org.springframework.web.bind.annotation.*;

@RestController
public class TypeVehicleController {

    private TypeVehicleRepository typeVehicleRepository;
    private TypeVehicleService typeVehicleService;

    public TypeVehicleController(TypeVehicleService typeVehicleService) {
        this.typeVehicleService = typeVehicleService;
    }

    @RequestMapping(value = "/typeVehicles/all", method=RequestMethod.GET)
    public Response<?> listTypeVehicles(){
        return typeVehicleService.getAllVehicleTypes();
    }

    @RequestMapping(value = "/typeVehicles/register", method=RequestMethod.POST)
    public Response<?> insert(@RequestBody TypeVehicle typeVehicle) {
        return typeVehicleService.registerTypeVehicle(typeVehicle);
    }

    @RequestMapping(value = "/typeVehicles/delete", method=RequestMethod.DELETE)
    public Response<?> delete(@RequestParam(value="code") String code) {
        return this.typeVehicleService.deleteTypeVehicle(code);
    }
}
