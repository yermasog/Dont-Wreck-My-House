package learn.reservations.data;

import learn.reservations.models.Host;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostFileRepository implements HostRepository {

    private final String filePath;

    public HostFileRepository (String filePath) {
        this.filePath = filePath;
    }

    public List<Host> findAllHosts() throws DataAccessException {
        ArrayList<Host> allHosts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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

        } catch (IOException e) {
            throw new DataAccessException("Can not access file");
        }
        return allHosts;
    }

    public Host matchHostEmailToId(String email) throws DataAccessException {
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
}
