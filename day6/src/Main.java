import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final String input_url = "../inputs/day6/input.txt";

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

    private static int rankOfEqualCharacter(LinkedList<Character> linkedList, Character character) {
        int index = linkedList.size();
        for (Iterator<Character> it = linkedList.descendingIterator(); it.hasNext(); ) {
            Character nextCharacter = it.next();
            if (character.equals(nextCharacter))
                return index;
            index--;
        }
        return index;
    }

    private static void findPacketMarker(BufferedReader reader, boolean findStartOfMessage) throws IOException {
        String datastream = reader.readLine();
        LinkedList<Character> recentCharacter = new LinkedList<>();
        int index = 0;
        int rankToRemove = 0;
        int sizeToFind = findStartOfMessage ? 13 : 3;
        for (; index < sizeToFind; index++) {
            rankToRemove = Math.max(rankToRemove, rankOfEqualCharacter(recentCharacter, datastream.charAt(index)));
            recentCharacter.add(datastream.charAt(index));
        }
        rankToRemove = Math.max(rankToRemove, rankOfEqualCharacter(recentCharacter, datastream.charAt(index)));
        while (rankToRemove > 0) {
            recentCharacter.pop();
            rankToRemove--;
            recentCharacter.add(datastream.charAt(index));
            index++;
            rankToRemove = Math.max(rankToRemove, rankOfEqualCharacter(recentCharacter, datastream.charAt(index)));
        }
        recentCharacter.add(datastream.charAt(index));
        index++;
        System.out.print("Unique series of character found ");
        for (Character character : recentCharacter) {
            System.out.print(character);
        }
        System.out.println(" after index -> " + index);
    }

    private static void firstPuzzle(BufferedReader reader) throws IOException {
        findPacketMarker(reader, false);
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        findPacketMarker(reader, true);
    }
}
