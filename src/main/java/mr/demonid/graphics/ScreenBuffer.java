package mr.demonid.graphics;

import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;


/**
 * Буфер экрана в памяти. Для порта старого DOS-кода.
 */
public class ScreenBuffer {
    private final int width;
    private final int height;

    private final BufferedImage image;
    private final int[] pixels;


    public ScreenBuffer(int width, int height) {
        this.width = width;
        this.height = height;

        pixels = new int[width * height];
        DataBufferInt dataBuffer = new DataBufferInt(pixels, pixels.length);
        WritableRaster raster = Raster.createPackedRaster(dataBuffer, width, height, width,
                new int[] { 0xFF0000, 0x00FF00, 0x0000FF }, null);
        ColorModel colorModel = new DirectColorModel(24, 0xFF0000, 0x00FF00, 0x0000FF);
        image = new BufferedImage(colorModel, raster, false, null);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] pixels() {
        return pixels;
    }

    public void clear() {
        Arrays.fill(pixels, 0);
    }

    public void setPixel(int x, int y, int rgb) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            pixels[y * width + x] = rgb;
        }
    }


    /**
     * Масштабированный вывод на экран без сохранения пропорций
     * */
    public void draw(Graphics2D g, int screenWidth, int screenHeight) {
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, screenWidth, screenHeight, null);
    }

    /** Масштабированный вывод с сохранением пропорций и центрированием */
    public void drawCentered(Graphics2D g, int screenWidth, int screenHeight) {
        double scaleX = screenWidth / (double) width;
        double scaleY = screenHeight / (double) height;
        double scale = Math.min(scaleX, scaleY); // сохранить пропорции

        int drawWidth = (int)(width * scale);
        int drawHeight = (int)(height * scale);
        int offsetX = (screenWidth - drawWidth) / 2;
        int offsetY = (screenHeight - drawHeight) / 2;

        g.drawImage(image, offsetX, offsetY, drawWidth, drawHeight, null);
    }
}
