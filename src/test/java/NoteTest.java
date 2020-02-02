import lib.Exceptions.NoteException;
import lib.Note;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {

    private static Note n;
    private static Note nAnglo;

    @BeforeAll
    public static void buildBinom() throws NoteException {
        n = new Note("Do#0");
        nAnglo = new Note("Ebb");
    }


    @Test
    public void Note_test() throws NoteException {

        assertNotNull(new Note("Do", "", 0));
        assertNotNull(new Note("C", "#", 4));
        assertNotNull(new Note("Do#"));
        assertNotNull(new Note("Cbb"));


        assertThrows(NoteException.class, () -> {
            new Note("foo", "", 0);
            new Note("C", "foo", 0);
            new Note("Do", "x", -1);
            new Note("foo");
        });
    }


    @Test
    public void getSemitones_test() {
        assertEquals("Do", Note.getSemitones(false).get(0));
        assertEquals("C", Note.getSemitones(true).get(0));
    }


    @Test
    public void checkSemitone_test() {
        assertEquals(true, Note.checkSemitone("Do#"));
        assertEquals(true, Note.checkSemitone("B"));
        assertEquals(false, Note.checkSemitone("B#"));

    }

    @Test
    public void getNotes_test() {
        assertEquals("Do", Note.getNotes(false).get(0).toString());
        assertEquals("C", Note.getNotes(true).get(0).toString());
    }

    @Test
    public void checkNote_test() {
        assertEquals(true, Note.checkNote("Do"));
        assertEquals(true, Note.checkNote("B"));
        assertEquals(false, Note.checkNote("foo"));

    }

    @Test
    public void retrogradation_test() {
        assertEquals("[3, 2, 1]", Note.retrogradation(new Integer[]{1, 2, 3}).toString());
    }


    @Test
    public void translateNoteToAnglo_test() throws NoteException {

        assertEquals("C", Note.translateNoteToAnglo("Do"));

        assertThrows(NoteException.class, () -> {
            Note.translateNoteToAnglo("A");
        });
    }

    @Test
    public void translateAngloToNote_test() throws NoteException {

        assertEquals("Do", Note.translateAngloToNote("C"));

        assertThrows(NoteException.class, () -> {
            Note.translateAngloToNote("Si");
        });
    }

    @Test
    public void getAlterations_test() throws NoteException {

        assertEquals("[Rebb, Do, Si#]", Note.getAlterations("Do", false).toString());
        assertEquals("[Rebb, Do, Si#]", Note.getAlterations("C", false).toString());
        assertEquals("[Dbb, C, B#]", Note.getAlterations("Do", true).toString());
        assertEquals("[Dbb, C, B#]", Note.getAlterations("C", true).toString());
    }

    @Test
    public void unbox_test() throws NoteException {


        assertEquals("Do#3", Note.unbox("Do#3")[0] +
                Note.unbox("Do#3")[1] +
                Note.unbox("Do#3")[2]);
        assertEquals("Ebb", Note.unbox("Ebb")[0] +
                Note.unbox("Ebb")[1] +
                Note.unbox("Ebb")[2]);


        assertThrows(NoteException.class, () -> {
            Note.unbox("foo");
        });
        assertThrows(NoteException.class, () -> {
            Note.unbox("9Do");
        });

        assertThrows(NoteException.class, () -> {
            Note.unbox("9C");
        });

        assertThrows(NoteException.class, () -> {
            Note.unbox("Do9#");
        });

        assertThrows(NoteException.class, () -> {
            Note.unbox("Do-1");
        });

        assertThrows(NoteException.class, () -> {
            Note.unbox("Do#0#");
        });

    }

    @Test
    public void stringify_test() {
        assertEquals("Do#0", n.stringify(false));
        assertEquals("Ebb", nAnglo.stringify(true));
    }

    @Test
    public void checkOctave() {
        assertEquals(true, Note.checkOctave(1));
        assertEquals(true, Note.checkOctave(0));
        assertEquals(false, Note.checkOctave(-1));
    }

    @Test
    public void isAnglo() throws NoteException {

        assertEquals(true, Note.isAnglo("B"));
        assertEquals(true, Note.isAnglo("B#"));
        assertEquals(true, Note.isAnglo("B#3"));


        assertEquals(false, Note.isAnglo("Sol"));
        assertEquals(false, Note.isAnglo("Sol#"));
        assertEquals(false, Note.isAnglo("Sol#3"));


        assertThrows(NoteException.class, () -> {
            Note.isAnglo("foo");
        });
    }

    @Test
    public void getAlteration_test() {
        assertEquals("#", n.getAlteration());
        assertEquals("bb", nAnglo.getAlteration());
    }


    @Test
    public void getNote_test() {
        assertEquals("Do", n.getNote(false));
        assertEquals("E", nAnglo.getNote(true));
        assertEquals("Mi", nAnglo.getNote(false));
    }


    @Test
    public void getOct_test() {
        assertEquals(0, n.getOct());
        assertEquals(null, nAnglo.getOct());
    }

}
