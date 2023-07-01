package airTravel.strategies;

import airTravel.AirTravel;
import airTravel.Utilities;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class System_flights implements IDataStrategy {
    @Override
    public List<AirTravel> getAirTravelsList() {
        List<AirTravel> airTravelList = new ArrayList<>();
        Utilities u = new Utilities();

        // wczytuję dolisty String dane z pliku
        List<String> lineList = u.getListStringFromEntireFile("src/main/java/airTravel/data/flights/flights.txt");

        // sprawdzam i usuwam puste elementy z listy
        for (int i = 0; i < lineList.size(); i++) {
            if (lineList.get(i).isEmpty()){
                lineList.remove(i);
                i--;
            }
        }

        // z elementów listy odcinam niepotrzebną część
        List<String> fileList = new ArrayList<>();
        lineList.forEach(s -> fileList.add(s.substring(25)));

        // tworzę listę list, do której jako elementy zapisuję dane wczytane z kolejnych plików
        List<List<String>> mainList = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            String line;
            List<String> dataList = new ArrayList<>();
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src/main/java/airTravel/data/flights/" + fileList.get(i))))) {
                while ((line = bufferedReader.readLine()) != null) {
                    dataList.add(line);
                }
                mainList.add(dataList);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // sprawdzam i usuwam puste elementy
        for (int i = 0; i < mainList.size(); i++) {
            List<String> temp = mainList.get(i);
            for (int j = 0; j < temp.size(); j++) {
                if (temp.get(j).isEmpty()){
                    temp.remove(j);
                    j--;
                }
            }
        }

        // zamieniem listy String z danymi na obiekty AirTravel
        for (List<String> stringList : mainList) {
            AirTravel airTravel = new AirTravel();

            String[] tabNumbers = getTabStringFromList(stringList, 0);
            airTravel.setNumber(tabNumbers[1]);

            String[] tabDeparture = getTabStringFromList(stringList, 1);
            setTimestamp(airTravel, tabDeparture[1], 1);

            String[] tabArrival = getTabStringFromList(stringList, 2);
            setTimestamp(airTravel, tabArrival[1], 2);

            String[] tabPremiumSeats = getTabStringFromList(stringList, 3);
            String[] firstClass = tabPremiumSeats[1].split(" ");
            System_LOTY1.setListIntegerToAirTravel(airTravel, firstClass, 1);

            String[] tabRegularSeats = getTabStringFromList(stringList, 4);
            String[] secondClass = tabRegularSeats[1].split(" ");
            System_LOTY1.setListIntegerToAirTravel(airTravel, secondClass, 2);

            String reservations = stringList.get(5).replace("RESERVATIONS=", "");
            if (reservations.length() != 0){
                String[] tabString_Reservations = reservations.split(" ");
                for (String tabString_reservation : tabString_Reservations) {
                    if (tabString_reservation.contains("/")){
                        System_LOTY1.setListReservationToAirTravel(airTravel, tabString_Reservations, "/");
                    } else {
                        System_LOTY1.setListReservationToAirTravel(airTravel, tabString_Reservations, ",");
                    }
                }
            }

            airTravelList.add(airTravel);
        }

        airTravelList.sort(Comparator.comparing(AirTravel::getNumber));

        return airTravelList;
    }

    private void setTimestamp(AirTravel airTravel, String data, int position) {
        List<String> strings = new ArrayList<>(Arrays.asList(data));
        long longTimestamp = Long.parseLong(strings.get(0));
        Timestamp timestamp = new Timestamp(longTimestamp);
        if (position == 1){
            airTravel.setDepartureTime(timestamp);
        } else {
            airTravel.setArrivalTime(timestamp);
        }
    }

    public String[] getTabStringFromList(List<String> stringList, int elementNummber) {
        return stringList.get(elementNummber).split("=");
    }
}
