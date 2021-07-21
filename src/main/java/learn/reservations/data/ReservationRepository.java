package learn.reservations.data;

import learn.reservations.models.Host;
import learn.reservations.models.Reservation;

import java.io.FileNotFoundException;
import java.util.List;

public interface ReservationRepository {
    List<Reservation> findResByHostEmail(String email);

    Reservation add(Reservation res) throws FileNotFoundException;

    boolean edit(Reservation res) throws FileNotFoundException;

    boolean cancel(Reservation res);
}
