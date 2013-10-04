/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class AlarmEventArgs {
    private int nrings;
    
    public AlarmEventArgs(){
        this(0);
    }
    
    public AlarmEventArgs(int rings){
        setNrings(rings);
    }
    
    public void setNrings( int rings) {
        nrings = rings;
    }
    
    public int getNrings() {
        return nrings;
    }
}
