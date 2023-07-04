package airTravel;

import airTravel.strategies.IDataStrategy;
import airTravel.strategies.System_LOTY1;
import airTravel.strategies.System_LOTY2;
import airTravel.strategies.System_flights;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println();

        DataSystem dataSystem = new DataSystem();
        Utilities utilities = new Utilities();
        ScannerWork scannerWork = new ScannerWork();

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


        IDataStrategy flights = new System_flights();
        dataSystem.setIDataStrategy(flights);
        List<AirTravel> airTravelsFlights = dataSystem.getAirTravels();
        System.out.println("System: flights");
        airTravelsFlights.forEach(System.out::println);
        System.out.println();

        Set<AirTravel> allUniqueAirTravels = new HashSet<>(airTravelsLOTY1);
        allUniqueAirTravels.addAll(airTravelsLOTY2);
        allUniqueAirTravels.addAll(airTravelsFlights);

        List<String> uniqueAirTravelNummbers = utilities.getUniqueAirTravelNummbers(allUniqueAirTravels);
        Map<String, List<AirTravel>> mapNummbers_AirTravels = utilities.getMap_Nummber_AirTravels(uniqueAirTravelNummbers, allUniqueAirTravels);
        List<String> nummberWithMistakes = new ArrayList<>(mapNummbers_AirTravels.keySet());
        nummberWithMistakes.sort(String::compareTo);
        Map<Character, String> mapChooseNummber = utilities.getChooseMap(nummberWithMistakes);
        boolean flag = false;
        do {
            System.out.println("Wybierz: a) wyszukiwanie błędów, q) koniec");
            boolean stop = false;
            char sign;
            do {
                sign = scannerWork.getChar();
                if (sign == 'a' || sign == 'q'){
                    stop = true;
                }else {
                    System.err.println("Bad choice! Try again.");
                }
            } while (!stop);
            if (sign == 'a'){
                System.out.println("Wybierz nr lotu do sprawdzenia: a, b, c... = ?");
                Map<Character, String> chooseNummber = utilities.getChooseMap(nummberWithMistakes);
                chooseNummber.forEach((k, v) -> System.out.println(k + ") " + v));
                char choosenNummber = scannerWork.getCharFromRange(nummberWithMistakes);
                String nummber = mapChooseNummber.get(choosenNummber);
                System.out.println("AirTravel nr: " + nummber);
                List<AirTravel> airTravelListWithMistakesForTheNummber = mapNummbers_AirTravels.get(nummber);
                List<Reservation> result = utilities.getDifferentReservationNummbers(airTravelListWithMistakesForTheNummber);
                result.forEach(System.out::println);
                System.out.println();
            }
            if (sign == 'q') {
                flag = true;
            }
        } while (!flag);
    }
}
