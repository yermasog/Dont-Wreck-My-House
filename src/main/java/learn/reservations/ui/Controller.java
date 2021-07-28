package learn.reservations.ui;

import learn.reservations.data.DataAccessException;
import learn.reservations.domain.GuestService;
import learn.reservations.domain.HostService;
import learn.reservations.domain.ReservationService;
import learn.reservations.domain.Result;
import learn.reservations.models.Guest;
import learn.reservations.models.Host;
import learn.reservations.models.Reservation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Component
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
        } catch (DataAccessException ex) {
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
        String hostEmail = view.promptEmails("host");
        view.displayMessage("View Reservations for Host");
        view.displayMessage("==========================");
        System.out.printf("Host Email: %s%n", hostEmail);
       Host host = hostService.findHost(hostEmail);
        System.out.printf("%s, %s%n",host.getCity(), host.getState());
        view.displayList(service.findByEmail(hostEmail));
        view.enterToContinue();
    }

    //gbowlandm6@devhub.com
    public void makeRes() throws DataAccessException {
        boolean confirm;
        Host host;
        Guest guest;
        LocalDate start = null;
        LocalDate end = null;
        BigDecimal total = BigDecimal.ZERO;

        do {
            String guestEmail = view.promptEmails("guest");
            String hostEmail = view.promptEmails("host");
            guest = guestService.findGuestByEmail(guestEmail);
            host = hostService.findHost(hostEmail);
            view.displayMessage(String.format("Guest email: %s", guestEmail));
            view.displayMessage(String.format("Host email: %s", hostEmail));
            System.out.printf("%s, %s%n",host.getCity(), host.getState());

            view.displayList(service.findByEmail(hostEmail));
            start = runCheckDate("start");
            end = runCheckDate("end");

            total = calculateTotal(host, start, end, total);
            view.displaySummary(start, end, total);
            confirm = view.confirmRes();

        } while (!confirm);

        Reservation res = new Reservation();
        res.setHost(host);
        res.setGuest(guest);
        res.setStartDate(start);
        res.setEndDate(end);
        res.setTotal(total);

        returnResult(res);

        view.enterToContinue();
    }

    //tkelbyke@diigo.com
    public void editRes() throws DataAccessException {
        List<Reservation> reservations = checkForReservations();
        int editResId;
        boolean existingRes;
        LocalDate start;
        LocalDate end;
        BigDecimal total = BigDecimal.ZERO;

        do{
            editResId = view.promptForResId("Enter the Id of the reservation you want to edit.");
            int finalEditResId = editResId;
            existingRes = reservations.stream().anyMatch(r -> r.getId() == finalEditResId);
        }while(!existingRes);

        view.displayMessage(String.format("Editing Reservation %s", editResId));
        view.displayMessage("======================");

        boolean confirm;
        do{
           start = runCheckDate("start");
           end = runCheckDate("end");

            total = calculateTotal(reservations.get(0).getHost(), start, end,total);

            view.displaySummary(start, end, total);
            confirm = view.confirmRes();
        }while(!confirm);


        Reservation res = new Reservation();
        res.setId(editResId);
        res.setHost(reservations.get(0).getHost());
        res.setGuest(reservations.get(0).getGuest());
        res.setStartDate(start);
        res.setEndDate(end);
        res.setTotal(total);

        Result result = service.editRes(res);

        if(result.isSuccess()){
            view.displayMessage("Reservation has been updated.");
        } else {
            view.displayMessage(result.getMessages().get(0));
        }

    }

    public void cancelRes() throws DataAccessException {
        List<Reservation> reservations = futureReservations();
        int cancelResId = view.promptForResId("Enter the ID of the reservation you want to delete.");

        Result result = new Result();
        for (Reservation r : reservations) {
            if (r.getId() == cancelResId) {
                result = service.cancelRes(r);
            } else {
                result.addMessage("Reservation not found, please try again. ");
            }
        }

        if (result.isSuccess()) {
            view.displayMessage("Reservation successfully canceled.");
        } else {
            view.displayMessage(result.getMessages().get(0));
        }

        view.enterToContinue();
    }

    private List<Reservation> checkForReservations() throws DataAccessException {
        String hostEmail = view.promptEmails("host");
        List<Reservation> reservations;
        do {
            reservations = service.findByEmail(hostEmail);
            view.displayList(reservations);
        } while (reservations.isEmpty());

        return reservations;
    }

    private List<Reservation> futureReservations() throws DataAccessException {
        String hostEmail = view.promptEmails("host");
        List<Reservation> reservations;
        List<Reservation> futureReservations;
        do {
            reservations = service.findByEmail(hostEmail);
            futureReservations = reservations.stream()
                    .filter(r -> r.getStartDate().isAfter(LocalDate.now()))
                    .sorted(Comparator.comparing(Reservation::getStartDate))
                    .collect(Collectors.toList());
            view.displayList(futureReservations);
        } while (reservations.isEmpty());

        return futureReservations;
    }

    private BigDecimal calculateTotal(Host host, LocalDate start, LocalDate end, BigDecimal total) {
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                    || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                total = total.add(host.getWeekendRate());
            } else {
                total = total.add(host.getStandardRate());
            }
        }
        return total;
    }

    private LocalDate runCheckDate(String message) {
        LocalDate date = null;
        do {
            try {
                date = checkDateFormat(message);
                return date;
            } catch (DataAccessException ex) {
                view.displayMessage(ex.getMessage());
            }
        } while (date == null);

        return null;
    }

    private LocalDate checkDateFormat(String message) throws DataAccessException {
        return view.promptDate(message);

    }

    private void returnResult(Reservation res) throws DataAccessException {
        Result result = service.addRes(res);
        if (result.isSuccess()) {
            view.displayMessage("Reservation successfully added!");
        } else {
            view.displayMessage(result.getMessages().get(0));
        }
    }
}
