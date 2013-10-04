/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class CountSortTest {
 
    public static void main( String args[] ) {
        CountSort arr = new CountSort();
        System.out.println(arr);
        int[] sortedArray = new int[arr.getIntArr().length];
        sortedArray = arr.sort();
        
        CountSort arr2 = new CountSort(sortedArray);
        System.out.println(arr2);
    }
}
