import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Main {

    private static final String input_url = "../inputs/day5/input.txt";

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

    private static void executerCrateMover(BufferedReader reader, boolean isModel9001) throws IOException {
        String stackLine = reader.readLine();
        ArrayList<Stack<Character>> stacksOfCrates = new ArrayList<>();
        for (int i = 0; i < stackLine.length(); i+=4)
            stacksOfCrates.add(new Stack<>());

        do {
            char currentCrate;
            for (int i = 1, j = 0; i < stackLine.length(); i+=4, j++) {
                currentCrate = stackLine.charAt(i);
                if (Character.isUpperCase(currentCrate))
                    stacksOfCrates.get(j).insertElementAt(currentCrate, 0);
            }
            stackLine = reader.readLine();
        } while (!Character.isDigit(stackLine.charAt(1)));
        reader.readLine();

        while (reader.ready()) {
            String movement = reader.readLine();
            makeAMove(movement, stacksOfCrates, isModel9001);
        }

        System.out.print("Crates at the top = ");
        for (Stack<Character> stacksOfCrate : stacksOfCrates) {
            System.out.print(stacksOfCrate.peek());
        }
    }

    private static void makeAMove(String movement, ArrayList<Stack<Character>> stackLine, boolean isModel9001) {
        String[] movementSplit = movement.split(" ");
        int sizeMovement = Integer.parseInt(movementSplit[1]);
        Stack<Character> stackOrigin = stackLine.get(Integer.parseInt(movementSplit[3])-1);
        Stack<Character> stackDestination = stackLine.get(Integer.parseInt(movementSplit[5])-1);
        if (isModel9001) {
            Stack<Character> stackBuffer = new Stack<>();
            for (int i = 0; i < sizeMovement; i++)
                stackBuffer.push(stackOrigin.pop());
            for (int i = 0; i < sizeMovement; i++)
                stackDestination.push(stackBuffer.pop());
        }
        else
            for (int i = 0; i < sizeMovement; i++)
                stackDestination.push(stackOrigin.pop());
    }

    private static void firstPuzzle(BufferedReader reader) throws IOException {
        executerCrateMover(reader, false);
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        executerCrateMover(reader, true);
    }
}
