package airTravel.strategies;

import airTravel.AirTravel;
import airTravel.Reservation;
import airTravel.Utilities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class System_LOTY2 implements IDataStrategy {
    @Override
    public List<AirTravel> getAirTravelsList() {
        Utilities utilities = new Utilities();
        List<AirTravel> airTravelList = new ArrayList<>();

        List<List<String>> mainList = utilities.getListStringFromSomeLinesFromFile("src/main/java/airTravel/data/LOTY2");

        if (mainList.get(15).get(5).isEmpty()){
            System.out.println("Empty");
        }

        for (List<String> stringList : mainList) {
            AirTravel airTravel = new AirTravel();

            airTravel.setNumber(stringList.get(0));

            setTimestampToAirTravel(stringList, airTravel, 1);

            setTimestampToAirTravel(stringList, airTravel, 2);

            String[] stringFirstClass = stringList.get(3).split(" ");
            System_LOTY1.setListIntegerToAirTravel(airTravel, stringFirstClass, 1);

            String[] stringSecondClass = stringList.get(4).split(" ");
            System_LOTY1.setListIntegerToAirTravel(airTravel, stringSecondClass, 2);

            if (stringList.get(5).split("").length > 1){
                List<Reservation> reservationList = new ArrayList<>();
                String[] tabString_Reservations = stringList.get(5).split(" ");
                System_LOTY1.setListReservationToAirTravel(airTravel, reservationList, tabString_Reservations, ",");
            } else {
                airTravel.setReservations(null);
            }

            airTravelList.add(airTravel);
        }

        airTravelList.sort(Comparator.comparing(AirTravel::getNumber));
        return airTravelList;
    }

    private void setTimestampToAirTravel(List<String> stringList, AirTravel airTravel, int i) {
        long longTime = Long.parseLong(stringList.get(i));
        if (i == 1) {
            airTravel.setDepartureTime(new Timestamp(longTime));
        } else {
            airTravel.setArrivalTime(new Timestamp(longTime));
        }
    }
}
