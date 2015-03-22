package audioSampleShuffle;

import audioSampleShuffle.ReadWrite;
import java.util.Arrays;

public class FrequencyBands {

    
    public float[] values;
    
    public FrequencyBands (String filePath) {
        String fileString = ReadWrite.readString(filePath, 0, ReadWrite.getSize(filePath));
        String[] dataPoints = fileString.split("\n");
        values = new float[dataPoints.length];
        for (int i = 0; i < dataPoints.length; i++) {
            values[i] = Float.parseFloat( dataPoints[i] );
        }
        Arrays.sort(values);
        
        
    }
    
//    FLAT_RESPONSE(new float[] {
//            14.1f,
//            17.8f,
//            22.4f,
//            28.2f,
//            35.5f,
//            44.7f,
//            56.2f,
//            70.8f,
//            89.1f,
//            112,
//            141,
//            178,
//            224,
//            282,
//            355,
//            447,
//            562,
//            708,
//            891,
//            1122,
//            1413,
//            1778,
//            2239,
//            2818,
//            3548,
//            4467,
//            5623,
//            7079,
//            8913,
//            11220,
//            14130,
//            17780,
//            22390,
//    }),
//    WEIGHTED_A_RESPONSE(new float[] {
//            14.1f,
//            17.8f,
//            22.4f,
//            28.2f,
//            35.5f,
//            44.7f,
//            56.2f,
//            70.8f,
//            89.1f,
//            112f,
//            141f,
//            178f,
//            224f,
//            282f,
//            318.5f,
//            355f,
//            401f,
//            447f,
//            504.5f,
//            562f,
//            635f,
//            708f,
//            799.5f,
//            891f,
//            1006.5f,
//            1122f,
//            1267.5f,
//            1413f,
//            1595.5f,
//            1778f,
//            2008.5f,
//            2239f,
//            2528.5f,
//            2818f,
//            3183f,
//            3548f,
//            4467f,
//            5623f,
//            7079f,
//            8913f,
//            11220f,
//            14130f,
//            17780f,
//            22390f,
//    }),
//    WEIGHTED_B_RESPONSE(new float[] {
//            14.1f,
//            17.8f,
//            22.4f,
//            28.2f,
//            35.5f,
//            44.7f,
//            56.2f,
//            70.8f,
//            89.1f,
//            112f,
//            141f,
//            178f,
//            224f,
//            282f,
//            300.25f,
//            318.5f,
//            336.75f,
//            355f,
//            378f,
//            401f,
//            424f,
//            447f,
//            475.75f,
//            504.5f,
//            533.25f,
//            562f,
//            598.5f,
//            635f,
//            671.5f,
//            708f,
//            753.75f,
//            799.5f,
//            845.25f,
//            891f,
//            948.75f,
//            1006.5f,
//            1064.25f,
//            1122f,
//            1194.75f,
//            1267.5f,
//            1340.25f,
//            1413f,
//            1504.25f,
//            1595.5f,
//            1686.75f,
//            1778f,
//            1893.25f,
//            2008.5f,
//            2123.75f,
//            2239f,
//            2383.75f,
//            2528.5f,
//            2673.25f,
//            2818f,
//            3000.5f,
//            3183f,
//            3548f,
//            4467f,
//            5623f,
//            7079f,
//            8913f,
//            11220f,
//            14130f,
//            17780f,
//            22390f,
//    });
//    
//    
//    public final float[] values;
//    FrequencyBands(float[] fart) {
//        this.values = fart;
//    }
}