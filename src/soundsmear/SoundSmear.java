/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundsmear;

import java.util.Scanner;


/**
 *
 * @author ymerejsasnak
 */
public class SoundSmear {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       AudioManager am = new AudioManager();
       runMenu(am); 
       System.exit(0);
    }
    
    
    public static void runMenu(AudioManager am)
    {
        int choice = 0;
        Scanner s = new Scanner(System.in);
        
        do {


            System.out.println("\n" +
                    "1. Load input file\n" +
                    "2. Play original sound\n" +
                    "3. Process sound\n" +
                    "4. Play processed sound\n" +
                    "5. Loop processed sound\n" +
                    "6. Stop all sounds\n" +
                    "7 exit"

                    );
            
            choice = s.nextInt();
            
            switch(choice)
            {
                case 1:
                    am.loadSample();
                    break;
                case 2:
                    am.playInput();
                    break;
                case 3:
                    am.processInput();
                    break;
                case 4:
                    am.playOutput();
                    break;
                case 5:
                    am.loopOutput();
                    break;
                case 6:
                    am.stopSounds();
                    break;
                    
                case 7:
                    break;
                    
            }
            
        } while (choice != 7);
    }
    
}
