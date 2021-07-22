package learn.reservations.ui;

import learn.reservations.models.Reservation;

import java.util.List;
import java.util.Scanner;

public class View {

    private Scanner console = new Scanner(System.in);

    public MainMenuOption selectMenuOption() {
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

    public String promptForHostEmail() {
        System.out.println("Enter a host email to view reservations:");
        String email = console.nextLine();
        return email;
    }

    public void displayList(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found. Please try again");}

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

