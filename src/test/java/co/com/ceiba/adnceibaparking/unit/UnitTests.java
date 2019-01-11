package co.com.ceiba.adnceibaparking.unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import co.com.ceiba.adnceibaparking.Models.TypeVehicle;
import co.com.ceiba.adnceibaparking.exceptions.GeneralException;
import co.com.ceiba.adnceibaparking.exceptions.NumberMaxVehicles;
import co.com.ceiba.adnceibaparking.exceptions.PlateForDay;
import co.com.ceiba.adnceibaparking.exceptions.VehicleRegisteredPreviously;
import co.com.ceiba.adnceibaparking.utilities.DateConverter;
import co.com.ceiba.adnceibaparking.utilities.Utils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import co.com.ceiba.adnceibaparking.Models.Constants;
import co.com.ceiba.adnceibaparking.Models.Response;
import co.com.ceiba.adnceibaparking.Models.Vehicle;
import co.com.ceiba.adnceibaparking.repositories.TypeVehicleRepository;
import co.com.ceiba.adnceibaparking.repositories.VehicleRepository;
import co.com.ceiba.adnceibaparking.services.TypeVehicleService;
import co.com.ceiba.adnceibaparking.services.VehicleService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.Silent.class)
public class UnitTests {

    @Rule
    public ExpectedException exceptions = ExpectedException.none();

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    VehicleService vehicleService;

    @InjectMocks
    TypeVehicleService typeVehicleService;

    @Mock
    VehicleRepository vehicleRepository;

    @Mock
    TypeVehicleRepository typeVehicleRepository;

    // TEST CASES

    @Test
    public void testShouldListVehicles() {
        // Arrange
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        getVehicles(vehicles);

        // Act
        when(vehicleRepository.findAll()).thenReturn(vehicles);
        Response<List<Vehicle>> response = vehicleService.getAllVehicles();

        // Assert
        assertFalse(response.getData().isEmpty());
    }

    @Test
    public void testShouldReturnMessageWhenParkingInEmpty() {
        // Arrange
        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        // Act
        when(vehicleRepository.findAll()).thenReturn(vehicles);
        Response<List<Vehicle>> response = vehicleService.getAllVehicles();

        // Assert
        assertEquals(response.getMessage(), Constants.NO_VEHICLES_IN_PARKING);
    }

    @Test
    public void testShouldListTypeVehicles() {
        // Arrange
        List<TypeVehicle> typeVehicles = new ArrayList<TypeVehicle>();
        getTypeVehicles(typeVehicles);

        // Act
        when(typeVehicleRepository.findAll()).thenReturn(typeVehicles);
        Response<List<TypeVehicle>> response = typeVehicleService.getAllVehicleTypes();

        // Assert
        assertFalse(response.getData().isEmpty());
    }

    @Test
    public void testShouldReturnMessageWhenDontThereAreVehicleTypes() {
        // Arrange
        List<TypeVehicle> typeVehicles = new ArrayList<TypeVehicle>();

        // Act
        when(typeVehicleRepository.findAll()).thenReturn(typeVehicles);
        Response<List<TypeVehicle>> response = typeVehicleService.getAllVehicleTypes();

        // Assert
        assertEquals(response.getMessage(), Constants.NO_VEHICLE_TYPES_REGISTERED);
    }

    @Test
    public void testInsertTypeVehicleSucessfully() {
        // Arrange
        String expected = Constants.SUCCESS;
        TypeVehicle typeVehicle = new TypeVehicle("1","Carro");

        // Act
        when(typeVehicleRepository.insert(typeVehicle)).thenReturn(typeVehicle);
        Response<List<TypeVehicle>> response = typeVehicleService.registerTypeVehicle(typeVehicle);
        String message = response.getMessage();

        // Assert
        assertEquals(message, expected);
    }

    @Test
    public void testDeleteTypeVehicleSucessfully() {
        TypeVehicle typeVehicle = new TypeVehicle("1","Carro");

        when(typeVehicleRepository.findByCode(typeVehicle.getCode())).thenReturn(typeVehicle);
        Response<Object> response = typeVehicleService.deleteTypeVehicle(typeVehicle.getCode());

        assertEquals(response.getMessage(), Constants.VEHICLE_TYPES_DELETED);
    }

