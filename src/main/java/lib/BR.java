package lib;

import lib.Exceptions.BRException;
import lib.Exceptions.BinomException;
import lib.Exceptions.NoteException;

import java.lang.reflect.Method;

import static lib.Note.unbox;

/**
 * Binomial Representation single integer
 *
 * @author @EdoardoVignati
 */

public class BR extends Binom {

    private int val;

    /**
     * Build a BR note from PC and NC
     *
     * @param pc
     * @param nc
     */
    public BR(int pc, int nc) throws BinomException {
        super(pc, nc);
        val = (pc * 10) + nc;
    }

    /**
     * Build a BR note from Note
     *
     * @param note
     */
    public BR(String note) throws BinomException, BRException {
        super(note);
        try {
            val = PC.getPCFromNote(note) * 10 + NC.getNCFromNote(unbox(note)[0]);
        } catch (Exception e) {
            throw new BRException("Note not valid => " + note);
        }
    }

    /**
     * Buld a BR note from its br value
     *
     * @param br
     */
    public BR(int br) throws BinomException {
        super(br / 10, br % 10);
        val = br;
    }

    /**
     * Get BR value
     *
     * @return
     */
    public int getBR() {
        return val;
    }

    /**
     * Get PC value
     *
     * @return
     */
    @Override
    public int getPC() {
        return val / 10;
    }

    /**
     * Get NC value
     *
     * @return
     */
    @Override
    public int getNC() {
        return val % 10;
    }

    /**
     * Get a note from BR object
     *
     * @param anglo
     * @return
     * @throws BinomException
     */
    @Override
    public String getNote(boolean anglo) throws BinomException {
        return new Binom(this.getPC(), this.getNC()).getNote(anglo);
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
        BR b;
        try {
            if (c.equals(BR.class)) {
                String[] unboxed = unbox((String) representation);
                b = new BR(unboxed[0] + unboxed[1]);
                out = b;
            } else {
                b = (BR) representation;

                Method method = c.getMethod("convert", Object.class, Class.class);
                out = method.invoke(null, b.getNote(false), c);
            }
        } catch (Exception e) {
            System.err.println(e.getCause());
        }

        return out;
    }

}
