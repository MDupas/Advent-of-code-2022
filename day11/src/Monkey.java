import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Monkey {
    private ArrayList<Long> items;
    private final String[] operation;
    private final int divisibleBy;
    private final int trueMonkey;
    private final int falseMonkey;
    private int inspectionCount;
    private final boolean withExponentialWorries;
    private long commonDivider;

    public Monkey(ArrayList<Long> items, String[] operation, int divisibleBy, int trueMonkey, int falseMonkey, boolean withExponentialWorries) {
        this.items = items;
        this.operation = operation;
        this.divisibleBy = divisibleBy;
        this.trueMonkey = trueMonkey;
        this.falseMonkey = falseMonkey;
        this.inspectionCount = 0;
        this.withExponentialWorries = withExponentialWorries;
    }

    public int getDivisibleBy() {
        return divisibleBy;
    }

    public int getInspectionCount() {
        return inspectionCount;
    }

    public void catchItem(long item) {
        items.add(item);
    }

    public void throwItem(ArrayList<Monkey> monkeys) {
        Iterator<Long> iterator = items.iterator();
        while (iterator.hasNext()) {
            inspectionCount++;
            Long next =  iterator.next();
            long newWorry = calculateNewWorry(next);
            if (newWorry % divisibleBy == 0)
                monkeys.get(trueMonkey).catchItem(newWorry);
            else
                monkeys.get(falseMonkey).catchItem(newWorry);
            iterator.remove();
        }
    }

    private long calculateNewWorry(Long item) {
        long secondValue;
        long newWorry;
        if (operation[2].equals("old"))
            secondValue = item;
        else
            secondValue = Integer.parseInt(operation[2]);
        switch (operation[1]) {
            case "+":
                newWorry = item + secondValue;
                break;
            case "-":
                newWorry = item - secondValue;
                break;
            case "*":
                newWorry = item * secondValue;
                break;
            case "/":
                newWorry = item / secondValue;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation[1]);
        }
        if (withExponentialWorries)
            return newWorry % commonDivider;
        else
            return newWorry / 3;
    }

    @Override
    public String toString() {
        return "Monkey{" +
                "items=" + items +
                ", operation=" + Arrays.toString(operation) +
                ", divisibleBy=" + divisibleBy +
                ", trueMonkey=" + trueMonkey +
                ", falseMonkey=" + falseMonkey +
                ", inspectionCount=" + inspectionCount +
                '}';
    }

    public void addCommonDivider(long commonDivider) {
        this.commonDivider = commonDivider;
    }
}
