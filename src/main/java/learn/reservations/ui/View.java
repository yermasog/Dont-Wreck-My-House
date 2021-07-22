package learn.reservations.ui;

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


}
