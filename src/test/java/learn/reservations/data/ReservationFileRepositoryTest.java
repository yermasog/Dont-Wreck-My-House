package learn.reservations.data;

import learn.reservations.models.Guest;
import learn.reservations.models.Host;
import learn.reservations.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "data/reservations_data_seed/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_FILE_PATH = "data/reservations_data_test/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_DIR_PATH = "data/reservations_data_test";

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    Guest guest = new Guest(1, "Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
    Guest guest2 = new Guest(799, "Goldy", "Bowland", "gbowlandm6@devhub.com", "(510) 1381796", "CA");
    Host host = new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db","de Clerk","kdeclerkdc@sitemeter.com","(208) 9496329","2 Debra Way", "Boise", "ID", 83757, new BigDecimal("200"), new BigDecimal("250"));
    Reservation res = new Reservation(5,LocalDate.of(2021,7,31),LocalDate.of(2021,8,2), guest2, host, new BigDecimal("450"));

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldAdd() throws FileNotFoundException {
        Reservation res = new Reservation();
        res.setId(13);
        res.setStartDate(LocalDate.of(2021,11,01));
        res.setEndDate(LocalDate.of(2021,11,5));
        res.setGuest(guest);
        res.setHost(host);
        res.setTotal(new BigDecimal("1100"));

        res = repository.add(res);

        assertNotNull(res);
        //findAll and compare the last id with the new id

    }

    @Test
    void edit() throws FileNotFoundException {
        Reservation res = new Reservation(
                5,
                LocalDate.of(2021,7,31),
                LocalDate.of(2021,10,2), //changed month
                guest2,
                host,
                new BigDecimal("450"));

        boolean actual = repository.edit(res);
        assertTrue(actual);

        List<Reservation> all = repository.findResByHostEmail(host.getEmail());
        Month month = null;
        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getId() == res.getId()) {
               month = res.getEndDate().getMonth();
            }
        }

        assertTrue(month.equals(res.getEndDate().getMonth()));
    }

    @Test
    void cancel() throws FileNotFoundException {
        assertTrue(repository.cancel(res));

        List<Reservation> all = repository.findResByHostEmail(host.getEmail());
        assertEquals(11, all.size());

        assertFalse(all.stream()
                .anyMatch(a -> a.getId() == res.getId()));
    }

    @Test
    void findResByHostEmail() {
        //should return 12 reservations
        String email = "kdeclerkdc@sitemeter.com";
        List<Reservation> reservations = repository.findResByHostEmail(email);
        assertEquals(12, reservations.size());
    }
}