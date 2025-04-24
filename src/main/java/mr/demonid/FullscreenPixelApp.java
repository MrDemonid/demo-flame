package mr.demonid;

import mr.demonid.graphics.ScreenBuffer;
import mr.demonid.graphics.ScreenVGA;
import mr.demonid.graphics.screenshoot.ScreenshotSaver;
import mr.demonid.jukebox.JukeBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;


public class FullscreenPixelApp extends Canvas implements Runnable, KeyListener {

    private boolean running = true;
    private final long frameTime;           // наносекунд на кадр

    private final int width;
    private final int height;

    private ScreenBuffer screenBuffer;

    private ScreenshotSaver saver = new ScreenshotSaver("screenshots", "png", "screen", 4);
    private boolean isScreenShoot;

    JukeBox box;


    public FullscreenPixelApp(long fps) {
        frameTime = 1000000000L / fps;

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();
        width = dm.getWidth();
        height = dm.getHeight();

        System.out.println("width: " + width + ", height: " + height);

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
        screenBuffer = new ScreenVGA(320, 200, box.getGamePalette());

        BufferStrategy bs = getBufferStrategy();
        isScreenShoot = false;

        while (running) {
            long startTime = System.nanoTime();

            // отрисовка
            box.render(screenBuffer);

            // перенос буфера на экран
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            screenBuffer.render(g, width, height);
            g.dispose();
            bs.show();

            if (isScreenShoot) {
                saver.save(screenBuffer.getImage());
                isScreenShoot = false;
            }

            // контроль скорости
            long delay = (frameTime - (System.nanoTime() - startTime)) / 1000000L;
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