    @Test
    public void testValidateCodeTypeVehicleIsFound() {
        // Arrange
        String codeExpected = "1";
        TypeVehicle typeVehicle = new TypeVehicle("1","Carro");

        // Act
        when(typeVehicleRepository.findByCode(typeVehicle.getCode())).thenReturn(typeVehicle);
        Response<Object> response = typeVehicleService.deleteTypeVehicle(typeVehicle.getCode());

        // Assert
        assertEquals(codeExpected, typeVehicle.getCode());
    }

    @Test
    public void testDeleteTypeVehicleFailedBecauseNoExists() {

        TypeVehicle typeVehicle = new TypeVehicle("1","Carro");

        when(typeVehicleRepository.findByCode(typeVehicle.getCode())).thenReturn(null);
        Response<Object> response = typeVehicleService.deleteTypeVehicle(typeVehicle.getCode());

        assertEquals(response.getMessage(), Constants.VEHICLE_TYPE_NOT_EXISTS);
    }

    @Test
    public void testDeleteVehicleFailedBecauseIsNotInParking() throws ParseException {
        // Arrange
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(null);
        Response<Object> response = vehicleService.deleteVehicle(vehicle.getPlate());

        // Assert
        assertEquals(response.getMessage(), Constants.VEHICLE_NOT_IN_PARKING);

    }

    @Test
    public void testDeleteMotorcycleSucessfully() throws ParseException {
        // Arrange
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(vehicle);
        Response<Object> response = vehicleService.deleteVehicle(vehicle.getPlate());

        // Assert
        assertEquals(response.getMessage(), Constants.VEHICLE_DELETED);
    }

    @Test
    public void testDeleteCarSucessfully() throws ParseException {
        // Arrange
        Vehicle vehicle = new Vehicle(1,"TTT678","Eduardo López",750,"08/01/2019 08:03:38","Carro");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(vehicle);
        Response<Object> response = vehicleService.deleteVehicle(vehicle.getPlate());

        // Assert
        assertEquals(response.getMessage(), Constants.VEHICLE_DELETED);
    }

    @Test
    public void testGet500InPaymentBillMotorycleWhenIsOneHour() throws ParseException {
        // Arrange
        Date date = DateConverter.addOrSubtractHoursToDate(new Date(), -1);
        String dateIn = DateConverter.convertDateToString(date);
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",100, dateIn,"Moto");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(vehicle);
        Response<Object> response = vehicleService.deleteVehicle(vehicle.getPlate());

        // Assert
        assertEquals(response.getData(), 500.0);
    }

    @Test
    public void testGet1000InPaymentBillCarWhenIsOneHour() throws ParseException {
        // Arrange
        Date date = DateConverter.addOrSubtractHoursToDate(new Date(), -1);
        String dateIn = DateConverter.convertDateToString(date);
        Vehicle vehicle = new Vehicle(1,"ABC123","Eduardo López",0, dateIn,"Carro");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(vehicle);
        Response<Object> response = vehicleService.deleteVehicle(vehicle.getPlate());

        // Assert
        assertEquals(response.getData(), 1000.0);
    }

    @Test
    public void testGet2500InPaymentBillMotorycleWhenIsOneHourAndIsHighCylinder() throws ParseException {
        // Arrange
        Date date = DateConverter.addOrSubtractHoursToDate(new Date(), -1);
        String dateIn = DateConverter.convertDateToString(date);
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",750, dateIn,"Moto");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(vehicle);
        Response<Object> response = vehicleService.deleteVehicle(vehicle.getPlate());

        // Assert
        assertEquals(response.getData(), 2500.0);
    }

    @Test
    public void testGet6000InPaymentBillMotorycleWhenIsOneDayAndIsHighCylinder() throws ParseException {
        // Arrange
        Date date = DateConverter.addOrSubtractHoursToDate(new Date(), -20);
        String dateIn = DateConverter.convertDateToString(date);
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",750, dateIn,"Moto");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(vehicle);
        Response<Object> response = vehicleService.deleteVehicle(vehicle.getPlate());

        // Assert
        assertEquals(response.getData(), 6000.0);
    }

