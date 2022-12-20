import java.util.*;

public class StateValves {

    public static Map<StateValves, Integer> stateComputed = new HashMap<>();
    private final Set<Valve> closedValves;
    private final Valve currentLocation;
    private final Valve elephantLocation;
    private final int myTimeBeforeAction;
    private final int elephantTimeBeforeAction;
    private final int timeLeft;

    private int bestValue;

    public StateValves(Set<Valve> closedValves, Valve currentLocation, Valve elephantLocation, int myTimeBeforeAction, int elephantTimeBeforeAction, int timeLeft) {
        this.closedValves = closedValves;
        this.currentLocation = currentLocation;
        this.elephantLocation = elephantLocation;
        this.myTimeBeforeAction = myTimeBeforeAction;
        this.elephantTimeBeforeAction = elephantTimeBeforeAction;
        this.timeLeft = timeLeft;
    }

    public StateValves(Set<Valve> closedValves, Valve currentLocation, int timeLeft) {
        this(closedValves, currentLocation, null, -1, -1, timeLeft);
    }

    public int getBestValue() {
        return bestValue;
    }

    public void generateBestPath() {
        if (elephantLocation == null)
            generateBestPathOnePerson();
        else
            generateBestPathTwoPerson();
    }

    private void generateBestPathTwoPerson() {
        if (closedValves.isEmpty() || timeLeft < 2) {
            this.bestValue = 0;
            return;
        }
        int finalValue = 0;

        for (Valve valveToOpen : closedValves) {
            if (closedValves.size() == Main.maxValve)
                System.out.println("First valve compute : " + valveToOpen.name + "; state computed = " + stateComputed.size());
            if (closedValves.size() == Main.maxValve - 1)
                System.out.println("WiP with : " + valveToOpen.name + "; state computed = " + stateComputed.size());
            int valveCost;
            if (myTimeBeforeAction == 0)
                valveCost = currentLocation.getCostOfTunnel(valveToOpen) + 1;
            else
                valveCost = elephantLocation.getCostOfTunnel(valveToOpen) + 1;
            if (timeLeft - valveCost < 0)
                continue;
            int value = (timeLeft - valveCost) * valveToOpen.flow;
            Set<Valve> newClosedValves = new HashSet<>(closedValves);
            newClosedValves.remove(valveToOpen);
            StateValves newState;
            int timeRemove;
            if (myTimeBeforeAction == 0)
                timeRemove = Math.min(valveCost, elephantTimeBeforeAction);
            else
                timeRemove = Math.min(myTimeBeforeAction, valveCost);
            if (timeLeft <= timeRemove) {
                if (finalValue < value)
                    finalValue = value;
                continue;
            }
            if (myTimeBeforeAction == 0) {
                newState = new StateValves(newClosedValves, valveToOpen, elephantLocation,
                        valveCost - timeRemove, elephantTimeBeforeAction - timeRemove, timeLeft - timeRemove);
            }
            else {
                newState = new StateValves(newClosedValves, currentLocation, valveToOpen,
                        myTimeBeforeAction - timeRemove, valveCost - timeRemove, timeLeft - timeRemove);
            }
            if (StateValves.stateComputed.containsKey(newState))
                value += stateComputed.get(newState);
            else {
                if (newState.timeLeft >= 0) {
                    newState.generateBestPath();
                    value += newState.bestValue;
                }
                else
                    value = 0;
                stateComputed.put(newState, newState.bestValue);
                if (stateComputed.size() > 9000000) {
                    stateComputed.clear();
                    System.out.println("clear");
                }
            }
            if (value > finalValue)
                finalValue = value;
        }
        //special case : do nothing
        if (myTimeBeforeAction < timeLeft && elephantTimeBeforeAction < timeLeft) {
            StateValves newState;
            int value;
            if (myTimeBeforeAction == 0) {
                newState = new StateValves(closedValves, currentLocation, elephantLocation, timeLeft - elephantTimeBeforeAction, 0, timeLeft - elephantTimeBeforeAction);
            } else {
                newState = new StateValves(closedValves, currentLocation, elephantLocation, 0, timeLeft - myTimeBeforeAction, timeLeft - myTimeBeforeAction);
            }
            if (StateValves.stateComputed.containsKey(newState))
                value = stateComputed.get(newState);
            else {
                newState.generateBestPath();
                value = newState.bestValue;
                stateComputed.put(newState, newState.bestValue);
                if (stateComputed.size() > 9000000) {
                    stateComputed.clear();
                    System.out.println("clear");
                }
            }
            if (value > finalValue)
                finalValue = value;
        }

        this.bestValue = finalValue;
    }

    private void generateBestPathOnePerson() {
        if (closedValves.isEmpty()) {
            this.bestValue = 0;
        }
        int finalValue = 0;
        for (Valve valveToOpen : closedValves) {
            if (closedValves.size() == Main.maxValve)
                System.out.println("First valve compute : " + valveToOpen.name + "; state computed = " + stateComputed.size());
            if (closedValves.size() == Main.maxValve - 1)
                System.out.println("WiP with : " + valveToOpen.name + "; state computed = " + stateComputed.size());
            int newTimeLeft = timeLeft - currentLocation.getCostOfTunnel(valveToOpen) - 1;
            if (newTimeLeft < 0)
                continue;
            int value = newTimeLeft * valveToOpen.flow;
            Set<Valve> newClosedValves = new HashSet<>(closedValves);
            newClosedValves.remove(valveToOpen);
            StateValves newState = new StateValves(newClosedValves, valveToOpen, newTimeLeft);
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
//        if (myTimeBeforeAction == that.elephantTimeBeforeAction && elephantTimeBeforeAction == that.myTimeBeforeAction &&
//        currentLocation.equals(that.elephantLocation) && elephantLocation.equals(that.currentLocation)) return true;
        if (myTimeBeforeAction != that.myTimeBeforeAction) return false;
        if (elephantTimeBeforeAction != that.elephantTimeBeforeAction) return false;
        if (!currentLocation.equals(that.currentLocation)) return false;
        return Objects.equals(elephantLocation, that.elephantLocation);
    }

    @Override
    public int hashCode() {
        int result = closedValves.hashCode();
        result = 31 * result + currentLocation.hashCode();
        result = 31 * result + (elephantLocation != null ? elephantLocation.hashCode() : 0);
        result = 31 * result + myTimeBeforeAction;
        result = 31 * result + elephantTimeBeforeAction;
        result = 31 * result + timeLeft;
        return result;
    }

    @Override
    public String toString() {
        return "StateValves{" +
                ", closedValves=" + closedValves +
                ", currentLocation=" + currentLocation +
                ", elephantLocation=" + elephantLocation +
                ", myTimeBeforeAction=" + myTimeBeforeAction +
                ", elephantTimeBeforeAction=" + elephantTimeBeforeAction +
                ", timeLeft=" + timeLeft +
                ", bestValue=" + bestValue +
                '}';
    }
}
