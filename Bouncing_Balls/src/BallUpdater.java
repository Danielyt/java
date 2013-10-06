/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class BallUpdater implements Runnable {
    private final Ball ball;
    public BallUpdater (Ball ball) {
        this.ball = ball;
    }
    
    public void run() {
        while (true){
            ball.update();
            //System.out.println("Vx = " + ball.speedX + ", Vy = " + ball.speedY);
        }
    }
    
}
