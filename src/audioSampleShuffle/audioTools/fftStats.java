/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioSampleShuffle.audioTools;

import java.lang.*;
import com.badlogic.audio.analysis.FFT;

/**
 *
 * @author samqgoldman
 */
public class fftStats {
    public int[] sampleBounds = new int[2];
    public float[] values;
    public float avgAmplitude;
    
    /**
     * initializes the object by running an FFT on the input audio.
     * 
     * @param audioData
     *      array of floats, where each float is one sample in an audio clip.
     * @param bounds
     * @param freqBands
     * @param sampleRate 
     */
    public fftStats(
            float[] audioData, 
            int[] bounds, 
            float[] freqBands, 
            float sampleRate
            ) 
    {
        sampleBounds = bounds;
        
        values = new float[freqBands.length - 1];
        
        FFT fftProcess = new FFT(audioData.length, sampleRate);
        fftProcess.forward(audioData);
        
        //  calc the frequency band average amplitudes.
        for (int i = 0; i < values.length; i++) {
            values[i] = (float)/*Math.log10*/(fftProcess.calcAvg(freqBands[i], freqBands[i+1]));
        }
        avgAmplitude = (float)(fftProcess.calcAvg(80, 100000));
    }
    
}
