package net.vpg.game2048;

public enum Move {
    UP(false, false),
    DOWN(true, false),
    LEFT(false, true),
    RIGHT(true, true);

    final boolean forward;
    final boolean horizontal;

    Move(boolean forward, boolean horizontal) {
        this.forward = forward;
        this.horizontal = horizontal;
    }

    public static Move fromKey(String key) {
        return fromKey(key.charAt(0));
    }

    public static Move fromKey(char key) {
        switch (key) {
            case 'u':
            case 'U':
                return UP;
            case 'd':
            case 'D':
                return DOWN;
            case 'l':
            case 'L':
                return LEFT;
            case 'r':
            case 'R':
                return RIGHT;
            default:
                return null;
        }
    }
}
