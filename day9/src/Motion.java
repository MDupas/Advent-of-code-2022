public class Motion {
    public enum Direction {
        Up,
        Right,
        Down,
        Left
    }
    final Direction direction;
    final int steps;

    public Motion(Direction direction, int steps) {
        this.direction = direction;
        this.steps = steps;
    }
}
