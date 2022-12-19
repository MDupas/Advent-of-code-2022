import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final String input_url = "../inputs/day17/input.txt";

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
        String jets = reader.readLine();
        Set<Point> rockStopped = new HashSet<>();
        int heightMax = 0;
        int fallenRocks = 0;
        int jetIndex = 0;
        while (fallenRocks < 2022) {
            int heightBegin = heightMax + 3;
            ArrayList<Point> newRock = new ArrayList<>();
            switch (fallenRocks % 5) {
                case 0: //  -
                    newRock.add(new Point(2, heightBegin));
                    newRock.add(new Point(3, heightBegin));
                    newRock.add(new Point(4, heightBegin));
                    newRock.add(new Point(5, heightBegin));
                    break;
                case 1: //  +
                    newRock.add(new Point(2, heightBegin + 1));
                    newRock.add(new Point(3, heightBegin + 1));
                    newRock.add(new Point(4, heightBegin + 1));
                    newRock.add(new Point(3, heightBegin));
                    newRock.add(new Point(3, heightBegin + 2));
                    break;
                case 2: //  ⅃
                    newRock.add(new Point(2, heightBegin));
                    newRock.add(new Point(3, heightBegin));
                    newRock.add(new Point(4, heightBegin));
                    newRock.add(new Point(4, heightBegin + 1));
                    newRock.add(new Point(4, heightBegin + 2));
                    break;
                case 3: //  I
                    newRock.add(new Point(2, heightBegin));
                    newRock.add(new Point(2, heightBegin + 1));
                    newRock.add(new Point(2, heightBegin + 2));
                    newRock.add(new Point(2, heightBegin + 3));
                    break;
                case 4: //  O
                    newRock.add(new Point(2, heightBegin));
                    newRock.add(new Point(3, heightBegin));
                    newRock.add(new Point(2, heightBegin + 1));
                    newRock.add(new Point(3, heightBegin + 1));
                    break;
            }
            boolean hasFallen = false;
            do {
//                displayRocks(rockStopped, newRock);
                ArrayList<Point> newPositionForRock = new ArrayList<>();
                switch (jets.charAt(jetIndex)) {
                    case '<':
                        for (Point currentPoint : newRock) {
                            Point newPoint = new Point(currentPoint.x - 1, currentPoint.y);
                            if (newPoint.x < 0 || rockStopped.contains(newPoint))
                                break;
                            else
                                newPositionForRock.add(newPoint);
                        }
                        break;
                    case '>':
                        for (Point currentPoint : newRock) {
                            Point newPoint = new Point(currentPoint.x + 1, currentPoint.y);
                            if (newPoint.x > 6 || rockStopped.contains(newPoint))
                                break;
                            else
                                newPositionForRock.add(newPoint);
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + jets.charAt(jetIndex));
                }
                jetIndex = (jetIndex + 1) % jets.length();
                if (newPositionForRock.size() == newRock.size()) {
                    newRock = newPositionForRock;
                }
                newPositionForRock = new ArrayList<>();
                for (Point currentPoint : newRock) {
                    Point newPoint = new Point(currentPoint.x, currentPoint.y - 1);
                    if (newPoint.y < 0 || rockStopped.contains(newPoint)) {
                        hasFallen = true;
                        rockStopped.addAll(newRock);
                        heightMax = Math.max(newRock.stream().max(Comparator.comparingInt(o -> o.y)).get().y + 1, heightMax);
                        break;
                    }
                    else
                        newPositionForRock.add(newPoint);
                }
                if (newPositionForRock.size() == newRock.size()) {
                    newRock = newPositionForRock;
                }
            } while (!hasFallen);
            fallenRocks++;
//            displayRocks(rockStopped, null);
        }

        System.out.println("Rocks has fallen. Max height = " + heightMax);
    }

    private static void displayRocks(Set<Point> rocks, ArrayList<Point> newRock) {
        int y = -1;
        if (newRock != null) {
            int yMax = newRock.stream().max(Comparator.comparingInt(value -> value.y)).get().y;
            int yMin = newRock.stream().min(Comparator.comparingInt(value -> value.y)).get().y;
            for (y = yMax; y >= yMin; y--) {
                System.out.print('|');
                for (int x = 0; x < 7; x++) {
                    Point p = new Point(x, y);
                    if (newRock.contains(p))
                        System.out.print('@');
                    else
                        System.out.print('.');
                }
                System.out.println('|');
            }
        }
        if (rocks.size() > 0) {
            if (newRock == null)
                y = rocks.stream().max(Comparator.comparingInt(value -> value.y)).get().y;
            for (; y >= 0; y--) {
                System.out.print('|');
                for (int x = 0; x < 7; x++) {
                    Point p = new Point(x, y);
                    if (rocks.contains(p))
                        System.out.print('#');
                    else
                        System.out.print('.');
                }
                System.out.println('|');
            }
        }
        System.out.println("+-------+");
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
