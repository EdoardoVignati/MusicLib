package lib;

import lib.Exceptions.BinomException;
import lib.Exceptions.CBRException;
import lib.Exceptions.NoteException;

import java.lang.reflect.Method;

import static lib.Note.unbox;

/**
 * Continuous Binomial Representation
 *
 * @author @EdoardoVignati
 */

public class CBR extends BR {
    private int oct;
    private int cbr;

    /**
     * Build a CBR representation from octave, PC and NC
     *
     * @param oct
     * @param pc
     * @param nc
     */
    public CBR(int oct, int pc, int nc) throws BinomException, CBRException {
        super(pc, nc);
        if (!Note.checkOctave(oct))
            throw new CBRException("Octave not valid => " + oct);
        this.oct = oct;
        int br = new BR(pc, nc).getBR();
        this.cbr = (oct * 1000) + br;
    }

    /**
     * Build a CBR from note and octave
     *
     * @param note
     */
    public CBR(String note, int octave) throws BinomException, CBRException {
        super(new Binom(note).getPC(), new Binom(note).getNC());
        if (!Note.checkOctave(oct))
            throw new CBRException("Octave not valid => " + oct);
        this.oct = octave;
        this.cbr = new CBR(octave, new Binom(note).getPC(), new Binom(note).getNC()).getCBR();
    }

    /**
     * Build a CBR from note
     *
     * @param note
     */
    public CBR(String note) throws BinomException, CBRException, NoteException {
        super(new Binom(unbox(note)[0] + unbox(note)[1]).getPC(),
                new Binom(unbox(note)[0] + unbox(note)[1]).getNC());
        int octave = Integer.parseInt(unbox(note)[2]);
        if (!Note.checkOctave(octave))
            throw new CBRException("Octave not valid => " + oct);
        this.oct = octave;
        this.cbr = new CBR(octave, new Binom(unbox(note)[0] + unbox(note)[1]).getPC(),
                new Binom(unbox(note)[0] + unbox(note)[1]).getNC()).getCBR();
    }

    /**
     * Build a CBR from cbr value
     *
     * @param cbr
     */
    public CBR(int cbr) throws BinomException, CBRException {
        super(cbr % 1000 / 10, cbr % 1000 % 10);
        if (!Note.checkOctave(oct))
            throw new CBRException("Octave not valid => " + oct);
        this.oct = cbr / 1000;
        this.cbr = cbr;
    }

    /**
     * Return cbr value
     *
     * @return
     */
    public int getCBR() {
        return this.cbr;
    }

    /**
     * Return note from CBR number
     *
     * @return
     */
    public String getNote(boolean anglo) {
        try {
            return super.getNote(anglo) + this.oct;
        } catch (BinomException e) {
            e.printStackTrace();
        }
        return null;
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
        CBR cbr;
        if (c.equals(CBR.class)) {
            String[] unboxed = unbox((String) representation);
            try {
                if (unboxed[2].equals(""))
                    throw new CBRException("Octave missing => " + representation);
                cbr = new CBR(unboxed[0] + unboxed[1], Integer.parseInt(unboxed[2]));
                out = cbr;

            } catch (CBRException e) {
                throw new BinomException(e.getMessage());
            }
        } else {
            cbr = (CBR) representation;
            try {
                Method method = c.getMethod("convert", Object.class, Class.class);
                out = method.invoke(null, cbr.getNote(false), c);
            } catch (Exception e) {
                System.err.println(e.getCause());
            }
        }
        return out;
    }

}
