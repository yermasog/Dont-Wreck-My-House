package learn.reservations.data;

import learn.reservations.models.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Repository
public class HostFileRepository implements HostRepository {

    private final String filePath;


    public HostFileRepository (@Value("${hostFilePath}")String filePath) {
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

    public Host findHostByEmail(String email) throws DataAccessException {
        List<Host> all = findAllHosts();
        Host host = new Host();
        for (Host value : all) {
            if (value.getEmail().equalsIgnoreCase(email)) {
                host.setId(value.getId());
                host.setLastName(value.getLastName());
                host.setEmail(value.getEmail());
                host.setPhone(value.getPhone());
                host.setAddress(value.getAddress());
                host.setCity(value.getCity());
                host.setState(value.getState());
                host.setPostalCode(value.getPostalCode());
                host.setStandardRate(value.getStandardRate());
                host.setWeekendRate(value.getWeekendRate());
            }
        }
        return host;
    }
}
