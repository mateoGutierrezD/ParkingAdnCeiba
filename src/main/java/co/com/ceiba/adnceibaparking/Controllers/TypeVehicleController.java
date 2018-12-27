package co.com.ceiba.adnceibaparking.Controllers;

import co.com.ceiba.adnceibaparking.Models.TypeVehicle;
import co.com.ceiba.adnceibaparking.Repositories.TypeVehicleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TypeVehicleController {

    private TypeVehicleRepository typeVehicleRepository;

    public TypeVehicleController(TypeVehicleRepository typeVehicleRepository) {
        this.typeVehicleRepository = typeVehicleRepository;
    }

    @RequestMapping(value = "/typeVehicles/all", method=RequestMethod.GET)
    public List<TypeVehicle> getAll(){
        List<TypeVehicle> typeVehicleList = this.typeVehicleRepository.findAll();
        return typeVehicleList;
    }

    @RequestMapping(value = "/typeVehicles/register", method=RequestMethod.POST)
    public TypeVehicle insert(@RequestBody TypeVehicle typeVehicle) {
        return this.typeVehicleRepository.insert(typeVehicle);
    }

    @RequestMapping(value = "/typeVehicles/delete", method=RequestMethod.DELETE)
    public void delete(@RequestParam(value="code") String code) {
        this.typeVehicleRepository.deleteByCode(code);
    }
}
