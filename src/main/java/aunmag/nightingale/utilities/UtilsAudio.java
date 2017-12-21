package aunmag.nightingale.utilities;

import aunmag.nightingale.audio.AudioSample;
import aunmag.nightingale.audio.AudioSampleType;
import aunmag.nightingale.audio.AudioSource;

public class UtilsAudio {

    public static AudioSource getOrCreateSoundOgg(String name) {
        AudioSource audioSource = new AudioSource();
        audioSource.setSample(AudioSample.getOrCreate(name, AudioSampleType.OGG));
        return audioSource;
    }

    public static AudioSource getOrCreateSoundWav(String name) {
        AudioSource audioSource = new AudioSource();
        audioSource.setSample(AudioSample.getOrCreate(name, AudioSampleType.WAV));
        return audioSource;
    }

}
