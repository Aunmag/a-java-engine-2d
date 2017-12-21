package aunmag.nightingale.utilities;

import java.util.List;

public class UtilsLanguage {

    public static float[] convertListToArray(List<Float> list) {
        float[] array = new float[list.size()];

        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }

        return array;
    }

}
