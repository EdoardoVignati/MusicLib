import lib.BR;
import lib.Exceptions.BRException;
import lib.Exceptions.BinomException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BRTest {

    @Test
    public void BR_test() throws BinomException, BRException {

        assertNotNull(new BR(0, 0));
        assertNotNull(new BR(0));
        assertNotNull(new BR("Do"));

        assertThrows(BinomException.class, () -> {
            new BR(-1, 0);
        });
        assertThrows(BinomException.class, () -> {
            new BR(0, -1);
        });

        assertThrows(BinomException.class, () -> {
            new BR("Do0");
        });
    }


    @Test
    public void BRGetters_test() throws BinomException, BRException {

        assertEquals(116, new BR(116).getBR());
        assertEquals(116, new BR("Si").getBR());
        assertEquals(116, new BR(11, 6).getBR());

        assertEquals(11, new BR(116).getPC());
        assertEquals(6, new BR(116).getNC());
        assertEquals("Si", new BR(116).getNote(false));
        assertEquals("B", new BR(116).getNote(true));

    }

}