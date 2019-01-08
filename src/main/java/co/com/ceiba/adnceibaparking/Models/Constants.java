package co.com.ceiba.adnceibaparking.Models;

public class Constants {

    public static final String CAR = "Carro";
    public static final String MOTORCYCLE = "Moto";

    public static final int CAR_DAY_PRICE = 8000;
    public static final int CAR_HOUR_PRICE = 1000;
    public static final int MOTORCYCLE_DAY_PRICE = 4000;
    public static final int MOTORCYCLE_HOUR_PRICE = 500;
    public static final int MOTORCYCLE_EXTRA_PRICE = 2000;
    public static final int MOTORCYCLE_CC_RULE = 500;

    public static final String SUCCESS = "Hecho!";

    public static final String NO_VEHICLE_TYPES_REGISTERED = "No hay tipos de vehículos registrados";
    public static final String VEHICLE_TYPE_NOT_EXISTS = "Este tipo de vehículo no existe.";
    public static final String VEHICLE_TYPES_DELETED = "Tipo de vehículo eliminado!";

    public static final String NO_VEHICLES_IN_PARKING = "No hay vehículos en el parqueadero";
    public static final String VEHICLE_IN_PARKING = "Este vehículo ya está en el parqueadero";

    public static final String LETTER_START_RULE = "A";
    public static final String VEHICLE_CANNOT_ENTER = "Este vehículo no está autorizado para ingresar; sólo puede hacerlo los días domingo y lunes";
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;

    public static final String VEHICLE_ENTERED = "Vehículo registrado!";
    public static final String VEHICLE_DELETED = "Vehículo eliminado!";

    public static final String VEHICLE_NOT_IN_PARKING = "Este vehículo no está en el parqueadero";
    public static final String DATE_FORMAT = "dd/MM/yyyy hh:mm:ss a";
    public static final String MAX_NUMBER_VEHICLES = "El parqueadero está lleno";

    public static final int MAX_NUMBER_CARS = 20;
    public static final int MAX_NUMBER_MOTORCYCLES = 10;

    public static final int DAY_BEGIN_HOUR = 9;
    public static final int DAY_FINISH_HOUR = 24;
    public static final int TIME_UNITY = 60;

}
