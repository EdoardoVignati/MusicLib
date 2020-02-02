import lib.*;
import lib.Exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterTest {

    @Test
    public void OPPC_MIDI() throws NoteException, OPPCException, MIDIException {

        assertEquals(96, (int) OPPC.convert(8.00, MIDI.class));
        assertEquals(110, (int) OPPC.convert(9.02, MIDI.class));
        assertEquals(0.0, MIDI.convert(0, OPPC.class));
        assertEquals(4.04, MIDI.convert(52, OPPC.class));
    }

    @Test
    public void MIDI_PC() throws NoteException, MIDIException {

        assertEquals(0, (int) MIDI.convert(0, PC.class));
        assertEquals(2, MIDI.convert(50, PC.class));
    }

    @Test
    public void PC_NC() throws PCException, NoteException {
        assertEquals(0, (int) PC.convert(0, NC.class));
        assertEquals(5, (int) PC.convert(10, NC.class));

    }

    @Test
    public void NC_CPC() throws NoteException, NCException, PCException, CPCException {

        assertEquals(null, NC.convert(0, CPC.class));
        assertEquals(5, CPC.convert(9, NC.class));
        assertEquals(5, CPC.convert(10, NC.class));
        assertEquals(0, CPC.convert(12, NC.class));
    }

    @Test
    public void NC_CNC() throws NoteException, NCException, CNCException {

        // Cannot inference octave
        assertEquals(null, NC.convert(0, CNC.class));
        assertEquals(0, CNC.convert(0, NC.class));

    }

    @Test
    public void CNC_Binom() throws NoteException, CNCException, BinomException {

        assertEquals(null, Binom.convert(new Binom(0, 0), CNC.class));
        Binom b = (Binom) CNC.convert(0, Binom.class);
        assertEquals(0, b.getPC());
        assertEquals(0, b.getNC());

        b = (Binom) CNC.convert(70, Binom.class);
        assertEquals(0, b.getPC());
        assertEquals(0, b.getNC());

    }


    @Test
    public void CNC_BR() throws NoteException, CNCException, BinomException {

        assertEquals(null, BR.convert(new BR(0, 0), CNC.class));
        assertEquals(null, BR.convert(new BR(5, 0), CNC.class));


        BR b = (BR) CNC.convert(0, BR.class);
        assertEquals(0, b.getPC());
        assertEquals(0, b.getNC());

        b = (BR) CNC.convert(70, BR.class);
        assertEquals(0, b.getPC());
        assertEquals(0, b.getNC());

    }

    @Test
    public void BR_CBR() throws BinomException, NoteException, CBRException {

        assertEquals(null, BR.convert(new BR(0, 0), CBR.class));

        BR b = (BR) CBR.convert(new CBR("Re#5"), BR.class);
        assertEquals(31, b.getBR());
    }

}