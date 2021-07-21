package learn.reservations.data;

import learn.reservations.models.Guest;
import learn.reservations.models.Host;
import learn.reservations.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    public List<Host> findAllHosts() {
        ArrayList<Host> allHosts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/hosts.csv"))) {
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                String id = fields[0];
                String lastName = fields[1];
                String email = fields[2];
                String phone = fields[3];
                String address = fields[4];
                String city = fields[5];
                String state = fields[6];
                int postalCode = Integer.parseInt(fields[7]);
                BigDecimal standardRate = new BigDecimal(fields[8]);
                BigDecimal weekendRate = new BigDecimal(fields[9]);

                Host host = new Host();
                host.setId(id);
                host.setLastName(lastName);
                host.setEmail(email);
                host.setPhone(phone);
                host.setAddress(address);
                host.setCity(city);
                host.setState(state);
                host.setPostalCode(postalCode);
                host.setStandardRate(standardRate);
                host.setWeekendRate(weekendRate);

                allHosts.add(host);
            }

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allHosts;
    }

    public List<Guest> findAllGuests() throws FileNotFoundException {
        ArrayList<Guest> allGuests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/guests.csv"))) {
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                int id = Integer.parseInt(fields[0]);
                String firstName = fields[1];
                String lastName = fields[2];
                String email = fields[3];
                String phone = fields[4];
                String state = fields[5];

                Guest guest = new Guest();
                guest.setId(id);
                guest.setFirstName(firstName);
                guest.setLastName(lastName);
                guest.setEmail(email);
                guest.setPhone(phone);
                guest.setState(state);

                allGuests.add(guest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allGuests;
    }

    public Guest matchGuestId(int id) throws FileNotFoundException {
        List<Guest> all = findAllGuests();
        Guest guest = new Guest();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == id) {
                guest.setId(all.get(i).getId());
                guest.setFirstName(all.get(i).getFirstName());
                guest.setLastName(all.get(i).getLastName());
                guest.setEmail(all.get(i).getEmail());
                guest.setPhone(all.get(i).getPhone());
                guest.setState(all.get(i).getState());
            }
        }
        return guest;
    }

    public Host matchHostEmailToId(String email) {
        List<Host> all = findAllHosts();
        Host host = new Host();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getEmail().equalsIgnoreCase(email)) {
                host.setId(all.get(i).getId());
                host.setLastName(all.get(i).getLastName());
                host.setEmail(all.get(i).getEmail());
                host.setPhone(all.get(i).getPhone());
                host.setAddress(all.get(i).getAddress());
                host.setCity(all.get(i).getCity());
                host.setState(all.get(i).getState());
                host.setPostalCode(all.get(i).getPostalCode());
                host.setStandardRate(all.get(i).getStandardRate());
                host.setWeekendRate(all.get(i).getWeekendRate());
            }
        }
        return host;
    }

@Override
    public List<Reservation> findResByHostEmail(String email) {
        Host host = matchHostEmailToId(email);
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

                Guest guest = matchGuestId(guestId);

                Reservation reservation = new Reservation();
                reservation.setId(id);
                reservation.setStartDate(startDate);
                reservation.setEndDate(endDate);
                reservation.setGuest(guest);
                reservation.setTotal(total);
                reservation.setHost(host);

                reservations.add(reservation);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    @Override
    public Reservation add(Reservation res) throws FileNotFoundException {
        List<Reservation> all = findResByHostEmail(res.getHost().getEmail());
        String hostId = res.getHost().getId();
        res.setId(all.size() + 1);
        all.add(res);
        writeToFile(all, hostId);

        return res;
    }

    private void writeToFile(List<Reservation> all, String hostId) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(directory +"/" + hostId + ".csv")) {
            writer.println(HEADER);
            all.stream().map(a -> String.format("%s,%s,%s,%s,%s",
                    a.getId(),
                    a.getStartDate(),
                    a.getEndDate(),
                    a.getGuest().getId(),
                    a.getTotal()))
                    .forEach(writer::println);
        }
    }

    @Override
    public boolean edit(Reservation res) throws FileNotFoundException {
    List<Reservation> all = findResByHostEmail(res.getHost().getEmail());
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
    public boolean cancel(Reservation res) {
        return false;
    }

    private LocalDate readDateString(String dateString) {
        String fields[] = dateString.split("-", 3);
        int year = Integer.parseInt(fields[0]);
        int month = Integer.parseInt(fields[1]);
        int day = Integer.parseInt(fields[2]);
        LocalDate date = LocalDate.of(year, month, day);
        return date;
    }


}
