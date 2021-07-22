package learn.reservations.ui;

import learn.reservations.data.DataAccessException;
import learn.reservations.domain.GuestService;
import learn.reservations.domain.HostService;
import learn.reservations.domain.ReservationService;
import learn.reservations.models.Guest;
import learn.reservations.models.Host;
import learn.reservations.models.Reservation;

import java.util.List;

public class Controller {

    private final ReservationService service;
    private final GuestService guestService;
    private final HostService hostService;
    private final View view;

    public Controller(ReservationService service, GuestService guestService, HostService hostService, View view) {
        this.service = service;
        this.guestService = guestService;
        this.hostService = hostService;
        this.view = view;
    }

    public void run() {

        try {
            runMenuLoop();
        } catch (DataAccessException ex){
            view.displayMessage(ex.getMessage());
        }
        view.displayMessage("Thank you; goodbye!");
    }

    public void runMenuLoop() throws DataAccessException {
        MainMenuOption option;
        do {
            option = view.selectMenuOption();
            switch (option) {
                case VIEW_RES_BY_HOST:
                    viewResByHost();
                    break;
                case MAKE_RESERVATION:
                    makeRes();
                    break;
                case EDIT_RESERVATION:
                    editRes();
                    break;
                case CANCEL_RESERVATION:
                    cancelRes();
                    break;
            }
        } while (option != MainMenuOption.EXIT);

    }

    //kdeclerkdc@sitemeter.com
    public void viewResByHost() throws DataAccessException {
        String hostEmail = view.promptForHostEmail();
        List<Reservation>  reservations = service.findByEmail(hostEmail);
        System.out.println(String.format("Host Email: %s", hostEmail ));
        view.displayList(reservations);
        view.enterToContinue();
    }

    //gbowlandm6@devhub.com
    public void makeRes() throws DataAccessException {
        String[] emailArr = view.promptEmails();
        String guestEmail = emailArr[0];
        String hostEmail = emailArr[1];
        Guest guest = guestService.findGuestByEmail(guestEmail);
        Host host = hostService.findHost(hostEmail);
        view.displayMessage(String.format("Guest email: %s", guestEmail ));
        view.displayMessage(String.format("Host email: %s", hostEmail));

        view.displayList(service.findByEmail(hostEmail));
        view.promptMakeRes(guest, host);

    }

    public void editRes() {
        view.displayMessage("edit res");
    }

    public void cancelRes() {
        view.displayMessage("cancel res");
    }
}
