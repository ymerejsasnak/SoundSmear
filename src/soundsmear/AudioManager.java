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
    private Sample[] inputSample = new Sample[2];
    private SamplePlayer[] inputPlayer = new SamplePlayer[2];
    
    private Sample outputSample;
    private SamplePlayer outputPlayer;
    
    
    public AudioManager()
    {
        ac = new AudioContext();
        ac.start();
        
        System.out.println(System.getProperty("user.dir"));
        
        try
        {
            inputSample[0] = new Sample("default.wav");
            inputSample[1] = new Sample("default.wav");
            inputPlayer[0] = setupPlayer(new SamplePlayer(ac, inputSample[0]));
            inputPlayer[1] = setupPlayer(new SamplePlayer(ac, inputSample[1]));
        }
        catch (IOException e)
        {
            System.out.println("Error loading default wavs.");    
        }
    }
    
    
    public String loadSample(int index)
    {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            File sampleFile = fc.getSelectedFile();
            try
            {
                inputSample[index] = new Sample(sampleFile.getAbsolutePath());
                
                inputPlayer[index] = setupPlayer(new SamplePlayer(ac, inputSample[index]));
                
                System.out.println(sampleFile.getName() + " loaded.");
                
                return sampleFile.getName();
            }
            catch (IOException e)
            {
                System.out.println(e);
                System.out.println("Unable to load " + sampleFile.getName());
                return "";
            }
        }
        else 
        {
            System.out.println("User cancelled selection. No file loaded.");
            return "";
        }  
    }
    
    
    public void playInput(int index)
    {        
        if (inputPlayer[index] == null) { return; }
        
        inputPlayer[index].reTrigger();
    }
    
    
    public void playOutput()
    {
        if (outputPlayer == null) { return; }
        
        outputPlayer.setLoopType(SamplePlayer.LoopType.NO_LOOP_FORWARDS);
        outputPlayer.reTrigger();
    }
    
    
    public void loopOutput()
    {        
        if (outputPlayer == null) { return; }
        
        outputPlayer.setLoopType(SamplePlayer.LoopType.LOOP_FORWARDS);
        outputPlayer.reTrigger();
    }
    
    
    public void stopSounds()
    {
        if (inputPlayer[0] != null) {  inputPlayer[0].pause(true);  }
        if (inputPlayer[1] != null) {  inputPlayer[1].pause(true);  }
        if (outputPlayer != null) {  outputPlayer.pause(true);  }
    }
    
    
    public Sample getInputSample(int index)
    {
        return inputSample[index];
    }
    
    
    public void processInput()
    {
        outputSample = SmearingProcess.processFrames(this);
        
        if (outputPlayer != null) {  outputPlayer.pause(true);  }
        
        outputPlayer = setupPlayer(new SamplePlayer(ac, outputSample));
    }
    
    
    private SamplePlayer setupPlayer(SamplePlayer sp)
    {
        sp.pause(true);
        sp.setKillOnEnd(false);
        ac.out.addInput(sp);
        
        return sp;
    }
}
