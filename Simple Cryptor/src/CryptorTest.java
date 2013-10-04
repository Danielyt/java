/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
/* 
 * Encrypts a string consisting of capital latin letters
 */
import java.util.Scanner;

public class CryptorTest {
    public static void main ( String args[] ){
        Scanner input = new Scanner ( System.in );
        int userChoice;
        String text; //text to either encrypt ot decrypt
        Cryptor cryptor = new Cryptor();
        
        while (true){
            System.out.print("Choose what to do:\n" +
                    "Press 1 to encrypt.\n" +
                    "Press 2 to decrypt.\n" +
                    "Press 3 to exit.\n" +
                    "Your choice? (1/2/3):");
            userChoice = input.nextInt();
            
            input.nextLine(); //clear buffer
            switch( userChoice ){
                case 1:
                    System.out.print("Enter plain text to encrypt:");
                    text = input.nextLine();
                    System.out.println("The cipher text is: " + cryptor.encrypt(text));
                    break;
                case 2:
                    System.out.print("Enter cipher text to decrypt:");
                    text = input.nextLine();
                    System.out.println("The plain text is: " + cryptor.decrypt(text));
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
            System.out.println();
        }
    }
}
