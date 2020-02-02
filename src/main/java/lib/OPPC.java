package lib;

import lib.Exceptions.NoteException;
import lib.Exceptions.OPPCException;

import static lib.Note.*;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * Octave Point Pitch Class
 * 
 * @author @EdoardoVignati
 */

public class OPPC {

    /**
     * Get note from OPPC value
     *
     * @param oppc
     * @param anglo
     * @return
     * @throws OPPCException
     */
    public static String getNoteFromOPPC(double oppc, boolean anglo) throws OPPCException {
        int octave = (int) oppc;
        int note;
        String noteString;
        if (!checkOctave(octave))
            throw new OPPCException("Octave not valid => " + oppc);
        try {
            NumberFormat formatter = new DecimalFormat("#0.00");
            note = Integer.parseInt(formatter.format(oppc - octave).substring(2));
            noteString = String.valueOf(Note.getSemitones(anglo).get(note));
        } catch (Exception e) {
            throw new OPPCException("OPPC not valid => " + oppc);
        }
        return noteString + octave;
    }

    /**
     * get OPPC value from note
     *
     * @param note
     * @return
     * @throws OPPCException
     */
    public static Double getOPPCFromNote(String note) throws OPPCException {
        try {
            String[] unboxed = unbox(note);
            if (unboxed[2].equals(""))
                throw new OPPCException("Octave missing => " + note);
            int n = Note.getSemitones(isAnglo(unboxed[0])).indexOf(unboxed[0]);
            NumberFormat formatter = new DecimalFormat("00");
            return Double.parseDouble(unboxed[2] + "." + formatter.format(n));
        } catch (Exception e) {
            throw new OPPCException(e.getMessage());
        }
    }

    /**
     * Dispatch conversion between representations
     *
     * @param representation
     * @param c
     * @return
     */
    public static Object convert(Object representation, Class c) throws NoteException, OPPCException {
        Object out = null;
        if (c.equals(OPPC.class)) {
            String[] unboxed = unbox((String) representation);
            out = getOPPCFromNote(unboxed[0] + unboxed[2]);
        } else {
            try {
                Method method = c.getMethod("convert", Object.class, Class.class);
                double d = Double.parseDouble(String.valueOf(representation));
                out = method.invoke(null, getNoteFromOPPC(d, false), c);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getCause());
            }
        }
        return out;
    }


}
