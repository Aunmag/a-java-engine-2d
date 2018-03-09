package aunmag.nightingale.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilsValidateTest {

    @Test
    void testIsInRange() {
        assertTrue(UtilsValidate.isInRange(0, 0, 0, "Test"));
        assertTrue(UtilsValidate.isInRange(0, 0, 1, "Test"));
        assertFalse(UtilsValidate.isInRange(0, 1, 1, "Test"));
        assertFalse(UtilsValidate.isInRange(-1.5f, -1, 1, "Test"));
        assertFalse(UtilsValidate.isInRange(+1.5f, -1, 1, "Test"));
    }

}
