/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioSampleShuffle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.*;

import audioSampleShuffle.audioTools.WavFile;
import audioSampleShuffle.audioTools.AudioAnalysis;

/**
 *
 * @author samqgoldman
 */
public class ReadWrite {
    
    
    public static void wavToRaw(String wavFileName, String rawFileName) {
        
        final int bufferSize = 2048;
        
        try 
        {
            WavFile wavFile = WavFile.openWavFile(new File(wavFileName));
            if (wavFile.getNumChannels() == 1) {
                double[] buffer = new double[bufferSize];
                int framesRead;
                
                do {
                    framesRead = wavFile.readFrames(buffer, 2048);
                    
                    writeRaw(rawFileName, buffer, true);
                    
                } while (framesRead != 0);
                
            }
            else if (wavFile.getNumChannels() == 2) {
                double[][] buffer = new double[2][bufferSize];
                int framesRead;
                do {
                    framesRead = wavFile.readFrames(buffer, 2048);
                    
                    writeRaw(rawFileName, AudioAnalysis.StereoToMono(buffer), true);
                    
                } while (framesRead != 0);
                
            }
        }
        catch (Exception e)
        {
                System.err.println(e);
        }

        
    }
    
//    public static void writeWav()
    
    
    public static float[] readRaw (String inputFileName, int fileOffset, int readLength) {
        byte[] incomingFile = read(inputFileName, fileOffset, readLength);
        float[] newAudio = new float[incomingFile.length/2];
        
        //  convert from bytes to int
        for (int i = 0; i < newAudio.length; i++) {
            newAudio[i] = (float)((int)(incomingFile[2*i+1] << 8) | (incomingFile[2*i] & 0xFF));
        }
        return newAudio;
    }

    
    public static void writeRaw (String outputFileName, double[] doubleData, boolean append) {
        byte[] byteData = new byte[doubleData.length * 2];
        
        //  converts from int to byte
        for (int i = 0; i < doubleData.length; i++) {
            double scaledDataPoint = 0x8000 * doubleData[i];
            byteData[2*i] = (byte)((int)scaledDataPoint & 0xFF);
            byteData[2*i + 1] = (byte)((int)scaledDataPoint >> 8 & 0xFF);
        }

        //  writes the byte array to a file.
        write(outputFileName, byteData, append);
    }
    public static void writeRaw (String outputFileName, float[] floatData, boolean append) {
        double doubleData[] = new double[floatData.length];
        for (int i = 0; i < floatData.length; i++) {
            doubleData[i] = (double) floatData[i];
        }
        writeRaw(outputFileName, doubleData, append);
    }
    
    public static String readString(String inputFileName, int fileOffset, int readLength) {
        byte[] bytes = read(inputFileName, fileOffset, readLength);
        String returnST = "";
        for (int i = 0; i < bytes.length; i++) {
            returnST += (char)bytes[i];
        }
        return returnST;
    }
    
    public static void writeString(String outputFileName, String writeString, boolean append) {
        byte[] byteString = new byte[writeString.length()];
        for (int i = 0; i < byteString.length; i++) {
            byteString[i] = (byte)writeString.charAt(i);
        }
        write(outputFileName, byteString, append);
    }
    
    public static int getSize(String fileName) {
        File file = new File(fileName);
        return (int)file.length();
    }
    
    private static byte[] read(String inputFileName, int fileOffset, int readLength) {
        File file = new File(inputFileName);
        byte[] result = new byte[readLength];
        int bytesRead = 0;
        try {
            InputStream input = new BufferedInputStream(new FileInputStream(file));
            input.skip(fileOffset);
            bytesRead = input.read(result, 0, readLength);
	         
        }
        catch (FileNotFoundException ex) {
          log("\"" + inputFileName + "\"" + "file not found.");
        }
        catch (IOException ex) {
          log(ex);
        }
        
//        log(bytesRead + " out of " + readLength + " read.");
        return result;
    }
    
    private static void write(String outputFileName, byte[] data, boolean append) {
        try {
            FileOutputStream outStream = new FileOutputStream(outputFileName, append);
            outStream.write(data);
        }
        catch(IOException ioe) {
            log("IOException: " + ioe);
        }
    }
    
    
    public static File[] listFiles(String dirName, String suffix) {
    	File dir = new File(dirName);
        final String suff = suffix;
    	return dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename)
    	              { return filename.endsWith(suff); }
    	} );

    }
    
    
    private static void log(Object aThing){
      System.out.println(String.valueOf(aThing));
    }
    
}
