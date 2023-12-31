import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    @Test
    void shouldThrowParkingFullExceptionWithZeroCapacityParkingLot() {
        ParkingLot parkingLot = new ParkingLot(0);

        Car car = new Car();

        assertThrows(ParkingLotFullException.class, () -> parkingLot.park(car));
    }

    @Test
    public void shouldBeAbleToParkCarWithNonZeroParkingCapacity() throws ParkingLotFullException, CarAlreadyParkedException {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();

        parkingLot.park(car);
    }

    @Test
    void shouldThrowExceptionOnlyWhenCapacityIsFull() throws ParkingLotFullException, CarAlreadyParkedException {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();
        parkingLot.park(car);

        assertThrows(ParkingLotFullException.class, () -> parkingLot.park(car));
    }

    @Test
    void shouldThrowsExceptionWhileUnParkingWhenCapacityIsZero() {
        ParkingLot parkingLot = new ParkingLot(0);

        Car car = new Car();

        assertThrows(ParkingLotEmptyException.class, () -> parkingLot.unPark(car));
    }

    @Test
    void shouldBeAbleToUnparkCar() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();

        assertThrows(CarNotParkedException.class, () -> parkingLot.unPark(car));
    }

    @Test
    void shouldBeAbleToParkAndUnParkCarWithoutException() throws ParkingLotFullException, ParkingLotEmptyException, CarNotParkedException, CarAlreadyParkedException {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();
        parkingLot.park(car);
        parkingLot.unPark(car);

    }

    @Test
    void shouldNotBeAbleToUnparkCarTwice() throws ParkingLotFullException, ParkingLotEmptyException, CarNotParkedException, CarAlreadyParkedException {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();
        parkingLot.park(car);
        parkingLot.unPark(car);
        assertThrows(CarNotParkedException.class, () -> parkingLot.unPark(car));

    }

    @Test
    void shouldThrowExceptionWhenSameCarIsBeingParkedAgain() throws ParkingLotFullException, CarAlreadyParkedException {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = new Car();
        parkingLot.park(car);

        assertThrows(CarAlreadyParkedException.class, () -> parkingLot.park(car));
    }

    @Test
    void shouldTellIfCarIsParkedOrNot() throws ParkingLotFullException, CarAlreadyParkedException {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();
        parkingLot.park(car);

        assertTrue(parkingLot.isParked(car));
    }

    @Test
    void shouldTellIfCarIsParkedOrNotIfUnParked() throws ParkingLotFullException, CarAlreadyParkedException, ParkingLotEmptyException, CarNotParkedException {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();
        parkingLot.park(car);
        parkingLot.unPark(car);

        assertFalse(parkingLot.isParked(car));
    }

    @Test
    void shouldGetNotifiedWhenParkingLotBecomeFull() throws ParkingLotFullException, CarAlreadyParkedException {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();
        ParkingLotObserver parkingLotObserver = Mockito.mock(ParkingLotObserver.class);

        parkingLot.register(parkingLotObserver);

        parkingLot.park(car);

        Mockito.verify(parkingLotObserver, Mockito.times(1)).notifyParkingFull();

    }

    @Test
    void shouldGetNotifiedWhenParkingLotBecomeAvailable() throws ParkingLotFullException, CarAlreadyParkedException, ParkingLotEmptyException, CarNotParkedException {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();
        ParkingLotObserver parkingLotObserver = Mockito.mock(ParkingLotObserver.class);

        parkingLot.register(parkingLotObserver);
        parkingLot.park(car);
        parkingLot.unPark(car);

        Mockito.verify(parkingLotObserver, Mockito.times(1)).notifyParkingAvailable();

    }

    @Test
    void allObserversShouldGetNotifiedWhenParkingLotBecomeFull() throws ParkingLotFullException, CarAlreadyParkedException {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();
        ParkingLotObserver owner = Mockito.mock(ParkingLotObserver.class);
        ParkingLotObserver trafficCop = Mockito.mock(ParkingLotObserver.class);

        parkingLot.register(owner);
        parkingLot.register(trafficCop);

        parkingLot.park(car);

        Mockito.verify(owner, Mockito.times(1)).notifyParkingFull();
        Mockito.verify(trafficCop, Mockito.times(1)).notifyParkingFull();

    }

    @Test
    void allObserversShouldGetNotifiedWhenParkingLotBecomeAvailable() throws ParkingLotFullException, CarAlreadyParkedException, ParkingLotEmptyException, CarNotParkedException {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();
        ParkingLotObserver owner = Mockito.mock(ParkingLotObserver.class);
        ParkingLotObserver trafficCop = Mockito.mock(ParkingLotObserver.class);

        parkingLot.register(owner);
        parkingLot.register(trafficCop);

        parkingLot.park(car);
        parkingLot.unPark(car);

        Mockito.verify(owner, Mockito.times(1)).notifyParkingAvailable();
        Mockito.verify(trafficCop, Mockito.times(1)).notifyParkingAvailable();

    }

    @Test
    void allObserversShouldGetNotifiedOnlyOnceWhenParkingLotBecomeAvailable() throws ParkingLotFullException, CarAlreadyParkedException, ParkingLotEmptyException, CarNotParkedException {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = new Car();
        Car car1 = new Car();
        ParkingLotObserver owner = Mockito.mock(ParkingLotObserver.class);
        ParkingLotObserver trafficCop = Mockito.mock(ParkingLotObserver.class);

        parkingLot.register(owner);
        parkingLot.register(trafficCop);

        parkingLot.park(car);
        parkingLot.park(car1);

        parkingLot.unPark(car);
        parkingLot.unPark(car1);

        Mockito.verify(owner, Mockito.times(1)).notifyParkingAvailable();
        Mockito.verify(trafficCop, Mockito.times(1)).notifyParkingAvailable();

    }
}
