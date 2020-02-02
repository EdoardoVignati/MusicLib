import lib.Exceptions.IntBinomException;
import lib.IntBinom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntBinomTest {

    private static IntBinom b = null;
    private static IntBinom b1 = null;


    @BeforeAll
    public static void buildBinom() throws IntBinomException {

        b = new IntBinom("P1");
        b1 = new IntBinom(0, 0);

    }


    @Test
    public void IntBinom_test() throws IntBinomException {

        assertNotNull(new IntBinom(0, 0));

        assertThrows(IntBinomException.class, () -> {
            new IntBinom(-1, 0);
        });
        assertThrows(IntBinomException.class, () -> {
            new IntBinom(0, -1);
        });
    }

    @Test
    public void IntBinomStr_test() throws IntBinomException {

        assertNotNull(new IntBinom("P1"));

        assertThrows(IntBinomException.class, () -> {
            new IntBinom("foo");
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
    public void getInterval_test() {
        assertEquals("P1", b.getInterval());
        assertEquals("P1", b1.getInterval());
    }


    @Test
    public void findPC_test() throws IntBinomException {

        assertEquals(0, IntBinom.findPC("P1"));

        assertThrows(IntBinomException.class, () -> {
            IntBinom.findPC("foo");
        });
    }

    @Test
    public void findNC_test() throws IntBinomException {

        assertEquals(0, IntBinom.findNC("P1"));

        assertThrows(IntBinomException.class, () -> {
            IntBinom.findNC("foo");
        });
    }

    @Test
    public void findInterval_test() throws IntBinomException {

        assertEquals("P1", IntBinom.findInterval(0, 0));

        assertThrows(IntBinomException.class, () -> {
            IntBinom.findInterval(-1, 0);
        });

        assertThrows(IntBinomException.class, () -> {
            IntBinom.findInterval(0, -1);
        });
    }


    @Test
    public void buildInterval_test() throws IntBinomException {

        assertEquals("Mib", IntBinom.buildInterval("Do", "m3", false));
        assertEquals("Eb", IntBinom.buildInterval("C", "m3", true));


        assertThrows(IntBinomException.class, () -> {
            IntBinom.buildInterval("foo", "m3", true);
        });
        assertThrows(IntBinomException.class, () -> {
            IntBinom.buildInterval("B", "foo", true);
        });
    }

    @Test
    public void checkIntervalString_test() {
        assertEquals(true, IntBinom.checkInterval("m3"));
        assertEquals(false, IntBinom.checkInterval("foo"));
    }

    @Test
    public void checkIntervaPcNc_test() {
        assertEquals(true, IntBinom.checkInterval(0, 0));
        assertEquals(false, IntBinom.checkInterval(-1, 0));
        assertEquals(false, IntBinom.checkInterval(0, -1));

    }

}