public class Cube {
    int x;
    int y;
    int z;

    public Cube(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cube cube = (Cube) o;

        if (x != cube.x) return false;
        if (y != cube.y) return false;
        return z == cube.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return "Cube{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
