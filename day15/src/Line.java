public class Line {
    int y;
    int x1;
    int x2;

    public Line(int y, int x1, int x2) {
        this.y = y;
        this.x1 = x1;
        this.x2 = x2;
    }

    public int length() {
        return Math.abs(x2 - x1) + 1;
    }

    public boolean mergeable(Line o) {
        return y == o.y && !(x2 < o.x1 - 1) && !(o.x2 < x1 - 1);
    }

    public Line merge(Line o) {
        return new Line(y, Math.min(x1, o.x1), Math.max(x2, o.x2));
    }

    @Override
    public String toString() {
        return "Line{" +
                "y=" + y +
                ", x1=" + x1 +
                ", x2=" + x2 +
                '}';
    }
}
