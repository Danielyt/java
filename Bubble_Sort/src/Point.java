/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class Point implements Comparable {
    private int x;
    private int y;
    
    public Point () {
        this(0,0);
    }
    
    public Point(int x, int y) {
        setX(x);
        setY(y);
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public boolean greater(Comparable obj){
        if ( obj instanceof Point){
            Point p = (Point) obj;
            if ( this.x >= p.x && this.y > p.y )
                return true;
        }
        return false;
    }
    
    public String toString(){
        return String.format("Point (%d, %d)", x, y);
    }
}
