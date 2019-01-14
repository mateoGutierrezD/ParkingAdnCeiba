package co.com.ceiba.adnceibaparking.services;

import co.com.ceiba.adnceibaparking.Models.Constants;
import co.com.ceiba.adnceibaparking.Models.ResponseController;
import co.com.ceiba.adnceibaparking.Models.TypeVehicle;
import co.com.ceiba.adnceibaparking.repositories.TypeVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeVehicleService {

    @Autowired
    TypeVehicleRepository typeVehicleRepository;

    public ResponseController<List<TypeVehicle>> getAllVehicleTypes() {
        List<TypeVehicle> typeVehicleList = typeVehicleRepository.findAll();

        if(typeVehicleList.size() < 1) {
            return new ResponseController<List<TypeVehicle>>(Constants.NO_VEHICLE_TYPES_REGISTERED);
        } else {
            return new ResponseController<List<TypeVehicle>>(typeVehicleList);
        }
    }

    public ResponseController<List<TypeVehicle>> registerTypeVehicle(TypeVehicle typeVehicle){
        this.typeVehicleRepository.insert(typeVehicle);
        return new ResponseController<List<TypeVehicle>>(Constants.SUCCESS);
    }

    public ResponseController<Object> deleteTypeVehicle(String code){
        TypeVehicle typeVehicle = this.typeVehicleRepository.findByCode(code);

        if(typeVehicle != null){
            String codeFound = typeVehicle.getCode();
            if(codeFound.equals(code)) {
                this.typeVehicleRepository.delete(typeVehicle);
                return new ResponseController<Object>(Constants.VEHICLE_TYPES_DELETED);
            }
        }
        return new ResponseController<Object>(Constants.VEHICLE_TYPE_NOT_EXISTS);
    }
}
