import lib.Binom;
import lib.Exceptions.BinomException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BinomTest {

    private static Binom b = null;
    private static Binom bAlt = null;


    @BeforeAll
    public static void buildBinom() throws BinomException {

        b = new Binom("Do");
        bAlt = new Binom("C#");

    }


    @Test
    public void Binom_test() throws BinomException {


        Binom b = new Binom(0, 0);

        assertThrows(BinomException.class, () -> {
            new Binom(-1, 0);
        });
        assertThrows(BinomException.class, () -> {
            new Binom(0, -1);
        });
    }

    @Test
    public void BinomString_test() throws BinomException {

        Binom bStandard = new Binom("Do");
        assertNotNull(bStandard);
        Binom bAnglo = new Binom("C");
        assertNotNull(bAnglo);

        assertThrows(BinomException.class, () -> {
            new Binom("foo");
        });
    }

    @Test
    public void getPC_test() {
        assertEquals(0, b.getPC());
    }

    @Test
    public void getNC_test() {
        assertEquals(0, b.getNC());
    }

    @Test
    public void getNote_test() throws BinomException {
        assertEquals("Do", b.getNote(false));
        assertEquals("C", b.getNote(true));
        assertEquals("Do#", bAlt.getNote(false));
    }


    @Test
    public void checkFullAlteredNote_test() throws BinomException {

        assertEquals(true, Binom.checkFullAlteredNote("Do"));
        assertEquals(true, Binom.checkFullAlteredNote("C#"));
        assertEquals(false, Binom.checkFullAlteredNote("foo"));
    }


    @Test
    public void getFullAlteredNotes_test() {
        assertNotNull(Binom.getFullAlteredNotes());
    }

}