package co.com.ceiba.adnceibaparking.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TypesVehicle")
public class TypeVehicle {

    @Id
    private String id;
    private String code;
    private String type;
    private int maxCapacityVehicles;

    public TypeVehicle(String code, String type, int maxCapacityVehicles) {
        this.code = code;
        this.type = type;
        this.maxCapacityVehicles = maxCapacityVehicles;
    }

    public TypeVehicle() {
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public int getMaxCapacityVehicles() {
        return maxCapacityVehicles;
    }
}
