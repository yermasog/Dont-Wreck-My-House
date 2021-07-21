package learn.reservations.data;

import learn.reservations.models.Guest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuestFileRepository implements GuestRepository {

    private final String filePath;

    public GuestFileRepository (String filePath) {
        this.filePath = filePath;
    }

    public List<Guest> findAllGuests() throws DataAccessException {
        ArrayList<Guest> allGuests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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
            throw new DataAccessException("Cannot access file");
        }
        return allGuests;
    }

    public Guest matchGuestId(int id) throws DataAccessException {
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
}
