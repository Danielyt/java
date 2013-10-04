/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class AlarmClock {
    private AlarmActionEventHandler alarm;
    private int nrings;
    
    public AlarmClock(AlarmActionEventHandler alarm, int nrings) {
        setAlarm(alarm);
        setNrings(nrings);
    }
    
    public void setAlarm(AlarmActionEventHandler alarm){
        this.alarm = alarm;
    }
    
    public void setNrings(int rings){
        nrings = rings;
    }
    
    public void onAlarm(AlarmEventArgs e) {
        if (alarm != null) {
            //Invoke the event handler.
            alarm.alarmActionPerformed(e);
        }
    }
    // event handling method
    public void Start()
    {
        for (;;)
        {
            nrings--;
            if (nrings < 0) {
                break;
            }
            else {
                AlarmEventArgs e = new AlarmEventArgs(nrings);
                onAlarm(e);
            }
        }
    }
}
