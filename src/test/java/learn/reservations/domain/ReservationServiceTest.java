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
    HostRepository hostRepository = new HostRepositoryDouble();
    GuestRepository guestRepository;
    ReservationService service = new ReservationService(reservationRepository,hostRepository,guestRepository);

    Host host = new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db","de Clerk","kdeclerkdc@sitemeter.com","(208) 9496329","2 Debra Way", "Boise", "ID", 83757, new BigDecimal("200"), new BigDecimal("250"));
    Guest guest = new Guest(799, "Goldy", "Bowland", "gbowlandm6@devhub.com", "(510) 1381796", "CA");

    @Test
    void findByEmail() throws DataAccessException {
        List<Reservation> reservations = reservationRepository.findResByHostEmail(host);

        assertEquals(2, reservations.size());

    }

    @Test
    void shouldAdd() throws DataAccessException {
        Reservation res = new Reservation(3, LocalDate.of(2021,11,20),
                LocalDate.of(2021,12,2), guest, host, new BigDecimal("450"));

        Result result = service.addRes(res);
        assertTrue(result.isSuccess());
        assertNotNull(result.getRes());
        assertEquals(0, result.getMessages().size());
    }

    @Test
    void shouldNotAddNullRes() throws DataAccessException {
        Result actual = service.addRes(null);

        assertFalse(actual.isSuccess());
        assertNull(actual.getRes());
        assertEquals(1, actual.getMessages().size());
        assertEquals("Reservation is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddResNullHost() throws DataAccessException {
        Host nullHost = new Host();
        Reservation res = new Reservation(3, LocalDate.of(2021,11,20),
                LocalDate.of(2021,12,2), guest, nullHost, new BigDecimal("450"));

        Result actual = service.addRes(res);

        assertFalse(actual.isSuccess());
        assertNull(actual.getRes());
        assertEquals(1, actual.getMessages().size());
        assertEquals("Host Id is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddResNullGuest() throws DataAccessException {
        Guest nullGuest = new Guest();
        Reservation res = new Reservation(3, LocalDate.of(2021,11,20),
                LocalDate.of(2021,12,2), nullGuest, host, new BigDecimal("450"));

        Result actual = service.addRes(res);

        assertFalse(actual.isSuccess());
        assertNull(actual.getRes());
        assertEquals(1, actual.getMessages().size());
        assertEquals("Guest Id is required.", actual.getMessages().get(0));

    }

    @Test
    void shouldNotAddNullStart() throws DataAccessException {
        LocalDate nullDate = null;
        Reservation res = new Reservation(3, nullDate,
                LocalDate.of(2021,12,2), guest, host, new BigDecimal("450"));

        Result actual = service.addRes(res);

        assertFalse(actual.isSuccess());
        assertNull(actual.getRes());
        assertEquals(1, actual.getMessages().size());
        assertEquals("Start date is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullEnd() throws DataAccessException {
        LocalDate nullDate = null;
        Reservation res = new Reservation(3, LocalDate.of(2021,12,2),nullDate,
                guest, host, new BigDecimal("450"));

        Result actual = service.addRes(res);

        assertFalse(actual.isSuccess());
        assertNull(actual.getRes());
        assertEquals(1, actual.getMessages().size());
        assertEquals("End date is required.", actual.getMessages().get(0));
    }


    //new Reservation(2, LocalDate.of(2021,9,20),LocalDate.of(2021,10,2), guest2, host, new BigDecimal("550"))
    @Test
    void shouldNotAddOverlap() throws DataAccessException {
        Reservation res = new Reservation(3, LocalDate.of(2021,9,21),
                LocalDate.of(2021,12,2), guest, host, new BigDecimal("450"));

        Result actual = service.addRes(res);

        assertFalse(actual.isSuccess());
        assertNull(actual.getRes());
        assertEquals(1, actual.getMessages().size());
        assertEquals("Cannot add overlapping reservation.", actual.getMessages().get(0));
    }


}