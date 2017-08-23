package nightingale.audio;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;
import org.newdawn.slick.openal.OggData;
import org.newdawn.slick.openal.OggDecoder;
import org.newdawn.slick.openal.WaveData;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

public class AudioMaster {

    private static HashMap<String, Integer> sounds = new HashMap<>();
    private static long device;
    private static long context;
    private static final int LISTENER_OFFSET_Z = 1;

    public static void initialize() {
        device = ALC10.alcOpenDevice((ByteBuffer) null);
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);

        context = ALC10.alcCreateContext(device, (IntBuffer) null);
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
    }

    public static int getOrCreate(String name) {
        if (sounds.containsKey(name)) {
            return sounds.get(name);
        } else {
            int sound = loadOggSound(name);
            sounds.put(name, sound);
            return sound;
        }
    }

    private static int loadOggSound(String name) {
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

    private static int loadWavSound(String name) {
        String path = "/" + name + ".wav";
        InputStream inputStream = AudioMaster.class.getResourceAsStream(path);
        WaveData waveData = WaveData.create(inputStream);

        int buffer = AL10.alGenBuffers();
        AL10.alBufferData(buffer, waveData.format, waveData.data, waveData.samplerate);
        waveData.dispose();
        return buffer;
    }

    public static void terminate() {
        for (int buffer: sounds.values()) {
            AL10.alDeleteBuffers(buffer);
        }

        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
    }

    /* Setters */

    public static void setListenerPosition(
            float x,
            float y,
            float z,
            Vector3f orientationVector
    ) {
        AL10.alListener3f(AL10.AL_POSITION, x / 32f, y / 32f, z + LISTENER_OFFSET_Z);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer orientationBuffer = byteBuffer.asFloatBuffer();
        orientationBuffer.put(0, orientationVector.x); // Look at X
        orientationBuffer.put(1, orientationVector.y); // Look at Y
        orientationBuffer.put(2, orientationVector.z); // Look at Z
        orientationBuffer.put(3, 0); // Up X
        orientationBuffer.put(4, 0); // Up Y
        orientationBuffer.put(5, 1); // Up Z

        AL10.alListenerfv(AL10.AL_ORIENTATION, orientationBuffer);
    }

    public static void setListenerVelocity(Vector3f velocity) {
        AL10.alListener3f(AL10.AL_VELOCITY, velocity.x, velocity.y, velocity.z);
    }

}
