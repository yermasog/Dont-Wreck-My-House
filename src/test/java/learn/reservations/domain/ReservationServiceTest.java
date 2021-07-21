package learn.reservations.domain;

import learn.reservations.data.*;
import learn.reservations.models.Guest;
import learn.reservations.models.Host;
import learn.reservations.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationRepository reservationRepository = new ReservationRepositoryDouble();
    HostRepository hostRepository;
    GuestRepository guestRepository;
    ReservationService service = new ReservationService(reservationRepository,hostRepository,guestRepository);

    Host host = new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db","de Clerk","kdeclerkdc@sitemeter.com","(208) 9496329","2 Debra Way", "Boise", "ID", 83757, new BigDecimal("200"), new BigDecimal("250"));
    Guest guest = new Guest(799, "Goldy", "Bowland", "gbowlandm6@devhub.com", "(510) 1381796", "CA");

    @Test
    void findByEmail() throws DataAccessException {
        List<Reservation> reservations = reservationRepository.findResByHostEmail(host);

        assertEquals(2, reservations.size());

    }

    void shouldAdd() throws DataAccessException {
        Reservation res = new Reservation(5, LocalDate.of(2021,7,31),LocalDate.of(2021,8,2), guest, host, new BigDecimal("450"));

        reservationRepository.add(res);
    }

}