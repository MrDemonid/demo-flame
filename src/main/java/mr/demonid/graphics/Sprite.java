package mr.demonid.graphics;

public abstract class Sprite {
    protected int x, y;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void render(ScreenBuffer buffer, int frame);

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
