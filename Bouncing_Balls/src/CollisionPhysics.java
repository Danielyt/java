/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class CollisionPhysics {
   // Working copy for computing response in intersect(ContainerBox box), 
   // to avoid repeatedly allocating objects.
   private static CollisionResponse tempResponse = new CollisionResponse(); 
   
   /**
    * Detect collision for a moving point bouncing inside a rectangular container,
    * within the given timeLimit.
    * If collision is detected within the timeLimit, compute collision time and 
    * response in the given CollisionResponse object. Otherwise, set collision time
    * to infinity.
    * The result is passed back in the given CollisionResponse object.
    */
   public static void pointIntersectsRectangleOuter(
         float pointX, float pointY, float speedX, float speedY, float radius,
         float rectX1, float rectY1, float rectX2, float rectY2,
         float timeLimit, CollisionResponse response) {
      
      response.reset();  // Reset detected collision time to infinity
      
      // A outer rectangular container box has 4 borders. 
      // Need to look for the earliest collision, if any.
  
      // Right border
      pointIntersectsLineVertical(pointX, pointY, speedX, speedY, radius,
            rectX2, timeLimit, tempResponse);
      if (tempResponse.getT() < response.getT()) {
         response.copy(tempResponse);  // Copy into resultant response
      }
      // Left border
      pointIntersectsLineVertical(pointX, pointY, speedX, speedY, radius,
            rectX1, timeLimit, tempResponse);
      if (tempResponse.getT() < response.getT()) {
         response.copy(tempResponse);
      }
      // Top border
      pointIntersectsLineHorizontal(pointX, pointY, speedX, speedY, radius,
            rectY1, timeLimit, tempResponse);
      if (tempResponse.getT() < response.getT()) {
         response.copy(tempResponse);
      }
      // Bottom border
      pointIntersectsLineHorizontal(pointX, pointY, speedX, speedY, radius,
            rectY2, timeLimit, tempResponse);
      if (tempResponse.getT() < response.getT()) {
         response.copy(tempResponse);
      }
   }
   
   /**
    * Detect collision for a moving point hitting a horizontal line,
    * within the given timeLimit.
    */
   public static void pointIntersectsLineVertical(
         float pointX, float pointY, float speedX, float speedY, float radius,
         float lineX, float timeLimit, CollisionResponse response) {
  
      response.reset();  // Reset detected collision time to infinity
  
      // No collision possible if speedX is zero
      if (speedX == 0) {
         return;
      }
  
      // Compute the distance to the line, offset by radius.
      float distance;
      if (lineX > pointX) {
         distance = lineX - pointX - radius; 
      } else {
         distance = lineX - pointX + radius; 
      }
      
      float t = distance / speedX;  // speedX != 0
      // Accept 0 < t <= timeLimit
      if (t > 0 && t <= timeLimit) {
         response.setT(t);
         response.newSpeedX = -speedX/Math.abs(speedX);  // Reflect horizontally
         response.newSpeedY = speedY/Math.abs(speedY);   // No change vertically
      }
   }
  
   /**
    * @see movingPointIntersectsLineVertical().
    */
   public static void pointIntersectsLineHorizontal(
         float pointX, float pointY, float speedX, float speedY, float radius,
         float lineY, float timeLimit, CollisionResponse response) {

      response.reset();  // Reset detected collision time to infinity
  
      // No collision possible if speedY is zero
      if (speedY == 0) {
         return;
      }
  
      // Compute the distance to the line, offset by radius.
      float distance;
      if (lineY > pointY) {
         distance = lineY - pointY - radius; 
      } else {
         distance = lineY - pointY + radius; 
      }
      
      float t = distance / speedY;  // speedY != 0
      // Accept 0 < t <= timeLimit
      if (t > 0 && t <= timeLimit) {
         response.setT(t);
         response.newSpeedY = -speedY/Math.abs(speedY);  // Reflect vertically
         response.newSpeedX = speedX/Math.abs(speedX);   // No change horizontally
      }
   }
   
   //returns the smallest positive solution of the quadratic equation (in any)
   //-1 otherwise
   private static float solveQuadraticEquation(float a, float b, float c) {
       
       if (a == 0)
           return -1;
       
       float discriminant =  b * b - 4 * a * c;
       
       if (discriminant < 0)
           return -1;
       
       float t1 = (float)( ( -b - Math.sqrt(discriminant) )/( 2*a ) );
       float t2 = (float)( ( -b + Math.sqrt(discriminant) )/( 2*a ) );
       
       if (t1 < 0 && t2 < 0)
           return -1;             
       
       //chose the smaller positive t
       if ( (t1 < t2 && t1 >= 0) || (t1 > t2 && t2 < 0))
           return t1;
       else
           return t2;
   }
   
   
   public static void pointIntersectsMovingPoint(
            float x1, float y1, float speedX1, float speedY1, float radius1,
            float x2, float y2, float speedX2, float speedY2, float radius2,
            float timeLimit, CollisionResponse response1,
            CollisionResponse response2) {
       
       response1.reset();  // Reset detected collision time to infinity
       response2.reset();
       float deltaSpeedX = speedX2 - speedX1;
       float deltaSpeedY = speedY2 - speedY1;
       float deltaX = x2 - x1;
       float deltaY = y2 - y1;
       
       if (deltaSpeedX == 0 && deltaSpeedY == 0)
           return;
       
       float r = radius1 + radius2;

       
       float t = solveQuadraticEquation(deltaSpeedX * deltaSpeedX + deltaSpeedY * deltaSpeedY,
               2 * (deltaSpeedX * deltaX + deltaSpeedY * deltaY),
               deltaX * deltaX + deltaY * deltaY  - r * r);
              
      // Accept 0 < t <= timeLimit
      if (t > 0 && t <= timeLimit) {
         response1.setT(t);
         response2.setT(t);
         //exchange speeds;
         response1.newSpeedX = speedX2/Math.abs(speedX2);
         response1.newSpeedY = speedY2/Math.abs(speedY2);
         response2.newSpeedX = speedX1/Math.abs(speedX1);
         response2.newSpeedY = speedY1/Math.abs(speedY1);
      }
   }
   
   public static void pointIntersectsRectangleInner(
         float pointX, float pointY, float speedX, float speedY, float radius,
         float rectX1, float rectY1, float rectX2, float rectY2,
         float timeLimit, CollisionResponse response) {
       
       response.reset();  // Reset detected collision time to infinity
       
      // A outer rectangular container box has 4 borders. 
      // Need to look for the earliest collision, if any.
       
       //top left corner
       pointIntersectsStationaryPoint(pointX, pointY, speedX, speedY, radius,
            rectX1, rectY1, timeLimit, tempResponse, (byte)1);
       if (tempResponse.getT() < response.getT()) {
           response.copy(tempResponse);  // Copy into resultant response           
       }
       
       //top right corner
       pointIntersectsStationaryPoint(pointX, pointY, speedX, speedY, radius,
            rectX2, rectY1, timeLimit, tempResponse, (byte)2);
       if (tempResponse.getT() < response.getT()) {
           response.copy(tempResponse);  // Copy into resultant response           
       }
       
       //bottom left corner
       pointIntersectsStationaryPoint(pointX, pointY, speedX, speedY, radius,
            rectX1, rectY2, timeLimit, tempResponse, (byte)4);
       if (tempResponse.getT() < response.getT()) {
           response.copy(tempResponse);  // Copy into resultant response           
       }
       
       //bottom right corner
       pointIntersectsStationaryPoint(pointX, pointY, speedX, speedY, radius,
            rectX2, rectY2, timeLimit, tempResponse, (byte)3);
       if (tempResponse.getT() < response.getT()) {
           response.copy(tempResponse);  // Copy into resultant response           
       }
       
       //right border
       pointIntersectsLineSegment(pointX, pointY, speedX, speedY, radius,
         rectX2 + radius, rectY1, rectX2 + radius, rectY2, timeLimit, tempResponse, true);
       if (tempResponse.getT() < response.getT()) {
           response.copy(tempResponse);  // Copy into resultant response
       }
       //left border
       pointIntersectsLineSegment(pointX, pointY, speedX, speedY, radius,
         rectX1 - radius, rectY1, rectX1 - radius, rectY2, timeLimit, tempResponse, true);
       if (tempResponse.getT() < response.getT()) {
           response.copy(tempResponse);  // Copy into resultant response
       }
       
       //top border
       pointIntersectsLineSegment(pointX, pointY, speedX, speedY, radius,
         rectX1, rectY1 - radius, rectX2, rectY1 - radius, timeLimit, tempResponse, false);
       if (tempResponse.getT() < response.getT()) {
           response.copy(tempResponse);  // Copy into resultant response
       }
       
       //bottom border
       pointIntersectsLineSegment(pointX, pointY, speedX, speedY, radius,
         rectX1, rectY2 + radius, rectX2, rectY2 + radius, timeLimit, tempResponse, false);
       if (tempResponse.getT() < response.getT()) {
           response.copy(tempResponse);  // Copy into resultant response           
       }
       
       
      /*// Right border
      pointIntersectsSegmentVertical(pointX, pointY, speedX, speedY, radius,
            rectX2, rectY1, rectY2, timeLimit, tempResponse);
      if (tempResponse.t < response.t) {
         response.copy(tempResponse);  // Copy into resultant response
      }
      // Left border
      pointIntersectsSegmentVertical(pointX, pointY, speedX, speedY, radius,
            rectX1, rectY1, rectY2, timeLimit, tempResponse);
      if (tempResponse.t < response.t) {
         response.copy(tempResponse);
      }
      
      // Top border
      pointIntersectsSegmentHorizontal(pointX, pointY, speedX, speedY, radius,
            rectY1, rectX1, rectX2, timeLimit, tempResponse);
      if (tempResponse.t < response.t) {
         response.copy(tempResponse);
      }
      // Bottom border
      pointIntersectsSegmentHorizontal(pointX, pointY, speedX, speedY, radius,
            rectY2, rectX1, rectX2, timeLimit, tempResponse);
      if (tempResponse.t < response.t) {
         response.copy(tempResponse);
      }*/
       
   }
   
   /**
    * Detect collision for a moving point hitting a vertical line segment,
    * within the given timeLimit.
    */
   public static void pointIntersectsLineSegment(
         float pointX, float pointY, float speedX, float speedY, float radius,
         float segmentX1, float segmentY1, float segmentX2, float segmentY2,
         float timeLimit, CollisionResponse response, boolean vertical) {
  
      response.reset();  // Reset detected collision time to infinity
  
      // No collision possible if speedX is zero
      if (speedX == 0 && speedY == 0) {
         return;
      }
  
      float dx = segmentX1 - pointX;
      float dy = segmentY1 - pointY;
      float sx = segmentX2 - segmentX1;
      float sy = segmentY2 - segmentY1;
      
      if ( (speedY * sx - speedX * sy) == 0) 
          return;
      
      float t = (sx * dy - sy * dx) / (speedY * sx - speedX * sy);
      float lambda = (speedX * dy - speedY * dx) / (speedY * sx - speedX * sy);
      
      if (t > 0 && t <= timeLimit && lambda >= 0 && lambda <= 1) {
         response.setT(t);         
         if (vertical){
             //System.out.println("vertical hit");
             response.newSpeedX = -speedX/Math.abs(speedX);  // Reflect horizontally
             response.newSpeedY = speedY/Math.abs(speedY);   // No change vertically
         }
         else {             
             response.newSpeedX = speedX/Math.abs(speedX);
             response.newSpeedY = -speedY/Math.abs(speedY);
         }
         
      }
   }
   
   public static void pointIntersectsStationaryPoint(
            float x1, float y1, float speedX, float speedY, float radius,
            float x2, float y2, float timeLimit, CollisionResponse response, byte side) {
       
       response.reset();  // Reset detected collision time to infinity
       // No collision possible if speedX is zero
       if (speedX == 0 && speedY == 0) {
           return;
       }
       
       float dx = x1 - x2;
       float dy = y1 - y2;
       
       float t = solveQuadraticEquation( speedX * speedX + speedY * speedY,
               2 * dx * speedX + 2 * dy * speedY,
               dx * dx + dy * dy - radius * radius);
       
       if (t > 0 && t <= timeLimit) {
         response.setT(t);
         float a = x1 + speedX * t;
         float b = y1 + speedY * t;
         if (Math.abs(x2 - a) < Math.abs(y2 - b)) {
             //System.out.println(side + ": Math.abs(x2 - a) < Math.abs(y2 - b)");
             if (side == 1 || side == 2) {
                 if (speedY > 0) {
                     response.newSpeedX = speedX/Math.abs(speedX);
                     response.newSpeedY = -speedY/Math.abs(speedY);
                 }
                 else {
                     response.newSpeedX = -speedX/Math.abs(speedX);
                     response.newSpeedY = speedY/Math.abs(speedY);
                 }
             }
             if (side == 3 || side == 4) {
                 if (speedY < 0) {
                     response.newSpeedX = speedX/Math.abs(speedX);
                     response.newSpeedY = -speedY/Math.abs(speedY);
                 }
                 else {
                     response.newSpeedX = -speedX/Math.abs(speedX);
                     response.newSpeedY = speedY/Math.abs(speedY);
                 }
             }
         }
         else if (Math.abs(x2 - a) > Math.abs(y2 - b)) {
             //System.out.println(side + ": Math.abs(x2 - a) > Math.abs(y2 - b)");
             if (side == 1 || side == 4) {
                 if (speedX > 0) {
                     response.newSpeedX = -speedX/Math.abs(speedX);
                     response.newSpeedY = speedY/Math.abs(speedY);
                 }
                 else {
                     response.newSpeedX = speedX/Math.abs(speedX);
                     response.newSpeedY = -speedY/Math.abs(speedY);
                 }
                 
             }
             if (side == 2 || side == 3) {
                 if (speedX < 0) {
                     response.newSpeedX = -speedX/Math.abs(speedX);
                     response.newSpeedY = speedY/Math.abs(speedY);
                 }
                 else {
                     response.newSpeedX = speedX/Math.abs(speedX);
                     response.newSpeedY = -speedY/Math.abs(speedY);
                 }
             }
         }
         else if (x2 - a == y2 - b && x2 - a > 0) {
             //System.out.println(side + ": x2 - a == y2 - b");
             if (speedX > 0 && speedY > 0){
                 response.newSpeedX = -speedX/Math.abs(speedX);
                 response.newSpeedY = -speedY/Math.abs(speedY);
             }
             if (speedX > 0 && speedY < 0) {
                 response.newSpeedX = -speedX/Math.abs(speedX);
                 response.newSpeedY = speedY/Math.abs(speedY);
             }
             if (speedX < 0 && speedY > 0) {
                 response.newSpeedX = speedX/Math.abs(speedX);
                 response.newSpeedY = -speedY/Math.abs(speedY);
             }
         }
         else if (a - x2 == y2 - b && a - x2 > 0) {
             //System.out.println(side + ": a - x2 == y2 - b");
             if (speedX < 0 && speedY > 0) {
                 response.newSpeedX = -speedX/Math.abs(speedX);
                 response.newSpeedY = -speedY/Math.abs(speedY);
             }
             if (speedX > 0 && speedY > 0) {
                 response.newSpeedX = speedX/Math.abs(speedX);
                 response.newSpeedY = -speedY/Math.abs(speedY);
             }
             if (speedX < 0 && speedY < 0) {
                 response.newSpeedX = -speedX/Math.abs(speedX);
                 response.newSpeedY = speedY/Math.abs(speedY);
             }
         }
         else if (a - x2 == b - y2 && a - x2 > 0) {
             //System.out.println(side + ": a - x2 == b - y2");
             if (speedX < 0 && speedY < 0) {
                 response.newSpeedX = -speedX/Math.abs(speedX);
                 response.newSpeedY = -speedY/Math.abs(speedY);
             }
             if (speedX > 0 && speedY < 0) {
                 response.newSpeedX = speedX/Math.abs(speedX);
                 response.newSpeedY = -speedY/Math.abs(speedY);
             }
             if (speedX < 0 && speedY > 0) {
                 response.newSpeedX = -speedX/Math.abs(speedX);
                 response.newSpeedY = speedY/Math.abs(speedY);
             }
         }
         else if (x2 - a == b - y2 && x2 - a > 0) {
             //System.out.println(side + ": x2 - a == b - y2");
             if (speedX > 0 && speedY < 0) {
                 response.newSpeedX = -speedX/Math.abs(speedX);
                 response.newSpeedY = -speedY/Math.abs(speedY);
             }
             if (speedX > 0 && speedY > 0) {
                 response.newSpeedX = -speedX/Math.abs(speedX);
                 response.newSpeedY = speedY/Math.abs(speedY);
             }
             if (speedX < 0 && speedY < 0) {
                 response.newSpeedX = speedX/Math.abs(speedX);
                 response.newSpeedY = -speedY/Math.abs(speedY);
             }
         }
         else {
            //System.out.println("out of if"); //response.reset();
         }
         
      }
   }   

}
