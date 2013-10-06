
import java.awt.Color;
import java.awt.Graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class VerticalSegment {
   private int minX, maxX, minY, maxY;  // Box's bounds (package access)
   private Color colorFilled;   // Box's filled color (background)
   private Color colorBorder;   // Box's border color
   private static final Color DEFAULT_COLOR_FILLED = Color.LIGHT_GRAY;
   private static final Color DEFAULT_COLOR_BORDER = Color.DARK_GRAY;
   
   /** Constructors */
   public VerticalSegment(int x, int y, int width, int height, Color colorFilled, Color colorBorder) {
      setMinX(x);
      setMinY(y);
      setMaxX(x + width - 1);
      setMaxY(y + height - 1);
      this.colorFilled = colorFilled;
      this.colorBorder = colorBorder;
   }

    public void setMinX(int x) {
        if (x >= 0)
            this.minX = x;
        else
            this.minX = 0;
    }
   
    public void setMinY(int y) {
        if (y >= 0)
            this.minY = y;
        else
            this.minY = 0;
    }
    
    public void setMaxX(int x) {
        if (x >= 0)
            this.maxX = x;
        else
            this.maxX = 0;
    }
    
    public void setMaxY(int y) {
        if (y >= 0)
            this.maxY = y;
        else
            this.maxY = 0;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
    
    
   /** Constructor with the default color */
   public VerticalSegment(int x, int y, int width, int height) {
      this(x, y, width, height, DEFAULT_COLOR_FILLED, DEFAULT_COLOR_BORDER);
   }
   
   /** Set or reset the boundaries of the box. */
   public void set(int x, int y, int width, int height) {
      setMinX(x);
      setMinY(y);
      setMaxX(x + width - 1);
      setMaxY(y + height - 1);
   }

   /** Draw itself using the given graphic context. */
   public void draw(Graphics g) {
      g.setColor(colorFilled);
      g.fill3DRect(minX, minY, maxX - minX - 1, maxY - minY - 1, true);
      g.setColor(colorBorder);
      g.draw3DRect(minX, minY, maxX - minX - 1, maxY - minY - 1, true);
   }
}
