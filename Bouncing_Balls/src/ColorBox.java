
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class ColorBox extends JPanel{
    //private int width;
    //private int height;
    private Color color;
    public ColorBox(int width, int height, Color color) {
        this.setPreferredSize(new Dimension(width, height));
        setColor(color);
    }
    
    public void setColor(Color color) {
        this.color = color;
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(color);
        
    }
}
