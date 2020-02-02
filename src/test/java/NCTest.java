import lib.Exceptions.NCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static lib.NC.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NCTest {

    @Test
    public void checkNC_test() {
        Assertions.assertEquals(true, checkNC(0));
        Assertions.assertEquals(false, checkNC(-1));
        Assertions.assertEquals(false, checkNC(7));
    }


    @Test
    public void getNoteFromNC_test() throws NCException {
        Assertions.assertEquals("Do", getNoteFromNC(0, false));
        Assertions.assertEquals("C", getNoteFromNC(0, true));
        assertThrows(NCException.class, () -> {
            getNoteFromNC(7, false);
        });
    }

    @Test
    public void getNCIFromNotes_test() throws NCException {
        Assertions.assertEquals(4, getNCIFromNotes("Do", "Sol"));
        Assertions.assertEquals(4, getNCIFromNotes("Do", "G"));
        assertThrows(NCException.class, () -> {
            getNCIFromNotes("Lab", "Do");
        });
    }


    @Test
    public void getNCFromNote_test() throws NCException {

        Assertions.assertEquals(0, getNCFromNote("Do"));
        Assertions.assertEquals(0, getNCFromNote("C"));

        assertThrows(NCException.class, () -> {
            getNCFromNote("Lab");
        });
    }

    @Test
    public void getNCIFromNC_test() throws NCException {

        Assertions.assertEquals(5, getNCIFromNC(4, 2));

        assertThrows(NCException.class, () -> {
            getNCIFromNC(0, -1);
        });
    }

    @Test
    public void getNCIInverse_test() throws NCException {

        Assertions.assertEquals(3, getNCIInverse(4));

        assertThrows(NCException.class, () -> {
            getNCIInverse(-1);
        });
    }

    @Test
    public void getNICFromNC_test() throws NCException {

        Assertions.assertEquals(3, getNICFromNC(0, 4));
        Assertions.assertEquals(2, getNICFromNC(4, 2));

        assertThrows(NCException.class, () -> {
            getNICFromNC(3, -1);
        });
    }

    @Test
    public void getNCIntervalsName_test() throws NCException {

        Assertions.assertEquals("Unisono | Ottava", getNCIntervalsName(0));

        assertThrows(NCException.class, () -> {
            getNCIntervalsName(-1);
        });
    }

    @Test
    public void checkNCI_test() {
        Assertions.assertEquals(true, checkNCI(0));
        Assertions.assertEquals(false, checkNCI(7));
    }
}