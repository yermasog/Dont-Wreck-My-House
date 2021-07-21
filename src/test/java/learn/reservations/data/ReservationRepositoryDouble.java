package learn.reservations.data;

import learn.reservations.models.Guest;
import learn.reservations.models.Host;
import learn.reservations.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository{

    Guest guest = new Guest(1, "Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
    Guest guest2 = new Guest(799, "Goldy", "Bowland", "gbowlandm6@devhub.com", "(510) 1381796", "CA");
    Host host = new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db","de Clerk","kdeclerkdc@sitemeter.com","(208) 9496329","2 Debra Way", "Boise", "ID", 83757, new BigDecimal("200"), new BigDecimal("250"));
    Reservation res = new Reservation(5, LocalDate.of(2021,7,20),LocalDate.of(2021,8,2), guest2, host, new BigDecimal("450"));


    @Override
    public List<Reservation> findResByHostEmail(Host host) throws DataAccessException {
        return Arrays.asList(
                new Reservation(1, LocalDate.of(2021,7,20),LocalDate.of(2021,8,2), guest2, host, new BigDecimal("450")),
                new Reservation(2, LocalDate.of(2021,9,20),LocalDate.of(2021,10,2), guest2, host, new BigDecimal("550"))
        );
    }

    @Override
    public Reservation add(Reservation res) throws DataAccessException {
        return res;
    }

    @Override
    public boolean edit(Reservation res) throws DataAccessException {

        return res.getId() == 3;
    }

    @Override
    public boolean cancel(Reservation res) throws DataAccessException {
        return false;
    }
}
