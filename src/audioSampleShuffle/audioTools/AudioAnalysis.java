/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioSampleShuffle.audioTools;

import audioSampleShuffle.FrequencyBands;
import audioSampleShuffle.ReadWrite;
import java.io.File;
import java.util.Arrays;

/**
 *
 * @author samqgoldman
 */
public class AudioAnalysis {
//    private float[] audioData;
    private String filePath;
    private int fileSize;
    private String RawTempFilePath = "";
    private float sampleRate;
    private int analysisInterval;
    private float[] frequencyBandDef;
    public fftStats[] fftData;
    
    //  only load 10Mb at once;
    final private int FILE_LOAD_CHUNK_SIZE = 1024*1024*10;
    
    public AudioAnalysis (String file, float sampleRate) {
        this.filePath = file;
        this.sampleRate = sampleRate;
        
//        fileSize = ReadWrite.getSize(filePath);
    }
    
    public void SetInterval(int interval) {
        analysisInterval = interval;
    }
    
    public void SetFrequencyBands (FrequencyBands curve) {
        frequencyBandDef = curve.values;
    }
    
    public void LoadWave() {
        //  creates the raw temp file.
        String[] pathPieces = filePath.split("[/]{1}");
        RawTempFilePath = "." + pathPieces[pathPieces.length-1].split("[.]{1}")[0];

        //  makes sure no previous tempo files are there from incomplete past runs.
        File deleteFile = new File(RawTempFilePath);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
        
        ReadWrite.wavToRaw(filePath, RawTempFilePath);
    }
    
    public void RunFFT() {
        fileSize = ReadWrite.getSize(RawTempFilePath);
        
        fftData = new fftStats[fileSize/(2*analysisInterval)];
        int fftCount = 0;
        
        //  breaking into loadable chunks (10Mb)
        for (int i = 0; i < fileSize; i = i + FILE_LOAD_CHUNK_SIZE) {
            
            System.out.println(Math.round(100*(float)i/fileSize) + "% FFT processed complete.");
            
            int selectionLength = FILE_LOAD_CHUNK_SIZE;
            if (i + selectionLength > fileSize)
                selectionLength = fileSize - i;
            
            //  load a chunk for analysis
            float[] fileChunk = ReadWrite.readRaw(RawTempFilePath, i, selectionLength);
            
            //  iterate through the pieces of the chunk, calc FFT.
            for (int j = 0; j <= fileChunk.length - analysisInterval; j += analysisInterval) {
                int chunkBounds[] = new int[] {j, j + analysisInterval};
                
                //  got to multiply chunkBounds by 2 to get from sample index to byte index.
                int sampleBounds[] = new int[] {chunkBounds[0] * 2 + i, chunkBounds[1] * 2 + i};
                
                fftData[fftCount] = new fftStats(Arrays.copyOfRange(fileChunk, chunkBounds[0], chunkBounds[1]), sampleBounds, frequencyBandDef, sampleRate);
                fftCount++;
            }
            
            
        }
        
        
    }
    
    public float[] GetAudioClip(int sampleStart, int sampleStop) {
        //  to handle 
        if (sampleStart <= -1)
            sampleStart = 0;
        if (sampleStop <= -1)
            sampleStop = fileSize;
        
        //  this is going to cause problems when the tails bled past the ends of the array.
        
        float[] output = ReadWrite.readRaw(RawTempFilePath, sampleStart, sampleStop - sampleStart);
        return output;
    }
    
    public int GetNumSamples() {
        return fileSize/2;
    }
    
    public void Close() {
        File deleteFile = new File(RawTempFilePath);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }
    
    public static double[] StereoToMono(double[][] stereo) {
        double[] mono = new double[stereo[0].length];
        for (int i = 0; i < stereo[0].length; i++) {
            mono[i] = stereo[0][i] * 0.5 + stereo[1][i] * 0.5;
        }
        return mono;
    }
    
}
