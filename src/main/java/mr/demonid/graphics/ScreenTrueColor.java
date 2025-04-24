package mr.demonid.graphics;

public class ScreenTrueColor extends ScreenAbstract {

    public ScreenTrueColor(int width, int height) {
        super(width, height);
    }


    @Override
    public void setPixel(int x, int y, int color) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            pixels[y * getWidth() + x] = color;
        }
    }

    @Override
    public int getPixel(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return pixels[y * getWidth() + x];
        }
        return 0;
    }


}
