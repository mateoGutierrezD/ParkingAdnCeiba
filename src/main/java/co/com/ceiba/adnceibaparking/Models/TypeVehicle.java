package co.com.ceiba.adnceibaparking.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TypesVehicle")
public class TypeVehicle {

    @Id
    private String id;
    private String code;
    private String type;

    public TypeVehicle(String code, String type) {
        this.code = code;
        this.type = type;
    }

    public TypeVehicle() {
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
