import lib.CBR;
import lib.Exceptions.BinomException;
import lib.Exceptions.CBRException;
import lib.Exceptions.NoteException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CBRTest {

    @Test
    public void CBRpcNc_test() throws BinomException, CBRException {

        assertNotNull(new CBR(0, 0, 0));

        assertThrows(CBRException.class, () -> {
            new CBR(-1, 0, 0);
        });
        assertThrows(BinomException.class, () -> {
            new CBR(0, -1, 0);
        });
        assertThrows(BinomException.class, () -> {
            new CBR(0, 0, -1);
        });
    }

    @Test
    public void CBRString_test() throws BinomException, CBRException, NoteException {

        assertNotNull(new CBR("Do", 0));
        assertNotNull(new CBR("C", 0));
        assertNotNull(new CBR("Do0"));

        assertThrows(BinomException.class, () -> {
            new CBR("foo", 0);
        });
        assertThrows(CBRException.class, () -> {
            new CBR("Do", -1);
        });
    }

    @Test
    public void CBRcbr_test() throws BinomException, CBRException {

        assertNotNull(new CBR(0));

        assertThrows(BinomException.class, () -> {
            new CBR(-1);
        });
    }

    @Test
    public void SBRGetter_test() throws BinomException, CBRException {
        CBR b = new CBR(0);

        assertEquals(0, b.getCBR());
        assertEquals(0, b.getPC());
        assertEquals(0, b.getNC());
        assertEquals("Do0", b.getNote(false));
        assertEquals("C0", b.getNote(true));
    }


}