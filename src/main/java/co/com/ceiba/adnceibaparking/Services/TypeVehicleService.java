package co.com.ceiba.adnceibaparking.Services;

import co.com.ceiba.adnceibaparking.Models.Constants;
import co.com.ceiba.adnceibaparking.Models.Response;
import co.com.ceiba.adnceibaparking.Models.TypeVehicle;
import co.com.ceiba.adnceibaparking.Repositories.TypeVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeVehicleService {

    @Autowired
    TypeVehicleRepository typeVehicleRepository;

    public Response<List<TypeVehicle>> getAllVehicleTypes() {
        List<TypeVehicle> typeVehicleList = typeVehicleRepository.findAll();

        if(typeVehicleList.size() < 1) {
            return new Response<List<TypeVehicle>>(Constants.NO_VEHICLE_TYPES_REGISTERED);
        } else {
            return new Response<List<TypeVehicle>>(Constants.SUCCESS, typeVehicleList);
        }
    }

    public Response<List<TypeVehicle>> registerTypeVehicle(TypeVehicle typeVehicle){
        this.typeVehicleRepository.insert(typeVehicle);
        return new Response<List<TypeVehicle>>(Constants.SUCCESS);
    }

    public Response<Object> deleteTypeVehicle(String code){
        TypeVehicle typeVehicle = this.typeVehicleRepository.findByCode(code);

        if(typeVehicle != null){
            String codeFound = typeVehicle.getCode();
            if(codeFound.equals(code)) {
                this.typeVehicleRepository.delete(typeVehicle);
                return new Response<Object>(Constants.VEHICLE_TYPES_DELETED);
            }
        }
        return new Response<Object>(Constants.VEHICLE_TYPE_NOT_EXISTS);
    }
}
