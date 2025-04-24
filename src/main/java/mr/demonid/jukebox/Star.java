package mr.demonid.jukebox;


import mr.demonid.graphics.ScreenBuffer;
import mr.demonid.graphics.VGAPalette;

public class Star {
    private int x;
    private int y;
    private int z;
    private int screenX;
    private int screenY;
    private int color;
    private VGAPalette palette;

    public Star(int x, int y, int z, VGAPalette palette) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = 0;
        screenX = 0;
        screenY = 0;
        this.palette = palette;
    }

    public void update() {

    }

    public void render(ScreenBuffer buffer) {
        int[] pixels = buffer.pixels();
        int width = buffer.getWidth();
        int height = buffer.getHeight();

        pixels[screenY * width + screenX] = palette.getColor(color);

        pixels[screenY * width + screenX - 1] = palette.getColor(color + 72);
        pixels[screenY * width + screenX + 1] = palette.getColor(color + 72);
        pixels[(screenY-1) * width + screenX] = palette.getColor(color + 72);
        pixels[(screenY+1) * width + screenX] = palette.getColor(color + 72);

        pixels[screenY * width + screenX - 2] = palette.getColor(color + 144);
        pixels[screenY * width + screenX + 2] = palette.getColor(color + 144);
        pixels[(screenY-2) * width + screenX] = palette.getColor(color + 144);
        pixels[(screenY+2) * width + screenX] = palette.getColor(color + 144);
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

    public VGAPalette getPalette() {
        return palette;
    }

    public void setPalette(VGAPalette palette) {
        this.palette = palette;
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
