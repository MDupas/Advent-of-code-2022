import java.util.*;

public class StateValves {

    public static Map<StateValves, Integer> stateComputed = new HashMap<>();
    private final List<Valve> openedValves;
    private final Set<Valve> closedValves;
    private final Valve currentLocation;

    private final int timeLeft;

    private int bestValue;

    public StateValves(List<Valve> openedValves, Set<Valve> closedValves, Valve currentLocation, int timeLeft) {
        this.openedValves = openedValves;
        this.closedValves = closedValves;
        this.currentLocation = currentLocation;
        this.timeLeft = timeLeft;
    }

    public int getBestValue() {
        return bestValue;
    }

    public List<Valve> getOpenedValves() {
        return openedValves;
    }

    public void generateBestPath() {
        if (closedValves.isEmpty()) {
            this.bestValue = 0;
        }
        int finalValue = 0;
        List<Valve> bestPath = null;
        for (Valve valveToOpen : closedValves) {
            if (openedValves.size() == 0)
                System.out.println("First valve compute : " + valveToOpen.name + "; state computed = " + stateComputed.size());
            if (openedValves.size() == 1)
                System.out.println("WiP with : " + valveToOpen.name + "; state computed = " + stateComputed.size());
            int newTimeLeft = timeLeft - currentLocation.getCostOfTunnel(valveToOpen) - 1;
            int value = newTimeLeft * valveToOpen.flow;
            Set<Valve> newClosedValves = new HashSet<>(closedValves);
            List<Valve> newOpenedValves = new ArrayList<>(openedValves);
            newOpenedValves.add(valveToOpen);
            newClosedValves.remove(valveToOpen);
            StateValves newState = new StateValves(newOpenedValves, newClosedValves, valveToOpen, newTimeLeft);
            if (StateValves.stateComputed.containsKey(newState)) {
                value += stateComputed.get(newState);
            }
            else {
                newState.generateBestPath();
                value += newState.bestValue;
                stateComputed.put(newState, newState.bestValue);
                if (stateComputed.size() > 8000000) {
                    stateComputed.clear();
                    System.out.println("clear");
                }
            }
            if (value > finalValue) {
                finalValue = value;
                bestPath = openedValves;
            }
        }
        this.bestValue = finalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateValves that = (StateValves) o;

        if (timeLeft != that.timeLeft) return false;
        if (!closedValves.equals(that.closedValves)) return false;
        return currentLocation.equals(that.currentLocation);
    }

    @Override
    public int hashCode() {
        int result = closedValves.hashCode();
        result = 31 * result + currentLocation.hashCode();
        result = 31 * result + timeLeft;
        return result;
    }
}
