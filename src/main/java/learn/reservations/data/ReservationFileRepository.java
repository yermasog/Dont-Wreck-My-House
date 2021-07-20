package learn.reservations.data;

import learn.reservations.models.Reservation;

import java.util.List;

public class ReservationFileRepository implements ReservationRepository{

    private final String directory;

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findByEmail(String email) {
        return null;
    }

    @Override
    public Reservation add(Reservation res) {
        return null;
    }

    @Override
    public boolean edit(Reservation res) {
        return false;
    }

    @Override
    public boolean cancel(Reservation res) {
        return false;
    }
}
