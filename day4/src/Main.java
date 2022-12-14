import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static final String input_url = "../inputs/day4/input.txt";

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input_url));
//            firstPuzzle(reader);
            secondPuzzle(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found at " + input_url);
        } catch (IOException e) {
            System.err.println("I/O error : ");
            e.printStackTrace();
        }
    }

    private static void firstPuzzle(BufferedReader reader) throws IOException {
        int total = 0;
        while (reader.ready()) {
            String[] sectionPair = reader.readLine().split(",");
            String[] sectionString1 = sectionPair[0].split("-");
            Section section1 = new Section (Integer.parseInt(sectionString1[0]), Integer.parseInt(sectionString1[1]));
            String[] sectionString2 = sectionPair[1].split("-");
            Section section2 = new Section (Integer.parseInt(sectionString2[0]), Integer.parseInt(sectionString2[1]));
            if (section1.containOrContained(section2))
                total++;
        }
        System.out.println("Number of range contained in the same pair = " + total);
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        int total = 0;
        while (reader.ready()) {
            String[] sectionPair = reader.readLine().split(",");
            String[] sectionString1 = sectionPair[0].split("-");
            Section section1 = new Section (Integer.parseInt(sectionString1[0]), Integer.parseInt(sectionString1[1]));
            String[] sectionString2 = sectionPair[1].split("-");
            Section section2 = new Section (Integer.parseInt(sectionString2[0]), Integer.parseInt(sectionString2[1]));
            if (section1.overlap(section2))
                total++;
        }
        System.out.println("Number of range overlap in the same pair = " + total);
    }
}
