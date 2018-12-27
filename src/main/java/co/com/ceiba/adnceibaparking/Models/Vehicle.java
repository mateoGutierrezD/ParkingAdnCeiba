package co.com.ceiba.adnceibaparking.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Vehicles")
public class Vehicle {

    @Id
    private String id;
    private int typeVehicleCode;
    private String plate;
    private String owner;
    private int cylinder;
    private String dateIn;
    private String typeVehicleDescription;

    public Vehicle() {
    }

    public Vehicle(int typeVehicleCode, String plate, String owner, int cylinder, String dateIn, String typeVehicleDescription) {
        this.typeVehicleCode = typeVehicleCode;
        this.plate = plate;
        this.owner = owner;
        this.cylinder = cylinder;
        this.dateIn = dateIn;
        this.typeVehicleDescription = typeVehicleDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTypeVehicleCode() {
        return typeVehicleCode;
    }

    public void setTypeVehicleCode(int typeVehicleCode) {
        this.typeVehicleCode = typeVehicleCode;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getCylinder() {
        return cylinder;
    }

    public void setCylinder(int cylinder) {
        this.cylinder = cylinder;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getTypeVehicleDescription() {
        return typeVehicleDescription;
    }

    public void setTypeVehicleDescription(String typeVehicleDescription) {
        this.typeVehicleDescription = typeVehicleDescription;
    }
}
