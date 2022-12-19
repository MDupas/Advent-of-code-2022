import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final String input_url = "../inputs/day18/input.txt";

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input_url));
//            firstPuzzle(reader);
            secondPuzzle_v2(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found at " + input_url);
        } catch (IOException e) {
            System.err.println("I/O error : ");
            e.printStackTrace();
        }
    }

    private static void firstPuzzle(BufferedReader reader) throws IOException {
        Set<Cube> cubes = new HashSet<>();
        int uncoveredFaces = 0;
        while (reader.ready()) {
            String[] cubeCoordinates = reader.readLine().split(",");
            Cube cube = new Cube(Integer.parseInt(cubeCoordinates[0]),
                    Integer.parseInt(cubeCoordinates[1]),
                    Integer.parseInt(cubeCoordinates[2]));
            uncoveredFaces += 6;
            uncoveredFaces -= coverFacesWithCube(cubes, cube);
            cubes.add(cube);
        }
        System.out.println("Total of uncovered faces = " + uncoveredFaces);
    }

    private static int coverFacesWithCube(Set<Cube> cubes, Cube cube) {
        List<Cube> neighborCubes = createNeighborCubes(cube);
        int coverFaces = 0;
        for (Cube neighborCube : neighborCubes) {
            if (cubes.contains(neighborCube))
                coverFaces += 2;
        }
        return coverFaces;
    }

    private static void secondPuzzle_v2(BufferedReader reader) throws IOException {
        Set<Cube> cubes = new HashSet<>();
        Set<Cube> phantomCubes = new HashSet<>();
        int uncoveredFaces = 0;
        while (reader.ready()) {
            String[] cubeCoordinates = reader.readLine().split(",");
            Cube cube = new Cube(Integer.parseInt(cubeCoordinates[0]),
                    Integer.parseInt(cubeCoordinates[1]),
                    Integer.parseInt(cubeCoordinates[2]));
            phantomCubes.remove(cube);
            uncoveredFaces += 6;
            int coverFaces = 0;
            List<Cube> neighborCubes = createNeighborCubes(cube);
            for (Cube neighborCube : neighborCubes) {
                if (cubes.contains(neighborCube))
                    coverFaces += 2;
                else
                    phantomCubes.add(neighborCube);
            }
            uncoveredFaces -= coverFaces;
            cubes.add(cube);
        }
        Set<Cube> newPhantomCube = new HashSet<>();
        for (Cube phantomCube : phantomCubes) {
            List<Cube> phantomNeighbor = createNeighborCubes(phantomCube);
            phantomNeighbor.removeIf(cubes::contains);
            newPhantomCube.addAll(phantomNeighbor);
        }
        phantomCubes.addAll(newPhantomCube);
        Stack<Cube> phantomCubeToRemove = new Stack<>();
        Cube startPhantomSurface = phantomCubes.stream().max(Comparator.comparingInt(o -> o.x)).get();
        phantomCubes.remove(startPhantomSurface);
        phantomCubeToRemove.add(startPhantomSurface);
        while (!phantomCubeToRemove.empty()) {
            List<Cube> newPhantomCubesToRemove = createNeighborCubes(phantomCubeToRemove.pop());
            newPhantomCubesToRemove.removeIf(neighbor -> !phantomCubes.contains(neighbor));
            phantomCubeToRemove.addAll(newPhantomCubesToRemove);
            newPhantomCubesToRemove.forEach(phantomCubes::remove);
        }
        for (Cube phantomCube : phantomCubes) {
            int coverFaces = 0;
            List<Cube> neighborCubes = createNeighborCubes(phantomCube);
            for (Cube neighborCube : neighborCubes) {
                if (cubes.contains(neighborCube))
                    coverFaces += 1;
            }
            uncoveredFaces -= coverFaces;
        }

        System.out.println("Total of uncovered faces after removing air cubes = " + uncoveredFaces);
    }

    private static List<Cube> createNeighborCubes(Cube cube) {
        List<Cube> neighborCubes = new ArrayList<>();
        neighborCubes.add(new Cube(cube.x + 1, cube.y, cube.z));
        neighborCubes.add(new Cube(cube.x - 1, cube.y, cube.z));
        neighborCubes.add(new Cube(cube.x, cube.y + 1, cube.z));
        neighborCubes.add(new Cube(cube.x, cube.y - 1, cube.z));
        neighborCubes.add(new Cube(cube.x, cube.y, cube.z + 1));
        neighborCubes.add(new Cube(cube.x, cube.y, cube.z - 1));
        return neighborCubes;
    }
}
