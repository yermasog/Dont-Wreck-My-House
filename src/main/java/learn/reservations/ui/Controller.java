package learn.reservations.ui;

import learn.reservations.data.DataAccessException;
import learn.reservations.domain.ReservationService;
import learn.reservations.models.Reservation;

import java.util.List;

public class Controller {

    private final ReservationService service;
    private final View view;

    public Controller(ReservationService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        view.displayMessage("Main Menu");
        view.displayMessage("=========");
        try {
            runMenuLoop();
        } catch (DataAccessException ex){
            view.displayMessage(ex.getMessage());
        }
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
        List<Reservation>  reservations = service.findByEmail(view.promptForHostEmail());
        view.displayList(reservations);
    }

    public void makeRes() {
        view.displayMessage("make res");
    }

    public void editRes() {
        view.displayMessage("edit res");
    }

    public void cancelRes() {
        view.displayMessage("cancel res");
    }
}
