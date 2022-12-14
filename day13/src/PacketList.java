import java.util.ArrayList;

public class PacketList extends Packet {
    private final ArrayList<Packet> packets;

    public PacketList(ArrayList<Packet> packets) {
        this.packets = packets;
    }

    public PacketList() {
        this(new ArrayList<>());
    }

    public void addPacket(Packet packet) {
        packets.add(packet);
    }

    @Override
    public ArrayList<Packet> getList() {
        return packets;
    }
}
