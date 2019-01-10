package co.com.ceiba.adnceibaparking.repositories;

import co.com.ceiba.adnceibaparking.Models.TypeVehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeVehicleRepository extends MongoRepository<TypeVehicle, String > {

    public TypeVehicle findByCode(String code);
}
