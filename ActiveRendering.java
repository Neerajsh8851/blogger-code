package jgame.example;

import jgame.util.FrameRate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ActiveRendering extends  JFrame implements Runnable{

private FrameRate frameRate;
private Thread gameThread;
private volatile boolean running;
private JPanel gamePanel;

public ActiveRendering() {
    frameRate = new FrameRate();
}

private void createAndShowGUI() {

    gamePanel = new JPanel();
    // Disable the paint notification from the System
    gamePanel.setIgnoreRepaint(true);
    setIgnoreRepaint(true);

    gamePanel.setBackground(Color.black);

    gamePanel.setPreferredSize(new Dimension(400, 300));
    getContentPane().add(gamePanel);
    pack();

    setLocationRelativeTo(null);

    setVisible(true);

    // create and start the gameThread
    gameThread = new Thread(this);
    gameThread.setName("gameThread");
    gameThread.start();
}

@Override
public void run() {
    running = true;
    // start the fps counter
    frameRate.start();
    while (running) {
        Graphics g = gamePanel.getGraphics();
        render(g);
        g.dispose();
        sleep();
    }

}

private void clear(Graphics g) {
    g.setColor(gamePanel.getBackground());
    g.fillRect(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
}

private void draw(Graphics g) {
    g.setColor(Color.white);
    frameRate.calculate();
    g.drawString(frameRate.getFps() , 20, 20);
}

private void render(Graphics g) {
    // Clear the screen with background color
    clear(g);
    // Draw the foreground stuff
    draw(g);
}

private void sleep() {
    try {
        Thread.sleep(10);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

private void shutDown() {
    running = false;
    // wait for gameThread to stop
    try {
        gameThread.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    // Stop the application
    System.exit(0);
}


public static void main(String[] args) {
    ActiveRendering app = new ActiveRendering();

    // schedule a task to event (EDT) thread
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            app.createAndShowGUI();
        }
    });

    app.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            app.shutDown();
        }
    });

}
}
