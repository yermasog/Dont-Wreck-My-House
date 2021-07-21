package learn.reservations.data;

import learn.reservations.models.Guest;

import java.util.List;

public interface GuestRepository {

    List<Guest> findAllGuests() throws DataAccessException;

    Guest matchGuestId(int id) throws DataAccessException;
}
