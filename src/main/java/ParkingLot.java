import java.util.HashSet;
import java.util.Set;

public class ParkingLot {
    private final int capacity;

    private final Set<ParkingLotObserver> parkingLotObservers;

    private final Set<Car> parkedCars;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        parkedCars = new HashSet<>();
        parkingLotObservers = new HashSet<>();
    }

    public void park(Car car) throws ParkingLotFullException, CarAlreadyParkedException {
        if (capacity == parkedCars.size()) {
            throw new ParkingLotFullException();
        }
        if (parkedCars.contains(car)) {
            throw new CarAlreadyParkedException();
        }

        parkedCars.add(car);

        if (capacity == parkedCars.size()) {
            parkingLotObservers.forEach(ParkingLotObserver::notifyParkingFull);
        }

    }

    public void unPark(Car car) throws ParkingLotEmptyException, CarNotParkedException {
        if (capacity == 0) {
            throw new ParkingLotEmptyException();
        }
        if (!parkedCars.contains(car)) {
            throw new CarNotParkedException();
        }
        parkedCars.remove(car);

        if (parkedCars.size() == capacity - 1) {
            parkingLotObservers.forEach(ParkingLotObserver::notifyParkingAvailable);
        }
    }

    public boolean isParked(Car car) {
        return parkedCars.contains(car);
    }

    public void register(ParkingLotObserver parkingLotObserver) {
        this.parkingLotObservers.add(parkingLotObserver);
    }
}
