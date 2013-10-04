/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class SortOrder {
    private class Upward implements Sortable{
        public boolean greater(int a, int b){
            if( a > b )
                return true;
            return false;
        }    
    }
    
    private class Downward implements Sortable{
        public boolean greater(int a, int b){
            if (a < b )
                return true;
            return false;
        }
    }
    
    public Sortable getUpward() {
        return new Upward();
    }
    
    public Sortable getDownward() {
        return new Downward();
    }
}
