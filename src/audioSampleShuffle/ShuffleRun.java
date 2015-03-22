//
//// at some point you have to deal with the mp3 reader and its libraries, but for now we are good.
//
//
//package audioSampleShuffle;
//
////import audioSampleShuffle;
//import audioSampleShuffle.audioTools.AudioAnalysis;
//import audioSampleShuffle.audioTools.fftStats;
//import audioSampleShuffle.ReadWrite;
//
//import audioSampleShuffle.audioTools.WavFile;
//import java.io.*;
//import javax.swing.JFileChooser;
//
////import java.io.*;
////import java.util.logging.Level;
////import java.util.logging.Logger;
////
////import java.lang.*;
////import java.util.Arrays;
////import java.util.HashSet;
//
///**
// *
// * @author samqgoldman
// */
//class ShuffleRun {
//    
//    String audioPath = "../Audio/";
//    
//    //  1024 samples or .023 seconds
//    int sampleRate = 44100;
//    int fftLength = (int)Math.pow(2, 11);
//    
//            
//    public AudioAnalysis poolAudio;
//    public AudioAnalysis targetAudio;
//    
////    public ShuffleRun() {
////    }
//    
//    public void Run() {
//        
////        float[] testAudio = readRaw(audioPath + "squareWave400Low.raw");
////        float[] test2Audio = readRaw(audioPath + "icatsPromo.raw");
//        
//        String poolAudioName = "best of mono.wav";
//        String sourceAudioName = "02 Beautiful Morning.wav";
//        String outputAudioName = "beautiful morning 2048.wav";
//        
//
//        AudioAnalysis sourceAudio = new AudioAnalysis(audioPath + sourceAudioName, sampleRate);
//        sourceAudio.SetInterval(fftLength);
////        sourceAudio.SetFrequencyBands(FrequencyBands.FLAT_RESPONSE.values);
//        sourceAudio.SetFrequencyBands(FrequencyBands.WEIGHTED_B_RESPONSE.values);
//        sourceAudio.LoadWave();
//        sourceAudio.RunFFT();
//        
//        
//        AudioAnalysis poolAudio = new AudioAnalysis(audioPath + poolAudioName, sampleRate);
//        poolAudio.SetInterval(fftLength);
////        poolAudio.SetFrequencyBands(FrequencyBands.FLAT_RESPONSE.values);
//        poolAudio.SetFrequencyBands(FrequencyBands.WEIGHTED_B_RESPONSE.values);
//        poolAudio.LoadWave();
//        poolAudio.RunFFT();
//        
//        //  setup wav
//        try
//        {
//            WavFile outWav = WavFile.newWavFile(new File(audioPath + outputAudioName), 1, sourceAudio.GetNumSamples(), 16, sampleRate);
//
//        
//            //  in samples, not bytes
//            int crossFadeTailLength = 200;
//            final int FILE_WRITE_CHUNK_SIZE = 1024*1024*4;
//            float[] outputRemainder = null;
//
//            float[] outputAudio = null;
//            for (int i = 0; i < sourceAudio.fftData.length; i++) {
//                float minDiff = Float.MAX_VALUE;
//                int minDiffIndex = -1;
//                for (int j = 0; j < poolAudio.fftData.length; j++) {
//                    float currentDiff = CalcFFTDiff(sourceAudio.fftData[i], poolAudio.fftData[j]);
//                    if (currentDiff < minDiff & j != 0 & j!= poolAudio.fftData.length)
//                    {
//                        minDiff = currentDiff;
//                        minDiffIndex = j;
//                    }
//                }
//
//                //  pulls the audio sample needed, included crossfade tails, which have been adjusted to put them in bytes.
//                float[] amplitudeAdjustedSelection = poolAudio.GetAudioClip(
//                            poolAudio.fftData[minDiffIndex].sampleBounds[0] - crossFadeTailLength*2, 
//                            poolAudio.fftData[minDiffIndex].sampleBounds[1] + crossFadeTailLength*2
//                        ); 
//
//                //  adjusts the amplitude of the new selection to match the original selection.
//                float ampAdjFactor = sourceAudio.fftData[i].avgAmplitude / poolAudio.fftData[minDiffIndex].avgAmplitude;
//                amplitudeAdjustedSelection = amplify(amplitudeAdjustedSelection, ampAdjFactor);
//
//
//                //  gets called only after the write buffer has been written to a wav file, and we need to reset the outputAudio array.
//                if (outputRemainder != null & outputAudio == null) {
//                    outputAudio = outputRemainder;
//                }
//
//                outputAudio = crossfade(outputAudio, amplitudeAdjustedSelection, crossFadeTailLength * 2);
//
//                if (outputAudio.length >= FILE_WRITE_CHUNK_SIZE) {
//                    outputRemainder = new float[FILE_WRITE_CHUNK_SIZE / 4];
//                    System.arraycopy(outputAudio, outputAudio.length - outputRemainder.length, outputRemainder, 0, outputRemainder.length);
//
//                    double[] writeArray = new double[outputAudio.length - outputRemainder.length];
//                    for (int j = 0; j < writeArray.length; j++) {
//                        writeArray[j] = (double)(outputAudio[j] / 0x8000);
//                    }
//                    
//                    outWav.writeFrames(writeArray, writeArray.length);
//
//
//                    outputAudio = null;
//                }
//
//                System.out.println(i + "/" + sourceAudio.fftData.length);            
//
//
//            }
//            
//            //  write everything else to teh wav file.
//            double[] writeArray = new double[outputAudio.length];
//            for (int j = 0; j < writeArray.length; j++) {
//                writeArray[j] = (double)(outputAudio[j] / 0x8000);
//            }
//            outWav.writeFrames(writeArray, writeArray.length);
//            outWav.close();
//
//        }
//        catch (Exception e)
//        {
//            System.err.println(e);
//        }
//        
//        poolAudio.Close();
//        sourceAudio.Close();
//
////        writeRaw(audioPath + outputAudioName, outputAudio);
//        
//    }
//    
//    public void LoadAudio(AudioAnalysis audio) {
//        JFileChooser a = new JFileChooser();
//        int rVal = a.showDialog(null, "Fuck it up!");
//        if (rVal == JFileChooser.APPROVE_OPTION) {
//            
////            audio = new AudioAnalysis(audioPath, sampleRate)
//        }
//        else if (rVal == JFileChooser.CANCEL_OPTION) {
//            
//        }
//    }
//    
//    private float CalcFFTDiff(fftStats a, fftStats b) {
//        float diff = 0;
//        for (int i = 0; i < a.values.length; i++) {
//            diff += Math.abs(a.values[i] - b.values[i]);
//        }
//        return diff;
//    }
//    
//    
////    private float[] readRaw (String fileName) {
////        byte[] incomingFile = BytesStreamsAndFiles.read(fileName);
////        float[] newAudio = new float[incomingFile.length/2];
////        
////        //  convert from bytes to int
////        for (int i = 0; i < newAudio.length; i++) {
////            newAudio[i] = (float)((int)(incomingFile[2*i+1] << 8) | (incomingFile[2*i] & 0xFF));
////        }
////        return newAudio;
////    }
//
//    
//    private void writeRaw (String fileName, float[] floatData) {
//        byte[] byteData = new byte[floatData.length * 2];
//        
//        //  converts from int to byte
//        for (int i = 0; i < floatData.length; i++) {
//            byteData[2*i] = (byte)((int)floatData[i] & 0xFF);
//            byteData[2*i + 1] = (byte)((int)floatData[i] >> 8 & 0xFF);
//        }
//
//        //  writes the byte array to a file.
//        BytesStreamsAndFiles.write(byteData, fileName);
//    }        
//    
//    
//    private float[] amplify(float[] inputAudio, float quotient) {
//        for (int i = 0; i < inputAudio.length; i++) {
//            inputAudio[i] *= quotient;
//        }
//        return inputAudio;
//    }
//    
//    
//    private float[] crossfade(float[] firstAudio, float[] secondAudio, int numCrossSamples) {
//        if (firstAudio == null)
//            return secondAudio;
//        if (secondAudio == null)
//            return firstAudio;
//        
//        int resultingLength = firstAudio.length + secondAudio.length - numCrossSamples;
//        int startOfFadePos = firstAudio.length - numCrossSamples;
//        
//        float[] resultingArray = new float[resultingLength];
//        
//        //  copy every before the crossfade begins.
//        System.arraycopy(firstAudio, 0, resultingArray, 0, startOfFadePos);
//        
//        //  preform the crossfade
//        for (int i = 0; i < numCrossSamples; i++) {
//            resultingArray[startOfFadePos + i] = (float)(
//                    firstAudio[startOfFadePos + i] * Math.pow((0.5 + 0.5 * Math.cos(Math.PI * (float)i/numCrossSamples)), 0.5) + 
//                    secondAudio[i] * Math.pow((0.5 - 0.5 * Math.cos(Math.PI * (float)i/numCrossSamples)), 0.5)
//                );
//        }
//        
//        //  copy everything after the crossfade
//        System.arraycopy(secondAudio, numCrossSamples, resultingArray, firstAudio.length, secondAudio.length - numCrossSamples);
//        
//        return resultingArray;
//    }
//    
//  
//
//}
//
//
