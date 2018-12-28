package co.com.ceiba.adnceibaparking.Repositories;

import co.com.ceiba.adnceibaparking.Models.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    public Vehicle findByPlate(String plate);
}
