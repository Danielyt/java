/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */

import javax.swing.JFrame;

public class Test {
    // Entry main program
    public static void main(String[] args) {
        // Run UI in the Event Dispatcher Thread (EDT), instead of Main thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("A World of Balls");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new BallPanel(1200, 800)); // BallWorld is a JPanel
                frame.pack();            // Preferred size of BallWorld
                frame.setVisible(true);  // Show it
            }
        });
    }
}
