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
            
        // declare array and load frame data from audiomanager's input samples
        float[][] inFrameData0 = new float[2][(int) am.getInputSample(0).getNumFrames()];
        am.getInputSample(0).getFrames(0, inFrameData0);
        float[][] inFrameData1 = new float[2][(int) am.getInputSample(1).getNumFrames()];
        am.getInputSample(1).getFrames(0, inFrameData1);
        
        
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
            
            
            if (r.nextBoolean())
            {
                for (int frame = 0; frame < inFrameData0[0].length; frame++)
                {
                    outFrameData[0][(frame + Loffset) % outFrameData[0].length] += inFrameData0[0][frame];
                    outFrameData[1][(frame + Roffset) % outFrameData[0].length] += inFrameData0[1][frame];
                }
            } 
            else
            {
                for (int frame = 0; frame < inFrameData1[0].length; frame++)
                {
                    outFrameData[0][(frame + Loffset) % outFrameData[0].length] += inFrameData1[0][frame];
                    outFrameData[1][(frame + Roffset) % outFrameData[0].length] += inFrameData1[1][frame];
                }
            }
        }
        
        //normalize output
        
        float maxValue = 0;
        for (int i = 0; i < outFrameData[0].length; i++)
        {
            // get max value
            maxValue = Math.max(maxValue, outFrameData[0][i]);
            maxValue = Math.max(maxValue, outFrameData[1][i]);
        }
        
        for (int i = 0; i < outFrameData[0].length; i++)
        {
            outFrameData[0][i] = outFrameData[0][i] / maxValue;
            outFrameData[1][i] = outFrameData[1][i] / maxValue;
        }
        
        
        
        output.putFrames(0, outFrameData);
        
        
        return output;
    }
    
   
    
}
