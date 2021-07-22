package learn.reservations.domain;

import learn.reservations.data.DataAccessException;
import learn.reservations.data.GuestRepository;
import learn.reservations.models.Guest;

public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest matchGuestId(int id) throws DataAccessException {
        Guest guest = guestRepository.matchGuestId(id);
        return guest;
    }

    public Guest findGuestByEmail(String email) throws DataAccessException {
        Guest guest = guestRepository.findGuestByEmail(email);
        return guest;
    }
}
