import lib.Exceptions.MIDIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static lib.MIDI.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MIDITest {

    @Test
    public void getNoteFromMIDI_test() throws MIDIException {

        Assertions.assertEquals("Do0", getNoteFromMIDI(0, false));
        Assertions.assertEquals("G10", getNoteFromMIDI(127, true));

        assertThrows(MIDIException.class, () -> {
            getNoteFromMIDI(-1, false);
        });
    }

    @Test
    public void getMIDIFromNote_test() throws MIDIException {

        Assertions.assertEquals(0, getMIDIFromNote("Do0"));
        Assertions.assertEquals(127, getMIDIFromNote("G10"));

        assertThrows(MIDIException.class, () -> {
            getMIDIFromNote("La#");
        });
    }


    @Test
    public void checkMIDI_test() throws MIDIException {

        Assertions.assertEquals(true, checkMIDI(0));

        assertThrows(MIDIException.class, () -> {
            checkMIDI(128);
        });
    }


}