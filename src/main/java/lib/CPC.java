package lib;

import lib.Exceptions.CPCException;
import lib.Exceptions.NoteException;
import lib.Exceptions.PCException;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static lib.Note.unbox;

/**
 * Continuous Pitch Code
 *
 * @author @EdoardoVignati
 */


public class CPC {

    /**
     * Calculate CPC from octave and PC
     *
     * @param oct
     * @param pc
     * @return
     */
    public static int getCPCFromPC(int oct, int pc) throws CPCException {
        if (!PC.checkPC(pc))
            throw new CPCException("PC is invalid => " + pc);
        if (!checkOctave(oct))
            throw new CPCException("Octave is invalid => " + oct);
        return (oct * 12) + pc;
    }

    /**
     * Calculate octave from CPC
     *
     * @param cpc
     * @return
     */
    public static int getOctave(int cpc) throws CPCException {
        if (!checkCPC(cpc))
            throw new CPCException("CPC is not valid => " + cpc);
        return cpc / 12;
    }

    /**
     * Calculate PC from CPC
     *
     * @param cpc
     * @return
     */
    public static int getPC(int cpc) throws CPCException {
        if (!checkCPC(cpc))
            throw new CPCException("CPC is not valid => " + cpc);
        return cpc % 12;
    }

    /**
     * Check octave is positive
     *
     * @param oct
     * @return
     */
    public static boolean checkOctave(int oct) {
        return (oct >= 0);
    }

    /**
     * Calculate CPCInterval
     *
     * @param cpc1
     * @param cpc2
     * @return
     */
    public static int getCPCI(int cpc1, int cpc2) throws CPCException {
        if (!checkCPC(cpc1) || !checkCPC(cpc2))
            throw new CPCException("CPC is not valid => (" + cpc1 + "," + cpc2 + ")");
        return cpc2 - cpc1;
    }

    /**
     * Transpose a CPC array of CPCI value
     *
     * @param cpc
     * @param cpci
     * @return
     */
    public static ArrayList<Integer> transpose(int[] cpc, int cpci) throws CPCException {
        ArrayList<Integer> transposed = new ArrayList<>();
        for (int i : cpc)
            if (checkCPC(i))
                transposed.add(i + cpci);
            else throw new CPCException("CPC is not valid => " + i);
        return transposed;
    }

    /**
     * Invert CPC value
     *
     * @param refPitch
     * @param cpc
     * @return
     */
    public static int invertCPC(int refPitch, int cpc) throws CPCException {
        if (!checkCPC(cpc))
            throw new CPCException("CPC not valid => " + cpc);
        if (!checkCPC(refPitch))
            throw new CPCException("CPC not valid => " + cpc);
        return ((2 * refPitch) - cpc);
    }

    /**
     * Check if CPC is valid
     *
     * @param cpc
     * @return
     */
    public static boolean checkCPC(int cpc) {
        return (cpc >= 0);
    }

    /**
     * Get note from CPC value
     *
     * @param cpc
     * @param anglo
     * @return
     * @throws CPCException
     * @throws PCException
     */
    public static String getNoteFromCPC(int cpc, boolean anglo) throws CPCException, PCException {
        if (!checkCPC(cpc))
            throw new CPCException("CPC error => " + cpc);
        if (!checkOctave(CPC.getOctave(cpc)))
            throw new CPCException("Pctave error => " + CPC.getOctave(cpc));
        if (!PC.checkPC(CPC.getPC(cpc)))
            throw new CPCException("PC error => " + CPC.getPC(cpc));

        return PC.getNoteFromPC(CPC.getPC(cpc), anglo) + CPC.getOctave(cpc);
    }

    /**
     * Get CPC from note
     *
     * @param note
     * @return
     */
    public static int getCPCFromNote(String note) throws CPCException {
        int pc;
        String[] unboxed;
        try {
            unboxed = Note.unbox(note);
            pc = PC.getPCFromNote(unboxed[0] + unboxed[1]);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CPCException(e.getMessage());
        }
        if (unboxed[2].equals(""))
            throw new CPCException("Octave missing => " + note);
        else
            return (Integer.parseInt(unboxed[2]) * 12) + pc;
    }


    /**
     * Dispatch conversion between representations
     *
     * @param representation
     * @param c
     * @return
     */
    public static Object convert(Object representation, Class c) throws PCException, NoteException, CPCException {
        Object out = null;
        if (c.equals(CPC.class)) {
            String[] unboxed = unbox((String) representation);
            out = getCPCFromNote(unboxed[0] + unboxed[1] + unboxed[2]);
        } else {
            try {
                Method method = c.getMethod("convert", Object.class, Class.class);
                out = method.invoke(null, getNoteFromCPC((int) representation, false), c);
            } catch (Exception e) {
                System.err.println(e.getCause());
            }
        }
        return out;
    }
}
