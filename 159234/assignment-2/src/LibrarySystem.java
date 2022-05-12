import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LibrarySystem implements LibraryOperations {

    private ArrayList<Record> Records = new ArrayList<>();

    public static void main(String[] args) {
        LibrarySystem MasseyLibrary = new LibrarySystem("library.txt");
        LibrarySystem.displayInfo();
        MasseyLibrary.printRecords();

        LibraryHandler e = new LibraryHandler();

        // This acts as the entry into our main menu. This system functions as a set of menus controlled by state.
        e.action(MasseyLibrary);
    }

    LibrarySystem(String records) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(records));
            String expression;
            while((expression = br.readLine()) != null) {
                String[] data = expression.split(",");
                switch(data[0]) {
                    // This is all gross. I should not be allowed to code.
                    case "Book":
                        Records.add(new Book(Integer.parseInt(data[1]), data[2], data[4], Integer.parseInt(data[5]), Integer.parseInt(data[3])));
                        break;
                    case "Journal":
                        Records.add(new Journal(Integer.parseInt(data[1]), data[2], Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[3])));
                        break;
                    case "Movie":
                        Records.add(new Movie(Integer.parseInt(data[1]), data[2], data[4], Integer.parseInt(data[3])));
                        break;
                    default:
                        break;
                }
            }
        }
        catch(IOException e) {
            System.out.println("The file \"" + records + "\" could not be found.");
            System.exit(0);
        }
        
        sortRecords();
    }

    public void printRecords() {
        Records.forEach(Record::shortPrint);
    }

    public void printFullRecords() {
        Records.forEach(Record::longPrint);
    }

    private static void displayInfo() {
        System.out.println("-----------------------------------");
        System.out.println("   Assignment 2  Semester 1 2022   ");
        System.out.println("Submitted by: Niamh, Ferns 21007069");
        System.out.println("-----------------------------------");
        System.out.println();
    }

    @Override
    public ArrayList<Record> search(String phrase) {
        // We filter through each element in the Records array and return either the first one or null if the filter ends.
        ArrayList<Record> found = new ArrayList<>();
        Records.forEach( (r) -> { if(r.getTitle().toLowerCase().contains(phrase.toLowerCase())) found.add(r); });
        return found;
    }
    @Override
    public Record search(int id) {
        // We filter through each element in the Records array and return either the first one or null if the filter ends.
        return Records.stream()
                .filter(r -> r.getID() == id)
                .findFirst()
                .orElse(null);

    }
    @Override
    public void sortRecords() {
        Collections.sort(Records, new RecordComparator());
    }
}