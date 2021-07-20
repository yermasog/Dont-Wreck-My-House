package learn.reservations.data;

import learn.reservations.models.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByEmail(String email);

    Reservation add(Reservation res);

    boolean edit(Reservation res);

    boolean cancel(Reservation res);
}
