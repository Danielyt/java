/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
import java.awt.*;
import java.util.Formatter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/**
 * The bouncing ball.
 * 
 * @author Hock-Chuan Chua
 * @version v0.4 (31 October 2010)
 */
public class Ball {
   private float x, y;           // Ball's center x and y (package access)
   private float speedX, speedY; // Ball's speed per step in x and y (package access)
   private float radius;         // Ball's radius (package access)
   private Color color;  // Ball's color
   private static final Color DEFAULT_COLOR = Color.BLUE;
   
   public final Lock accessLock = new ReentrantLock();
   
   public final Condition canUpdate = accessLock.newCondition();
   public final Condition canIntersect = accessLock.newCondition();
   
   public boolean isUpdatable = false;
   public boolean isIntersectable = true;
   public float timeToUpdate;
   private int maxSpeed;
   
   // For collision detection and response
   // Maintain the response of the earliest collision detected 
   //  by this ball instance. Only the first collision matters! (package access)
   CollisionResponse earliestCollisionResponse = new CollisionResponse();

  public Ball(float x, float y, float radius, float speedX, float speedY, int maxSpeed, Color color) {
      setX(x);
      setY(y);
      
      setSpeedX(speedX);
      setSpeedY(speedY);
      setRadius(radius);
      setColor(color);
      setTimeToUpdate(0);
      setMaxSpeed(maxSpeed);
   }
   
   public void setX(float x) {
       if (x >= 0)
           this.x = x;
       else
           this.x = 20;       
   }
   
   public void setY(float y) {
       if (y >= 0)
           this.y = y;
       else
           this.y = 20;
   }
   
   public void setSpeedX(float speedX) {
       if (speedX != 0)
           this.speedX = speedX;
       else
           this.speedX = 1;
   }
   
   public void setSpeedY(float speedY) {
       if (speedY != 0)
           this.speedY = speedY;
       else
           this.speedY = 1;
   }
   
   public void setRadius(float radius) {
       if (radius > 0)
           this.radius = radius;
       else
           this.radius = 15;
   }
   
   public void setColor(Color color) {
       if (color != null)
           this.color = color;
       else
           this.color = DEFAULT_COLOR;
   }
   
   public void setTimeToUpdate(float timeToUpdate) {
       if (timeToUpdate >= 0)
           this.timeToUpdate = timeToUpdate;
       else
           this.timeToUpdate = 0;
   }
   
   public void setMaxSpeed(int maxSpeed) {
       if (maxSpeed > 0)
           this.maxSpeed = maxSpeed;
       else
           this.maxSpeed = 1;
   }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public Color getColor() {
        return color;
    }

    public float getTimeToUpdate() {
        return timeToUpdate;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }
   
   
   /** Constructor with the default color */
   public Ball(float x, float y, float radius, float speedX, float speedY, int maxSpeed) {
      this(x, y, radius, speedX, speedY, maxSpeed, DEFAULT_COLOR);
   }
   
   // Working copy for computing response in intersect(), 
   // to avoid repeatedly allocating objects.
   private CollisionResponse tempResponse = new CollisionResponse(); 

   /**
    * Check if this ball collides with the container box in the interval 
    * (0, timeLimit].
    */
   public void intersect(ContainerBox box, float timeLimit) {
      // Call movingPointIntersectsRectangleOuter, which returns the 
      // earliest collision to one of the 4 borders, if collision detected.
      CollisionPhysics.pointIntersectsRectangleOuter(x, y, speedX, speedY, radius + 1,
            box.getMinX(), box.getMinY(), box.getMaxX(), box.getMaxY(), timeLimit, tempResponse);
      if (tempResponse.getT() < earliestCollisionResponse.getT()) {
         earliestCollisionResponse.copy(tempResponse);
      }
   }
   
   // Working copy for computing response in intersect(Ball, timeLimit), 
   // to avoid repeatedly allocating objects.
   private CollisionResponse thisResponse = new CollisionResponse(); 
   private CollisionResponse anotherResponse = new CollisionResponse(); 
   
