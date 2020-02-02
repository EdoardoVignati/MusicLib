package lib;

import lib.Exceptions.NoteException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Note tools
 *
 * @author @EdoardoVignati
 */

public class Note {
    enum StandardNote {Do, Re, Mi, Fa, Sol, La, Si}

    enum AngloNote {C, D, E, F, G, A, B}

    public static final ArrayList<String> semitones = new ArrayList(
            Arrays.asList("Do", "Do#", "Re", "Re#", "Mi", "Fa", "Fa#", "Sol", "Sol#", "La", "La#", "Si")

    );

    public static final ArrayList<String> angloSemitones = new ArrayList(
            Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")

    );

    private static Map<String, String[]> alterations;

    static {
        alterations = new HashMap();
        alterations.put("Do", new String[]{"Rebb", "Do", "Si#"});
        alterations.put("Do#", new String[]{"Reb", "Do#", "Six"});
        alterations.put("Re", new String[]{"Mibb", "Re", "Dox"});
        alterations.put("Re#", new String[]{"Fabb", "Mib", "Re#"});
        alterations.put("Mi", new String[]{"Fab", "Mi", "Rex"});
        alterations.put("Fa", new String[]{"Solbb", "Fa", "Mi#"});
        alterations.put("Fa#", new String[]{"Solb", "Fa#", "Mix"});
        alterations.put("Sol", new String[]{"Labb", "Sol", "Fax"});
        alterations.put("Sol#", new String[]{"Lab", "Sol#"});
        alterations.put("La", new String[]{"Sibb", "La", "Solx"});
        alterations.put("La#", new String[]{"Sib", "Dobb", "La#"});
        alterations.put("Si", new String[]{"Dob", "Si", "Lax"});
    }

    StandardNote note;
    String alteration;
    Integer oct;

    /**
     * Build a note from parameters
     *
     * @param note
     * @param alteration
     * @param octave
     * @throws NoteException
     */
    public Note(String note, String alteration, int octave) throws NoteException {
        String[] unboxed = unbox(note + alteration + octave);
        if (Note.isAnglo(unboxed[0]))
            this.note = StandardNote.valueOf(Note.translateAngloToNote(unboxed[0]));
        else
            this.note = StandardNote.valueOf(unboxed[0]);
        this.alteration = unboxed[1];
        this.oct = Integer.parseInt(unboxed[2]);
    }

    /**
     * Build a note from its name
     *
     * @param note
     * @throws NoteException
     */
    public Note(String note) throws NoteException {
        String[] unboxed = unbox(note);
        if (!isAnglo(unboxed[0]))
            this.note = StandardNote.valueOf(unboxed[0]);
        else if (isAnglo(unboxed[0]))
            this.note = StandardNote.valueOf(translateAngloToNote(unboxed[0]));
        this.alteration = unboxed[1];
        if (unboxed[2] != null && !unboxed[2].equals(""))
            this.oct = Integer.parseInt(unboxed[2]);
        else this.oct = null;
    }


    /**
     * Return the semitones array
     *
     * @return
     */
    public static ArrayList getSemitones(boolean anglo) {
        if (!anglo)
            return semitones;
        else
            return angloSemitones;
    }

    /**
     * Check if note is a semitone allowed
     *
     * @param note
     * @return
     */
    public static boolean checkSemitone(String note) {
        return (semitones.indexOf(note) != -1 || angloSemitones.indexOf(note) != -1);
    }

    /**
     * Return the notes array
     *
     * @return
     */
    public static ArrayList<String> getNotes(boolean anglo) {

        ArrayList<String> out = new ArrayList();

        if (!anglo)
            for (StandardNote s : StandardNote.values())
                out.add(s.toString());
        else
            for (AngloNote a : AngloNote.values())
                out.add(a.toString());

        return out;
    }

