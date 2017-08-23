package nightingale.audio;

import org.lwjgl.openal.AL10;

public class AudioSource {

    private final int id;

    public AudioSource() {
        id = AL10.alGenSources();
    }

    public void play(int buffer) {
        stop();
        AL10.alSourcei(id, AL10.AL_BUFFER, buffer);
        playContinue();
    }

    public void playContinue() {
        AL10.alSourcePlay(id);
        setVolume(1);
    }

    public void pause() {
        AL10.alSourcePause(id);
    }

    public void stop() {
        AL10.alSourceStop(id);
    }

    public void delete() {
        stop();
        AL10.alDeleteSources(id);
    }

    /* Setters */

    public void setVolume(float volume) {
        AL10.alSourcef(id, AL10.AL_GAIN, volume);
    }

    public void setPitch(float pitch) {
        AL10.alSourcef(id, AL10.AL_PITCH, pitch);
    }

    public void setPosition(float x, float y) {
        AL10.alSource3f(id, AL10.AL_POSITION, x / 32f, y / 32f, 0);
    }

    public void setVelocity(float x, float y) {
        AL10.alSource3f(id, AL10.AL_VELOCITY, x, y, 0);
    }

    public void setIsLooped(boolean isLooped) {
        AL10.alSourcei(id, AL10.AL_LOOPING, isLooped ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    /* Getters */

    public boolean isPlaying() {
        return AL10.alGetSourcei(id, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

}
