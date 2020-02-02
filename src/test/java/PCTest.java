import lib.Exceptions.PCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static lib.PC.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PCTest {

    @Test
    public void getNoteFromPC_test() throws PCException {

        Assertions.assertEquals("Do", getNoteFromPC(0, false));
        Assertions.assertEquals("B", getNoteFromPC(11, true));


        assertThrows(PCException.class, () -> {
            getNoteFromPC(13, false);
        });
    }

    @Test
    public void getPCFromNote_test() throws PCException {

        Assertions.assertEquals(0, getPCFromNote("Do"));
        Assertions.assertEquals(0, getPCFromNote("C"));

        assertThrows(PCException.class, () -> {
            getPCFromNote("Lab");
        });
    }

    @Test
    public void getPCIFromNotes_test() throws PCException {

        Assertions.assertEquals(0, getPCIFromNotes("Do", "Do"));
        Assertions.assertEquals(4, getPCIFromNotes("E", "G#"));

        assertThrows(PCException.class, () -> {
            getPCIFromNotes("Mi#", "Mi#");
        });
    }

    @Test
    public void getPCIFromPC_test() throws PCException {

        Assertions.assertEquals(0, getPCIFromPC(1, 1));
        Assertions.assertEquals(4, getPCIFromPC(4, 8));


        assertThrows(PCException.class, () -> {
            getPCIFromPC(1, 13);
        });
    }

    @Test
    public void getPCIInverse_test() throws PCException {

        Assertions.assertEquals(8, getPCIInverse(4));
        Assertions.assertEquals(6, getPCIInverse(6));

        assertThrows(PCException.class, () -> {
            getPCIInverse(13);
        });
    }

    @Test
    public void getPCIName_test() throws PCException {

        Assertions.assertEquals("Unisono giusto | Ottava giusta", getPCIName(0));
        Assertions.assertEquals("Quarta giusta", getPCIName(5));

        assertThrows(PCException.class, () -> {
            getPCIName(-1);
        });
    }

    @Test
    public void getICFromPC_test() throws PCException {

        Assertions.assertEquals(5, getICFromPC(0, 7));
        Assertions.assertEquals(4, getICFromPC(7, 3));

        assertThrows(PCException.class, () -> {
            getICFromPC(12, 13);
        });
    }


    @Test
    public void getICFromNotes_test() throws PCException {

        Assertions.assertEquals(5, getICFromNotes("Do", "Sol"));
        Assertions.assertEquals(5, getICFromNotes("C", "G"));

        assertThrows(PCException.class, () -> {
            getICFromNotes("Lab", "Do");
        });
    }

    @Test
    public void checkPCI_test() {
        Assertions.assertEquals(true, checkPCI(4));
        Assertions.assertEquals(false, checkPCI(-1));
    }

    @Test
    public void transposePCArraytoPC_test() throws PCException {

        String res = transposePCArraytoPC(new int[]{0, 7, 3, 2, 5, 10, 8, 11}, 4).toString();
        String res1 = transposePCArraytoPC(new int[]{0, 7, 3, 2, 5, 10, 8, 11}, -1).toString();

        Assertions.assertEquals("[4, 11, 7, 6, 9, 2, 0, 3]", res);
        Assertions.assertEquals("[11, 6, 2, 1, 4, 9, 7, 10]", res1);

        assertThrows(PCException.class, () -> {
            transposePCArraytoPC(new int[]{12, 7, 3, 2, 5, 10, 8, 11}, 4);
        });

    }

    @Test
    public void transposePCtoPC_test() throws PCException {

        Assertions.assertEquals(10, transposePCtoPC(11, 11));
        Assertions.assertEquals(10, transposePCtoPC(11, -1));

        assertThrows(PCException.class, () -> {
            transposePCtoPC(12, 4);
        });

    }

    @Test
    public void checkPC_test() {
        Assertions.assertEquals(true, checkPC(4));
        Assertions.assertEquals(false, checkPC(-1));
    }
}