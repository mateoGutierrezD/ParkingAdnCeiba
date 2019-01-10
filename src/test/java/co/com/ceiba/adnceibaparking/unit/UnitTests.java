package co.com.ceiba.adnceibaparking.unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import co.com.ceiba.adnceibaparking.Utilities.DateConverter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import co.com.ceiba.adnceibaparking.Exceptions.NumberMaxVehicles;
import co.com.ceiba.adnceibaparking.Exceptions.PlateForDay;
import co.com.ceiba.adnceibaparking.Exceptions.VehicleRegisteredPreviously;
import co.com.ceiba.adnceibaparking.Models.Constants;
import co.com.ceiba.adnceibaparking.Models.Response;
import co.com.ceiba.adnceibaparking.Models.TypeVehicle;
import co.com.ceiba.adnceibaparking.Models.Vehicle;
import co.com.ceiba.adnceibaparking.Repositories.TypeVehicleRepository;
import co.com.ceiba.adnceibaparking.Repositories.VehicleRepository;
import co.com.ceiba.adnceibaparking.Services.TypeVehicleService;
import co.com.ceiba.adnceibaparking.Services.VehicleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public void testShouldNotAddExtraPaymentToLOWCylinderVehicle() {
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
        assertNotNull(response.getData());
    }

    @Test
    public void testShouldCalculateOneHourBetweenDateInAndDateOut() {
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

    static Response<List<Vehicle>> getVehicles(List<Vehicle> vehicleList) {
        vehicleList.add(new Vehicle(1,"ZZZ000","Eduardo López",0,"09/01/2019 18:03:38","Carro"));
        vehicleList.add(new Vehicle(2,"III999","Pepe Cabral",750,"08/01/2019 08:03:38","Moto"));
        vehicleList.add(new Vehicle(2,"QQQ111","Ramiro Castrillón",100,"09/01/2019 15:03:38","Moto"));
        return new Response<List<Vehicle>>(Constants.SUCCESS, vehicleList);
    }

}
