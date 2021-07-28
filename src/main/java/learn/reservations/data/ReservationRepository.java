package learn.reservations.data;

import learn.reservations.models.Guest;
import learn.reservations.models.Host;
import learn.reservations.models.Reservation;
import java.util.List;

public interface ReservationRepository {
    List<Reservation> findResByHostEmail(Host host) throws DataAccessException;

    Reservation add(Reservation res) throws DataAccessException;

    boolean edit(Reservation res) throws DataAccessException;

    boolean cancel(Reservation res) throws DataAccessException;
}
