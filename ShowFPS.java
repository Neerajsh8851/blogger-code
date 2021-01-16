package jgame.example;

import jgame.util.FrameRate;

import javax.swing.*;
import java.awt.*;

public class ShowFPS extends  JFrame{

    private FrameRate frameRate;

    public ShowFPS() {
        frameRate = new FrameRate();
    }

    private void createAndShowGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                render(g);
            }
        };

        panel.setBackground(Color.black);

        panel.setPreferredSize(new Dimension(400, 300));
        getContentPane().add(panel);
        pack();

        setLocationRelativeTo(null);
        frameRate.start();

        setVisible(true);
    }


    private void render(Graphics g) {

        g.setColor(Color.white);

        frameRate.calculate();
        g.drawString(frameRate.getFps() , getWidth()/2, getHeight()/2);
        repaint();
    }


    public static void main(String[] args) {
        ShowFPS app = new ShowFPS();

        // schedule a task to event (EDT) thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                app.createAndShowGUI();
            }
        });
    }
}
