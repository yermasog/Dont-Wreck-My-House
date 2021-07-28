package learn.reservations.domain;

import learn.reservations.data.DataAccessException;
import learn.reservations.data.HostRepository;
import learn.reservations.models.Host;
import org.springframework.stereotype.Service;


@Service
public class HostService {
    private final HostRepository hostRepository;

    public HostService(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    public Host findHost(String email) throws DataAccessException {
        Host host = hostRepository.findHostByEmail(email);
        return host;
    }


}
