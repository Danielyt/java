/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */

import java.util.Random;

public class CountSort {
    
    private int[] intArr;
    private static Random generator = new Random();

    public CountSort() {
        int[] array = new int[20];
        for (int i = 0; i < array.length; i++)
            array[i] = 10 + generator.nextInt(11);
        setIntArr(array);
    }
    
    public CountSort(int[] array) {
        setIntArr(array);
    }
        
    public CountSort(CountSort cSort){
        this(cSort.getIntArr());
    }
    
    public void setIntArr(int[] array) {
        
        if ( array != null) {
            boolean containsNegativeIntegers = false;
            //check if array contains negative integers;
            for (int i = 0; i < array.length; i++)
                if (array[i] < 0){
                    containsNegativeIntegers = true;
                    break;
                }
            if (!containsNegativeIntegers) {
                intArr = new int[array.length];
                for (int i = 0; i < intArr.length; i++)
                    intArr[i] = array[i];
            }
            else {
                intArr = new int[1];
                intArr[0] = 0;
            }
        }
        else {
            intArr = new int[1];
            intArr[0] = 0;
        }
    }

    public int[] getIntArr() {
        int[] returnArray = new int[intArr.length];
        
        for (int i = 0; i < intArr.length; i++)
            returnArray[i] = intArr[i];
        return returnArray;
    }

    @Override
    public String toString() {
        String resultString = "";
        
        for (int i = 0; i < intArr.length; i++)
            resultString += intArr[i] + " ";
        
        return resultString;
    }
    
    private int getMax(){
        int max = 0;
        for (int i = 0; i < intArr.length; i++)
            if ( intArr[i] > max )
                max = intArr[i];
        return max;
    }
    
    public int[] sort() {
        int [] c = new int [this.getMax() + 1];
        int [] sortedArray = new int[intArr.length];
        
        for (int i = 0; i < c.length; i++ )
            c[i] = 0;
        for (int i = 0; i < intArr.length; i++)
            c[intArr[i]]++;
        for (int i = 1; i < c.length; i++)
            c[i] = c[i] + c[i-1];
        for (int i = intArr.length - 1; i >= 0; i--){
            sortedArray[c[intArr[i]] - 1] = intArr[i];
            c[intArr[i]]--;
        }
        return sortedArray;
    }
}
