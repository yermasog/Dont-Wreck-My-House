package learn.reservations.ui;

import learn.reservations.data.DataAccessException;
import learn.reservations.domain.GuestService;
import learn.reservations.domain.HostService;
import learn.reservations.domain.ReservationService;
import learn.reservations.domain.Result;
import learn.reservations.models.Guest;
import learn.reservations.models.Host;
import learn.reservations.models.Reservation;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println(String.format("Host Email: %s", hostEmail ));
        view.displayList(service.findByEmail(hostEmail));
        view.enterToContinue();
    }

    //gbowlandm6@devhub.com
    public void makeRes() throws DataAccessException {
        boolean confirm;
        Host host;
        Guest guest;
        LocalDate start;
        LocalDate end;
        BigDecimal total;

        do{
            String[] emailArr = view.promptEmails();
            String guestEmail = emailArr[0];
            String hostEmail = emailArr[1];
            guest = guestService.findGuestByEmail(guestEmail);
            host = hostService.findHost(hostEmail);
            view.displayMessage(String.format("Guest email: %s", guestEmail ));
            view.displayMessage(String.format("Host email: %s", hostEmail));

            view.displayList(service.findByEmail(hostEmail));
            start = view.promptStartDate();
            end = view.promptEndDate();

            total = BigDecimal.ZERO;
            total = calculateTotal(host, start, end, total);
            confirm = view.confirmMakeRes(start, end, total);

        } while (!confirm);

        Reservation res = new Reservation();
        res.setHost(host);
        res.setGuest(guest);
        res.setStartDate(start);
        res.setEndDate(end);
        res.setTotal(total);

        Result result = service.addRes(res);
        if(result.isSuccess()){
            view.displayMessage("Reservation successfully added!");
        } else {
            view.displayMessage(result.getMessages().get(0));
        }

        view.enterToContinue();
    }

//tkelbyke@diigo.com
    public void editRes() throws DataAccessException {
        String[] emailsArr = view.promptEmails();
        String guestEmail = emailsArr[0];
        String hostEmail = emailsArr[1];

        List<Reservation> reservations = service.findByEmail(hostEmail);

        List<Reservation> filteredGuestRes = reservations.stream()
                .filter(res -> res.getGuest().getEmail().equals(guestEmail))
                .collect(Collectors.toList());


        view.displayList(filteredGuestRes);
        int editResId = view.promptForResId("Enter the Id of the reservation you want to edit.");

        view.displayMessage(String.format("Editing Reservation %s", editResId));
        view.displayMessage("======================");
        view.displayMessage(String.format("Start (%s)", filteredGuestRes.get(0).getStartDate()));
        view.displayMessage(String.format("End (%s)", filteredGuestRes.get(0).getEndDate()));

        LocalDate start = view.promptStartDate();
        LocalDate end = view.promptEndDate();

        BigDecimal total = BigDecimal.ZERO;
        total = calculateTotal(filteredGuestRes.get(0).getHost(), start, end,total);

        view.confirmMakeRes(start, end, total);

        Reservation res = new Reservation();
        res.setId(editResId);
        res.setHost(filteredGuestRes.get(0).getHost());
        res.setGuest(filteredGuestRes.get(0).getGuest());
        res.setStartDate(start);
        res.setEndDate(end);
        res.setTotal(total);

        Result result = null;
        for (Reservation guest : filteredGuestRes) {
            if (guest.getId() == editResId) {
                result = service.editRes(res);
            }
        }
        if(result.isSuccess()){
            view.displayMessage("Reservation has been updated.");
        } else {
            view.displayMessage(result.getMessages().get(0));
        }

    }

    public void cancelRes() throws DataAccessException {
        String[] emailsArr = view.promptEmails();
        String guestEmail = emailsArr[0];
        String hostEmail = emailsArr[1];

        List<Reservation> reservations = service.findByEmail(hostEmail);

        List<Reservation> filteredGuestRes = reservations.stream()
                .filter(res -> res.getGuest().getEmail().equals(guestEmail))
                .collect(Collectors.toList());

        view.displayList(filteredGuestRes);
        int cancelResId = view.promptForResId("Enter the ID of the reservation you want to delete.");

        Result result = null;
        for (Reservation guest : filteredGuestRes) {
            if (guest.getId() == cancelResId) {
                result = service.cancelRes(guest);
            }
        }
        if(result.isSuccess()){
            view.displayMessage("Reservation successfully canceled.");
        } else {
            view.displayMessage(result.getMessages().get(0));
        }
        

    }

    private BigDecimal calculateTotal(Host host, LocalDate start, LocalDate end, BigDecimal total) {
        for(LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)){
            if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                    || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                total = total.add(host.getWeekendRate());
            } else {
                total = total.add(host.getStandardRate());
            }
        }
        return total;
    }


}
