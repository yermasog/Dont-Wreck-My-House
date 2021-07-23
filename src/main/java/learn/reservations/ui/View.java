package learn.reservations.ui;

import learn.reservations.models.Guest;
import learn.reservations.models.Host;
import learn.reservations.models.Reservation;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.sql.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class View {

    private Scanner console = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public MainMenuOption selectMenuOption() {
        System.out.println("Main Menu");
        System.out.println("=========");
        for (MainMenuOption option : MainMenuOption.values()){
            System.out.println(String.format("%s. %s", option.getValue(), option.getMessage()));
        }
        System.out.println("Select [0-4]");
        int numberOption = Integer.parseInt(console.nextLine());
        return MainMenuOption.fromValue(numberOption);

    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public String enterToContinue() {
        System.out.println();
        System.out.println("Press ENTER to continue.");
        return console.nextLine();
    }

    public String promptForHostEmail() {
        System.out.println("Enter a host email to view reservations:");
        String email = console.nextLine();
        return email;
    }

    public void displayList(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found. Please try again");
        } else {

            for (Reservation r :reservations) {
                System.out.println(String.format("ID:%s, %s - %s, Guest: %s %s, Email: %s",
                        r.getId(),
                        r.getStartDate(),
                        r.getEndDate(),
                        r.getGuest().getFirstName(),
                        r.getGuest().getLastName(),
                        r.getGuest().getEmail()
                ));
            }
        }
    }

    public String[] promptEmails() {
        String[] emailArr = new String[2];

        System.out.println("Enter the guest email: ");
        String guestEmail = console.nextLine();
        emailArr[0] = guestEmail;

        System.out.println("Enter the host email: ");
        String hostEmail = console.nextLine();
        emailArr[1] = hostEmail;

        return emailArr;

    }

    public LocalDate promptStartDate(){

        System.out.print("Enter the start date in MM/DD/YYYY format:");
        LocalDate startDate = LocalDate.parse(console.nextLine(), formatter);
        return startDate;

    }

    public LocalDate promptEndDate(){
        System.out.print("Enter the end date in MM/DD/YYYY format: ");
        LocalDate endDate = LocalDate.parse(console.nextLine(), formatter);
        return endDate;
    }


    public int promptForResId(String prompt) {
        System.out.println(prompt);
        int resId = Integer.parseInt(console.nextLine());
        return resId;
    }

    public boolean confirmMakeRes(LocalDate start, LocalDate end, BigDecimal total){
        displayMessage("Summary:");
        displayMessage("=======");
        displayMessage(String.format("Start: %s", start));
        displayMessage(String.format("End: %s", end));
        displayMessage(String.format("Total: %s", total));
        displayMessage("Is this okay? [y/n]: ");
        boolean confirm = Boolean.parseBoolean(console.nextLine());

        return confirm;

    }

}

