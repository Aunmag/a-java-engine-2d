package aunmag.nightingale;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FrameRateTest {

    private static final double precision = 0.000000000000000015;

    @Test
    void testInitialize() {
        assertEquals(60, FrameRate.DEFAULT);

        FrameRate frameRate = new FrameRate(30);
        assertEquals(30, frameRate.getFrequency());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(1.0 / 30.0, frameRate.getTimeDuration());

        frameRate = new FrameRate(240);
        assertEquals(240, frameRate.getFrequency());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(1.0 / 240.0, frameRate.getTimeDuration());

        frameRate = new FrameRate(241);
        assertEquals(FrameRate.DEFAULT, frameRate.getFrequency());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(1.0 / 60.0, frameRate.getTimeDuration());

        frameRate = new FrameRate(1);
        assertEquals(1, frameRate.getFrequency());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(1.0, frameRate.getTimeDuration());

        frameRate = new FrameRate(0);
        assertEquals(FrameRate.DEFAULT, frameRate.getFrequency());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(1.0 / 60.0, frameRate.getTimeDuration());

        frameRate = new FrameRate(-1);
        assertEquals(FrameRate.DEFAULT, frameRate.getFrequency());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(1.0 / 60.0, frameRate.getTimeDuration());
    }

    @Test
    void testSetFrequency() {
        FrameRate frameRate = new FrameRate(FrameRate.DEFAULT);

        frameRate.setFrequency(30);
        assertEquals(30, frameRate.getFrequency());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(1.0 / 30.0, frameRate.getTimeDuration());

        frameRate.setFrequency(240);
        assertEquals(240, frameRate.getFrequency());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(1.0 / 240.0, frameRate.getTimeDuration());

        frameRate.setFrequency(241);
        assertEquals(240, frameRate.getFrequency());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(1.0 / 240.0, frameRate.getTimeDuration());

        frameRate.setFrequency(1);
        assertEquals(1, frameRate.getFrequency());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(1.0, frameRate.getTimeDuration());

        frameRate.setFrequency(0);
        assertEquals(1, frameRate.getFrequency());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(1.0 / 1.0, frameRate.getTimeDuration());

        frameRate.setFrequency(-1);
        assertEquals(1, frameRate.getFrequency());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(1.0 / 1.0, frameRate.getTimeDuration());
    }

    @Test
    void testCalculateIsNow() {
        FrameRate frameRate = new FrameRate(100);
        assertEquals(0.01, frameRate.getTimeDuration());

        double timeCurrent = 0.0;
        double timeDuration = frameRate.getTimeDuration();
        assertFalse(frameRate.calculateIsNow(timeCurrent));
        assertFalse(frameRate.calculateIsNow(timeCurrent));

        timeCurrent += timeDuration / 2.0;
        assertEquals(0.005, timeCurrent);
        assertFalse(frameRate.calculateIsNow(timeCurrent));

        timeCurrent = timeDuration;
        assertEquals(0.01, timeCurrent);
        assertTrue(frameRate.calculateIsNow(timeCurrent));
        assertFalse(frameRate.calculateIsNow(timeCurrent));

        timeCurrent += timeDuration;
        assertEquals(0.02, timeCurrent);
        assertTrue(frameRate.calculateIsNow(timeCurrent));
        assertFalse(frameRate.calculateIsNow(timeCurrent));

        timeCurrent += timeDuration * 10;
        assertEquals(0.12, timeCurrent, precision);
        assertTrue(frameRate.calculateIsNow(timeCurrent));
        assertFalse(frameRate.calculateIsNow(timeCurrent));
    }

}
