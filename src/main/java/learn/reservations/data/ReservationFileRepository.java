package learn.reservations.data;

import learn.reservations.models.Guest;
import learn.reservations.models.Host;
import learn.reservations.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findResByHostEmail(Host host) throws DataAccessException {
        String hostId = host.getId();

        ArrayList<Reservation> reservations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(directory +"/" + hostId + ".csv"))) {
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                int id = Integer.parseInt(fields[0]);
                LocalDate startDate = readDateString(fields[1]);
                LocalDate endDate = readDateString(fields[2]);
                int guestId = Integer.parseInt(fields[3]);
                BigDecimal total = new BigDecimal(fields[4]);

                Guest guest = new Guest();
                guest.setId(guestId);

                Reservation reservation = new Reservation();
                reservation.setId(id);
                reservation.setStartDate(startDate);
                reservation.setEndDate(endDate);
                reservation.setGuest(guest);
                reservation.setTotal(total);
                reservation.setHost(host);

                reservations.add(reservation);
            }

        } catch (IOException ex) {
            throw new DataAccessException("Cannot access file");
        }
        return reservations;
    }

    @Override
    public Reservation add(Reservation res) throws DataAccessException {
        List<Reservation> all = findResByHostEmail(res.getHost());
        String hostId = res.getHost().getId();
        res.setId(all.size() + 1); //what happens when reservation is canceled? get maxId
        all.add(res);
        writeToFile(all, hostId);

        return res;
    }

    @Override
    public boolean edit(Reservation res) throws DataAccessException {
    List<Reservation> all = findResByHostEmail(res.getHost());
        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getId() == res.getId()) {
                all.set(i, res);
                writeToFile(all, res.getHost().getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean cancel(Reservation res) throws DataAccessException {
        List<Reservation> all = findResByHostEmail(res.getHost());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == res.getId()) {
                all.remove(i);
                writeToFile(all,res.getHost().getId());
                return true;
            }
        }
        return false;
    }

    private void writeToFile(List<Reservation> all, String hostId) throws DataAccessException {
        try (PrintWriter writer = new PrintWriter(directory +"/" + hostId + ".csv")) {
            writer.println(HEADER);
            all.stream().map(a -> String.format("%s,%s,%s,%s,%s",
                    a.getId(),
                    a.getStartDate(),
                    a.getEndDate(),
                    a.getGuest().getId(),
                    a.getTotal()))
                    .forEach(writer::println);
        } catch (IOException ex) {
            throw new DataAccessException("Could not write to file");
        }
    }

    private LocalDate readDateString(String dateString) { //TODO create LocalDate from string
        String[] fields = dateString.split("-", 3);
        int year = Integer.parseInt(fields[0]);
        int month = Integer.parseInt(fields[1]);
        int day = Integer.parseInt(fields[2]);
        return LocalDate.of(year, month, day);
    }


}
