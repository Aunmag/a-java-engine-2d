package aunmag.nightingale.utilities;

import aunmag.nightingale.audio.AudioSample;
import aunmag.nightingale.audio.AudioSampleType;
import aunmag.nightingale.audio.AudioSource;

public class UtilsAudio {

    public static AudioSource getOrCreateSound(String name, AudioSampleType type) {
        AudioSource audioSource = new AudioSource();
        audioSource.setSample(AudioSample.getOrCreate(name, type));
        return audioSource;
    }

}
