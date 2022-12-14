import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final String input_url = "G:/Prog/Advent_of_code_2022/inputs/day3/input.txt";

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
            String bothInventory = reader.readLine();
            Set<Character> counter = new TreeSet<>();
            int i = 0;
            for (; i < bothInventory.length() / 2; i++)
                counter.add(bothInventory.charAt(i));
            char item;
            do {
                item = bothInventory.charAt(i);
                i++;
            } while (!counter.contains(item));
            total += convertItem(item);
        }
        System.out.println("Sum of common items = " + total);
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        int total = 0;
        while (reader.ready()) {
            String inventory1 = reader.readLine();
            Set<Character> listItem1 = new TreeSet<>();
            for (int i = 0; i < inventory1.length(); i++) {
                char item = inventory1.charAt(i);
                listItem1.add(item);
            }
            String inventory2 = reader.readLine();
            Set<Character> listItem2 = new TreeSet<>();
            for (int i = 0; i < inventory2.length(); i++) {
                char item = inventory2.charAt(i);
                if (listItem1.contains(item))
                listItem2.add(item);
            }
            String inventory3 = reader.readLine();
            char item;
            int i = 0;
            do {
                item = inventory3.charAt(i);
                i++;
            } while (!listItem2.contains(item));
            total += convertItem(item);
        }
        System.out.println("Sum of common items = " + total);
    }

    private static int convertItem(char item) {
        int value;
        if (Character.isLowerCase(item))
            value = item - 'a' + 1;
        else
            value = item - 'A' + 27;
        System.out.println("item = " + item + "; value = " + value);
        return value;
    }
}
