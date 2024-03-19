package greginformatics.client.style.background;

public enum BackgroundStyles {

    DEFAULT(0xF0100010, 0xF0100010, 0x505000FF, 1344798847),
    JADE(0xF0383838, 0xF0383838, 0xFF242424, 0xFF242424);

    // TODO: Add more

    private final int colorbg1;
    private final int colorbg2;
    private final int coloredge1;
    private final int coloredge2;

    private BackgroundStyles(int colorbg1, int colorbg2, int coloredge1, int coloredge2) {
        this.colorbg1 = colorbg1;
        this.colorbg2 = colorbg2;
        this.coloredge1 = coloredge1;
        this.coloredge2 = coloredge2;
    }

    public int getColorbg1() {
        return this.colorbg1;
    }

    public int getColorbg2() {
        return this.colorbg2;
    }

    public int getColoredge1() {
        return this.coloredge1;
    }

    public int getColoredge2() {
        return this.coloredge2;
    }
}
