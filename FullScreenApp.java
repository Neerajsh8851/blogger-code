package jgame.example;

import jgame.util.FrameRate;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class FullScreenApp extends JFrame implements Runnable {
    private BufferStrategy bs;
    private FrameRate frameRate;
    private Thread gameThread;
    private Canvas canvas;
    private volatile boolean running;
    private GraphicsDevice gd;

    public FullScreenApp() {

        frameRate = new FrameRate();
        // get the graphics environment
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // get the graphics device from ge
        gd = ge.getDefaultScreenDevice();
    }

    public void createAndShowGUI() {
        canvas = new Canvas();
        canvas.setSize(400, 300);
        canvas.setBackground(Color.black);
        // ignore the repaint messages
        canvas.setIgnoreRepaint(true);
        setIgnoreRepaint(true);
        getContentPane().add(canvas);

        // make the window undecorated
        setUndecorated(true);
        setVisible(true);
        // Set this window to full screen
        gd.setFullScreenWindow(this);

        //create bufferStrategy object
        // with two buffers
        canvas.createBufferStrategy(2);
        // get the bs object to this canvas component
        bs = canvas.getBufferStrategy();

        //create and start game thread
        gameThread = new Thread(this);
        gameThread.setName("gameThread");
        gameThread.start();
    }

    public void run() {
        running = true;
        frameRate.start();
        while (running) {
            gameLoop();
            sleep(10L);
        }
    }

    //sleep for some time
    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void render(Graphics g) {
        g.setColor(Color.green);
        frameRate.calculate();
        g.drawString(frameRate.getFps(), 30, 40);
    }


    public void gameLoop() {
        do {
            do {
                Graphics g = null;
                try {
                    g = bs.getDrawGraphics();
                    g.clearRect(0, 0, getWidth(), getHeight());
                    render(g);
                } finally {
                    if (g != null) {
                        g.dispose();
                    }
                }
                // Repeat the rendering if the drawing buffer contents
                // were restored
            } while (bs.contentsRestored());
            bs.show();
            // Repeat the rendering if the drawing buffer was lost
        } while (bs.contentsLost());
    }

    private void shutDown() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        FullScreenApp app = new FullScreenApp();

        app.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    app.shutDown();
                }
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                app.createAndShowGUI();
            }
        });
    }
}
