package learn.reservations.data;

import learn.reservations.models.Host;

import java.util.List;

public interface HostRepository {

    List<Host> findAllHosts() throws DataAccessException;

    Host findHostByEmail(String email) throws DataAccessException;
}
