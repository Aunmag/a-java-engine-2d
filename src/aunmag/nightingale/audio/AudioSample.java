package aunmag.nightingale.audio;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.OggData;
import org.newdawn.slick.openal.OggDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.HashMap;

public class AudioSample {

    private static HashMap<String, Integer> samples = new HashMap<>();

    public static int getOrCreate(String name) {
        if (samples.containsKey(name)) {
            return samples.get(name);
        } else {
            int sample = loadOggSample(name);
            samples.put(name, sample);
            return sample;
        }
    }

    private static int loadOggSample(String name) {
        String path = "/" + name + ".ogg";
        InputStream inputStream = AudioMaster.class.getResourceAsStream(path);
        OggData oggData;

        try {
            oggData = new OggDecoder().getData(inputStream);
        } catch (IOException e) {
            String message = String.format("Can't load audio file at \"%s\"!", path);
            System.err.println(message);
            return 0;
        }

        IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        AL10.alGenBuffers(intBuffer);
        int buffer = intBuffer.get(0);

        int format = oggData.channels > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
        AL10.alBufferData(buffer, format, oggData.data, oggData.rate);

        return buffer;
    }

    public static void cleanUp() {
        for (int sample: samples.values()) {
            AL10.alDeleteBuffers(sample);
        }

        samples.clear();
    }

}
