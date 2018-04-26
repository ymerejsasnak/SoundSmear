/*
 
TO DO:

-cleanup/refactor existing code - make it clean and clear and tight
-also need lots of error checking/handling
-add options for output file length, # of paste iterations (figure way to scale volume properly)
-options to set start and end, and add variation (ie paste partial output)
-add ability to load multiple input files to randomly draw from, store multiple output files before saving
-add ability to save files of course (single or all)
-fix handling of stereo vs mono sounds
-options for panning and pitch variation per-paste
-helpful text display of it all
-maybe command based input (ie 'play [filename]' 'process [filename]' etc)
-ways to slowly change many above settings for morphing sound (maybe do it 'palindromically' to retain loopability)

-super simple gui eventually?


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
        int choice;
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
