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
    
    

    
    public static Sample processFrames(AudioManager am, int lengthMS, int iterations)
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
            int offset = r.nextInt(outFrameData[0].length - 1);
            
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
