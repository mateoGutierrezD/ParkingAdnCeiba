package co.com.ceiba.adnceibaparking.unit;

import co.com.ceiba.adnceibaparking.Models.Vehicle;
import co.com.ceiba.adnceibaparking.Repositories.TypeVehicleRepository;
import co.com.ceiba.adnceibaparking.Repositories.VehicleRepository;
import co.com.ceiba.adnceibaparking.Services.TypeVehicleService;
import co.com.ceiba.adnceibaparking.Services.VehicleService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UnitTests {

    @Rule
    public ExpectedException exceptions = ExpectedException.none();

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
        Vehicle vehicle = new Vehicle(

                2,
                "ABC123",
                "Eduardo López",
                750,
                "08/01/2019 08:03:38",
                "Moto"
        );

        boolean response = vehicleService.validateCylinder(vehicle.getCylinder());

        assertEquals(response,true);
    }
}
