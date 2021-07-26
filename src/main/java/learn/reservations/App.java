package learn.reservations;

import learn.reservations.data.*;
import learn.reservations.domain.GuestService;
import learn.reservations.domain.HostService;
import learn.reservations.domain.ReservationService;
import learn.reservations.models.Guest;
import learn.reservations.ui.Controller;
import learn.reservations.ui.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@PropertySource("classpath:data.properties")
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        Controller controller = context.getBean(Controller.class);
        controller.run();
    }


//    public static void main(String[] args) {
//        ReservationRepository reservationRepository = new ReservationFileRepository("data/reservations_data");
//        GuestRepository guestRepository = new GuestFileRepository("data/guests.csv");
//        HostRepository hostRepository =  new HostFileRepository("data/hosts.csv");
//        ReservationService service = new ReservationService(reservationRepository,hostRepository,guestRepository);
//        HostService hostService = new HostService(hostRepository);
//        GuestService guestService = new GuestService(guestRepository);
//        View view = new View();
//        Controller controller = new Controller(service, guestService, hostService, view);
//
//        controller.run();
//
//
//    }
}
