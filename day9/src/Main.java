import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Main {

    private static final String input_url = "../inputs/day9/input.txt";

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
        ArrayList<Motion> motions = computeMotions(reader);
        int xTail = 0, yTail = 0, xHead = 0, yHead = 0;
        Set<Point> points = new HashSet<>();
        for (Motion motion : motions) {
            for (int i = 0; i < motion.steps; i++) {
                switch (motion.direction) {
                    case Up:
                        xHead--;
                        break;
                    case Right:
                        yHead++;
                        break;
                    case Down:
                        xHead++;
                        break;
                    case Left:
                        yHead--;
                        break;
                }
                Point tailPosition = newKnotPosition(xTail, yTail, xHead, yHead);
                xTail = tailPosition.x;
                yTail = tailPosition.y;
                points.add(tailPosition);
            }
        }
        System.out.println("Motions completed. Tail total visit = " + points.size());
    }

    private static ArrayList<Motion> computeMotions(BufferedReader reader) throws IOException {
        ArrayList<Motion> motions = new ArrayList<>();
        while (reader.ready()) {
            String[] motion = reader.readLine().split(" ");
            Motion.Direction direction = null;
            switch (motion[0].charAt(0)) {
                case 'R':
                    direction = Motion.Direction.Right;
                    break;
                case 'U':
                    direction = Motion.Direction.Up;
                    break;
                case 'L':
                    direction = Motion.Direction.Left;
                    break;
                case 'D':
                    direction = Motion.Direction.Down;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }
            int steps = Integer.parseInt(motion[1]);
            motions.add(new Motion(direction, steps));
        }
        return motions;
    }

    private static Point newKnotPosition(int xTail, int yTail, int xHead, int yHead) {
        int newXTail = xTail, newYTail = yTail;
        if (xTail - xHead == 2) {
            newXTail = xTail - 1;
            newYTail = yHead;
        } else if (xHead - xTail == 2) {
            newXTail = xTail + 1;
            newYTail = yHead;
        }
        if (yHead - yTail == 2) {
            if (newXTail == xTail)
                newXTail = xHead;
            newYTail = yTail + 1;
        } else if (yTail - yHead == 2) {
            if (newXTail == xTail)
                newXTail = xHead;
            newYTail = yTail - 1;
        }
        return new Point(newXTail, newYTail);
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        ArrayList<Motion> motions = computeMotions(reader);
        ArrayList<Point> knots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            knots.add(new Point(0, 0));
        }
        Set<Point> points = new HashSet<>();
        for (Motion motion : motions) {
            for (int i = 0; i < motion.steps; i++) {
                Point head = knots.get(0), newHead;
                switch (motion.direction) {
                    case Up:
                        newHead = new Point(head.x - 1, head.y);
                        break;
                    case Right:
                        newHead = new Point(head.x, head.y + 1);
                        break;
                    case Down:
                        newHead = new Point(head.x + 1, head.y);
                        break;
                    case Left:
                        newHead = new Point(head.x, head.y - 1);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + motion.direction);
                }
                knots.set(0, newHead);
                for (int j = 1; j < 10; j++) {
                    Point knotTail = knots.get(j), knotHead = knots.get(j-1);
                    Point newKnotPosition = newKnotPosition(knotTail.x, knotTail.y, knotHead.x, knotHead.y);
                    knots.set(j, newKnotPosition);
                }
//                displayRope(knots);
                points.add(knots.get(knots.size()-1));
            }
        }
        System.out.println("Motions completed. Tail total visit with 10 knots = " + points.size());
    }

    private static void displayRope(ArrayList<Point> knots) {
        int xMin = knots.stream().min(Comparator.comparingInt(o -> o.x)).get().x;
        int yMin = knots.stream().min(Comparator.comparingInt(o -> o.y)).get().y;
        int xMax = knots.stream().max(Comparator.comparingInt(o -> o.x)).get().x;
        int yMax = knots.stream().max(Comparator.comparingInt(o -> o.y)).get().y;
        for (int i = xMin-1; i < xMax+2; i++) {
            for (int j = yMin-1; j < yMax+2; j++) {
                if (knots.contains(new Point(i,j)))
                    System.out.print(knots.indexOf(new Point(i,j)));
                else
                    System.out.print('.');
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
}
