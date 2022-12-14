public class Tree {
    final int x;
    final int y;

    final int size;

//    @Override

    @Override
    public String toString() {
        return "Tree{" +
                "x=" + x +
                ", y=" + y +
                ", size=" + size +
                ", notCounted=" + notCounted +
                '}';
    }
//    public String toString() {
//        return "Tree{" +
//                "size=" + size +
//                '}';
//    }

    private boolean notCounted;

    public Tree(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.notCounted = true;
    }

    public boolean isNotCounted() {
        return notCounted;
    }

    public boolean count() {
        boolean counted = notCounted;
        this.notCounted = false;
        return counted;
    }
}
