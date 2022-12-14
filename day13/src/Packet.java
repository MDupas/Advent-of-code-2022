import java.util.ArrayList;

public abstract class Packet implements Comparable<Packet> {

    public abstract ArrayList<Packet> getList();

    @Override
    public int compareTo(Packet o) {
        for (int i = 0; i < getList().size(); i++) {
            if (o.getList().size() <= i)
                return 1;
            else {
                int comparison = getList().get(i).compareTo(o.getList().get(i));
                if (comparison != 0)
                    return comparison;
            }
        }
        return getList().size() - o.getList().size();
    }
}
