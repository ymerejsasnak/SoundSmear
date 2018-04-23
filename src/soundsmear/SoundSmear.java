/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundsmear;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.WavePlayer;

/**
 *
 * @author ymerejsasnak
 */
public class SoundSmear {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AudioContext ac = new AudioContext();
        WavePlayer wp = new WavePlayer(ac, 440.0f, Buffer.SINE);
        ac.out.addInput(wp);
        ac.start();
    }
    
}
