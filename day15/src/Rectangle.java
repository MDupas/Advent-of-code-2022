import java.util.Optional;

public class Rectangle {
    private final Point upperPoint;
    private final Point rightMostPoint;
    private final Point lowerPoint;
    private final Point leftMostPoint;

    public Rectangle(Point upperPoint, Point rightMostPoint, Point lowerPoint, Point leftMostPoint) {
        this.upperPoint = upperPoint;
        this.rightMostPoint = rightMostPoint;
        this.lowerPoint = lowerPoint;
        this.leftMostPoint = leftMostPoint;
    }

    public int size() {
        return (Math.abs(rightMostPoint.y - upperPoint.y) + 1) * (Math.abs(upperPoint.y - leftMostPoint.y) + 1);
    }

    // only work with Rhombus
    public Optional<Line> intersectionOnRow(int y) {
        if (y < upperPoint.y || y > lowerPoint.y || upperPoint.x != lowerPoint.x) {
            return Optional.empty();
        }
        int x1, x2;
        if (y < (lowerPoint.y - upperPoint.y) / 2 + upperPoint.y) {
            x1 = upperPoint.x - (y - upperPoint.y);
            x2 = upperPoint.x + (y - upperPoint.y);
        }
        else {
            x1 = upperPoint.x - (lowerPoint.y - y);
            x2 = upperPoint.x + (lowerPoint.y - y);
        }
        return Optional.of(new Line(y, x1, x2));
    }
}
