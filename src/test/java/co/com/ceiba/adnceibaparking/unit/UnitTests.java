package co.com.ceiba.adnceibaparking.unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import co.com.ceiba.adnceibaparking.Models.TypeVehicle;
import co.com.ceiba.adnceibaparking.exceptions.GeneralException;
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
    public void testShouldAddExtraPaymentToHighCylinderVehicle() {
        // Arrange
        Vehicle vehicle = new Vehicle(

                2,
                "ABC123",
                "Eduardo López",
                750,
                "08/01/2019 08:03:38",
                "Moto"
        );

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
    public void testShouldReturnVehicleCannotParkInMondayAndSunday() {
        // Arrange
        String plate = "AAA123";

        // Act
        boolean cannot = vehicleService.vehicleCanPark(plate);

        // Assert
        if (DateConverter.getCurrentDayOfWeek() == Constants.SUNDAY || DateConverter.getCurrentDayOfWeek() == Constants.MONDAY) {
            assertTrue(cannot);
        } else {
            assertFalse(cannot);
        }
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

    @Test
    public void testInsertVehicleFailureByPlateRule() throws GeneralException {
        // Arrange
        String expected = Constants.VEHICLE_CANNOT_ENTER;
        Vehicle vehicle = new Vehicle(2,"ABC123","Eduardo López",750,"08/01/2019 08:03:38","Moto");

        // Act
        try {
            when(vehicleRepository.insert(vehicle)).thenReturn(vehicle);
            Response<List<Vehicle>> response = vehicleService.registerVehicle(vehicle);

        } catch (GeneralException e) {
            // Assert
            assertEquals(e.getMessage(), expected);
        }
    }


    static Response<List<Vehicle>> getVehicles(List<Vehicle> vehicleList) {
        vehicleList.add(new Vehicle(1,"EXT567","Eduardo López",0,"09/01/2019 18:03:38","Carro"));
        vehicleList.add(new Vehicle(2,"III999","Pepe Cabral",750,"08/01/2019 08:03:38","Moto"));
        vehicleList.add(new Vehicle(2,"QQQ111","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        return new Response<List<Vehicle>>(Constants.SUCCESS, vehicleList);
    }

    static Response<List<TypeVehicle>> getTypeVehicles(List<TypeVehicle> typeVehicleList) {
        typeVehicleList.add(new TypeVehicle("1","Carro"));
        typeVehicleList.add(new TypeVehicle("2","Moto"));
        return new Response<List<TypeVehicle>>(Constants.SUCCESS, typeVehicleList);
    }

}
