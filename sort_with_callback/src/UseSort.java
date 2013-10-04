/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */

import java.util.Random;
public class UseSort {
    public static void printArray(int [] arr) {
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
    
    public static void main (String args[]){
        Random generator = new Random();
        int size = 20;
        int [] data = new int[size];
        
        for ( int i = 0; i < data.length; i++) {
            data[i] = generator.nextInt(101);
        }
        printArray(data);
        SortOrder so = new SortOrder();
        MySort up = new MySort(so.getUpward());
        MySort down = new MySort(so.getDownward());
        
        System.out.println( "Sorted upwards: ");
        up.sort(data);
        printArray(data);
        
        System.out.println( "Sorted downwards: ");
        down.sort(data);
        printArray(data);
    }
    
}
