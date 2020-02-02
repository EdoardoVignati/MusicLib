import lib.CPC;
import lib.Exceptions.CPCException;
import lib.Exceptions.PCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static lib.CPC.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CPCTest {

    @Test
    public void getCPCFromPC_test() {
        try {
            Assertions.assertEquals(0, getCPCFromPC(0, 0));
        } catch (CPCException e) {
            e.printStackTrace();
        }
        assertThrows(CPCException.class, () -> {
            getCPCFromPC(-1, 0);
        });
    }

    @Test
    public void getOctave_test() {
        try {
            Assertions.assertEquals(0, getOctave(0));
        } catch (CPCException e) {
            e.printStackTrace();
        }
        assertThrows(CPCException.class, () -> {
            getOctave(-1);
        });
    }

    @Test
    public void getPC_test() {
        try {
            Assertions.assertEquals(0, getPC(0));
        } catch (CPCException e) {
            e.printStackTrace();
        }
        assertThrows(CPCException.class, () -> {
            getPC(-1);
        });
    }

    @Test
    public void checkOctave_test() {
        Assertions.assertEquals(true, checkOctave(0));
        Assertions.assertEquals(false, checkOctave(-1));

    }

    @Test
    public void getCPCI_test() {
        try {
            Assertions.assertEquals(-5, getCPCI(60, 55));
            Assertions.assertEquals(+8, getCPCI(55, 63));
        } catch (CPCException e) {
            e.printStackTrace();
        }
        assertThrows(CPCException.class, () -> {
            getCPCI(0, -1);
        });
    }

    @Test
    public void transpose_test() {
        try {
            Assertions.assertEquals("[62]", transpose(new int[]{58}, 4).toString());
            Assertions.assertEquals("[58, 59]", transpose(new int[]{60, 61}, -2).toString());
        } catch (CPCException e) {
            e.printStackTrace();
        }
        assertThrows(CPCException.class, () -> {
            transpose(new int[]{-1}, 0);
        });
    }

    @Test
    public void invertCPC_test() throws CPCException {
        Assertions.assertEquals(58, invertCPC(60, 62));

        assertThrows(CPCException.class, () -> {
            invertCPC(-1, -1);
        });
    }

    @Test
    public void checkCPC_test() {
        Assertions.assertEquals(true, checkCPC(0));
        Assertions.assertEquals(false, checkCPC(-1));
    }

    @Test
    public void getNoteFromCPC_test() throws PCException, CPCException {

        Assertions.assertEquals("Do0", CPC.getNoteFromCPC(0, false));
        Assertions.assertEquals("C0", CPC.getNoteFromCPC(0, true));

        assertThrows(CPCException.class, () -> {
            CPC.getNoteFromCPC(-1, false);
        });
    }

    @Test
    public static void getCPCFromNote_test() throws CPCException {

        Assertions.assertEquals(0, CPC.getCPCFromNote("Do0"));
        Assertions.assertEquals(0, CPC.getCPCFromNote("C0"));

        assertThrows(CPCException.class, () -> {
            CPC.getCPCFromNote("");
            CPC.getCPCFromNote("Do");
        });

    }

}