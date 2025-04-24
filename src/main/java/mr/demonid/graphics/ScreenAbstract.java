package mr.demonid.graphics;

import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;

public abstract class ScreenAbstract implements ScreenBuffer {

    private int width;
    private int height;

    protected final BufferedImage image;
    protected final int[] pixels;

    protected Font font;


    public ScreenAbstract(int width, int height) {
        this.width = width;
        this.height = height;
        font = new Font("Arial", Font.PLAIN, 12);

        pixels = new int[width * height];
        DataBufferInt dataBuffer = new DataBufferInt(pixels, pixels.length);
        WritableRaster raster = Raster.createPackedRaster(dataBuffer, width, height, width,
                new int[] { 0xFF0000, 0x00FF00, 0x0000FF }, null);
        ColorModel colorModel = new DirectColorModel(24, 0xFF0000, 0x00FF00, 0x0000FF);
        image = new BufferedImage(colorModel, raster, false, null);
    }


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void clear() {
        Arrays.fill(pixels, 0);
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void drawText(String text, int x, int y, int color) {
        Graphics2D g = image.createGraphics();
        // Включим сглаживание шрифтов
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(new Color(0xFF000000 | color));
        g.drawString(text, x, y);       // x и y — координаты нижней линии текста

        g.dispose();
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public void render(Graphics2D g, int screenWidth, int screenHeight) {
        this.render(g, screenWidth, screenHeight, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }

    @Override
    public void render(Graphics2D g, int screenWidth, int screenHeight, Object hintValue) {
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hintValue);
        g.drawImage(image, 0, 0, screenWidth, screenHeight, null);
    }

//    /** Масштабированный вывод с сохранением пропорций и центрированием */
//    public void drawCentered(Graphics2D g, int screenWidth, int screenHeight) {
//        double scaleX = screenWidth / (double) width;
//        double scaleY = screenHeight / (double) height;
//        double scale = Math.min(scaleX, scaleY); // сохранить пропорции
//
//        int drawWidth = (int)(width * scale);
//        int drawHeight = (int)(height * scale);
//        int offsetX = (screenWidth - drawWidth) / 2;
//        int offsetY = (screenHeight - drawHeight) / 2;
//
//        g.drawImage(image, offsetX, offsetY, drawWidth, drawHeight, null);
//    }


}
