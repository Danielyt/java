/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class Circle extends Point implements Comparable {
    private int radius;
    
    public Circle(){
        this(0,0,0);
    }
    
    public Circle(int x, int y, int r) {
        super(x, y);
        setRadius(r);
    }
    
    public void setRadius(int radius){
        if( radius >= 0)
            this.radius = radius;
        else
            this.radius = 0;
    }
    
    public int getRadius(){
        return radius;
    }
    
    public boolean greater(Comparable obj){
        if ( obj instanceof Circle){
            Circle c = (Circle) obj;
            if ( this.getX() >= c.getX() && this.getY() >= c.getY() && this.radius > c.radius )
                return true;
        }
        return false;
    }
    
    public String toString(){
        return String.format( "%s, radius = %d", super.toString(), radius);
    }
}
