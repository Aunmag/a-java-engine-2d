package aunmag.nightingale;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrameRateTest {

    private static final double precision = 0.0000000000001;

    @Test
    void testInitialize() {
        assertEquals(60, FrameRate.DEFAULT);

        FrameRate frameRate = new FrameRate(30);
        assertEquals(30, frameRate.getFrequency());
        assertEquals(1.0 / 30.0, frameRate.getTimeDuration());

        frameRate = new FrameRate(1000);
        assertEquals(1000, frameRate.getFrequency());
        assertEquals(1.0 / 1000.0, frameRate.getTimeDuration());

        frameRate = new FrameRate(1);
        assertEquals(1, frameRate.getFrequency());
        assertEquals(1.0, frameRate.getTimeDuration());

        frameRate = new FrameRate(0);
        assertEquals(FrameRate.DEFAULT, frameRate.getFrequency());
        assertEquals(1.0 / 60.0, frameRate.getTimeDuration());

        frameRate = new FrameRate(-1);
        assertEquals(FrameRate.DEFAULT, frameRate.getFrequency());
        assertEquals(1.0 / 60.0, frameRate.getTimeDuration());
    }

    @Test
    void testSetFrequency() {
        FrameRate frameRate = new FrameRate(FrameRate.DEFAULT);

        frameRate.setFrequency(30);
        assertEquals(30, frameRate.getFrequency());
        assertEquals(1.0 / 30.0, frameRate.getTimeDuration());

        frameRate.setFrequency(1000);
        assertEquals(1000, frameRate.getFrequency());
        assertEquals(1.0 / 1000.0, frameRate.getTimeDuration());

        frameRate.setFrequency(1);
        assertEquals(1, frameRate.getFrequency());
        assertEquals(1.0, frameRate.getTimeDuration());

        frameRate.setFrequency(0);
        assertEquals(1, frameRate.getFrequency());
        assertEquals(1.0 / 1.0, frameRate.getTimeDuration());

        frameRate.setFrequency(-1);
        assertEquals(1, frameRate.getFrequency());
        assertEquals(1.0 / 1.0, frameRate.getTimeDuration());
    }

    @Test
    void testTryNext() {
        FrameRate frameRate = new FrameRate(10);
        assertEquals(0.1, frameRate.getTimeDuration());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(0.0, frameRate.getTimeLastUpdate());

        double timeCurrent = 0.0;
        assertFalse(frameRate.tryNext(timeCurrent));
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(0.0, frameRate.getTimeLastUpdate());

        timeCurrent = 0.05; // + 0.05 seconds
        assertFalse(frameRate.tryNext(timeCurrent));
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(0.0, frameRate.getTimeLastUpdate());

        timeCurrent = 0.1; // + 0.05 seconds
        assertTrue(frameRate.tryNext(timeCurrent));
        assertEquals(0.1, frameRate.getTimeDelta());
        assertEquals(0.1, frameRate.getTimeLastUpdate());

        timeCurrent = 0.2; // + 0.1 seconds
        assertTrue(frameRate.tryNext(timeCurrent));
        assertEquals(0.1, frameRate.getTimeDelta());
        assertEquals(0.2, frameRate.getTimeLastUpdate());

        timeCurrent = 0.35; // + 0.15 seconds
        assertTrue(frameRate.tryNext(timeCurrent));
        assertEquals(0.15, frameRate.getTimeDelta(), precision);
        assertEquals(0.35, frameRate.getTimeLastUpdate(), precision);

        timeCurrent = 0.30; // - 0.05 seconds
        assertFalse(frameRate.tryNext(timeCurrent));
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(0.35, frameRate.getTimeLastUpdate(), precision);
    }

    @Test
    void testTryNextLater() {
        assertEquals(1.0, FrameRate.TIME_DELTA_MAX);

        FrameRate frameRate = new FrameRate(10);
        assertEquals(0.1, frameRate.getTimeDuration());
        assertEquals(0.0, frameRate.getTimeDelta());
        assertEquals(0.0, frameRate.getTimeLastUpdate());

        double timeCurrent = 1000.0; // + 1000.0 seconds
        assertTrue(frameRate.tryNext(timeCurrent));
        assertEquals(FrameRate.TIME_DELTA_MAX, frameRate.getTimeDelta());
        assertEquals(1000.0, frameRate.getTimeLastUpdate());

        timeCurrent = 1000.1; // + 0.1 seconds
        assertTrue(frameRate.tryNext(timeCurrent));
        assertEquals(0.1, frameRate.getTimeDelta(), precision);
        assertEquals(1000.1, frameRate.getTimeLastUpdate());

        timeCurrent = 1002.1; // + 2.0 seconds
        assertTrue(frameRate.tryNext(timeCurrent));
        assertEquals(FrameRate.TIME_DELTA_MAX, frameRate.getTimeDelta());
        assertEquals(1002.1, frameRate.getTimeLastUpdate());
    }

}
