import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static final String input_url = "../inputs/day11/input.txt";

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
        ArrayList<Monkey> monkeys = createMonkeys(reader, false);
        for (int i = 0; i < 20; i++) {
            for (Monkey monkey : monkeys) {
                monkey.throwItem(monkeys);
            }
        }
        long result = calculateMonkeyBusinessLevel(monkeys);
        System.out.println("Product of most active monkeys = " + result);
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        ArrayList<Monkey> monkeys = createMonkeys(reader, true);
        for (int i = 0; i < 10000; i++) {
            for (Monkey monkey : monkeys) {
                monkey.throwItem(monkeys);
            }
        }
        long result = calculateMonkeyBusinessLevel(monkeys);
        System.out.println("Product of most active monkeys = " + result);
    }

    private static ArrayList<Monkey> createMonkeys(BufferedReader reader, boolean withExponentialWorries) throws IOException {
        ArrayList<Monkey> monkeys = new ArrayList<>();
        while (reader.ready()) {
            reader.readLine();
            String[] itemsString = reader.readLine().split(": ")[1].split(",");
            ArrayList<Long> items = new ArrayList<>();
            for (String s : itemsString) items.add(Long.parseLong(s.trim()));
            String[] operation = reader.readLine().split(" = ")[1].split(" ");
            int divisibleBy = Integer.parseInt(reader.readLine().split(" ")[5]);
            int trueMonkey = Integer.parseInt(reader.readLine().split(" ")[9]);
            int falseMonkey = Integer.parseInt(reader.readLine().split(" ")[9]);
            if (reader.ready())
                reader.readLine();
            monkeys.add(new Monkey(items, operation, divisibleBy, trueMonkey, falseMonkey, withExponentialWorries));
        }
        if (withExponentialWorries) {
            long commonDivider = monkeys.stream().mapToInt(Monkey::getDivisibleBy).reduce((left, right) -> left * right).getAsInt();
            for (Monkey monkey : monkeys) {
                monkey.addCommonDivider(commonDivider);
            }
        }
        return monkeys;
    }

    private static long calculateMonkeyBusinessLevel(ArrayList<Monkey> monkeys) {
        long inspectionMax1 = -1, inspectionMax2 = -1;
        for (Monkey monkey : monkeys) {
            if (monkey.getInspectionCount() > inspectionMax2) {
                if (monkey.getInspectionCount() > inspectionMax1) {
                    inspectionMax2 = inspectionMax1;
                    inspectionMax1 = monkey.getInspectionCount();
                }
                else
                    inspectionMax2 = monkey.getInspectionCount();
            }
        }
        return inspectionMax2 * inspectionMax1;
    }
}
