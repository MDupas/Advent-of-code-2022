import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static final String input_url = "G:/Prog/Advent_of_code_2022/inputs/day1/input.txt";

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input_url));
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
        int max = -1;
        int currentInventory = 0;
        while (reader.ready()) {
            String input = reader.readLine();
            if (!input.isEmpty())
                currentInventory += Integer.parseInt(input);
            else {
                if (max < currentInventory)
                    max = currentInventory;
                currentInventory = 0;
            }
        }
        System.out.println("Max inventory = " + max);
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
//        int inventory1st = -1;
//        int inventory2nd = -2;
//        int inventory3rd = -3;
        int[] inventoryTop3 = new int[]{-1, -2, -3};
        int currentInventory = 0;
        while (reader.ready()) {
            String input = reader.readLine();
            if (!input.isEmpty())
                currentInventory += Integer.parseInt(input);
            else {
//                if (inventoryTop3[2] < currentInventory) {
//                    if (inventoryTop3[1] < currentInventory) {
//                        if (inventoryTop3[0] < currentInventory) {
//                            inventoryTop3[2] = inventoryTop3[1];
//                            inventoryTop3[1] = inventoryTop3[0];
//                            inventoryTop3[0] = currentInventory;
//                        }
//                        else {
//                            inventoryTop3[2] = inventoryTop3[1];
//                            inventoryTop3[1] = currentInventory;
//                        }
//                    }
//                    else
//                        inventoryTop3[2] = currentInventory;
//                }
                compareInventories(inventoryTop3, currentInventory);
                currentInventory = 0;
            }
        }
        compareInventories(inventoryTop3, currentInventory);

        int result = Arrays.stream(inventoryTop3).sum();
        System.out.println("Sum of top 3 inventories = " + result + "\nMax inventories = " + inventoryTop3[0] + "; " +
                inventoryTop3[1] + "; " + inventoryTop3[2]);
    }

    private static int[] compareInventories(int[] inventoryTop3, int currentInventory) {
        if (inventoryTop3[2] < currentInventory) {
            if (inventoryTop3[1] < currentInventory) {
                if (inventoryTop3[0] < currentInventory) {
                    inventoryTop3[2] = inventoryTop3[1];
                    inventoryTop3[1] = inventoryTop3[0];
                    inventoryTop3[0] = currentInventory;
                }
                else {
                    inventoryTop3[2] = inventoryTop3[1];
                    inventoryTop3[1] = currentInventory;
                }
            }
            else
                inventoryTop3[2] = currentInventory;
        }
        return inventoryTop3;
    }
}
