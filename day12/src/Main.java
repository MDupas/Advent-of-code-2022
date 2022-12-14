import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String input_url = "../inputs/day12/input.txt";

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
        char startMark = 'S', endMark = 'E';
        Node start = null, end = null;
        ArrayList<ArrayList<Node>> marks = new ArrayList<>();
        ArrayList<Node> unvaluedMarks = new ArrayList<>();
        ArrayList<Integer> currentCostForUnvaluedMarks = new ArrayList<>();
        Map<Node,Integer> valuedMarks = new HashMap<>();
        int lineCounter = 0;
        while (reader.ready()) {
            String lineOfMarks = reader.readLine();
            ArrayList<Node> currentLineMark = new ArrayList<>();
            marks.add(currentLineMark);
            for (int i = 0; i < lineOfMarks.length(); i++) {
                char mark = lineOfMarks.charAt(i);
                Node newMark;
                if (mark == startMark) {
                    newMark = new Node('a');
                    start = newMark;
                    valuedMarks.put(newMark, 0);
                } else if (mark == endMark) {
                    newMark = new Node('z');
                    end = newMark;
                    unvaluedMarks.add(newMark);
                    currentCostForUnvaluedMarks.add(Integer.MAX_VALUE);
                } else {
                    newMark = new Node(mark);
                    unvaluedMarks.add(newMark);
                    currentCostForUnvaluedMarks.add(Integer.MAX_VALUE); //only difference with secondPuzzle...
                }
                currentLineMark.add(newMark);
                if (lineCounter > 0) {
                    Node neighbourMark = marks.get(lineCounter - 1).get(i);
                    newMark.addNeighbourLimited(neighbourMark);
                    neighbourMark.addNeighbourLimited(newMark);
                }
                if (i > 0) {
                    Node neighbourMark = marks.get(lineCounter).get(i - 1);
                    newMark.addNeighbourLimited(neighbourMark);
                    neighbourMark.addNeighbourLimited(newMark);
                }
            }
            lineCounter++;
        }

        int result = dijkstra(start,end,unvaluedMarks,currentCostForUnvaluedMarks,valuedMarks);
        System.out.println("Shortest path to the end found with a cost of : " + result);
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        char startMark = 'S', endMark = 'E';
        Node start = null, end = null;
        ArrayList<ArrayList<Node>> marks = new ArrayList<>();
        ArrayList<Node> unvaluedMarks = new ArrayList<>();
        ArrayList<Integer> currentCostForUnvaluedMarks = new ArrayList<>();
        Map<Node,Integer> valuedMarks = new HashMap<>();
        int lineCounter = 0;
        while (reader.ready()) {
            String lineOfMarks = reader.readLine();
            ArrayList<Node> currentLineMark = new ArrayList<>();
            marks.add(currentLineMark);
            for (int i = 0; i < lineOfMarks.length(); i++) {
                char mark = lineOfMarks.charAt(i);
                Node newMark;
                if (mark == startMark) {
                    newMark = new Node('a');
                    start = newMark;
                    valuedMarks.put(newMark, 0);
                } else if (mark == endMark) {
                    newMark = new Node('z');
                    end = newMark;
                    unvaluedMarks.add(newMark);
                    currentCostForUnvaluedMarks.add(Integer.MAX_VALUE);
                } else {
                    newMark = new Node(mark);
                    unvaluedMarks.add(newMark);
                    if (newMark.getHeight() == 'a')
                        currentCostForUnvaluedMarks.add(0); //only difference with firstPuzzle...
                    else
                        currentCostForUnvaluedMarks.add(Integer.MAX_VALUE);
                }
                currentLineMark.add(newMark);
                if (lineCounter > 0) {
                    Node neighbourMark = marks.get(lineCounter - 1).get(i);
                    newMark.addNeighbourLimited(neighbourMark);
                    neighbourMark.addNeighbourLimited(newMark);
                }
                if (i > 0) {
                    Node neighbourMark = marks.get(lineCounter).get(i - 1);
                    newMark.addNeighbourLimited(neighbourMark);
                    neighbourMark.addNeighbourLimited(newMark);
                }
            }
            lineCounter++;
        }
        int result = dijkstra(start,end,unvaluedMarks,currentCostForUnvaluedMarks,valuedMarks);
        System.out.println("Shortest path to the end found with a cost of : " + result);
    }

    private static int dijkstra(Node start, Node end, ArrayList<Node> unvaluedMarks, ArrayList<Integer> currentCostForUnvaluedMarks, Map<Node,Integer> valuedMarks) {
        Node examinedNode = start;
        do {
            for (int i = 0; i < examinedNode.getNeighbours().size(); i++) {
                Node neighbour = examinedNode.getNeighbours().get(i);
                if (valuedMarks.containsKey(neighbour))
                    continue;
                int cost = examinedNode.getTravelCost().get(i) + valuedMarks.get(examinedNode);
                int index = unvaluedMarks.indexOf(neighbour);
                currentCostForUnvaluedMarks.set(index, Math.min(cost, currentCostForUnvaluedMarks.get(index)));
            }

            int minCost = currentCostForUnvaluedMarks.stream().min(Integer::compare).get();
            examinedNode = unvaluedMarks.remove(currentCostForUnvaluedMarks.indexOf(minCost));
            currentCostForUnvaluedMarks.remove(Integer.valueOf(minCost));
            valuedMarks.put(examinedNode, minCost);
        } while (!unvaluedMarks.isEmpty());
        return valuedMarks.get(end);
    }

    private static void displayMarks(ArrayList<ArrayList<Node>> marks, Map<Node, Integer> valuedMarks) {
        for (ArrayList<Node> line : marks) {
            for (Node mark : line) {
                if (valuedMarks.containsKey(mark)) {
                    char upperCase = (char) (mark.getHeight() - (97 - 65));
                    System.out.print(upperCase);
                } else
                    System.out.print(mark.getHeight());
            }
            System.out.println();
        }
    }
}
