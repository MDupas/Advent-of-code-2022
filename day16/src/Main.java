import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final String input_url = "../inputs/day16/test.txt";

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
        Map<String, Valve> valves = new HashMap<>();
        while (reader.ready()) {
            String[] valve = reader.readLine().split("( |=|; |, )");
            String name = valve[1];
            int flowRate = Integer.parseInt(valve[5]);
            String[] tunnels = Arrays.copyOfRange(valve, 10, valve.length);
            valves.put(name, new Valve(name, flowRate, tunnels));
        }
        ArrayList<Valve> valveList = new ArrayList<>();
        for (Valve valve : valves.values()) {
            valve.addTunnel(valves);
            valveList.add(valve);
        }
        for (Valve valve : valves.values()) {
            dijkstra(valve,new ArrayList<>(valveList));
        }
        for (Valve value : valves.values()) {
            value.removeUselessTunnel();
            System.out.println(value);
        }
        Set<Valve> closedValve = valveList.stream().filter(valve -> valve.flow > 0).collect(Collectors.toCollection(HashSet::new));
        for (Valve valve : closedValve) {
            System.out.println(valve.name);
        }
        Valve startValve = valveList.stream().filter(valve -> valve.name.equals("AA")).reduce((valve, valve2) -> valve).get();
        StateValves startingState = new StateValves(new ArrayList<>(), closedValve, startValve, 30);
        startingState.generateBestPath();
        System.out.println(startingState.getBestValue());
//        for (Valve valve : bestPath) {
//            System.out.println(valve.name);
//        }
        //correct order = DD; BB; JJ; HH; EE; CC
    }

    private static void dijkstra(Valve start, List<Valve> valveList) {
        List<Valve> unvaluedMarks = new ArrayList<>();
        ArrayList<Integer> currentCostForUnvaluedMarks = new ArrayList<>();
        Map<Valve,Integer> valuedMarks = new HashMap<>();
        for (Valve valve : valveList) {
            if (valve != start) {
                unvaluedMarks.add(valve);
                currentCostForUnvaluedMarks.add(Integer.MAX_VALUE);
            } else
                valuedMarks.put(valve, 0);
        }
        Valve examinedValve = start;
        do {
            for (int i = 0; i < examinedValve.tunnels.size(); i++) {
                Valve neighbour = examinedValve.tunnels.get(i);
                if (valuedMarks.containsKey(neighbour))
                    continue;
                int cost;
                if (examinedValve.getTunnelsCost() == null)
                    cost = 1 + valuedMarks.get(examinedValve);
                else
                    cost = examinedValve.getTunnelsCost().get(i) + valuedMarks.get(examinedValve);
                int index = unvaluedMarks.indexOf(neighbour);
                currentCostForUnvaluedMarks.set(index, Math.min(cost, currentCostForUnvaluedMarks.get(index)));
            }

            int minCost = currentCostForUnvaluedMarks.stream().min(Integer::compare).get();
            examinedValve = unvaluedMarks.remove(currentCostForUnvaluedMarks.indexOf(minCost));
            currentCostForUnvaluedMarks.remove(Integer.valueOf(minCost));
            valuedMarks.put(examinedValve, minCost);
        } while (!unvaluedMarks.isEmpty());
        start.updateTunnel(valuedMarks);
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
