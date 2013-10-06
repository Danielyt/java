/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BallPanel extends JPanel{
   private static final int UPDATE_RATE = 30;    // Frames per second (fps)
   private static final float EPSILON_TIME = 1e-2f;  // Threshold for zero time
    
   // Balls
   private static final int MAX_BALLS = 30; // Max number allowed 
   private int currentNumBalls;             // Number currently active
   private Ball[] balls = new Ball[MAX_BALLS];
   private static final int BALLS_RADIUS = 30;

   private ContainerBox box; // The container rectangular box
   private VerticalSegment verticalSegment;
   private DrawCanvas canvas;    // The Custom canvas for drawing the box/ball
   private JPanel controlPanel;
   private JButton startButton;
   private JSlider speedControl;
   private ColorBox colorBox;
   private JButton changeColorButton;
   private JButton quitButton;
   private int controlPanelHeight = 30;
   private int canvasWidth;
   private int canvasHeight;
   
   //thread pool for balls
   private ExecutorService ballThreadPool;
   
   //max speed for next ball;
   private int nextBallMaxSpeed = 10;
   private Color nextBallColor = Color.GREEN;
   
   private boolean paused = false;  // Flag for pause/resume control
   private boolean running = true; //Flag for terminating;
   
   private long startTime;
   private long pausedTime;
   private long startPausedTime;
   public BallPanel(int width, int height) {
      startTime = System.currentTimeMillis();
      pausedTime = 0;
      canvasWidth = width;
      canvasHeight = height - controlPanelHeight;
      
      currentNumBalls = 0;      
      //balls[0] = new Ball(100, 200, 30, 1, 1, nextBallMaxSpeed, Color.YELLOW);
      //balls[1] = new Ball(200, 100, 30, 8, -15, nextBallMaxSpeed, Color.GREEN);
      //balls[2] = new Ball(300, 400, 40, -2, 10, nextBallMaxSpeed, Color.RED);
      //balls[3] = new Ball(160, 400, 50, -10, -9, nextBallMaxSpeed, Color.WHITE);
      //balls[4] = new Ball(300, 300, 20, 12, 5, nextBallMaxSpeed, Color.PINK);
      ballThreadPool = Executors.newCachedThreadPool();
      //ballThreadPool.execute( new BallUpdater( balls[0] ) );
      //ballThreadPool.execute( new BallUpdater( balls[1] ) );
      //ballThreadPool.execute( new BallUpdater( balls[2] ) );
      //ballThreadPool.execute( new BallUpdater( balls[3] ) );
      //ballThreadPool.execute( new BallUpdater( balls[4] ) );
             
      // Init the Container Box to fill the screen
      box = new ContainerBox(0, 0, canvasWidth, canvasHeight, Color.WHITE, Color.LIGHT_GRAY);

      verticalSegment = new VerticalSegment(canvasWidth/2 - 10, canvasHeight/2 - canvasHeight/12 ,20, canvasHeight/6);
      // Init the custom drawing panel for drawing the game
      canvas = new DrawCanvas();
      canvas.addMouseListener(new MouseAdapter() {

            @Override
                public void mousePressed( final MouseEvent event) {
                     startNewBall(event.getX(), event.getY());
                    //System.out.println("x = " + event.getX() + ", y = " + event.getY());
                }
        });
      
      controlPanel = new JPanel();
      startButton = new JButton("Start");
      controlPanel.add(startButton);
      startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paused = !paused;                
                if(paused) {
                    startButton.setText("Start");
                    startPausedTime = System.currentTimeMillis();                            
                }
                else {
                    startButton.setText("Pause");
                    pausedTime += System.currentTimeMillis() - startPausedTime;
                }
            }
         });
      
      speedControl = new JSlider(JSlider.HORIZONTAL, 1, 15, nextBallMaxSpeed);
      controlPanel.add(new JLabel("Next Ball's Speed"));
      controlPanel.add(speedControl);
      speedControl.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
               JSlider source = (JSlider)e.getSource();
               if (!source.getValueIsAdjusting()) {
                  nextBallMaxSpeed = (int)source.getValue();
                  System.out.println(nextBallMaxSpeed);
               }
               transferFocusUpCycle();  // To handle key events
            }
         });
      // Layout the drawing panel and control panel
      colorBox = new ColorBox(40, 25, nextBallColor);
      controlPanel.add(new JLabel("Next Ball's Colour"));
      controlPanel.add(colorBox);
      changeColorButton = new JButton("Change colour");
      controlPanel.add(changeColorButton);
      changeColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextBallColor = JColorChooser.showDialog(BallPanel.this,
                        "Choose a clour", nextBallColor);
                // set default color, if no color is returned 
               if ( nextBallColor == null )
                  nextBallColor = Color.GREEN;
               colorBox.setColor(nextBallColor);
            }
         });
      quitButton = new JButton("Quit");
      controlPanel.add(quitButton);
      quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running = false;
                ballThreadPool.shutdown();
                System.exit(0);
            }
         });
      this.setLayout(new BorderLayout());
      this.add(canvas, BorderLayout.CENTER);
      this.add(controlPanel, BorderLayout.SOUTH);
      
      startBouncing();
   }
   
   /** Start the ball bouncing. */
   public void startBouncing() {
      //System.out.println("Entering startBouncing");
      // Run the game logic in its own thread.
      Thread ballMasterThread = new Thread() {
         public void run() {
            while (running) {
               long beginTimeMillis, timeTakenMillis, timeLeftMillis;
               beginTimeMillis = System.currentTimeMillis();
               
               // Execute one game step
               if(!paused) {
                  updateCanvas();
                  // Refresh the display
                  repaint();               
               }
               // Provide the necessary delay to meet the target rate
               timeTakenMillis = System.currentTimeMillis() - beginTimeMillis;
               timeLeftMillis = 1000L / UPDATE_RATE - timeTakenMillis;
               if (timeLeftMillis < 5) timeLeftMillis = 5; // Set a minimum
               
               // Delay and give other thread a chance
               try {
                  Thread.sleep(timeLeftMillis);
               } catch (InterruptedException ex) {}
            }
         }
      };
      ballMasterThread.start();  // Invoke GaemThread.run()
   }
   
   private void startNewBall(final int x, final int y) {
       Thread startNewBallThread = new Thread() {
           public void run() {
               //obtain lock for all balls
               int newNumBalls = currentNumBalls;
               for (int i = 0; i < currentNumBalls; i++) {
                   balls[i].accessLock.lock();
               }
               
               //wait until all balls are available for intersecting
               try {                         
                   boolean areAllBallsIntersectable = false;
                   while (!areAllBallsIntersectable){
                       areAllBallsIntersectable = true;
                       for (int i = 0; i < currentNumBalls; i++) {
                           if (balls[i].isUpdatable) {
                               //System.out.println("Waiting until ball " + i + " is available for intersecting");
                               areAllBallsIntersectable = false;
                               balls[i].canIntersect.await();
                           }
                       }
                   }
                   
                   //check if user clicked inside or too close another  ball
                   boolean isWithinAnObject = false;
                   for (int i = 0; i < currentNumBalls; i++) {
                       if (distance(balls[i].getX(), balls[i].getY(), (float)x, (float)y) <= (int)balls[i].getRadius() + BALLS_RADIUS)
                           isWithinAnObject = true;
                   }
                   
                   //check if user clicked inside or too close to vertical segment
                   if (x >= verticalSegment.getMinX() - BALLS_RADIUS 
                           && x <= verticalSegment.getMaxX() + BALLS_RADIUS
                           && y >= verticalSegment.getMinY() - BALLS_RADIUS
                           && y <= verticalSegment.getMaxY() + BALLS_RADIUS)
                       isWithinAnObject = true;
                   
                   //check if user clicked to close to canvas boundaries
                   if (x <= box.getMinX() + BALLS_RADIUS + 1
                           || x >= box.getMaxX() - BALLS_RADIUS - 1
                           || y <= box.getMinY() + BALLS_RADIUS + 1
                           || y >= box.getMaxY() - BALLS_RADIUS - 1)
                       isWithinAnObject = true;
                   
                   
                   if (!isWithinAnObject && currentNumBalls < MAX_BALLS) {
                       balls[currentNumBalls] = new Ball(x, y, BALLS_RADIUS, 1, 1, nextBallMaxSpeed, nextBallColor);
                       ballThreadPool.execute( new BallUpdater( balls[currentNumBalls] ) );
                       newNumBalls++;
                   }
                                 
               }
               catch (InterruptedException exception) {
                   exception.printStackTrace();
               }
               finally {
                   for (int i = 0; i < currentNumBalls; i++) {
                       balls[i].accessLock.unlock();
                   }
                   currentNumBalls = newNumBalls;
               }
           }
           
       };
       startNewBallThread.start();
   }
   
   //returns the euclidian distance between two points
   private int distance(float x1, float y1, float x2, float y2){
       return (int) Math.sqrt( (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
   }
      
   public void updateCanvas(){
       float timeLeft = 1.0f;  // One time-step to begin with
       // Repeat until the one time-step is up
       do {
           //obtain lock for all balls
           for (int i = 0; i < currentNumBalls; i++) {
               balls[i].accessLock.lock();
           }
           
           //wait until all balls are available for intersecting
           try {
               boolean areAllBallsIntersectable = false;
               while (!areAllBallsIntersectable){
                   areAllBallsIntersectable = true;
                   for (int i = 0; i < currentNumBalls; i++) {
                       if (balls[i].isUpdatable) {
                           //System.out.println("Waiting until ball " + i + " is available for intersecting");
                           areAllBallsIntersectable = false;
                           balls[i].canIntersect.await();
                       }
                   }
               }
               // Find the earliest collision up to timeLeft among all objects
               float tMin = timeLeft;
               
               // Check collision between two balls
               for (int i = 0; i < currentNumBalls; i++) {
                   for (int j = 0; j < currentNumBalls; j++) {
                       if (i < j) {
                           balls[i].intersect(balls[j], tMin);
                           if (balls[i].earliestCollisionResponse.getT() < tMin) {
                               tMin = balls[i].earliestCollisionResponse.getT();
                           }
                       }
                   }
               }
               
               // Check collision between the balls and the box
               for (int i = 0; i < currentNumBalls; i++) {
                   balls[i].intersect(box, tMin);
                   if (balls[i].earliestCollisionResponse.getT() < tMin) {
                       tMin = balls[i].earliestCollisionResponse.getT();
                   }
               }
               
               // Check collision between the balls and the vertical segment
               for (int i = 0; i < currentNumBalls; i++) {
                   balls[i].intersect(verticalSegment, tMin);
                   if (balls[i].earliestCollisionResponse.getT() < tMin) {
                       tMin = balls[i].earliestCollisionResponse.getT();
                   }
               }
               for (int i = 0; i < currentNumBalls; i++){
                   balls[i].timeToUpdate = tMin;
               }
               
               timeLeft -= tMin;
                //Indicate that all balls are updatable
               for (int i = 0; i < currentNumBalls; i++) {
                   balls[i].isUpdatable = true;
                   balls[i].canUpdate.signal();
               }
               
            }
            catch (InterruptedException exception) {
                exception.printStackTrace();
            }
           finally {
                for (int i = 0; i < currentNumBalls; i++) {
                    balls[i].accessLock.unlock();
                }
           }
       } while (timeLeft > EPSILON_TIME);     // Ignore remaining time less than threshold
   }
      /** The custom drawing panel for the bouncing ball (inner class). */
   class DrawCanvas extends JPanel {
      /** Custom drawing codes */
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);    // Paint background
         // Draw the balls and box
         box.draw(g);
         verticalSegment.draw(g);
         for (int i = 0; i < currentNumBalls; i++) {
            balls[i].draw(g);
         }
         // Display balls' information
         g.setColor(Color.BLACK);
         g.setFont(new Font("Courier New", Font.PLAIN, 12));
         long currentTime = System.currentTimeMillis() - pausedTime - startTime;
         
         g.drawString(String.format("%02d:%02d:%02d",
                 (int) currentTime / (1000 * 60 * 60),
                 (int) (currentTime % (1000 * 60 * 60)) / (1000 * 60),
                 (int) ((currentTime % (1000 * 60 * 60)) % (1000 * 60)) / 1000), 20, 30);
         /*for (int i = 0; i < currentNumBalls; i++) {
            g.drawString("Ball " + (i+1) + " " + balls[i].toString(), 20, 30 + (i + 1)*20);
         }*/
      }

      /** Called back to get the preferred size of the component. */
      @Override
      public Dimension getPreferredSize() {
         return (new Dimension(canvasWidth, canvasHeight));
      }
   }
}
