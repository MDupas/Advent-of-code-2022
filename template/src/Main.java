import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static final String input_url = "../inputs/day/test.txt";

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input_url));
            firstPuzzle(reader);
//            secondPuzzle(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found at " + input_url);
        } catch (IOException e) {
            System.err.println("I/O error : ");
            e.printStackTrace();
        }
    }

    private static void firstPuzzle(BufferedReader reader) throws IOException {
        ArrayList<Integer> lot_of_arguments = new ArrayList<>();
        String[] arguments = reader.readLine().split(",");
        for (String s : arguments) {
            lot_of_arguments.add(Integer.parseInt(s));
        }
        while (reader.ready()) {
            int input = Integer.parseInt(reader.readLine());
        }
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        ArrayList<Integer> lot_of_arguments = new ArrayList<>();
        String[] arguments = reader.readLine().split(",");
        for (String s : arguments) {
            lot_of_arguments.add(Integer.parseInt(s));
        }
        while (reader.ready()) {
            int input = Integer.parseInt(reader.readLine());
        }
    }
}
