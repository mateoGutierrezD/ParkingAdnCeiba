package co.com.ceiba.adnceibaparking.Repositories;

import co.com.ceiba.adnceibaparking.Models.TypeVehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeVehicleRepository extends MongoRepository<TypeVehicle, String > {

    public void deleteByCode(String code);
}
