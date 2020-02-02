package lib;

import lib.Exceptions.CNCException;
import lib.Exceptions.NCException;
import lib.Exceptions.NoteException;

import java.lang.reflect.Method;

import static lib.Note.unbox;

/**
 * Continuous Name Code
 *
 * @author @EdoardoVignati
 */

public class CNC {

    /**
     * Get CNC from NC
     *
     * @param oct
     * @param nc
     * @return
     * @throws CNCException
     */
    public static int getCNCFromNC(int oct, int nc) throws CNCException {
        if (!NC.checkNC(nc) || !checkOctave(oct))
            throw new CNCException("NC not valid => " + nc);
        return (oct * 7) + nc;
    }

    /**
     * Get octave from CNC
     *
     * @param cnc
     * @return
     */
    public static int getOctaveFromCNC(int cnc) throws CNCException {
        int oct = cnc / 7;
        if (!checkOctave(oct) || !NC.checkNC(CNC.getNCFromCNC(cnc)))
            throw new CNCException("CNC not valid => " + cnc);
        return oct;
    }

    /**
     * Get NC from CNC
     *
     * @param cnc
     * @return
     */
    public static int getNCFromCNC(int cnc) throws CNCException {
        int nc = cnc % 7;
        if (!NC.checkNC(nc))
            throw new CNCException("NC not valid => " + cnc);
        return nc;
    }


    /**
     * Check CNC from CNC
     *
     * @param cnc
     * @return
     */
    public static boolean checkCNC(int cnc) {
        try {
            if (checkOctave(CNC.getOctaveFromCNC(cnc)) && NC.checkNC(CNC.getNCFromCNC(cnc)))
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Check octave
     *
     * @param oct
     * @return
     */
    public static boolean checkOctave(int oct) {
        return (oct >= 0);
    }


    /**
     * Get note from CNC
     *
     * @param cnc
     * @param anglo
     * @return
     * @throws CNCException
     */
    public static String getNoteFromCNC(int cnc, boolean anglo) throws CNCException {
        if (!checkCNC(cnc))
            throw new CNCException("CNC not valid => " + cnc);
        if (!checkOctave(CNC.getOctaveFromCNC(cnc)))
            throw new CNCException("Ocatave not valid =>" + cnc);
        String out;
        try {
            out = NC.getNoteFromNC(CNC.getNCFromCNC(cnc), anglo) + CNC.getOctaveFromCNC(cnc);
        } catch (Exception e) {
            throw new CNCException(e.getMessage());
        }
        return out;
    }

    /**
     * Get CNC from note
     *
     * @return
     */
    public static Integer getCNCFromNote(String note) throws CNCException {
        Integer out;
        try {
            String[] unboxed = unbox(note);
            int nc = NC.getNCFromNote(unboxed[0]);
            if (!unboxed[2].equals(""))
                out = Integer.parseInt(unboxed[2]) * 7 + nc;
            else
                throw new CNCException("Octave missing => " + note);
        } catch (Exception e) {
            throw new CNCException(e.getMessage());
        }
        return out;
    }


    /**
     * Dispatch conversion between representations
     *
     * @param representation
     * @param c
     * @return
     */
    public static Object convert(Object representation, Class c) throws NoteException, CNCException {
        Object out = null;
        if (c.equals(CNC.class)) {
            String[] unboxed = unbox((String) representation);
            out = getCNCFromNote(unboxed[0] + unboxed[2]);
        } else {
            try {
                Method method = c.getMethod("convert", Object.class, Class.class);
                out = method.invoke(null, getNoteFromCNC((int) representation, false), c);
            } catch (Exception e) {
                System.err.println(e.getCause());
            }
        }
        return out;
    }
}
