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
    
    

    
    public static Sample processFrames(AudioManager am)
    {
        
        float[][] inFrameData = new float[2][(int) am.getInputSample().getNumFrames()];
        am.getInputSample().getFrames(0, inFrameData);
        
        float[][] outFrameData = new float[2][inFrameData[0].length];
        
        Random r = new Random();
        
        for (int i = 0; i < 100; i++)
        {
            int offset = r.nextInt(inFrameData[0].length - 1);
            
            for (int frame = 0; frame < inFrameData[0].length; frame++)
            {
                outFrameData[0][(frame + offset) % inFrameData[0].length] += inFrameData[0][frame] * 0.1f;
                outFrameData[1][(frame + offset) % inFrameData[0].length] += inFrameData[0][frame] * 0.1f;
            }
        }
        
        Sample output = new Sample(inFrameData[0].length);
        
        output.putFrames(0, outFrameData);
        
        
        return output;
    }
    
   
    
}
