import java.util.ArrayList;

public class PacketValue extends Packet {
    private final int value;

    public PacketValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Packet o) {
        if (o instanceof PacketValue)
            return Integer.compare(value, ((PacketValue) o) .getValue());
        else
            return super.compareTo(o);
    }

    @Override
    public ArrayList<Packet> getList() {
        ArrayList<Packet> individualList = new ArrayList<>();
        individualList.add(this);
        return individualList;
    }
}
