package lib;

import lib.Exceptions.NCException;
import lib.Exceptions.NoteException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static lib.Note.unbox;

/**
 * Name Class
 *
 * @author @EdoardoVignati
 */

public class NC {
    private static final ArrayList<String> NCIntervalsName = new ArrayList(
            Arrays.asList(
                    "Unisono | Ottava",
                    "Seconda",
                    "Terza",
                    "Quarta",
                    "Quinta",
                    "Sesta",
                    "Settima"
            )
    );

    /**
     * Check if the NC value is valid
     *
     * @param nc
     * @return
     */
    public static boolean checkNC(int nc) {
        return (nc >= 0 && nc <= 6);
    }


    /**
     * Get the name of a note from its NC
     *
     * @param val
     * @return
     */
    public static String getNoteFromNC(int val, boolean anglo) throws NCException {
        if (!checkNC(val))
            throw new NCException("NC not valid => " + val);
        if (!anglo)
            return String.valueOf(Arrays.asList(Note.StandardNote.values()).get(val));
        else
            return String.valueOf(Arrays.asList(Note.AngloNote.values()).get(val));
    }

    /**
     * Calculate NCI from notes names
     *
     * @param noteA
     * @param noteB
     * @return
     */
    public static int getNCIFromNotes(String noteA, String noteB) throws NCException {
        Integer notea, noteb;
        notea = getNCFromNote(noteA);
        noteb = getNCFromNote(noteB);
        int nci = (noteb - notea) % 7;
        if (nci < 0)
            return nci + 7;
        else return nci;
    }

    /**
     * Get corresponding NC int from note name
     *
     * @param note
     * @return
     */
    public static Integer getNCFromNote(String note) throws NCException {
        try {
            if (!Note.isAnglo(note)) {
                for (Note.StandardNote n : Note.StandardNote.values())
                    if (n.toString().equals(note))
                        return Note.StandardNote.valueOf(note).ordinal();
            } else {
                for (Note.AngloNote n : Note.AngloNote.values())
                    if (n.toString().equals(note))
                        return Note.AngloNote.valueOf(note).ordinal();
            }
        } catch (NoteException e) {
            throw new NCException(e.getMessage());
        }
        throw new NCException("Note not valid => " + note);
    }

    /**
     * Calculate NCI from two NC
     *
     * @param noteA
     * @param noteB
     * @return
     */
    public static int getNCIFromNC(int noteA, int noteB) throws NCException {
        if (!checkNC(noteA) || !checkNC(noteB))
            throw new NCException("Note not valid => " + "(" + noteA + "," + noteB + ")");
        int nci = (noteB - noteA) % 7;
        if (nci < 0)
            return nci + 7;
        else return nci;
    }

    /**
     * Get inverse of NCI
     *
     * @param nc
     * @return
     */
    public static int getNCIInverse(int nc) throws NCException {
        if (!checkNC(nc))
            throw new NCException("NC not valid => " + nc);
        return ((7 - nc) % 7);
    }

    /**
     * Get CI from NC number
     *
     * @param nc1
     * @param nc2
     * @return
     */
    public static int getNICFromNC(int nc1, int nc2) throws NCException {
        if (!checkNC(nc1) || !checkNC(nc2))
            throw new NCException("Note not valid => " + "(" + nc1 + "," + nc2 + ")");
        int nci1 = getNCIFromNC(nc1, nc2);
        int nci2 = getNCIFromNC(nc2, nc1);
        return (nci1 <= nci2) ? nci1 : nci2;
    }

    /**
     * Get NCI name
     *
     * @param nci
     * @return
     */
    public static String getNCIntervalsName(int nci) throws NCException {
        if (!checkNCI(nci))
            throw new NCException("NCI not valid => " + nci);
        else return NCIntervalsName.get(nci);
    }

    /**
     * Checl NCI
     *
     * @param nci
     * @return
     */
    public static boolean checkNCI(int nci) {
        return (nci >= 0 && nci <= 6);
    }

    /**
     * Dispatch conversion between representations
     *
     * @param representation
     * @param c
     * @return
     */
    public static Object convert(Object representation, Class c) throws NCException, NoteException {
        Object out = null;
        if (c.equals(NC.class)) {
            String[] unboxed = unbox((String) representation);
            out = getNCFromNote(unboxed[0]);
        } else {
            try {
                Method method = c.getMethod("convert", Object.class, Class.class);
                out = method.invoke(null, getNoteFromNC((int) representation, false), c);
            } catch (Exception e) {
                System.err.println(e.getCause());
            }
        }
        return out;
    }
}