    /**
     * Check if note is correctly spelled
     *
     * @param note
     * @return
     */
    public static boolean checkNote(String note) {
        try {
            return unbox(note) != null;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Retrogradate a melody
     *
     * @param melody
     * @return
     */
    public static ArrayList<Integer> retrogradation(Integer[] melody) {
        ArrayList<Integer> retro = new ArrayList();
        for (int i = melody.length - 1; i >= 0; i--)
            retro.add(melody[i]);
        return retro;
    }

    /**
     * Translate a note to anglo
     *
     * @param note
     * @return
     */
    public static String translateNoteToAnglo(String note) throws NoteException {
        try {
            String noteToTranslate = unbox(note)[0];
            int pos = StandardNote.valueOf(noteToTranslate).ordinal();
            return Arrays.asList(AngloNote.values()).get(pos) + unbox(note)[1] + unbox(note)[2];
        } catch (Exception e) {
            throw new NoteException(e.getMessage());
        }
    }

    /**
     * Translate a note to anglo
     *
     * @param note
     * @return
     */
    public static String translateAngloToNote(String note) throws NoteException {
        try {
            String noteToTranslate = unbox(note)[0];
            int pos = AngloNote.valueOf(noteToTranslate).ordinal();
            return Arrays.asList(StandardNote.values()).get(pos) + unbox(note)[1] + unbox(note)[2];
        } catch (Exception e) {
            throw new NoteException(e.getMessage());
        }
    }


    /**
     * Get all alterations for a note
     *
     * @param note
     * @return
     */
    public static List<String> getAlterations(String note, boolean anglo) throws NoteException {
        if (isAnglo(note))
            note = translateAngloToNote(note);
        ArrayList<String> out = new ArrayList();
        List<String> std = Arrays.asList(alterations.get(unbox(note)[0]));
        if (!anglo)
            return std;
        for (int i = 0; i < std.size(); i++)
            out.add(translateNoteToAnglo(std.get(i)));
        return out;
    }

    /**
     * Unbox components of a note passed as String
     *
     * @param note
     * @return
     * @throws NoteException
     */
    public static String[] unbox(String note) throws NoteException {

        String unboxed[] = new String[]{"", "", ""};
        String patternString = "(Do|Re|Mi|Fa|Sol|La|Si|A|B|C|D|E|F|G)";
        patternString += "(b|bb|bbb|bbbb|bbbbb|#|x|x#|xx|xx#|xxx|)";
        patternString += "(\\d+|)$";


        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(note);
        if (matcher.matches()) {
            unboxed[0] = matcher.group(1);
            unboxed[1] = matcher.group(2);
            unboxed[2] = matcher.group(3);
            if (!unboxed[2].equals("") && !checkOctave(Integer.parseInt(unboxed[2])))
                throw new NoteException("Octave is invalid => " + note);
        } else throw new NoteException("Note malformed => " + note);
        return unboxed;
    }

    /**
     * Stringify the note
     *
     * @return
     */
    public String stringify(boolean anglo) {
        String trimmedNote = String.valueOf(this.note);
        if (this.alteration != null)
            trimmedNote += this.alteration;
        if (this.oct != null)
            trimmedNote += this.oct;
        if (anglo) {
            try {
                return Note.translateNoteToAnglo(trimmedNote);
            } catch (NoteException e) {
                e.printStackTrace();
            }
        }
        return trimmedNote;
    }

    /**
     * Check if octave is valid
     *
     * @param oct
     * @return
     */
    public static boolean checkOctave(int oct) {
        return oct >= 0;
    }

    /**
     * Check if a note is anglo
     *
     * @param note
     * @return
     * @throws NoteException
     */
    public static boolean isAnglo(String note) throws NoteException {
        for (AngloNote n : AngloNote.values()) {
            if (n.toString().equals(unbox(note)[0]))
                return true;
        }
        return false;
    }


    /**
     * Get alteration
     *
     * @return
     */
    public String getAlteration() {
        return this.alteration;
    }

    /**
     * Get note
     *
     * @return
     */
    public String getNote(boolean anglo) {
        try {
            if (anglo)
                return Note.translateNoteToAnglo(this.note.toString());
        } catch (NoteException e) {
            e.printStackTrace();
        }
        return this.note.toString();
    }

    /**
     * Get ocvate
     *
     * @return
     */
    public Integer getOct() {
        return this.oct;
    }
}