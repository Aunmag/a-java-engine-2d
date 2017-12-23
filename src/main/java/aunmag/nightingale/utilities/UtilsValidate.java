package aunmag.nightingale.utilities;

public class UtilsValidate {

    public static boolean isInRange(float value, float min, float max, String title) {
        boolean isValid = min <= value && value <= max;

        if (!isValid) {
            String message = String.format(
                    "%s must not be less than %s and grater than %s. Got %s instead!",
                    title,
                    min,
                    max,
                    value
            );
            System.out.println(message);
        }

        return isValid;
    }

}
