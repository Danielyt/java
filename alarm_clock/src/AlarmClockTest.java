/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class AlarmClockTest implements AlarmActionEventHandler{
    private AlarmClock ac;
    public AlarmClockTest(int nrings){
        ac = new AlarmClock(this, nrings);
    }
    
    public void alarmActionPerformed (AlarmEventArgs args) {
        System.out.println( args.getNrings() );
    }
    
    public AlarmClock getAc(){
        return ac;
    }
    
    public static void main( String args[] ) {
        AlarmClockTest act = new AlarmClockTest(12);
        act.getAc().Start();
    }
}
