package mr.demonid;

import mr.demonid.graphics.ScreenBuffer;
import mr.demonid.graphics.screenshoot.ScreenshotSaver;
import mr.demonid.jukebox.JukeBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class FullscreenPixelApp extends Canvas implements Runnable, KeyListener {

    private boolean running = true;
    private final long frameTime;

    private final int width;
    private final int height;

    private final ScreenBuffer screenBuffer;

    private ScreenshotSaver saver = new ScreenshotSaver("screenshots", "png", "screen", 4);
    private boolean isScreenShoot;

    JukeBox box;


    public FullscreenPixelApp(long fps) {
        frameTime = 1000 / fps;

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();
        width = dm.getWidth();
        height = dm.getHeight();

        System.out.println("width: " + width + ", height: " + height);

        screenBuffer = new ScreenBuffer(320, 200);
//        screenBuffer = new ScreenBuffer(width, height);
        initWindow();
    }

    private void initWindow() {
        setPreferredSize(new Dimension(width, height));
        setIgnoreRepaint(true);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();

        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(frame);
        createBufferStrategy(2);
    }

    @Override
    public void run() {
        box = new JukeBox();

        BufferStrategy bs = getBufferStrategy();
        int frame = 0;
        isScreenShoot = false;

        while (running) {
            long startTime = System.currentTimeMillis();


            box.render(screenBuffer);
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            screenBuffer.draw(g, width, height);
            g.dispose();
            bs.show();

            if (isScreenShoot) {
                saver.save(screenBuffer);
                isScreenShoot = false;
            }

//            int[] pixels = screenBuffer.pixels();
//            for (int y = 0; y < screenBuffer.getHeight(); y++) {
//                for (int x = 0; x < screenBuffer.getWidth(); x++) {
//                    int r = (x + frame) % 256;
//                    int g = (y + frame) % 256;
//                    int b = (x + y + frame) % 256;
//                    pixels[y * screenBuffer.getWidth() + x] = (r << 16) | (g << 8) | b;
//                }
//            }
//
//            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
//            screenBuffer.draw(g, width, height);
//            g.dispose();
//            bs.show();

            frame++;
            long elapsed = System.currentTimeMillis() - startTime;
            long delay = frameTime - elapsed;
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        System.exit(0);
    }

    @Override public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            running = false;
        } else  if (e.getKeyCode() == KeyEvent.VK_F12) {
            isScreenShoot = true;
        } else {
            box.keyPressed(e);
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

}
