package airTravel;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Utilities {
    public List<String> getListStringFromEntireFile(String file) {
        List<String> listFromFile = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)))) {
            bufferedReader.lines().forEach(listFromFile::add);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listFromFile;
    }

    public List<List<String>> getListStringFromSomeLinesFromFile(String file) {
        String line;
        List<List<String>> mainList = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            List<String> strings = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("-")){
                    mainList.add(strings);
                    strings = new ArrayList<>();
                }
                if (!line.startsWith("-")){
                    strings.add(line);
                }
            }
            mainList.add(strings);  // dodaję ostatnią listę, gdy line = null
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainList.remove(0);
        return mainList;
    }

    public List<String> getUniqueAirTravelNummbers(Set<AirTravel> allUniqueAirTrevels) {
        return allUniqueAirTrevels.stream()
                .map(AirTravel::getNumber)
                .collect(Collectors.toSet())
                .stream()
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }

    public Map<String, List<AirTravel>> getMap_Nummber_AirTravels(List<String> uniqueAirTravelNummbers, Set<AirTravel> allUniqueAirTravels){
         Map<String, List<AirTravel>> map = uniqueAirTravelNummbers.stream()
                .collect(Collectors.toMap(
                        s -> s,
                        s -> allUniqueAirTravels.stream().filter(airTravel -> airTravel.getNumber().equals(s))
                            .collect(Collectors.toList())
                ));

         Map<String, List<AirTravel>> mapWithListSizeMoreThan1 = map.entrySet().stream()
                 .filter(stringListEntry -> stringListEntry.getValue().size() > 1)
                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return mapWithListSizeMoreThan1.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(String::compareTo))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    Map<Character, String> getChooseMap(List<String> nummbersWithMistake){
        char[] tab = {'a'};
        Map<Character, String> map = new HashMap<>();
        nummbersWithMistake.forEach(s -> map.put(tab[0]++, s));
        return map;
    }

    List<Reservation> getDifferentReservationNummbers(List<AirTravel> airTravelListWithMistakesForTheNummber){
        List<Reservation> result = new ArrayList<>();
        if (airTravelListWithMistakesForTheNummber.size() == 2){
            List<Reservation> list0 = airTravelListWithMistakesForTheNummber.get(0).getReservations();
            List<Reservation> list1 = airTravelListWithMistakesForTheNummber.get(1).getReservations();

            // zbieram wszystkie unikalne rezerwacje do seta, a następnie do listy
            Set<Reservation> reservationSet = new HashSet<>(list0);
            reservationSet.addAll(list1);
            List<Reservation> allReservations1 = getReservations(reservationSet);
            List<Reservation> allReservations2 = getReservations(reservationSet);

            // wyszukuję unikalne elementy z poszczególnych list
            allReservations1.removeAll(list0);  // zostają unikalne z list1
            allReservations2.removeAll(list1);  // zostają unikalne z list0

            // zbieram unikalne elementy do jednej kolekcji
            result.addAll(allReservations1);
            result.addAll(allReservations2);
        }
        if (airTravelListWithMistakesForTheNummber.size() == 3) {
            List<Reservation> list1 = airTravelListWithMistakesForTheNummber.get(0).getReservations();
            List<Reservation> list2 = airTravelListWithMistakesForTheNummber.get(1).getReservations();
            List<Reservation> list3 = airTravelListWithMistakesForTheNummber.get(2).getReservations();

            // zbieram wszystkie unikalne rezerwacje do seta, a następnie do listy
            Set<Reservation> reservationSet = new HashSet<>(list1);
            reservationSet.addAll(list2);
            reservationSet.addAll(list3);
            List<Reservation> allReservations1 = getReservations(reservationSet);
            List<Reservation> allReservations2 = getReservations(reservationSet);
            List<Reservation> allReservations3 = getReservations(reservationSet);

            // wyszukuję unikalne elementy z poszczególnych list
            allReservations1.removeAll(list2);
            allReservations1.removeAll(list3);  // zostają unikalne z list1

            allReservations2.removeAll(list1);
            allReservations2.removeAll(list3);  // zostają unikalne z list2

            allReservations3.removeAll(list1);
            allReservations3.removeAll(list2);  // zostają unikalne z list3

            // zbieram unikalne elementy do jednej kolekcji
            result.addAll(allReservations1);
            result.addAll(allReservations2);
            result.addAll(allReservations3);
        }
        result.sort(Comparator.comparing(Reservation::getName));
        return result;
    }

    private List<Reservation> getReservations(Set<Reservation> reservationSet) {
        return new ArrayList<>(reservationSet);
    }
}
