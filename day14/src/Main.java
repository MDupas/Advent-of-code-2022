import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final String input_url = "../inputs/day14/input.txt";

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
        Set<Point> obstacleSet = generateRocks(reader);
        displayRocks(obstacleSet);
        int sandCounter = 0;
        Stack<Point> lastPos = new Stack<>();
        int maxY = obstacleSet.stream().max(Comparator.comparingInt(Point::getY)).orElseThrow().getY();
        boolean abyssFall = false;
        while (!abyssFall) {
            Point sand;
            if (lastPos.empty())
                sand = new Point(500, 0);
            else {
                sand = lastPos.pop();
            }
            while (true) {
                if (sand.getY() > maxY)  {
                    abyssFall = true;
                    break;
                } else if (!obstacleSet.contains(new Point(sand.getX(), sand.getY() + 1))) {
                    lastPos.add(sand);
                    sand = new Point(sand.getX(), sand.getY() + 1);
                } else if (!obstacleSet.contains(new Point(sand.getX() - 1, sand.getY() + 1))) {
                    lastPos.add(sand);
                    sand = new Point(sand.getX() - 1, sand.getY() + 1);
                } else if (!obstacleSet.contains(new Point(sand.getX() + 1, sand.getY() + 1))) {
                    lastPos.add(sand);
                    sand = new Point(sand.getX() + 1, sand.getY() + 1);
                } else {
                    if (!obstacleSet.contains(sand)) {
                        sandCounter++;
                        obstacleSet.add(sand);
                    }
                    break;
                }
            }
        }

        System.out.println("Total number of sand falling : " + sandCounter);
    }

    private static Set<Point> generateRocks(BufferedReader reader) throws IOException {
        Set<Point> rockSet = new HashSet<>();
        while (reader.ready()) {
            String[] points = reader.readLine().split("->");
            Point lastRock = null;
            for (String point : points) {
                String[] coordinates = point.trim().split(",");
                Point newRock = new Point(Integer.parseInt(coordinates[0]),
                        Integer.parseInt(coordinates[1]));
                rockSet.add(newRock);
                if (lastRock != null) {
                    if (lastRock.getX() != newRock.getX()) {
                        for (int x = Math.min(lastRock.getX(), newRock.getX());
                             x <= Math.max(lastRock.getX(), newRock.getX()); x++) {
                            Point linePoint = new Point(x, newRock.getY());
                            rockSet.add(linePoint);
                        }
                    } else {
                        for (int y = Math.min(lastRock.getY(), newRock.getY());
                             y <= Math.max(lastRock.getY(), newRock.getY()); y++) {
                            Point linePoint = new Point(newRock.getX(), y);
                            rockSet.add(linePoint);
                        }
                    }
                }
                lastRock = newRock;
            }
        }
        return rockSet;
    }

    private static void displayRocks(Set<Point> rockSet) {
        int minX = rockSet.stream().min(Comparator.comparingInt(Point::getX)).orElseThrow().getX();
        int minY = rockSet.stream().min(Comparator.comparingInt(Point::getY)).orElseThrow().getY();
        int maxX = rockSet.stream().max(Comparator.comparingInt(Point::getX)).orElseThrow().getX();
        int maxY = rockSet.stream().max(Comparator.comparingInt(Point::getY)).orElseThrow().getY();
        System.out.print("__");
        for (int i = minX; i <= maxX; i++) {
            System.out.print((i % 10));
        }
        System.out.println();
        for (int y = minY; y <= maxY; y++) {
            System.out.print(y + ' ');
            for (int x = minX; x <= maxX; x++) {
                Point currentPoint = new Point(x,y);
                if (rockSet.contains(currentPoint))
                    System.out.print('#');
                else
                    System.out.print('.');
            }
            System.out.println();
        }
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        Set<Point> obstacleSet = generateRocks(reader);
        displayRocks(obstacleSet);
        int sandCounter = 0;
        Stack<Point> lastPos = new Stack<>();
        int maxY = obstacleSet.stream().max(Comparator.comparingInt(Point::getY)).orElseThrow().getY();
        Point source = new Point(500, 0);
        while (!obstacleSet.contains(source)) {
            Point sand;
            if (lastPos.empty())
                sand = new Point(500, 0);
            else {
                sand = lastPos.pop();
            }
            while (true) {
                if (sand.getY() == maxY + 1)  {
                    sandCounter++;
                    obstacleSet.add(sand);
                    break;
                } else if (!obstacleSet.contains(new Point(sand.getX(), sand.getY() + 1))) {
                    lastPos.add(sand);
                    sand = new Point(sand.getX(), sand.getY() + 1);
                } else if (!obstacleSet.contains(new Point(sand.getX() - 1, sand.getY() + 1))) {
                    lastPos.add(sand);
                    sand = new Point(sand.getX() - 1, sand.getY() + 1);
                } else if (!obstacleSet.contains(new Point(sand.getX() + 1, sand.getY() + 1))) {
                    lastPos.add(sand);
                    sand = new Point(sand.getX() + 1, sand.getY() + 1);
                } else {
                    if (!obstacleSet.contains(sand)) {
                        sandCounter++;
                        obstacleSet.add(sand);
                    }
                    break;
                }
            }
        }

        System.out.println("Total number of sand falling : " + sandCounter);
    }
}
