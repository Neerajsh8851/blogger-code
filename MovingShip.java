package jgame.example;


import jgame.model.Ship;
import jgame.util.FrameRate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class MovingShip extends JFrame implements Runnable {
    private BufferStrategy bs;
    private FrameRate frameRate;
    private Thread gameThread;
    private Canvas canvas;
    private volatile boolean running;

    private volatile boolean up, down, left, right;
    private Ship ship;

    private void initialize() {
        frameRate = new FrameRate();
        int tx = (int) canvas.getWidth()/2;
        int ty = (int) canvas.getHeight()/2;
        Point [] poly = new Point[] {
                new Point(0, 0),
                new Point(7, 7),
                new Point(0, -10),
                new Point(-7, 7)
        };
        ship = new Ship(poly, tx, ty);
        frameRate.start();
    }

    public void createAndShowGUI() {
        canvas = new Canvas();
        canvas.setSize(400, 300);
        canvas.setBackground(Color.black);
        // ignore the repaint messages
        canvas.setIgnoreRepaint(true);
        setIgnoreRepaint(true);
        getContentPane().add(canvas);

        pack();
        setLocationByPlatform(true);
        setVisible(true);
        canvas.requestFocus();

        //create bufferStrategy object
        // with two buffers
        canvas.createBufferStrategy(2);
        // get the bs object to this canvas component
        bs = canvas.getBufferStrategy();

        // add key listener
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    up = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    down = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    left = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    right = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    up = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    down = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    left = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    right = false;
                }
            }
        });

        //create and start game thread
        gameThread = new Thread(this);
        gameThread.setName("gameThread");
        gameThread.start();
    }

    public void run() {
        running = true;
        initialize();
        long ct = System.nanoTime();
        long lt = ct;
        long dt;
        while (running) {
            ct = System.nanoTime();
            dt = ct - lt;
            updateObject(dt * 1E-9f);   // dt * 10 raise to -9
            renderFrame();
            sleep(10L);
            // very important
            lt = ct;
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


    private void updateObject(float dt) {
        if (up) {
            ship.ty -= dt * ship.velocity;
        }
        if (down) {
            ship.ty += dt * ship.velocity;
        }

        if (left) {
            ship.tx -= dt * ship.velocity;
        }
        if (right) {
            ship.tx += dt * ship.velocity;
        }
    }

    private void render(Graphics g) {
        g.setColor(Color.green);
        frameRate.calculate();
        g.drawString(frameRate.getFps(), 30, 40);

        //copy and translate polygon points
        Point[] shipCpy = new Point[ship.poly.length];
        for (int i = 0; i < ship.poly.length; i++) {
            Point p = ship.poly[i];
            shipCpy[i] = new Point(p.x + (int) ship.tx, p.y + (int) ship.ty);
        }

        // render the ship
        Point lp = shipCpy[shipCpy.length - 1];
        g.setColor(Color.RED);
        for (int i = 0; i < shipCpy.length; i++) {
            Point ip = shipCpy[i];
            g.drawLine(lp.x, lp.y, ip.x, ip.y);
            lp = ip;
        }
    }


    public void renderFrame() {
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
        MovingShip app = new MovingShip();
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
