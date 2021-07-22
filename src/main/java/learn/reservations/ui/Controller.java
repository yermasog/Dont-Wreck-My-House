package learn.reservations.ui;

import learn.reservations.domain.ReservationService;

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

            view.selectMenuOption();


    }

    public void runMenuLoop() {
        MainMenuOption option;
        do{
            option = view.selectMenuOption();
            switch (option) {
                case EXIT:
                    break;
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

    public void viewResByHost() {

    }

    public void makeRes() {

    }

    public void editRes() {

    }

    public void cancelRes() {

    }
}
