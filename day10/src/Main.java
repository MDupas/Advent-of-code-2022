import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    private static final String input_url = "../inputs/day10/input.txt";

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

    private static Map<Integer, Integer> createRegisterXStates(BufferedReader reader) throws IOException {
        String noopCommand = "noop", addxCommand = "addx";
        Map<Integer,Integer> stateList = new TreeMap<>();
        int cycle = 1, registerX = 1;
        stateList.put(cycle,registerX);
        while (reader.ready()) {
            String[] command = reader.readLine().split(" ");
            if (command[0].equals(noopCommand)) {
                cycle++;
            } else if (command[0].equals(addxCommand)) {
                int increase = Integer.parseInt(command[1]);
                for (int newCycle = cycle + 2; cycle < newCycle; cycle++) {
                    stateList.put(cycle, registerX);
                }
                registerX += increase;
            } else
                throw new IllegalStateException("Unexpected value: " + command[0]);
            stateList.put(cycle, registerX);
        }
        return stateList;
    }

    private static void firstPuzzle(BufferedReader reader) throws IOException {
        Map<Integer, Integer> stateList = createRegisterXStates(reader);
        int result = 0;
        for (int i = 20; i < 221; i+=40) {
            int signalStrength = i * stateList.get(i);
            System.out.println("cycle = " + i + "; value = " + stateList.get(i));
            result += signalStrength;
        }
        System.out.println("Sum of interesting signal strength = " + result);
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        Map<Integer,Integer> stateList = createRegisterXStates(reader);
        for (int i = 1; i < 241; i++) {
            if (i % 40 == 1)
                System.out.println();
            int middleSprite = stateList.get(i), leftSprite = middleSprite-1, rightSprite = middleSprite+1;
            int pixelDrawn = (i - 1) % 40;
            if (pixelDrawn == middleSprite || pixelDrawn == leftSprite || pixelDrawn == rightSprite)
                System.out.print('#');
            else
                System.out.print('.');
        }
    }
}
