import java.util.ArrayList;

public class Node {
    private final char height;
    ArrayList<Node> neighbours;
    ArrayList<Integer> travelCost;

    public Node(char height) {
        this.height = height;
        this.neighbours = new ArrayList<>();
        this.travelCost = new ArrayList<>();
    }

    public char getHeight() {
        return height;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    public ArrayList<Integer> getTravelCost() {
        return travelCost;
    }

    public void addNeighbourLimited(Node neighbour) {
        if (neighbour.getHeight() - this.getHeight() == 1 ||
            this.getHeight() >= neighbour.getHeight()) {
            this.neighbours.add(neighbour);
            this.travelCost.add(1);
        }
    }
}
