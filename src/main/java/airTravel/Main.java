package airTravel;

import airTravel.strategies.IDataStrategy;
import airTravel.strategies.System_LOTY1;
import airTravel.strategies.System_LOTY2;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println();

        DataSystem dataSystem = new DataSystem();

        IDataStrategy loty1 = new System_LOTY1();
        dataSystem.setIDataStrategy(loty1);
        List<AirTravel> airTravelsLOTY1 = dataSystem.getAirTravels();
        System.out.println("System: LOTY1");
        airTravelsLOTY1.forEach(System.out::println);
        System.out.println();


        IDataStrategy loty2 = new System_LOTY2();
        dataSystem.setIDataStrategy(loty2);
        List<AirTravel> airTravelsLOTY2 = dataSystem.getAirTravels();
        System.out.println("System: LOTY2");
        airTravelsLOTY2.forEach(System.out::println);
        System.out.println();



    }
}
