import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Main {

    private static final String input_url = "../inputs/day13/input.txt";

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
        int index = 1;
        int indexSum = 0;
        while (reader.ready()) {
            String packet1String = reader.readLine();
            Packet packet1 = generatePacket(packet1String);
            String packet2String = reader.readLine();
            Packet packet2 = generatePacket(packet2String);
            if (packet1.compareTo(packet2) < 0)
                indexSum += index;
            index++;
            reader.readLine();
        }
        System.out.println("Packet computed. Sum of their index = " + indexSum);
    }

    private static Packet generatePacket(String packetString) {
        Stack<PacketList> nestedPacket = new Stack<>();
        for (int i = 0; i < packetString.length()-1; i++) {
            char element = packetString.charAt(i);
            if (element == '[') {
                nestedPacket.push(new PacketList());
            } else if (element == ']') {
                Packet packetDone = nestedPacket.pop();
                nestedPacket.peek().addPacket(packetDone);
            } else if (element == ',') {
            } else if (Character.isDigit(element)) {
                int indexEndNumber = i+1;
                for (int j = i+1; j < packetString.length(); j++) {
                    indexEndNumber = j;
                    if (!Character.isDigit(packetString.charAt(j)))
                        break;
                }
                int value = Integer.parseInt(packetString.substring(i, indexEndNumber));
                Packet newPacket = new PacketValue(value);
                nestedPacket.peek().addPacket(newPacket);
            } else {
                throw new IllegalStateException("Unexpected value: " + element);
            }
        }
        return nestedPacket.pop();
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        ArrayList<Packet> packets = new ArrayList<>();
        while (reader.ready()) {
            String packet1String = reader.readLine();
            packets.add(generatePacket(packet1String));
            String packet2String = reader.readLine();
            packets.add(generatePacket(packet2String));
            reader.readLine();
        }
        Packet dividerFirstNested = new PacketValue(2);
        PacketList dividerFirst = new PacketList();
        dividerFirst.addPacket(dividerFirstNested);
        Packet dividerSecondNested = new PacketValue(6);
        PacketList dividerSecond = new PacketList();
        dividerSecond.addPacket(dividerSecondNested);
        packets.add(dividerFirst); packets.add(dividerSecond);

        packets.sort(Packet::compareTo);
        int indexDiv1st = packets.indexOf(dividerFirst)+1, indexDiv2nd = packets.indexOf(dividerSecond)+1;
        int result = indexDiv1st * indexDiv2nd;
        System.out.println("Decoder key = " + result);
    }
}
