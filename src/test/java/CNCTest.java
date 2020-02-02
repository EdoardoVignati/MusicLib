import lib.Exceptions.CNCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static lib.CNC.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CNCTest {

    @Test
    public void getCNCFromNC_test() throws CNCException {

        Assertions.assertEquals(0, getCNCFromNC(0, 0));
        Assertions.assertEquals(28, getCNCFromNC(4, 0));

        assertThrows(CNCException.class, () -> {
            getCNCFromNC(-1, 0);
        });
        assertThrows(CNCException.class, () -> {
            getCNCFromNC(0, 7);
        });
    }

    @Test
    public void getOctaveFromCNC_test() throws CNCException {
        Assertions.assertEquals(4, getOctaveFromCNC(28));

        assertThrows(CNCException.class, () -> {
            getOctaveFromCNC(-1);
        });
    }


    @Test
    public void getNCFromCNC_test() throws CNCException {

        Assertions.assertEquals(0, getNCFromCNC(28));

        assertThrows(CNCException.class, () -> {
            getNCFromCNC(-1);
        });
    }


    @Test
    public void checkCNC_test() {
        Assertions.assertEquals(true, checkCNC(28));
        Assertions.assertEquals(false, checkCNC(-1));
    }

    @Test
    public void checkOctave_test() {
        Assertions.assertEquals(true, checkOctave(28));
        Assertions.assertEquals(false, checkOctave(-1));
    }


    @Test
    public void getNoteFromCNC_test() throws CNCException {

        Assertions.assertEquals("Do4", getNoteFromCNC(28, false));
        Assertions.assertEquals("C4", getNoteFromCNC(28, true));

        assertThrows(CNCException.class, () -> {
            getNoteFromCNC(-1, false);
        });
    }

    @Test
    public void getCNCFromNote_test() throws CNCException {
        Assertions.assertEquals(0, getCNCFromNote("Do0"));
        Assertions.assertEquals(7, getCNCFromNote("C1"));

        assertThrows(CNCException.class, () -> {
            getCNCFromNote("foo");
        });
        assertThrows(CNCException.class, () -> {
            getCNCFromNote("Do");
        });
        assertThrows(CNCException.class, () -> {
            getCNCFromNote("Do#");
        });
    }

}