    @Test
    public void testGet8000InPaymentBillCarWhenIsOneDay() throws ParseException {
        // Arrange
        Date date = DateConverter.addOrSubtractHoursToDate(new Date(), -20);
        String dateIn = DateConverter.convertDateToString(date);
        Vehicle vehicle = new Vehicle(1,"ABC123","Eduardo López",0, dateIn,"Carro");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(vehicle);
        Response<Object> response = vehicleService.deleteVehicle(vehicle.getPlate());

        // Assert
        assertEquals(response.getData(), 8000.0);
    }

    @Test
    public void testGet8000InPaymentBillMotorcycleWhenIsTwoDay() throws ParseException {
        // Arrange
        Date date = DateConverter.addOrSubtractHoursToDate(new Date(), -48);
        String dateIn = DateConverter.convertDateToString(date);
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",100, dateIn,"Moto");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(vehicle);
        Response<Object> response = vehicleService.deleteVehicle(vehicle.getPlate());

        // Assert
        assertEquals(response.getData(), 8000.0);
    }

    @Test
    public void testGet1000InPaymentBillMotorcycleWhenIsTwoDayAndIsHighCylinder() throws ParseException {
        // Arrange
        Date date = DateConverter.addOrSubtractHoursToDate(new Date(), -48);
        String dateIn = DateConverter.convertDateToString(date);
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",750, dateIn,"Moto");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(vehicle);
        Response<Object> response = vehicleService.deleteVehicle(vehicle.getPlate());

        // Assert
        assertEquals(response.getData(), 10000.0);
    }

    @Test
    public void testGet1000InPaymentBillCarWhenIsTwoDay() throws ParseException {
        // Arrange
        Date date = DateConverter.addOrSubtractHoursToDate(new Date(), -48);
        String dateIn = DateConverter.convertDateToString(date);
        Vehicle vehicle = new Vehicle(1,"ABC123","Eduardo López",0, dateIn,"Carro");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(vehicle);
        Response<Object> response = vehicleService.deleteVehicle(vehicle.getPlate());

        // Assert
        assertEquals(response.getData(), 16000.0);
    }

    @Test
    public void testShouldCalculateOneHourBetweenDateInAndDateOut() throws Exception{
        // Arrange
        double hourExpected = 1;
        String dateIn = "08/01/2019 08:03:38";
        String dateOut = "08/01/2019 09:03:38";
        Date date1 = DateConverter.convertStringToDate(dateIn);
        Date date2 = DateConverter.convertStringToDate(dateOut);
        long hoursIn = date1.getTime();
        long hoursOut = date2.getTime();

        // Act
        double hours = vehicleService.calculateHoursInParking(hoursOut, hoursIn);
        hours = Math.ceil(hours);

        // Assert
        assertEquals(hours, hourExpected, 1.0);
    }

    @Test
    public void testShouldAddExtraPaymentToHighCylinderVehicle() {
        // Arrange
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        boolean response = vehicleService.validateCylinder(vehicle.getCylinder());

        // Assert
        assertEquals(response,true);
    }

    @Test
    public void testShouldNotAddExtraPaymentToLowCylinderVehicle() {
        // Arrange
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",125,"08/01/2019 08:03:38","Moto");

        // Act
        boolean response = vehicleService.validateCylinder(vehicle.getCylinder());

        // Assert
        assertEquals(response,false);
    }

    @Test
    public void testShouldConvertNumberNegativeToPositive() {
        // Arrange
        double number = -1.0;

        // Act
        number = Utils.convertNegativeNumberToPositive(number);

        // Assert
        assertEquals(number, 1.0, 1);
    }

    @Test
    public void testShouldReturnTheSameNumberWhenIsPositiveTryingToConvert() {
        // Arrange
        double number = 1.0;

        // Act
        number = Utils.convertNegativeNumberToPositive(number);

        // Assert
        assertEquals(number, 1.0, 1);
    }

    @Test
    public void testShouldConvertDateAndReturnString() {
        // Arrange
        Date date = new Date();

        // Act
        String string  = DateConverter.convertDateToString(date);

        // Assert
        assertNotNull(string);
    }

