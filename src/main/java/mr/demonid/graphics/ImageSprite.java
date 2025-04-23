package mr.demonid.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class ImageSprite extends Sprite {
    private final int width;
    private final int height;
    private final int[] imagePixels;


    public ImageSprite(Path path, int x, int y) throws IOException {
        super(x, y);
        BufferedImage img = ImageIO.read(path.toFile());
        width = img.getWidth();
        height = img.getHeight();
        imagePixels = new int[width * height];
        img.getRGB(0, 0, width, height, imagePixels, 0, width);
    }

    @Override
    public void render(ScreenBuffer buffer, int frame) {
        for (int yy = 0; yy < height; yy++) {
            int screenY = y + yy;
            if (screenY < 0 || screenY >= buffer.getHeight())
                continue;
            for (int xx = 0; xx < width; xx++) {
                int screenX = x + xx;
                if (screenX < 0 || screenX >= buffer.getWidth())
                    continue;
                int color = imagePixels[yy * width + xx];
                if ((color >> 24) != 0) { // Прозрачность
                    buffer.setPixel(screenX, screenY, color);
                }
            }
        }
    }
}
