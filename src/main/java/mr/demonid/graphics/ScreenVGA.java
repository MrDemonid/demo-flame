package mr.demonid.graphics;

import java.awt.*;
import java.awt.image.*;

public class ScreenVGA extends ScreenAbstract implements Palette {

    private final int[] colors = new int[256];              // палитра из 256 цветов

    private static final int[] VGA_TO_RGB = new int[64];    // 6-бит в 8-бит
    static {
        for (int i = 0; i < 64; i++) {
            VGA_TO_RGB[i] = i * 255 / 63;
        }
    }

    public ScreenVGA(int width, int height, byte[] pal) {
        super(width, height);
        setPalette(pal);
    }

    @Override
    public void setPalette(byte[] rawPalette) {
        if (rawPalette.length != colors.length*3) {
            throw new IllegalArgumentException("Не совпадает размер палитры!");
        }
        for (int i = 0; i < colors.length; i++) {
            int r = VGA_TO_RGB[rawPalette[i * 3] & 0x3F];
            int g = VGA_TO_RGB[rawPalette[i * 3 + 1] & 0x3F];
            int b = VGA_TO_RGB[rawPalette[i * 3 + 2] & 0x3F];

            colors[i] = 0xFF000000 | (r << 16) | (g << 8) | b;
        }
    }

    @Override
    public byte[] getPalette() {
        byte[] res = new byte[colors.length * 3];
        for (int i = 0; i < colors.length; i++) {
            res[i*3] = (byte) ((colors[i] & 0xFF0000) >>> 16);
            res[i*3+1] = (byte) ((colors[i] & 0xFF00) >> 8);
            res[i*3+2] = (byte) (colors[i] & 0xFF);
        }
        return res;
    }

    @Override
    public void setColor(int index, int red, int green, int blue) {
        if (index >= 0 && index < colors.length) {
            colors[index] = 0xFF000000 | (red << 16) | (green << 8) | blue;
        }
    }

    @Override
    public int getColor(int index) {
        if (index >= 0 && index < colors.length) {
            return colors[index];
        }
        return 0;
    }


    @Override
    public void setPixel(int x, int y, int color) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            pixels[y * getWidth() + x] = colors[color & 0xFF];
        }
    }

    @Override
    public int getPixel(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return colors[pixels[y * getWidth() + x] & 0xFF];
        }
        return 0;
    }

    @Override
    public void drawText(String text, int x, int y, int color) {
        super.drawText(text, x, y, colors[color & 0xFF]);
    }
}
