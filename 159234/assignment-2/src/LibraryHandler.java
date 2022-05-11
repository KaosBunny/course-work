import java.util.ArrayList;
import java.util.Scanner;

public class LibraryHandler {
    public enum States {
        OPEN,
        CLOSED,
        ON_HOLD,
        QUIT
    }
    private States currentState;

    public final void action(LibrarySystem system) {
        Scanner sc = new Scanner(System.in);
        while (currentState != States.QUIT) {
            currentState = States.ON_HOLD;
            System.out.println("\nEnter 'q' to quit,\n's' to sort and display\n'i' to search by ID\nanything else to search by phrase");
            String userIn = sc.next();

            currentState = States.CLOSED;
            Record record;
            switch (userIn) {
                case "q":
                    currentState = States.QUIT;
                    return;

                case "s":
                    system.sortRecords();
                    break;

                case "i":
                    System.out.println("Please enter in an ID to search for.");
                    userIn = sc.next();
                    record = system.search(Integer.parseInt(userIn));
                    if (record == null) {
                        System.out.println("Item with that ID could not be found.");
                        break;
                    }

                    record.shortPrint();
                    System.out.println("Enter s to select or anything else to continue.");
                    userIn = sc.next();
                    if (userIn.equals("s")) action(record);

                    break;

                default:
                    System.out.println("Please enter in a phrase to search for.");
                    userIn = sc.next();
                    ArrayList<Record> options = system.search(userIn);
                    if (options.isEmpty()) {
                        System.out.println("Item with that phrase could not be found.");
                        break;
                    }

                    for (int i = 0; i < options.size(); ++i) {
                        System.out.println("* " + (i + 1) + ":" );
                        options.get(i).shortPrint();
                        System.out.println();
                    }

                    System.out.println("Enter the item number to select the item, or enter any other key to continue searching");
                    userIn = sc.next();

                    if (isValidOption(userIn, options.size())) action(options.get(Integer.parseInt(userIn) - 1));
            }
        }
        sc.close();
    }
    public final void action(Record record) {
        Scanner sc = new Scanner(System.in);
        while(currentState != States.QUIT) {
            System.out.println("\nSelected item is:");
            record.fullPrint();
            currentState = States.ON_HOLD;

            System.out.println("\nEnter " + (record.getStatus() ? "'b' to borrow" : "'r' to return") + " the item, 'a' to rate the item, or anything else to return.");
            String userIn = sc.next();

            currentState = States.CLOSED;
            switch (userIn) {
                case "r":
                    // This is dumb... why would you change the key here. No...
                    currentState = States.QUIT;
                    if (record.getStatus()) break;
                case "b":
                    record.borrow();
                    break;
                case "a":
                    record.rate();
                    break;

                default:
                    currentState = States.QUIT;

            }
        }
        // sc.close(); if I close the scanner here it crashes...
        currentState = States.ON_HOLD;
    }

    private boolean isValidOption(String s, int n) {
        for (int i = 0; i < s.length(); ++i) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return Integer.parseInt(s) <= n;
    }

    public LibraryHandler() {
        currentState = States.OPEN;
    }
}
