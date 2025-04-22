package mr.demonid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;

public class FullscreenPixelApp extends Canvas implements Runnable, KeyListener {

    private boolean running = true;

    private final int width;
    private final int height;
    private final BufferedImage buffer;
    private final int[] pixels;

    public FullscreenPixelApp() {
        // Получаем размеры экрана
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();
        width = dm.getWidth();
        height = dm.getHeight();
        System.out.println("width: " + width + ", height: " + height);

        // Создаем буфер изображения и получаем доступ к пикселям
//        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        // Сразу после создания буфера — безопасно, иначе можно словить несоответствие типов
//        WritableRaster raster = buffer.getRaster();
//        DataBuffer dataBuffer = raster.getDataBuffer();
//        if (!(dataBuffer instanceof DataBufferInt)) {
//            throw new IllegalStateException("Unexpected data buffer type");
//        }
//        pixels = ((DataBufferInt) dataBuffer).getData();

        // 2-й способ, гарантирующий что наш буфер точно не изменится.
        pixels = new int[width * height];
        DataBufferInt dataBuffer = new DataBufferInt(pixels, pixels.length);
        WritableRaster raster = Raster.createPackedRaster(dataBuffer, width, height, width,
                new int[] { 0xFF0000, 0x00FF00, 0x0000FF }, null);
        ColorModel colorModel = new DirectColorModel(24, 0xFF0000, 0x00FF00, 0x0000FF);
        buffer = new BufferedImage(colorModel, raster, false, null);


        // Настройка Canvas
        setPreferredSize(new Dimension(width, height));
        setIgnoreRepaint(true); // Отключаем автоматическую перерисовку, это ни к чему.
        addKeyListener(this);
        setFocusable(true);
        requestFocus();


        // Окно
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        gd.setFullScreenWindow(frame);

        createBufferStrategy(2); // двойная буферизация
    }

    public void run() {
        BufferStrategy bs = getBufferStrategy();
        long frameTime = 1000 / 60;

        int t = 0;

        while (running) {
            long start = System.currentTimeMillis();

            // Обновляем пиксели вручную
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int r = (x + t) % 256;
                    int g = (y + t) % 256;
                    int b = (x + y + t) % 256;
                    pixels[y * width + x] = (r << 16) | (g << 8) | b;
                }
            }

            // Выводим изображение на экран
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.drawImage(buffer, 0, 0, null);
            g.dispose();
            bs.show();

            // Следующий кадр
            t++;

            // Пауза
            long elapsed = System.currentTimeMillis() - start;
            long delay = frameTime - elapsed;
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        System.exit(0); // завершаем остальные потоки Swing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("keyPressed: " + e.getKeyChar());
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            running = false;
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}


    public static void main(String[] args) {
        FullscreenPixelApp app = new FullscreenPixelApp();
        new Thread(app).start();
    }
}
