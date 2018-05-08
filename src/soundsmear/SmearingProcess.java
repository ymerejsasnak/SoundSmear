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
    public static boolean randomPan = false;
    public static float pitchRange0 = 1;
    public static float pitchRange1 = 1;
    
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
        randomPan = s;
    }
    
    public static void setPitchRange0(float range)
    {
        pitchRange0 = range;
    }
    
    public static void setPitchRange1(float range)
    {
        pitchRange1 = range;
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
            float leftGain = .5f;
            float rightGain = .5f;
            
            int offset = r.nextInt(outFrameData[0].length);
                        
            
            if (randomPan)
            {
                // linear panning ok?
                leftGain = r.nextFloat();
                rightGain = 1 - leftGain;
            }
            
            //temporary stuff, clean it up!
            float lRate =  r.nextFloat() * (pitchRange0/1 - 1/pitchRange0) + 1/pitchRange0;
            float rRate =  r.nextFloat() * (pitchRange1/1 - 1/pitchRange1) + 1/pitchRange1;
            
            int writeFrame = 0;
            
            if (r.nextBoolean())
            {
                for (float frame = 0; frame < inFrameData0[0].length; frame+= lRate)
                {
                    outFrameData[0][(writeFrame + offset) % outFrameData[0].length] += inFrameData0[0][(int)frame] * leftGain;
                    outFrameData[1][(writeFrame + offset) % outFrameData[0].length] += inFrameData0[1][(int)frame] * rightGain;
                    writeFrame++;
                }
            } 
            else
            {
                for (float frame = 0; frame < inFrameData1[0].length; frame+= rRate)
                {
                    outFrameData[0][(writeFrame + offset) % outFrameData[0].length] += inFrameData1[0][(int)frame] * leftGain;
                    outFrameData[1][(writeFrame + offset) % outFrameData[0].length] += inFrameData1[1][(int)frame] * rightGain;
                    writeFrame++;
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
