/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class BubbleSort {
    public static void sortArray(Comparable[] arr)
    {   
       int end = arr.length - 1;
       boolean sorted = false;
      // loop to control number of passes
      for ( int pass = 1; pass < arr.length; pass++ ) { 
          if( sorted )
              break;
          sorted = true;
          // loop to control number of comparisons
          for ( int element = 0; element < end; element++ ) {
              
              // compare side-by-side elements and swap them if 
              // first element is greater than second element
              if ( arr[ element ].greater( arr[ element + 1 ] ) ) {
                  swap( arr, element, element + 1 );
                  sorted = false;
              }
          }  // end loop to control comparisons
          
          end--;

      }  // end loop to control passes

   }  // end method bubbleSort

   // swap two elements of an array
   public static void swap( Comparable[] arr, int first, int second )
   {
      Comparable hold;  // temporary holding area for swap

      hold = arr[ first ];         
      arr[ first ] = arr[ second ];  
      arr[ second ] = hold;
   }
}
