package learn.reservations.data;

import learn.reservations.models.Guest;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "data/reservations_data_seed/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_FILE_PATH = "data/reservations_data_test/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_DIR_PATH = "data/reservations_data_test";

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }


    @Test
    void shouldAdd() throws FileNotFoundException {
        String email = "kdeclerkdc@sitemeter.com";

        Guest guest = new Guest();
        guest.setId(1);
        guest.setFirstName("Sullivan");
        guest.setLastName("Lomas");
        guest.setEmail("slomas0@mediafire.com");
        guest.setPhone("(702) 7768761");
        guest.setState("NV");

        Reservation res = new Reservation();
        res.setId(13);
        res.setStartDate(LocalDate.of(2021,11,01));
        res.setEndDate(LocalDate.of(2021,11,5));
        res.setGuest(guest);
        res.setTotal(new BigDecimal("1100"));

        res = repository.add(res, email);

        assertNotNull(res);
        //findAll and compare the last id with the new id

    }

    @Test
    void edit() {
    }

    @Test
    void cancel() {
    }

    @Test
    void findResByHostEmail() {
        //should return 12 reservations
        String email = "kdeclerkdc@sitemeter.com";
        List<Reservation> reservations = repository.findResByHostEmail(email);
        assertEquals(12, reservations.size());
    }
}