    @Test
    public void testShouldReturnVehicleCannotParkInMondayAndSunday() {
        // Arrange
        String plate = "AAA123";

        // Act
        boolean can = vehicleService.vehicleCanPark(plate);

        // Assert
        assertFalse(can);
    }

    @Test
    public void testShouldReturnVehicleCanParkInMondayAndSunday() {
        // Arrange
        String plate = "BAA123";

        // Act
        boolean can = vehicleService.vehicleCanPark(plate);

        // Assert
        assertTrue(can);
    }

    @Test
    public void testShouldReturnVehicleIsNotAlreadyRegistered() {
        // Arrange
        String plate = "0";

        // Act
        boolean isIn = vehicleService.carIsAlreadyRegistered(plate);

        // Assert
        assertFalse(isIn);
    }

    @Test
    public void testShouldReturnSomeValueCalculatingCarPaymentBill() throws ParseException {
        // Arrange
        Vehicle vehicle = new Vehicle(1,"ABC123","Eduardo López",0,"08/01/2019 08:03:38","Carro");

        // Act
        double amount = vehicleService.calculateCarPaymentBill(vehicle);

        // Assert
        assertNotNull(amount);
    }

    @Test
    public void testShouldReturnSomeValueCalculatingMotorcyclePaymentBill() throws ParseException {
        // Arrange
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        double amount = vehicleService.calculateMotorcyclePaymentBill(vehicle);

        // Assert
        assertNotNull(amount);
    }

    @Test
    public void testShouldReturnCarParkingCapacityIsFull() {
        // Arrange
        Vehicle vehicle = new Vehicle(1,"ABC123","Eduardo López",0,"08/01/2019 08:03:38","Carro");

        // Act
        boolean maxCapacity = vehicleService.maxCapacity(vehicle.getTypeVehicleDescription(), 20);

        // Assert
        assertTrue(maxCapacity);
    }

    @Test
    public void testShouldReturnCarParkingCapacityIsNotFull() {
        Vehicle vehicle = new Vehicle(1,"ABC123","Eduardo López",0,"08/01/2019 08:03:38","Carro");

        // Act
        boolean maxCapacity = vehicleService.maxCapacity(vehicle.getTypeVehicleDescription(), 10);

        // Assert
        assertFalse(maxCapacity);
    }

    @Test
    public void testShouldReturnMotorcycleParkingCapacityIsFull() {
        // Arrange
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        boolean maxCapacity = vehicleService.maxCapacity(vehicle.getTypeVehicleDescription(), 10);

        // Assert
        assertTrue(maxCapacity);
    }

    @Test
    public void testShouldReturnMotorcycleParkingCapacityIsNotFull() {
        // Arrange
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        boolean maxCapacity = vehicleService.maxCapacity(vehicle.getTypeVehicleDescription(), 5);

        // Assert
        assertFalse(maxCapacity);

    }

