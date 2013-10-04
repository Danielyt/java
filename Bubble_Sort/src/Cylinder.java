/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class Cylinder extends Circle implements Comparable{
    private int height;
    
    public Cylinder () {
        this(0, 0, 0, 0);
    }
    
    public Cylinder (int x, int y, int r, int h){
        super(x, y, r);
        setHeight(h);
    }
    
    public void setHeight(int height){
        if (height >- 0)
            this.height = height;
        else
            this.height = 0;
    }
    
    public int getheight() {
        return height;
    }
    
    public boolean greater(Comparable obj){
        if (obj instanceof Cylinder){
            Cylinder cl = (Cylinder) obj;
            if ( this.getX() >= cl.getX() && this.getY() >= cl.getY() && this.getRadius() >= cl.getRadius() && this.height > cl.height )
                return true;
        }
        return false;
    }
    
    public String toString() {
        return String.format( "%s, height = %d", super.toString(), height);
    }
}
