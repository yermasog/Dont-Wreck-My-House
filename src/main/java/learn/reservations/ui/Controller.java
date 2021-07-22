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
        view.test();
    }
}
