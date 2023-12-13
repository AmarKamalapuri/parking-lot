import java.util.HashSet;
import java.util.Set;

public class ParkingLot {
    private final int capacity;
    private int noOfCarsParked = 0;

    private Set<Car> parkedCars;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        parkedCars = new HashSet<>();
    }

    public void park(Car car) throws ParkingLotFullException, CarAlreadyParkedException {
        if (capacity == parkedCars.size()) {
            throw new ParkingLotFullException();
        }

        if (parkedCars.contains(car)) {
            throw new CarAlreadyParkedException();

        }
        parkedCars.add(car);

    }

    public void unPark(Car car) throws ParkingLotEmptyException, CarNotParkedException {
        if (capacity == 0) {
            throw new ParkingLotEmptyException();
        }
        if (!parkedCars.contains(car)) {
            throw new CarNotParkedException();
        }
        parkedCars.remove(car);


    }

    public boolean isParked(Car car) {
        return parkedCars.contains(car);
    }
}
