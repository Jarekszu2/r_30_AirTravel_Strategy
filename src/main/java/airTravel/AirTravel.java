package airTravel;

import airTravel.strategies.IDataStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/*
- nr lotu
- czas wylotu (timestamp)
- czas przylotu (timestamp)
- numery miejsc pierwszej klasy
- numery miejsc drugiej klasy
- nazwiska osób które zarezerwowały loty, numery zarezerwowanych miejsc, numery rezerwacji
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AirTravel {
    private String number;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private List<Integer> firstClassNummbers;
    private List<Integer> secondClassNummbers;
    private List<Reservation> reservations;
}
