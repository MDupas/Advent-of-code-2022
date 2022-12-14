import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static final String input_url = "../inputs/day7/input.txt";
    static int size_limit = 100000;
    static int space_limit = 40000000;

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

    private static Directory createTreeDirectory(BufferedReader reader) throws IOException {
        Directory source = new Directory("/");
        Directory currentDirectory = source;
        while (reader.ready()) {
            String line = reader.readLine();
            if (line.charAt(0) == '$') {
                String[] command = line.split(" ");
                String function = command[1];
                if (function.equals("cd")) {
                    String argument = command[2];
                    if (argument.equals("/"))
                        currentDirectory = source;
                    else if (argument.equals(".."))
                        currentDirectory = currentDirectory.parent;
                    else
                        currentDirectory = currentDirectory.getOrCreateChildDirectory(argument);
                }
            }
            else {
                String[] file = line.split(" ");
                String elementFirst = file[0], name = file[1];
                if (!elementFirst.equals("dir"))
                    currentDirectory.addNewData(name, Integer.parseInt(elementFirst));
            }
        }
        return source;
    }

    private static void firstPuzzle(BufferedReader reader) throws IOException {
        Directory sourceDirectory = createTreeDirectory(reader);
        System.out.println("Directories computed. Sum of small files = " + sourceDirectory.getLimitSize());
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        Directory sourceDirectory = createTreeDirectory(reader);
        int spaceToRemove = sourceDirectory.getSize() - space_limit;
        System.out.println("Space to remove = " + spaceToRemove);
        System.out.println("Directories computed. Smallest directory to remove = " + sourceDirectory.smallestAboveSize(spaceToRemove));
    }
}
