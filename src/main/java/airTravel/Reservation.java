package airTravel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
- nazwiska osób które zarezerwowały loty, numery zarezerwowanych miejsc, numery rezerwacji
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation {
    private String name;
    private int placeNummber;
    private String reservationNummber;
}
