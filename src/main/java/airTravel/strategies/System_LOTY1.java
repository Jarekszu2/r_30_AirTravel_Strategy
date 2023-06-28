package airTravel.strategies;

import airTravel.AirTravel;
import airTravel.Reservation;
import airTravel.Utilities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class System_LOTY1 implements IDataStrategy {
    public List<AirTravel> getAirTravelsList() {

        Utilities utilities = new Utilities();
        List<AirTravel> airTravels = new ArrayList<>();

        // pobieram dane z pliku (linie tekstu) jako kolejne elementy listy
        List<String> listFromFile = utilities.getListStringFromEntireFile("src/main/java/airTravel/data/LOTY1");

        // sprawdzam i usuwam jak są puste elementy
        for (int i = 0; i < listFromFile.size(); i++) {
            if (listFromFile.get(i).split(";").length < 2) {
                listFromFile.remove(i);
                i--;
            }
        }

        for (int i = 0; i < listFromFile.size(); i++) {

            String[] strings = listFromFile.get(i).split(";");

            // ponieważ dla niektórych AirTravel nie ma kompletu danych,
            // sprawdzam kompletność, gdy czegoś brakuje, uzupełniam null'em
            if(strings.length <6) {
                if (strings[4].charAt(0) >= 'A' && strings[4].charAt(0) <= 'Z'){    // brak firstClassNummbers
                    String[] temp = new String[6];
                    for (int j = 0; j < 3; j++) {
                        temp[j] = strings[j];
                        temp[3] = null;
                        temp[4] = strings[3];
                        temp[5] = strings[4];
                    }
                    strings = temp;
                } else {    // brak listReservation
                    String[] temp = new String[6];
                    for (int j = 0; j < 5; j++) {
                        temp[j] = strings[j];
                        temp[5] = null;
                    }
                    strings = temp;
                }
            }

            AirTravel airTravel = new AirTravel();

            airTravel.setNumber(strings[0]);

            long longTimestampDeparture = Long.valueOf(strings[1]);
            Timestamp timestampDeparture = new Timestamp(longTimestampDeparture);
            airTravel.setDepartureTime(timestampDeparture);

            long longTimestampArrival = Long.parseLong(strings[2]);
            Timestamp timestampArrival = new Timestamp(longTimestampArrival);
            airTravel.setArrivalTime(timestampArrival);

            if (strings[3] != null) {
                String[] tabFirstClass = strings[3].split("%");
                setListIntegerToAirTravel(airTravel, tabFirstClass, 1);
            } else {
                airTravel.setFirstClassNummbers(null);
            }

            String[] tabSecondClass = strings[4].split("%");
//            List<String> stringsSecondClassNummbers = new ArrayList<>(Arrays.asList(tabSecondClass));
            setListIntegerToAirTravel(airTravel, tabSecondClass, 2);
//            ArrayList secondClassNummbers = new ArrayList<>();
//            stringsSecondClassNummbers.forEach(s -> secondClassNummbers.add(Integer.valueOf(s)));
//            airTravel.setSecondClassNummbers(secondClassNummbers);

            if (strings[5] != null){
                List<Reservation> reservationList = new ArrayList<>();
                String[] tabString_Reservations = strings[5].split("%");
                setListReservationToAirTravel(airTravel, reservationList, tabString_Reservations, "#");
            }

            airTravels.add(airTravel);
        }

        airTravels.sort(Comparator.comparing(AirTravel::getNumber));

        return airTravels;
    }

    public static void setListReservationToAirTravel(AirTravel airTravel, List<Reservation> reservationList, String[] tabString_Reservations, String regex) {
        List<String> listString_Reservations = new ArrayList<>(Arrays.asList(tabString_Reservations));
        for (int j = 0; j < listString_Reservations.size(); j++) {
            String[] reservations = listString_Reservations.get(j).split(regex);
            Reservation reservation = new Reservation();
            reservation.setName(reservations[0]);
            reservation.setPlaceNummber(Integer.valueOf(reservations[1]));
            reservation.setReservationNummber(reservations[2]);

            reservationList.add(reservation);
        }
        airTravel.setReservations(reservationList);
    }

    public static void setListIntegerToAirTravel(AirTravel airTravel, String[] tabPlaceNummbers, int classNummber) {
        List<String> stringsFirstClassNummbers = new ArrayList<>(Arrays.asList(tabPlaceNummbers));
        ArrayList classNummbers = new ArrayList<>();
        stringsFirstClassNummbers.forEach(s -> classNummbers.add(Integer.valueOf(s)));
        if (classNummber == 1) {
            airTravel.setFirstClassNummbers(classNummbers);
        } else {
            airTravel.setSecondClassNummbers(classNummbers);
        }
    }
}
