/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundsmear;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.ugens.SamplePlayer;

import javax.swing.JFileChooser;
import java.io.*;
/**
 *
 * @author ymerejsasnak
 */
public class AudioManager {

    private final AudioContext ac;
    private Sample inputSample;
    private SamplePlayer inputPlayer;
    
    
    public AudioManager()
    {
        ac = new AudioContext();
        ac.start();
        
    }
    
    
    public void loadSample()
    {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            File sampleFile = fc.getSelectedFile();
            try
            {
                inputSample = new Sample(sampleFile.getAbsolutePath());
                inputPlayer = new SamplePlayer(ac, inputSample);
                inputPlayer.pause(true);
                inputPlayer.setKillOnEnd(false);
                ac.out.addInput(inputPlayer);
                
                System.out.println(sampleFile.getName() + " loaded.");
            }
            catch (IOException e)
            {
                System.out.println(e);
                System.out.println("Unable to load " + sampleFile.getName());
            }
        }
        else 
        {
            System.out.println("User cancelled selection. No file loaded.");
        }
        
        
    }
    
    
    public void playInput()
    {
        
        inputPlayer.reTrigger();
        
        //inputPlayer.
    }
}
