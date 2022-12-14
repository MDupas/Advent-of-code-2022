import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static final String input_url = "../inputs/day8/input.txt";

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

    private static ArrayList<ArrayList<Tree>>[] createTreesArray(BufferedReader reader) throws IOException {
        ArrayList<ArrayList<Tree>> rows = new ArrayList<>();
        ArrayList<ArrayList<Tree>> columns = new ArrayList<>();
        int indexColumn = 0, indexRow = 0;
        String row = reader.readLine();
        rows.add(new ArrayList<>());
        for (indexRow = 0; indexRow < row.length(); indexRow++) {
            int treeSize = row.charAt(indexRow) - '0';
            columns.add(new ArrayList<>());
            Tree tree = new Tree(indexColumn, indexRow, treeSize);
            columns.get(indexRow).add(tree);
            rows.get(indexColumn).add(tree);
        }
        indexColumn++;
        while (reader.ready()) {
            row = reader.readLine();
            rows.add(new ArrayList<>());
            for (indexRow = 0; indexRow < row.length(); indexRow++) {
                int treeSize = row.charAt(indexRow) - '0';
                Tree tree = new Tree(indexColumn, indexRow, treeSize);
                columns.get(indexRow).add(tree);
                rows.get(indexColumn).add(tree);
            }
            indexColumn++;
        }
        ArrayList<ArrayList<Tree>>[] rowsAndColumn = new ArrayList[2];
        rowsAndColumn[0] = rows; rowsAndColumn[1] = columns;
        return rowsAndColumn;
    }

    private static int computeTreeVisible(ArrayList<ArrayList<Tree>> lines) {
        int treeVisible = 0;
        for (ArrayList<Tree> line : lines) {
            int sizeLimit = -1;
            for (Tree tree : line) {
                if (tree.size > sizeLimit) {
                    if (tree.count())
                        treeVisible++;
                    sizeLimit = tree.size;
                }
                if (sizeLimit == 9)
                    break;
            }
            Collections.reverse(line);
            sizeLimit = -1;
            for (Tree tree : line) {
                if (tree.size > sizeLimit) {
                    if (tree.count())
                        treeVisible++;
                    sizeLimit = tree.size;
                }
                if (sizeLimit == 9) {
                    break;
                }
            }
            Collections.reverse(line);
        }
        return treeVisible;
    }

    private static void firstPuzzle(BufferedReader reader) throws IOException {
        ArrayList<ArrayList<Tree>>[] rowsAndColumn = createTreesArray(reader);
        ArrayList<ArrayList<Tree>> rows = rowsAndColumn[0];
        ArrayList<ArrayList<Tree>> columns = rowsAndColumn[1];

        int totalTreeVisible = computeTreeVisible(rows);
        totalTreeVisible += computeTreeVisible(columns);
        System.out.println("Number of visible trees from all angle = " + totalTreeVisible);
    }

    private static int highestScenicScore(ArrayList<ArrayList<Tree>> trees) {
        int maxScenicScore = -1;
        for (int i = 1; i < trees.size()-1; i++) {
            for (int j = 1; j < trees.get(i).size()-1; j++) {
                int size = trees.get(i).get(j).size;
                int up = 0, right = 0, down = 0, left = 0;
                for (int k = i+1; k < trees.size(); k++) {
                        down++;
                    if (trees.get(k).get(j).size >= size)
                        break;
                }
                for (int k = j+1; k < trees.get(i).size(); k++) {
                        right++;
                    if (trees.get(i).get(k).size >= size)
                        break;
                }
                for (int k = i-1; k > -1; k--) {
                        up++;
                    if (trees.get(k).get(j).size >= size)
                        break;
                }
                for (int k = j-1; k > -1; k--) {
                        left++;
                    if (trees.get(i).get(k).size >= size)
                        break;
                }
                int scenicScore = up * right * down * left;
                maxScenicScore = Math.max(maxScenicScore, scenicScore);
            }
            System.out.println("One line done");
        }
        return maxScenicScore;
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        ArrayList<ArrayList<Tree>>[] rowsAndColumn = createTreesArray(reader);
        ArrayList<ArrayList<Tree>> rows = rowsAndColumn[0];

        System.out.println("Max scenic score found = " + highestScenicScore(rows));
    }
}
