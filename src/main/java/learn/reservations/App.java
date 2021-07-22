package learn.reservations;

import learn.reservations.data.*;
import learn.reservations.domain.ReservationService;
import learn.reservations.ui.Controller;
import learn.reservations.ui.View;

public class App {
    public static void main(String[] args) {
        ReservationRepository reservationRepository = new ReservationFileRepository("data/reservations_data");
        GuestRepository guestRepository = new GuestFileRepository("data/guests.csv");
        HostRepository hostRepository =  new HostFileRepository("data/hosts.csv");
        ReservationService service = new ReservationService(reservationRepository,hostRepository,guestRepository);
        View view = new View();
        Controller controller = new Controller(service, view);

        controller.run();


    }
}
