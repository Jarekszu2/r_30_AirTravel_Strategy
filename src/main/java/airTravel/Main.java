package airTravel;

import airTravel.strategies.IDataStrategy;
import airTravel.strategies.System_LOTY1;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println();

        DataSystem dataSystem = new DataSystem();

        IDataStrategy loty1 = new System_LOTY1();
        dataSystem.setIDataStrategy(loty1);
        List<AirTravel> airTravels = dataSystem.getAirTravels();
        airTravels.forEach(System.out::println);
        System.out.println();
    }
}
