package mr.demonid.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface ScreenBuffer {
    int getWidth();
    int getHeight();

    BufferedImage getImage();

    void clear();
    void setPixel(int x, int y, int color);
    int getPixel(int x, int y);

    void drawText(String text, int x, int y, int color);
    void setFont(Font font);

    void render(Graphics2D g, int screenWidth, int screenHeight);
    void render(Graphics2D g, int screenWidth, int screenHeight, Object hintValue);
}