   /**
    * Check if this ball collides with the given another ball in the interval 
    * (0, timeLimit].
    */
   public void intersect(Ball another, float timeLimit) {
      // Call movingPointIntersectsMovingPoint() with timeLimit.
      // Use thisResponse and anotherResponse, as the working copies, to store the
      // responses of this ball and another ball, respectively.
      // Check if this collision is the earliest collision, and update the ball's
      // earliestCollisionResponse accordingly.
      CollisionPhysics.pointIntersectsMovingPoint(
            this.x, this.y, this.speedX, this.speedY, this.radius + 1,
            another.x, another.y, another.speedX, another.speedY, another.radius + 1,
            timeLimit, thisResponse, anotherResponse);
      
      if (anotherResponse.getT() < another.earliestCollisionResponse.getT()) {
            another.earliestCollisionResponse.copy(anotherResponse);
      }
      if (thisResponse.getT() < this.earliestCollisionResponse.getT()) {
            this.earliestCollisionResponse.copy(thisResponse);
      }
   }
   
   
   /**
    * Check if this ball collides with the vertical segment in the interval 
    * (0, timeLimit].
    */
   public void intersect(VerticalSegment verticalSegment, float timeLimit) {
      // Call movingPointIntersectsRectangleOuter, which returns the 
      // earliest collision to one of the 4 borders, if collision detected.
      CollisionPhysics.pointIntersectsRectangleInner(x, y, speedX, speedY, radius + 1,
            verticalSegment.getMinX(), verticalSegment.getMinY(),
            verticalSegment.getMaxX(), verticalSegment.getMaxY(), timeLimit, tempResponse);
      if (tempResponse.getT() < earliestCollisionResponse.getT()) {
         earliestCollisionResponse.copy(tempResponse);
      }
   }
   /** 
    * Update the states of this ball for one time-step.
    * Move for one time-step if no collision occurs; otherwise move up to 
    * the earliest detected collision. 
    */
   public void update() {
       accessLock.lock();
       try {
           while (!isUpdatable) {
               //System.out.println("The ball is not updatable");
               canUpdate.await();
           }
           //System.out.println("Ball is updatable");
           // Check if this ball is responsible for the first collision?
           if (earliestCollisionResponse.getT() <= timeToUpdate) {
               // This ball collided, get the new position and speed
               this.x = earliestCollisionResponse.getNewX(this.x, this.speedX);
               this.y = earliestCollisionResponse.getNewY(this.y, this.speedY);
               float speedXSign = earliestCollisionResponse.newSpeedX;
               float speedYSign = earliestCollisionResponse.newSpeedY;
               //System.out.println("speedXSing = " + speedXSign +
               //        ", speedYSign = " + speedYSign);
               this.speedX = (float)( speedXSign * (Math.random() * maxSpeed + 2) );
               this.speedY = (float)( speedYSign * (Math.random() * maxSpeed + 2) );
               //this.speedX = earliestCollisionResponse.newSpeedX;
               //this.speedY = earliestCollisionResponse.newSpeedY;
            
           } else {
               // This ball does not involve in a collision. Move straight.
               this.x += this.speedX * timeToUpdate;         
               this.y += this.speedY * timeToUpdate;         
           }
           // Clear for the next collision detection
           earliestCollisionResponse.reset();
           isUpdatable = false;
           canIntersect.signal();
       }
       catch (InterruptedException exception) {
           exception.printStackTrace();
       }
       finally{
           accessLock.unlock();
       }
           
           
   }
   
   /** Draw itself using the given graphics context. */
   public void draw(Graphics g) {
      g.setColor(color);
      g.fillOval((int)(x - radius), (int)(y - radius), (int)(2 * radius),
            (int)(2 * radius));
   }
   
   public String toString() {
      sb.delete(0, sb.length());
      formatter.format("@(%3.0f,%3.0f) r=%3.0f V=(%2.0f,%2.0f) ", 
            x, y, radius, speedX, speedY);  // \u0398 is theta
      return sb.toString();
   }
   private StringBuilder sb = new StringBuilder();
   private Formatter formatter = new Formatter(sb);

}