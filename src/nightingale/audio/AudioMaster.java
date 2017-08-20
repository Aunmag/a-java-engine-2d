package nightingale.audio;

import org.lwjgl.openal.*;
import org.newdawn.slick.openal.WaveData;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class AudioMaster {

    private static List<Integer> buffers = new ArrayList<>();
    private static long device;
    private static long context;

    public static void initialize() {
        device = ALC10.alcOpenDevice((ByteBuffer) null);
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);

        context = ALC10.alcCreateContext(device, (IntBuffer) null);
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
    }

    public static void setListenerData(float x, float y) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, 0);
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }

    public static int loadSound(String filename) {
        int buffer = AL10.alGenBuffers();
        buffers.add(buffer);
        WaveData waveData = WaveData.create(filename);
        AL10.alBufferData(buffer, waveData.format, waveData.data, waveData.samplerate);
        waveData.dispose();
        return buffer;
    }

    public static void terminate() {
        for (int buffer: buffers) {
            AL10.alDeleteBuffers(buffer);
        }

        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
    }

}
