package ua.com.meom.enums;

public enum KeyCommand {

    MOVE_RIGHT("▶", 1, 0),
    MOVE_LEFT("◀", -1, 0),
    MOVE_DOWN("▼", 0, 1),
    MOVE_UP("▲", 0, -1),
    LAUNCH("L", 0, 0),
    CLEANUP("C", 0, 0);

    private final String text;
    private final int keyX;
    private final int keyY;

    KeyCommand(String text, int keyX, int keyY) {
        this.text = text;
        this.keyX = keyX;
        this.keyY = keyY;
    }

    public String getText() {
        return text;
    }

    public int getKeyX() {
        return keyX;
    }

    public int getKeyY() {
        return keyY;
    }

    @Override
    public String toString() {
        return "KeyCommand{" +
                "text='" + text + '\'' +
                ", keyX=" + keyX +
                ", keyY=" + keyY +
                '}';
    }
}
