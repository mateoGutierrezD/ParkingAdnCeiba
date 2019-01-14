package co.com.ceiba.adnceibaparking.controllers;

import co.com.ceiba.adnceibaparking.Models.ResponseController;
import co.com.ceiba.adnceibaparking.Models.TypeVehicle;
import co.com.ceiba.adnceibaparking.services.TypeVehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TypeVehicleController {

    private TypeVehicleService typeVehicleService;

    public TypeVehicleController(TypeVehicleService typeVehicleService) {
        this.typeVehicleService = typeVehicleService;
    }

    @RequestMapping(value = "/typeVehicles/all", method=RequestMethod.GET)
    public ResponseController<List<TypeVehicle>> listTypeVehicles(){
        return typeVehicleService.getAllVehicleTypes();
    }

    @RequestMapping(value = "/typeVehicles/register", method=RequestMethod.POST)
    public ResponseController<List<TypeVehicle>> insert(@RequestBody TypeVehicle typeVehicle) {
        return typeVehicleService.registerTypeVehicle(typeVehicle);
    }

    @RequestMapping(value = "/typeVehicles/delete", method=RequestMethod.DELETE)
    public ResponseController<Object> delete(@RequestParam(value="code") String code) {
        return this.typeVehicleService.deleteTypeVehicle(code);
    }
}
