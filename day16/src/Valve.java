import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Valve {
    final String name;
    final int flow;
    final ArrayList<Valve> tunnels;
    private ArrayList<Integer> tunnelsCost;
    private Map<Valve, Integer> tunnelsMap;
    private final String[] futureTunnels;

    public Valve(String name, int flow, String[] futureTunnels) {
        this.name = name;
        this.flow = flow;
        this.futureTunnels = futureTunnels;
        this.tunnels = new ArrayList<>();
    }

    public ArrayList<Integer> getTunnelsCost() {
        return tunnelsCost;
    }

    public int getCostOfTunnel(Valve valve) {
        return tunnelsMap.get(valve);
    }

    public void updateTunnel(Map<Valve, Integer> indirectTunnels) {
        this.tunnelsCost = new ArrayList<>();
        tunnels.clear();
        for (Map.Entry<Valve, Integer> destinationCost : indirectTunnels.entrySet()) {
            if (destinationCost.getKey().flow > 0) {
                tunnels.add(destinationCost.getKey());
                tunnelsCost.add(destinationCost.getValue());
            }
        }
    }

    public void removeUselessTunnel() {
        Map<Valve,Integer> tunnelsMap = new HashMap<>();
        for (int i = 0; i < tunnels.size(); i++) {
            if (tunnels.get(i).flow == 0 || tunnels.get(i) == this) {
                tunnels.remove(i);
                tunnelsCost.remove(i);
                i--;
            } else {
                tunnelsMap.put(tunnels.get(i), tunnelsCost.get(i));
            }
        }
        this.tunnelsMap = tunnelsMap;
    }

    public void addTunnel(Map<String, Valve> valves) {
        for (String tunnel : futureTunnels)
            tunnels.add(valves.get(tunnel));
    }

    public void addTunnel(Valve valve) {
        tunnels.add(valve);
    }

    @Override
    public String toString() {
        return "Valve{" +
                "name='" + name + '\'' +
                ", flow=" + flow +
                ", tunnels=" + displayTunnel() +
                ", tunnelsCost=" + tunnelsCost +
                ", futureTunnels=" + Arrays.toString(futureTunnels) +
                '}';
    }

    private String displayTunnel() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Valve tunnel : tunnels) {
            stringBuilder.append(tunnel.name).append(",");;
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Valve valve = (Valve) o;

        return name.equals(valve.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
