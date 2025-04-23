package mr.demonid.jukebox;


import mr.demonid.graphics.ScreenBuffer;

public class Star {
    private int x;
    private int y;
    private int z;
    private int screenX;
    private int screenY;
    private int color;


    public Star(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = 0xFF0000AA;
        screenX = 0;
        screenY = 0;
    }

    public void render(ScreenBuffer buffer) {
        int[] pixels = buffer.pixels();
        int width = buffer.getWidth();
        int height = buffer.getHeight();

        pixels[screenY * width + screenX] = color;

        pixels[screenY * width + screenX - 1] = color + 72;
        pixels[screenY * width + screenX + 1] = color + 72;
        pixels[(screenY-1) * width + screenX] = color + 72;
        pixels[(screenY+1) * width + screenX] = color + 72;

        pixels[screenY * width + screenX - 2] = color + 144;
        pixels[screenY * width + screenX + 2] = color + 144;
        pixels[(screenY-2) * width + screenX] = color + 144;
        pixels[(screenY+2) * width + screenX] = color + 144;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Star{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", ox=" + screenX +
                ", oy=" + screenY +
                '}';
    }
}
