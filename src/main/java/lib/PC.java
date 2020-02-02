package lib;

import lib.Exceptions.NoteException;
import lib.Exceptions.PCException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static lib.Note.*;

/**
 * Pitch Class
 *
 * @author @EdoardoVignati
 */

public class PC {
    public static int pc;
    public static final ArrayList<String> PCIntervalsName = new ArrayList(
            Arrays.asList(
                    "Unisono giusto | Ottava giusta",
                    "Seconda minore",
                    "Seconda maggiore",
                    "Terza minore",
                    "Terza maggiore",
                    "Quarta giusta",
                    "Quarta eccedente / Quinta diminuita",
                    "Quinta gusta",
                    "Sesta minore",
                    "Sesta maggiore",
                    "Settima minore",
                    "Settima maggiore")
    );


    /**
     * Get the name of a note from its PC
     *
     * @param val
     * @return
     */
    public static String getNoteFromPC(int val, boolean anglo) throws PCException {
        int value = val;
        if (!checkPC(val))
            throw new PCException("PC not valid => " + val);
        if (!anglo)
            return semitones.get(value);
        else return angloSemitones.get(value);
    }

    /**
     * Get corresponding PC int from note name
     *
     * @param note
     * @return
     */
    public static int getPCFromNote(String note) throws PCException {
        if (!(Note.semitones.indexOf(note) == -1))
            return Note.semitones.indexOf(note);
        else if (!(Note.angloSemitones.indexOf(note) == -1))
            return Note.angloSemitones.indexOf(note);
        else
            throw new PCException("Note not valid => " + note);
    }

    /**
     * Calculate PCI from notes names
     *
     * @param noteA
     * @param noteB
     * @return
     */
    public static int getPCIFromNotes(String noteA, String noteB) throws PCException {
        int notea = getPCFromNote(noteA);
        int noteb = getPCFromNote(noteB);
        int pci = (noteb - notea) % 12;
        if (pci < 0)
            return pci + 12;
        else return pci;
    }

    /**
     * Calculate PCI from two PC
     *
     * @param noteA
     * @param noteB
     * @return
     */
    public static int getPCIFromPC(int noteA, int noteB) throws PCException {
        if (!checkPC(noteA) || !checkPC(noteB))
            throw new PCException("PC not valid => (" + noteA + "," + noteB + ")");
        int pci = (noteB - noteA) % 12;
        if (pci < 0)
            return pci + 12;
        else return pci;
    }


    /**
     * Get inverse of PCI
     *
     * @param pc
     * @return
     */
    public static int getPCIInverse(int pc) throws PCException {
        if (!checkPC(pc))
            throw new PCException("PC not valid => " + pc);
        return ((12 - pc) % 12);
    }

    /**
     * Get PCI musical name
     *
     * @param pci
     * @return
     */
    public static String getPCIName(int pci) throws PCException {
        if (pci < 0 || pci > 11)
            throw new PCException("PCI must be [0..11] => " + pci);
        return (PCIntervalsName.get((pci)));
    }

    /**
     * Get CI from PC number
     *
     * @param pc1
     * @param pc2
     * @return
     */
    public static int getICFromPC(int pc1, int pc2) throws PCException {
        int pci1 = getPCIFromPC(pc1, pc2);
        int pci2 = getPCIFromPC(pc2, pc1);
        return (pci1 <= pci2) ? pci1 : pci2;
    }

    /**
     * Get CI from name of notes
     *
     * @param noteA
     * @param noteB
     * @return
     */
    public static int getICFromNotes(String noteA, String noteB) throws PCException {
        int pci1 = getPCIFromNotes(noteA, noteB);
        int pci2 = getPCIFromNotes(noteB, noteA);
        return (pci1 <= pci2) ? pci1 : pci2;
    }


    /**
     * Check PCI range
     *
     * @param pci
     * @return
     */
    public static boolean checkPCI(int pci) {
        return (pci >= 0 && pci <= 11);
    }

    /**
     * Transposes an array of notes
     *
     * @param notes
     * @param pci
     * @return
     */
    public static ArrayList<Integer> transposePCArraytoPC(int[] notes, int pci) throws PCException {
        ArrayList<Integer> transposed = new ArrayList();

        // Accept negative translation and convert it
        int pciChecked = pci;
        if (pci < 0)
            pciChecked = (12 - Math.abs(pci)) % 12;

        // Check translation is in range
        if (!checkPCI(pciChecked))
            throw new PCException("PCI not valid => " + pciChecked);

        // Translate and put into output array
        for (int n : notes) {
            if (!checkPC(n))
                throw new PCException("PC not valid => " + n);

            transposed.add((n + pciChecked) % 12);
        }

        return transposed;
    }


    /**
     * Transposes a note
     *
     * @param pc
     * @param pci
     * @return
     */
    public static int transposePCtoPC(int pc, int pci) throws PCException {

        int pciChecked = pci;
        if (pci < 0)
            pciChecked = (12 - Math.abs(pci)) % 12;

        if (!checkPC(pc))
            throw new PCException("PC not valid => " + pc);

        return (pc + pciChecked) % 12;
    }

    /**
     * Check if the PC value is valid
     *
     * @param pc
     * @return
     */
    public static boolean checkPC(int pc) {
        return (pc >= 0 && pc <= 11);
    }

    /**
     * Dispatch conversion between representations
     *
     * @param representation
     * @param c
     * @return
     */
    public static Object convert(Object representation, Class c) throws PCException, NoteException {
        Object out = null;
        if (c.equals(PC.class)) {
            String[] unboxed = unbox((String) representation);
            out = getPCFromNote(unboxed[0] + unboxed[1]);
        } else {
            try {
                Method method = c.getMethod("convert", Object.class, Class.class);
                out = method.invoke(null, getNoteFromPC((int) representation, false), c);
            } catch (Exception e) {
                System.err.println(e.getCause());
            }
        }
        return out;
    }

}
