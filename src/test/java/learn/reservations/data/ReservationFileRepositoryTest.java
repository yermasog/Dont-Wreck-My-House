package learn.reservations.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_DIR_PATH = "data/reservations_data_seed";
    static final String TEST_DIR_PATH = "data/reservations_data_test";

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_DIR_PATH);
        Path testPath = Paths.get(TEST_DIR_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findByEmail() {
        //should return 25 reservations

    }

    @Test
    void add() {
    }

    @Test
    void edit() {
    }

    @Test
    void cancel() {
    }
}