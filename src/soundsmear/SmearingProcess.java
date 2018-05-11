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
    
    private static int lengthMS = 1000;
    private static int iterations = 10;
    private static boolean randomPan = false;
    private static float pitchRange0 = 1;
    private static float pitchRange1 = 1;
    
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
        float[][] inFrameDataA = new float[2][(int) am.getInputSample(0).getNumFrames()];
        am.getInputSample(0).getFrames(0, inFrameDataA);
        float[][] inFrameDataB = new float[2][(int) am.getInputSample(1).getNumFrames()];
        am.getInputSample(1).getFrames(0, inFrameDataB);
        
        // setup output frame array
        float[][] outFrameData = new float[2][(int)(lengthMS * 44.1)];
               
        Random r = new Random();
        
                
                
        for (int i = 0; i < iterations; i++)
        {
            
            
            float leftGain, rightGain;
            
            if (randomPan)
            {
                leftGain = r.nextFloat();
                rightGain = 1 - leftGain;
            }
            else 
            {
                leftGain = rightGain = .5f;
            }
            
              
            
            
            float rate;
            float[][] inData;
            
            // choose sample A or B and set rate according to settings
            if (r.nextBoolean())
            {
                inData = inFrameDataA;
                rate = r.nextFloat() * (pitchRange0/1 - 1/pitchRange0) + 1/pitchRange0;
            }
            else
            {
                inData = inFrameDataB;
                rate = r.nextFloat() * (pitchRange1/1 - 1/pitchRange1) + 1/pitchRange1;
            }
            
            
            
            // position to start working from
            int offset = r.nextInt(outFrameData[0].length);
           
            int writeFrame = 0;
            
            for (float frame = 0; frame < inData[0].length; frame += rate)
            {                   
                outFrameData[0][(writeFrame + offset) % outFrameData[0].length] += inData[0][(int)frame] * leftGain;
                outFrameData[1][(writeFrame + offset) % outFrameData[0].length] += inData[1][(int)frame] * rightGain;
                writeFrame++;
            }
        }
        
        
        // create sample and write frames to it
        Sample output = new Sample(lengthMS);
        output.putFrames(0, normalize(outFrameData)); 
        
        return output;
    }
    
       
    
    private static float[][] normalize(float[][] frameData)
    {
        
        float maxValue = 0;
        for (int i = 0; i < frameData[0].length; i++)
        {
            // get max value
            maxValue = Math.max(maxValue, frameData[0][i]);
            maxValue = Math.max(maxValue, frameData[1][i]);
        }
        
        for (int i = 0; i < frameData[0].length; i++)
        {
            frameData[0][i] = frameData[0][i] / maxValue;
            frameData[1][i] = frameData[1][i] / maxValue;
        }

        return frameData;
    }
}
