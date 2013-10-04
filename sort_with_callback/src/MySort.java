/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class MySort {
    private Sortable callback;
    public MySort( Sortable cb){
        callback = cb;
    }
    public void sort(int [] arr){
        int insert;
        
        for ( int next = 1; next < arr.length; next++){
            insert = arr[next];
            
            int moveItem = next;
            
            while ( moveItem > 0 && callback.greater( arr[ moveItem - 1], insert ) ) {
                arr[ moveItem ] = arr[ moveItem - 1 ];
                moveItem--;
            }
            
            arr[ moveItem ] = insert;
        }
    }
}
