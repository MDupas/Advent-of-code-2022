import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final String input_url = "../inputs/day15/input.txt";
    private static final int TUNING_FACTOR = 4000000;

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
        ArrayList<Rectangle> beaconsLessZone = new ArrayList<>();
        Set<Point> beaconsSet = new HashSet<>();
        while (reader.ready()) {
            String[] sensorAndBeacon = reader.readLine().split("[=,:]");
            int xSensor = Integer.parseInt(sensorAndBeacon[1]), ySensor = Integer.parseInt(sensorAndBeacon[3]),
                    xBeacon = Integer.parseInt(sensorAndBeacon[5]), yBeacon = Integer.parseInt(sensorAndBeacon[7]);
            beaconsLessZone.add(createNoBeaconZone(xSensor, ySensor, xBeacon, yBeacon));
            beaconsSet.add(new Point(xBeacon, yBeacon));
        }
        int row;
        if (input_url.charAt(16) == 't')
            row = 10;
        else if (input_url.charAt(16) == 'i')
            row = 2000000;
        else
            throw new IllegalStateException("Unexpected value, was using char number 16 to change row tested " + input_url);
        int noBeaconRow = noBeaconRow(row, beaconsLessZone).stream().map(Line::length).reduce(Integer::sum).get();
        noBeaconRow -= beaconsSet.stream().filter(point -> point.y == row).count();
        System.out.println("Number of positions without beacon = " + noBeaconRow);
    }

    private static ArrayList<Line> noBeaconRow(int row, ArrayList<Rectangle> beaconsLessZone) {
        Queue<Line> lines = new LinkedList<>();
        for (Rectangle rectangle : beaconsLessZone)
            rectangle.intersectionOnRow(row).ifPresent(lines::add);
        ArrayList<Line> finalLines = new ArrayList<>();
        while (!lines.isEmpty()) {
            Line lookedLine = lines.poll();
            int i = 0;
            while (i < lines.size()) {
                Line currentLine = lines.poll();
                if (lookedLine.mergeable(currentLine)) {
                    lookedLine = lookedLine.merge(currentLine);
                    i = 0;
                } else {
                    lines.add(currentLine);
                    i++;
                }
            }
            finalLines.add(lookedLine);
        }
        return finalLines;
    }

    private static Rectangle createNoBeaconZone(int xSensor, int ySensor, int xBeacon, int yBeacon) {
        int size = Math.abs(Math.max(xBeacon - xSensor, xSensor - xBeacon)) +
                Math.abs(Math.max(yBeacon - ySensor, ySensor - yBeacon));
        return new Rectangle(new Point(xSensor, ySensor - size), new Point(xSensor + size, ySensor),
                new Point(xSensor, ySensor + size), new Point(xSensor - size, ySensor));
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        //Aborted solution because clipping is too hard !
        //Step 1 : create a polygon around the sensor
        //Step 2 : create new polygons that are the intersection with other polygon around the sensor
        //Step 3 : add the number of points inside the polygon, minus the intersection with other polygon
        // plus the intersection with the newly created polygons minus this intersection etc. (only two lists to keep of)
        ArrayList<Rectangle> beaconsLessZone = new ArrayList<>();
        while (reader.ready()) {
            String[] sensorAndBeacon = reader.readLine().split("[=,:]");
            int xSensor = Integer.parseInt(sensorAndBeacon[1]), ySensor = Integer.parseInt(sensorAndBeacon[3]),
                    xBeacon = Integer.parseInt(sensorAndBeacon[5]), yBeacon = Integer.parseInt(sensorAndBeacon[7]);
            beaconsLessZone.add(createNoBeaconZone(xSensor, ySensor, xBeacon, yBeacon));
        }
        int rowMax;
        if (input_url.charAt(16) == 't')
            rowMax = 20;
        else if (input_url.charAt(16) == 'i')
            rowMax = TUNING_FACTOR;
        else
            throw new IllegalStateException("Unexpected value, was using char number 16 to change row tested " + input_url);
        Point beacon = null;
        for (int y = 0; y <= rowMax; y++) {
            if (y % 1000000 == 0)
                System.out.println("Progress at " + y);
            ArrayList<Line> noBeaconLine = noBeaconRow(y, beaconsLessZone);
            if (noBeaconLine.size() > 2 || noBeaconLine.get(0).x1 > 0 || noBeaconLine.get(0).x2 < rowMax) {
                if (noBeaconLine.size() == 1)
                    throw new IllegalStateException("Unexpected case, don't work when x=0 or 4000000");
                int xBeacon;
                if (noBeaconLine.get(0).x2 < 4000000)
                    xBeacon = noBeaconLine.get(0).x2 + 1;
                else
                    xBeacon = noBeaconLine.get(0).x1 - 1;
                beacon = new Point(xBeacon, y);
            }
        }
        assert beacon != null;
        long tuningFrequency = ((long) beacon.x) * TUNING_FACTOR + beacon.y;
        System.out.println("Beacon found. Tuning frequency = " + tuningFrequency);
    }
}
