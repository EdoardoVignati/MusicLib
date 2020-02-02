import lib.Exceptions.RDCException;
import lib.RDC;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static lib.RDC.*;
import static org.junit.jupiter.api.Assertions.*;

public class RDCTest {

    private static RDC r;

    @BeforeAll
    public static void buildBinom() throws RDCException {
        r = new RDC(3, 4);
    }

    @Test
    public void RDC_test() throws RDCException {

        assertNotNull(new RDC(1, 2));

        assertThrows(RDCException.class, () -> {
            new RDC(0, 1);
        });
        assertThrows(RDCException.class, () -> {
            new RDC(1, 0);
        });
        assertThrows(RDCException.class, () -> {
            new RDC(3, 0);
        });
        assertThrows(RDCException.class, () -> {
            new RDC(1, 3);
        });
    }

    @Test
    public void getSecondsPerBeat_test() throws RDCException {

        assertEquals(1, getSecondsPerBeat(60));
        assertEquals(2, getSecondsPerBeat(30));
        assertEquals(0.5, getSecondsPerBeat(120));

        assertThrows(RDCException.class, () -> {
            getSecondsPerBeat(0);
        });
    }

    @Test
    public void checkBPM_test() {
        assertEquals(true, checkBPM(120));
        assertEquals(false, checkBPM(0));
    }

    @Test
    public void checkNum_test() {
        assertEquals(true, checkNum(3));
        assertEquals(false, checkDen(0));
    }


    @Test
    public void checkDen_test() {
        assertEquals(true, checkDen(64));
        assertEquals(false, checkDen(63));
        assertEquals(false, checkDen(0));
    }

    @Test
    public void gcm_test() {
        assertEquals(12, gcm(12, 24));
    }


    @Test
    public void simplify_test() {
        assertEquals("[1, 2]", "[" + simplify(12, 24)[0]
                + ", " + simplify(12, 24)[1] + "]");
        assertEquals("[13, 24]", "[" + simplify(13, 24)[0]
                + ", " + simplify(13, 24)[1] + "]");
    }

    @Test
    public void getters_test() {
        assertEquals(3, r.getNum());
        assertEquals(4, r.getDen());
        assertEquals(0.75, r.getBeatDurationInSeconds());
    }


}