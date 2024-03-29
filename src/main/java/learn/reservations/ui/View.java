package learn.reservations.ui;

import learn.reservations.data.DataAccessException;
import learn.reservations.models.Reservation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


@Component
public class View {

    private final Scanner console = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public MainMenuOption selectMenuOption() {
        System.out.println("Main Menu");
        System.out.println("=========");
        for (MainMenuOption option : MainMenuOption.values()) {
            System.out.printf("%s. %s%n", option.getValue(), option.getMessage());
        }
        System.out.println("Select [0-4]");
        int numberOption = readInt();
        return MainMenuOption.fromValue(numberOption);
    }

    private int readInt() {
        while (true)
            try {
                return Integer.parseInt(console.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number; please select [0-4]");
            }

    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public String enterToContinue() {
        System.out.println();
        System.out.println("Press ENTER to continue.");
        return console.nextLine();
    }

    public void displayList(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found. Please try again");
        } else {

            for (Reservation r : reservations) {
                System.out.printf("ID:%s, %s - %s, Guest: %s %s, Email: %s%n",
                        r.getId(),
                        r.getStartDate().format(formatter),
                        r.getEndDate().format(formatter),
                        r.getGuest().getFirstName(),
                        r.getGuest().getLastName(),
                        r.getGuest().getEmail()
                );
            }
        }
    }

    public String promptEmails(String message) {

        System.out.printf("Enter %s email:%n", message);
        return console.nextLine();
    }

    public LocalDate promptDate(String message) throws DataAccessException {

        System.out.printf("Enter the %s date in MM/DD/YYYY format: ", message);
//        return LocalDate.parse(console.nextLine(), formatter);
        try {
            LocalDate date = LocalDate.parse(console.nextLine(), formatter);
            return date;
        } catch (Exception ex) {
            throw new DataAccessException("Invalid date. Please enter date in MM/DD/YYYY format.");
        }

    }


    public int promptForResId(String prompt) {
        System.out.println(prompt);
        return Integer.parseInt(console.nextLine());
    }

    public void displaySummary(LocalDate start, LocalDate end, BigDecimal total) {
        displayMessage("Summary:");
        displayMessage("=======");
        displayMessage(String.format("Start: %s", start.format(formatter)));
        displayMessage(String.format("End: %s", end.format(formatter)));
        displayMessage(String.format("Total: $%s", total));

    }

    public boolean confirmRes() {
        String confirm;
        do {
            displayMessage("Is this okay? [y/n]: ");
            confirm = console.nextLine();
        } while (!(confirm.equals("y") || confirm.equals("n")));

        if (confirm.equals("y")) {
            return true;
        }
        return false;
    }

}

