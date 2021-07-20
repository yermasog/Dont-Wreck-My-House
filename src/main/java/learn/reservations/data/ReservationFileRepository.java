package learn.reservations.data;

import learn.reservations.models.Reservation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository{

    private final String directory;

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findByEmail(String email) {
        ArrayList<Reservation> all = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(directory))) {
            reader.readLine();
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                int id = Integer.parseInt(fields[0]);
                LocalDate startDate = readDateString(fields[1]);
                LocalDate endDate = readDateString(fields[2]);
                int guestId = Integer.parseInt(fields[3]);
                BigDecimal total = fields[4];


            }

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return all;
    }

    public LocalDate readDateString(String dateString){
        String fields[] = dateString.split("-", 3);
        int year = Integer.parseInt(fields[0]);
        int month = Integer.parseInt(fields[1]);
        int day = Integer.parseInt(fields[2]);
        LocalDate date = LocalDate.of(year, month, day);
        return date;
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
