package jgame.example;
import jgame.util.FrameRate;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class DoubleBufferExample extends JFrame implements Runnable{

private BufferStrategy bs;
private FrameRate frameRate;
private Thread gameThread;
private Canvas canvas;
private volatile boolean running;

public DoubleBufferExample() {
    frameRate = new FrameRate();
}

public void createAndShowGUI() {
    canvas = new Canvas();
    canvas.setSize(400, 300);
    canvas.setBackground(Color.black);
    // ignore the repaint messages
    canvas.setIgnoreRepaint(true);
    setIgnoreRepaint(true);
    getContentPane().add(canvas);

    setTitle("Double buffering example app");
    pack();
    setVisible(true);

    //create bufferStrategy object
    // with two buffers or off screen images
    canvas.createBufferStrategy(2);
    // get the bs object to this canvas component
    bs = canvas.getBufferStrategy();

    gameThread = new Thread(this);
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
    DoubleBufferExample app = new DoubleBufferExample();
    app.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            app.shutDown();
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
