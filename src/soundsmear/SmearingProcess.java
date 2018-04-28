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

    
    public static void setOutputLength(int l)
    {
        lengthMS = l;
    }
    
    public static void setIterations(int i)
    {
        iterations = i;
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
            int offset = r.nextInt(outFrameData[0].length);
            
            for (int frame = 0; frame < inFrameData[0].length; frame++)
            {
                outFrameData[0][(frame + offset) % outFrameData[0].length] += inFrameData[0][frame] * 0.2f;
                outFrameData[1][(frame + offset) % outFrameData[0].length] += inFrameData[0][frame] * 0.2f;
            }
        }
        
        
        
        output.putFrames(0, outFrameData);
        
        
        return output;
    }
    
   
    
}
