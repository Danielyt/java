/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class Cryptor {
    public static final int SHIFT_LENGTH = 3;
    
    public String encrypt( String plainText){
        char[] cipherArray = plainText.toCharArray();
        for ( int i = 0; i < cipherArray.length; i++)
            cipherArray[i] = (char)( ( cipherArray[i] + SHIFT_LENGTH - 'A' ) % 26 + 'A');
        return new String(cipherArray);
    }
    
    public String decrypt( String cipherText){
        char[] plainArray = cipherText.toCharArray();
        for ( int i = 0; i < plainArray.length; i ++)
            plainArray[i] = (char)( ( plainArray[i] - SHIFT_LENGTH - 'A' + 26) % 26 + 'A' );
        return new String(plainArray);
    }
}
