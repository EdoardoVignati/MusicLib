package lib;

import lib.Exceptions.MIDIException;
import lib.Exceptions.NoteException;

import java.lang.reflect.Method;

/**
 * MIDI
 *
 * @author @EdoardoVignati
 */

public class MIDI {

    /**
     * Get note from MIDI number
     *
     * @param val
     * @param anglo
     * @return
     * @throws MIDIException
     */
    public static String getNoteFromMIDI(int val, boolean anglo) throws MIDIException {
        if (!checkMIDI(val))
            throw new MIDIException("Note not valid => " + val);
        int note = val % 12;
        int oct = (val / 12);
        String out;
        out = Note.getSemitones(anglo).get(note).toString() + oct;
        return out;
    }

    /**
     * Get MIDI number from note name
     *
     * @param note
     * @return
     * @throws MIDIException
     */
    public static int getMIDIFromNote(String note) throws MIDIException {

        String[] n;
        int oct;
        try {
            n = Note.unbox(note);
            if (!Note.checkSemitone(n[0] + n[1]) || n[2] == "")
                throw new MIDIException("Octave missing => " + note);

            oct = Integer.parseInt(n[2]);

            if (!Note.isAnglo(n[0]))
                return (oct * 12) + Note.semitones.indexOf(n[0]);
            else
                return (oct * 12) + Note.angloSemitones.indexOf(n[0]);

        } catch (Exception e) {
            throw new MIDIException(e.getMessage());
        }
    }

    /**
     * Check MIDI value
     *
     * @param value
     * @return
     * @throws MIDIException
     */
    public static boolean checkMIDI(int value) throws MIDIException {
        if ((value > 127) || (value < 0))
            throw new MIDIException("MIDI value not valid => " + value);
        return true;
    }

    /**
     * Dispatch conversion between representations
     *
     * @param representation
     * @param c
     * @return
     */
    public static Object convert(Object representation, Class c) throws MIDIException, NoteException {
        Object out = null;
        if (c.equals(MIDI.class)) {
            String[] unboxed = Note.unbox((String) representation);
            out = getMIDIFromNote(unboxed[0] + unboxed[1] + unboxed[2]);
        } else {
            try {
                Method method = c.getMethod("convert", Object.class, Class.class);
                out = method.invoke(null, getNoteFromMIDI((int) representation, false), c);
            } catch (Exception e) {
                System.err.println(e.getCause());
            }
        }
        return out;
    }
}