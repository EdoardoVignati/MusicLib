import lib.Exceptions.OPPCException;
import lib.OPPC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OPPCTest {

    @Test
    public void getNoteFromOPPC_test() throws OPPCException {

        Assertions.assertEquals("Do8", OPPC.getNoteFromOPPC(8.00, false));
        Assertions.assertEquals("D9", OPPC.getNoteFromOPPC(9.02, true));
        Assertions.assertEquals("B9", OPPC.getNoteFromOPPC(9.11, true));

        assertThrows(OPPCException.class, () -> {
            OPPC.getNoteFromOPPC(-1, false);
        });
        assertThrows(OPPCException.class, () -> {
            OPPC.getNoteFromOPPC(8.12, false);
        });
    }

    @Test
    public void getOPPCFromNote_test() throws OPPCException {

        Assertions.assertEquals(8.00, OPPC.getOPPCFromNote("Do8"));
        Assertions.assertEquals(9.02, OPPC.getOPPCFromNote("Re9"));
        Assertions.assertEquals(9.11, OPPC.getOPPCFromNote("B9"));

        assertThrows(OPPCException.class, () -> {
            OPPC.getOPPCFromNote("B-1");
        });
        assertThrows(OPPCException.class, () -> {
            OPPC.getOPPCFromNote("Si");
        });
    }


}