package engine.utilities;

import engine.basics.BasePosition;
import org.joml.Vector3f;

import java.util.Random;

public class UtilsMath {

    public static final Random random = new Random();
    public static final Vector3f axisRotation = new Vector3f(0, 0, 1);
    public static final double PI_0_5 = Math.PI / 2.0;
    public static final double PI_1_5 = Math.PI + PI_0_5;
    public static final double PI_2_0 = Math.PI * 2.0;

    public static float correctRadians(double value) {
        return (float) (value % PI_2_0);
    }

    public static int randomizeBetween(int min, int max) {
        if (min > max) {
            String message = "Received min value is grater than max. Values has been swapped.";
            UtilsLog.log("randomizeBetween", message);
            int min_copy = min;
            min = max;
            max = min_copy;
        } else if (min == max) {
            String message = String.format(
                    "Min and max values are equal %1$s. Returned %s %1$s once.", min
            );
            UtilsLog.log("randomizeBetween", message);
            return min;
        }

        int difference = max - min;
        return random.nextInt(difference + 1) + min;
    }

    public static float randomizeBetween(float min, float max) {
        if (min > max) {
            String message = "Received min value is grater than max. Values has been swapped.";
            UtilsLog.log("randomizeBetween", message);
            float min_copy = min;
            min = max;
            max = min_copy;
        } else if (min == max) {
            String message = String.format(
                    "Min and max values are equal %1$s. Returned %s %1$s once.", min
            );
            UtilsLog.log("randomizeBetween", message);
            return min;
        }

        float difference = max - min;
        return random.nextFloat() * difference + min;
    }

    public static float randomizeFlexibly(float middle, float offset) {
        return randomizeFlexibly(middle, offset, 0.5f);
    }

    public static float randomizeFlexibly(float middle, float offset, float flex) {
        if (flex <= 0 || flex > 1) {
            String message = String.format("Got flex value as %s. replaced with 0.5.", flex);
            UtilsLog.log("randomizeFlexibly", message);
            flex = 0.5f;
        }

        // Randomize offset to flexed result
        float offsetMin = offset * randomizeBetween(0, flex);
        float offsetMax = offset * randomizeBetween(flex, 1);
        float offsetRandom = randomizeBetween(offsetMin, offsetMax);

        // Set minimal and maximal result value
        float resultMin = middle - offsetRandom;
        float resultMax = middle + offsetRandom;

        // Randomize and return result
        return randomizeBetween(resultMin, resultMax);
    }

    public static float calculateDistanceBetween(BasePosition a, BasePosition b) {
        return calculateDistanceBetween(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public static float calculateDistanceBetween(float x1, float y1, float x2, float y2) {
        double powX = Math.pow(x1 - x2, 2);
        double powY = Math.pow(y1 - y2, 2);
        return (float) Math.sqrt(powX + powY);
    }

    public static float calculateRadiansBetween(BasePosition a, BasePosition b) {
        return calculateRadiansBetween(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public static float calculateRadiansBetween(float x1, float y1, float x2, float y2) {
        float differenceX = x1 - x2;
        float differenceY = y1 - y2;
        return (float) Math.atan2(differenceY, differenceX);
    }

    public static float calculateRoundValue(float value, float round) {
        return Math.round(value * round) / round;
    }

}
