package lib;

import lib.Exceptions.BinomException;
import lib.Exceptions.NoteException;

import java.lang.reflect.Method;

import static lib.Note.unbox;

/**
 * Binomial representation
 *
 * @author @EdoardoVignati
 */

public class Binom {
    public int pc;
    public int nc;
    public String val;

    protected static String[][] binomNotes = new String[][]{
            {"Do", "Do#", "Dox", "Dox#", "Doxx", "Doxx#", "Doxxx", "Dobbbbb", "Dobbbb", "Dobbb", "Dobb", "Dob"},
            {"Rebb", "Reb", "Re", "Re#", "Rex", "Rex#", "Rexx", "Rexx#", "Rexxx", "Rebbbbb", "Rebbbb", "Rebbb"},
            {"Mibbbb", "Mibbb", "Mibb", "Mib", "Mi", "Mi#", "Mix", "Mix#", "Mixx", "Mixx#", "Mixxx", "Mibbbbb"},
            {"Fabbbbb", "Fabbbb", "Fabbb", "Fabb", "Fab", "Fa", "Fa#", "Fax", "Fax#", "Faxx", "Faxx#", "Faxxx"},
            {"Solxx#", "Solxxx", "Solbbbbb", "Solbbbb", "Solbbb", "Solbb", "Solb", "Sol", "Sol#", "Solx", "Solx#", "Solxx"},
            {"Lax#", "Laxx", "Laxx#", "Laxxx", "Labbbbb", "Labbbb", "Labbb", "Labb", "Lab", "La", "La#", "Lax"},
            {"Si#", "Six", "Six#", "Sixx", "Sixx#", "Sixxx", "Sibbbbb", "Sibbbb", "Sibbb", "Sibb", "Sib", "Si"}};

    /**
     * Build a binom representation from PC and NC
     *
     * @param pc
     * @param nc
     */
    public Binom(int pc, int nc) throws BinomException {
        if (!PC.checkPC(pc) || !NC.checkNC(nc))
            throw new BinomException("PC or NC value wrong => " + "(" + pc + "," + nc + ")");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 12; j++) {
                if (i == nc && j == pc)
                    this.pc = j;
                this.nc = i;
                this.val = binomNotes[nc][pc];
            }
        }
    }


    /**
     * Build binom from the note
     *
     * @param val
     */
    public Binom(String val) throws BinomException {
        if (!checkFullAlteredNote(val))
            throw new BinomException("Value not valid => " + val);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 12; j++) {
                try {
                    if (binomNotes[i][j].equals(val) ||
                            Note.translateNoteToAnglo(binomNotes[i][j]).equals(val)) {
                        this.pc = j;
                        this.nc = i;
                        this.val = binomNotes[i][j];
                        break;
                    }
                } catch (NoteException e) {
                    throw new BinomException(e.getMessage());
                }
            }
        }
    }

    /**
     * Return the PC value
     *
     * @return
     */
    public int getPC() {
        return this.pc;
    }

    /**
     * Return the NC value
     *
     * @return
     */
    public int getNC() {
        return this.nc;
    }

    /**
     * Get the note
     *
     * @return
     */
    public String getNote(boolean anglo) throws BinomException {

        if (!anglo)
            return this.val;
        else {
            try {
                return Note.translateNoteToAnglo(this.val);
            } catch (NoteException e) {
                throw new BinomException(e.getMessage());
            }
        }
    }

    /**
     * Check if note with alteration is valid
     *
     * @param note
     * @return
     */
    public static boolean checkFullAlteredNote(String note) throws BinomException {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 12; j++) {
                try {
                    if (note.equals(binomNotes[i][j]) ||
                            Note.translateNoteToAnglo(binomNotes[i][j]).equals(note))
                        return true;
                } catch (NoteException e) {
                    throw new BinomException(e.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * Return array of notes
     *
     * @return
     */
    public static String[][] getFullAlteredNotes() {
        return binomNotes;
    }

    /**
     * Dispatch conversion between representations
     *
     * @param representation
     * @param c
     * @return
     */
    public static Object convert(Object representation, Class c) throws NoteException, BinomException {
        Object out = null;
        if (c.equals(Binom.class)) {
            String[] unboxed = unbox((String) representation);
            out = new Binom(unboxed[0] + unboxed[1]);
        } else {
            try {
                Binom b = (Binom) representation;
                Method method = c.getMethod("convert", Object.class, Class.class);
                out = method.invoke(null, b.getNote(false), c);
            } catch (Exception e) {
                //System.err.println(e.getCause());
            }
        }
        return out;
    }
}