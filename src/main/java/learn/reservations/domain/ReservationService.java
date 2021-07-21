package learn.reservations.domain;

import learn.reservations.data.DataAccessException;
import learn.reservations.data.GuestRepository;
import learn.reservations.data.HostRepository;
import learn.reservations.data.ReservationRepository;
import learn.reservations.models.Guest;
import learn.reservations.models.Host;
import learn.reservations.models.Reservation;

import java.util.List;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final HostRepository hostRepository;
    private final GuestRepository guestRepository;


    public ReservationService(ReservationRepository reservationRepository, HostRepository hostRepository, GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.hostRepository = hostRepository;
        this.guestRepository = guestRepository;
    }

    public List<Reservation> findByEmail(String email) throws DataAccessException {
        Host host = hostRepository.matchHostEmailToId(email);
        List<Reservation> reservations = reservationRepository.findResByHostEmail(host);

        return reservations;
    }
}
