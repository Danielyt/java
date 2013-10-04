/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
import java.util.Random;
import javax.swing.JOptionPane;

public class BubbleSortTest {
    private static Comparable[] arrComparable;
    public static String arrToString(){
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < arrComparable.length; i++ )
            result.append(arrComparable[i] + "; ");
        return result.toString();
    }
    
    public static void main(String args[]){
        arrComparable = new Comparable[3];
        Random generator = new Random();
        
        for (int i = 0; i < arrComparable.length; i++){
            arrComparable[i] = new Point(10 + generator.nextInt(41), 10 + generator.nextInt(41));
        }
        
        String pointsBeforeSorting = arrToString();
        
        BubbleSort.sortArray(arrComparable);
        
        String pointsAfterSorting = arrToString();
        
        for (int i = 0; i < arrComparable.length; i++) {
            arrComparable[i] = new Circle( ((Point)arrComparable[i]).getX(),
                    ((Point)arrComparable[i]).getY(), 10 + generator.nextInt(21));
        }
        
        String circlesBeforeSorting = arrToString();
        
        BubbleSort.sortArray(arrComparable);
        
        String circlesAfterSorting = arrToString();
        
        for (int i = 0; i < arrComparable.length; i++){
            arrComparable[i] = new Cylinder( ((Circle)arrComparable[i]).getX(),
                    ((Circle)arrComparable[i]).getY(), ((Circle)arrComparable[i]).getRadius(),
                    10 + generator.nextInt(51));
        }
        
        String cylindersBeforeSorting = arrToString();
        
        BubbleSort.sortArray(arrComparable);
        
        String cylindersAfterSorting = arrToString();
        
        JOptionPane.showMessageDialog(null, "Points before sorting:\n" + pointsBeforeSorting + "\n" +
                "Points after sorting:\n" + pointsAfterSorting + "\n" +
                "Circles before sorting:\n" + circlesBeforeSorting + "\n" +
                "Circles after sorting:\n" + circlesAfterSorting + "\n" +
                "Cylinders before sorting:\n" + cylindersBeforeSorting + "\n" +
                "Cylinders after sorting:\n" + cylindersAfterSorting);
    }
}