    @Test
    public void testInsertVehicleSucessfully() throws GeneralException {
        // Arrange
        String expected = Constants.SUCCESS;
        Vehicle vehicle = new Vehicle(2,"EBC123","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        when(vehicleRepository.insert(vehicle)).thenReturn(vehicle);
        Response<List<Vehicle>> response = vehicleService.registerVehicle(vehicle);
        String message = response.getMessage();

        // Assert
        assertEquals(message, expected);
    }

    @Test(expected = PlateForDay.class)
    public void testInsertVehicleFailureByPlateRule() throws GeneralException {
        // Arrange
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        vehicleService.registerVehicle(vehicle);
    }

    @Test(expected = NumberMaxVehicles.class)
    public void testInsertVehicleFailureByMaxCapacity() throws GeneralException {
        // Arrange
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",750,"08/01/2019 08:03:38","Moto");
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        getListVehiclesWithFullCapacity(vehicles);

        // Act
        when(vehicleRepository.findAllByTypeVehicleDescription(vehicle.getTypeVehicleDescription())).thenReturn(vehicles);
        vehicleService.registerVehicle(vehicle);
    }

    @Test(expected = VehicleRegisteredPreviously.class)
    public void testInsertVehicleAlreadyInParking() throws GeneralException  {
        // Arrange
        Vehicle vehicle = new Vehicle(2,"EXT333","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        when(vehicleRepository.findByPlate("EXT333")).thenReturn(vehicle);
        vehicleService.registerVehicle(vehicle);
    }

    @Test
    public void testShouldReturnTrueWhenVehicleIsAlreadyInRegistered() {
        // Arrange
        Vehicle vehicle = new Vehicle(2,"EXT333","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(vehicle);
        boolean isIn = vehicleService.carIsAlreadyRegistered(vehicle.getPlate());

        // Assert
        assertTrue(isIn);
    }

    @Test
    public void testShouldReturnFalseWhenVehicleIsNotRegistered() {
        // Arrange
        Vehicle vehicle = new Vehicle(2,"EXT333","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        when(vehicleRepository.findByPlate(vehicle.getPlate())).thenReturn(null);
        boolean isIn = vehicleService.carIsAlreadyRegistered(vehicle.getPlate());

        // Assert
        assertFalse(isIn);
    }

    @Test
    public void testShoulGetCurrentDate() {
        // Act
        String date = DateConverter.getCurrentDate();

        // Assert
        assertNotNull(date);
    }

    @Test
    public void testShouldGetCurrentDayOfWeek() {
        // Act
        int day = DateConverter.getCurrentDayOfWeek();

        // Assert
        assertNotNull(day);
    }

    @Test
    public void testShouldConvertStringToDate() throws ParseException {
        // Arrange
        String dateString = "09/01/2019 18:03:38";

        // Act
        Date date = DateConverter.convertStringToDate(dateString);

        // Assert
        assertNotNull(date);
    }

    @Test
    public void testShouldGetVehicleOwner() {
        String nameExpected = "Eduardo López";
        Vehicle vehicle = new Vehicle(2,"EXT333","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        String owner = vehicle.getOwner();

        // Assert
        assertEquals(nameExpected, owner);
    }

    @Test
    public void testShouldGetVehicleTypeVehicleCode() {
        int codeExpected = 2;
        Vehicle vehicle = new Vehicle(2,"EXT333","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        int typeCode = vehicle.getTypeVehicleCode();

        // Assert
        assertEquals(codeExpected, typeCode);
    }

    @Test
    public void testShouldGetVehicleType() {
        String  typeExpected = "Carro";
        TypeVehicle typeVehicle = new TypeVehicle("1","Carro");
        // Act
        String typeCode = typeVehicle.getType();

        // Assert
        assertEquals(typeExpected, typeCode);
    }


    static Response<List<Vehicle>> getVehicles(List<Vehicle> vehicleList) {
        vehicleList.add(new Vehicle(1,"EXT567","Eduardo López",0,"09/01/2019 18:03:38","Carro"));
        vehicleList.add(new Vehicle(2,"III999","Pepe Cabral",750,"08/01/2019 08:03:38","Moto"));
        vehicleList.add(new Vehicle(2,"QQQ111","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        return new Response<List<Vehicle>>(Constants.SUCCESS, vehicleList);
    }

    static Response<List<Vehicle>> getListVehiclesWithFullCapacity(List<Vehicle> vehicleList) {
        vehicleList.add(new Vehicle(2,"1","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        vehicleList.add(new Vehicle(2,"2","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        vehicleList.add(new Vehicle(2,"3","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        vehicleList.add(new Vehicle(2,"4","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        vehicleList.add(new Vehicle(2,"5","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        vehicleList.add(new Vehicle(2,"6","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        vehicleList.add(new Vehicle(2,"7","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        vehicleList.add(new Vehicle(2,"8","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        vehicleList.add(new Vehicle(2,"9","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        vehicleList.add(new Vehicle(2,"10","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        return new Response<List<Vehicle>>(Constants.SUCCESS, vehicleList);
    }

    static Response<List<TypeVehicle>> getTypeVehicles(List<TypeVehicle> typeVehicleList) {
        typeVehicleList.add(new TypeVehicle("1","Carro"));
        typeVehicleList.add(new TypeVehicle("2","Moto"));
        return new Response<List<TypeVehicle>>(Constants.SUCCESS, typeVehicleList);
    }

}
