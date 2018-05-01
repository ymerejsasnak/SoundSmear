/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundsmear;

import java.util.Random;
import net.beadsproject.beads.data.Sample;
/**
 *
 * @author ymerejsasnak
 */
public class SmearingProcess {
    
    public static int lengthMS = 1000;
    public static int iterations = 10;
    public static boolean stereo = false;

    
    public static void setOutputLength(int l)
    {
        lengthMS = l;
    }
    
    public static void setIterations(int i)
    {
        iterations = i;
    }
    
    public static void setStereo(boolean s)
    {
        stereo = s;
    }
    
    public static Sample processFrames(AudioManager am)
    {
            
        // declare array and load frame data from audiomanager's input sample
        float[][] inFrameData = new float[2][(int) am.getInputSample().getNumFrames()];
        am.getInputSample().getFrames(0, inFrameData);
        
        
        // setup output frame array
        float[][] outFrameData = new float[2][(int)(lengthMS * 44.1)];
        
        Sample output = new Sample(lengthMS);
        
        
        Random r = new Random();
        
        for (int i = 0; i < iterations; i++)
        {
            
            int Loffset = r.nextInt(outFrameData[0].length);
            int Roffset = Loffset;
            
            // if set to, use separate offsets for left and right
            if (stereo)
                Roffset = r.nextInt(outFrameData[0].length);
            
            
            for (int frame = 0; frame < inFrameData[0].length; frame++)
            {
                outFrameData[0][(frame + Loffset) % outFrameData[0].length] += inFrameData[0][frame] * 0.2f;
                outFrameData[1][(frame + Roffset) % outFrameData[0].length] += inFrameData[1][frame] * 0.2f;
            }
        }
        
        
        
        output.putFrames(0, outFrameData);
        
        
        return output;
    }
    
   
    
}
