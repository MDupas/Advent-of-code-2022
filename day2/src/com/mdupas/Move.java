package com.mdupas;

public enum Move {
    Rock(1),
    Paper(2),
    Scissors(3);

    public final int moveValue;

    Move(int i) {
        this.moveValue = i;
    }

    public static Move generateMove(char move) {
        switch (move) {
            case 'A':
            case 'X':
                return Rock;
            case 'B':
            case 'Y':
                return Paper;
            case 'C':
            case 'Z':
                return Scissors;
            default:
                throw new IllegalStateException("Unexpected value: " + move);
        }
    }

    public static Move generateMoveOnResult(char result, Move moveOpponnent) {
        switch (result) {
            case 'X':
                return moveOpponnent.losingMove();
            case 'Y':
                return moveOpponnent.drawingMove();
            case 'Z':
                return moveOpponnent.winningMove();
            default:
                throw new IllegalStateException("Unexpected value: " + result);
        }
    }

    public Move drawingMove() {
        switch (this) {
            case Rock:
                return Rock;
            case Paper:
                return Paper;
            case Scissors:
                return Scissors;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Move losingMove() {
        switch (this) {
            case Rock:
                return Scissors;
            case Paper:
                return Rock;
            case Scissors:
                return Paper;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Move winningMove() {
        switch (this) {
            case Rock:
                return Paper;
            case Paper:
                return Scissors;
            case Scissors:
                return Rock;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public static int evaluateMove(Move p1, Move p2) {
        switch (p1) {
            case Rock:
                switch (p2) {
                    case Rock:
                        return 3; //draw
                    case Paper:
                        return 0; //lost
                    case Scissors:
                        return 6; //win
                }
            case Paper:
                switch (p2) {
                    case Rock:
                        return 6; //win
                    case Paper:
                        return 3; //draw
                    case Scissors:
                        return 0; //lost
                }
            case Scissors:
                switch (p2) {
                    case Rock:
                        return 0; //lost
                    case Paper:
                        return 6; //win
                    case Scissors:
                        return 3; //draw
                }
            default:
                throw new IllegalStateException("Unexpected value: " + p1);
        }
    }
}
