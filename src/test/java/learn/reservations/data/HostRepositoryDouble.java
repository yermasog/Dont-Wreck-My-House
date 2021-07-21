package learn.reservations.data;

import learn.reservations.models.Host;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class HostRepositoryDouble implements HostRepository{


    @Override
    public List<Host> findAllHosts() throws DataAccessException {
       return Arrays.asList(
                new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db","de Clerk","kdeclerkdc@sitemeter.com",
                        "(208) 9496329","2 Debra Way", "Boise", "ID", 83757, new BigDecimal("200"), new BigDecimal("250")),
                new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db","de Clerk","kdeclerkdc@sitemeter.com",
                        "(208) 9496329","2 Debra Way", "Boise", "ID", 83757, new BigDecimal("200"), new BigDecimal("250"))
        );

    }

    @Override
    public Host matchHostEmailToId(String email) throws DataAccessException {
        Host host = new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db","de Clerk","kdeclerkdc@sitemeter.com",
                "(208) 9496329","2 Debra Way", "Boise", "ID", 83757, new BigDecimal("200"), new BigDecimal("250"));

        return host;
    }
}
