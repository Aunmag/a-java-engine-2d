package aunmag.nightingale.utilities;

import aunmag.nightingale.audio.AudioSample;
import aunmag.nightingale.audio.AudioSource;

public class UtilsAudio {

    public static AudioSource getOrCreateSound(String name) {
        AudioSource audioSource = new AudioSource();
        audioSource.setSample(AudioSample.getOrCreateOgg(name));
        return audioSource;
    }

}
