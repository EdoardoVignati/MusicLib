package lib;

import lib.Exceptions.IntBinomException;

/**
 * Binomial Intervals
 *
 * @author @EdoardoVignati
 */

public class IntBinom {

    int nc, pc;
    String interval;

    public static String[][] intervals = {
            {"P1", "A1", "2A1", "3A1", "4A1", "5A1", "6A1", "5d1", "4d1", "3d1", "2d1", "d1"},
            {"d2", "m2", "M2", "A2", "2A2", "3A2", "4A2", "5A2", "5d2", "4d2", "3d2", "2d2"},
            {"3d3", "2d3", "d3", "m3", "M3", "A3", "2A3", "3A3", "4A3", "5A3", "5d3", "4d3"},
            {"5d4", "4d4", "3d4", "2d4", "d4", "P4", "A4", "2A4", "3A4", "4A4", "5A4", "6A4"},
            {"5A5", "6A5", "5d5", "4d5", "3d5", "2d5", "d5", "P5", "A5", "2A5", "3A5", "4A5"},
            {"3A6", "4A6", "5A6", "5d6", "4d6", "3d6", "2d6", "d6", "m6", "M6", "A6", "2A6"},
            {"A7", "2A7", "3A7", "4A7", "5A7", "5d7", "4d7", "3d7", "2d7", "d7", "m7", "M7"}};


    /**
     * Build an interval given its PC and NC
     *
     * @param pc
     * @param nc
     */
    public IntBinom(int pc, int nc) throws IntBinomException {
        if (!PC.checkPC(pc) || !NC.checkNC(nc))
            throw new IntBinomException("PC or NC not valid => (" + pc + "," + nc + ")");
        this.interval = intervals[nc][pc];
    }


    /**
     * Build an interval given its PC and NC
     *
     * @param interv
     */
    public IntBinom(String interv) throws IntBinomException {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 12; j++) {
                if (interv.equals(intervals[i][j])) {
                    this.interval = interv;
                    this.pc = j;
                    this.nc = i;
                    return;
                }
            }
        }
        throw new IntBinomException("Interval not valid => " + interv);
    }

    /**
     * Return object PC
     *
     * @return
     */
    public Integer getPC() {
        return this.pc;
    }

    /**
     * Return object NC
     *
     * @return
     */
    public Integer getNC() {
        return this.nc;
    }


    /**
     * Return object interval string
     *
     * @return
     */
    public String getInterval() {
        return this.interval;
    }

    /**
     * Get the corresponding PC given a note
     *
     * @param interval
     * @return
     */
    public static Integer findPC(String interval) throws IntBinomException {
        return new IntBinom(interval).getPC();
    }

    /**
     * Get the corresponding NC given a note
     *
     * @param interval
     * @return
     */
    public static Integer findNC(String interval) throws IntBinomException {
        return new IntBinom(interval).getNC();
    }

    /**
     * Get interval from PC and NC
     *
     * @param pc
     * @param nc
     * @return
     * @throws IntBinomException
     */
    public static String findInterval(int pc, int nc) throws IntBinomException {
        return new IntBinom(pc, nc).getInterval();
    }

    /**
     * Translate a note to note + interval
     *
     * @param note
     * @param interv
     * @return
     */
    public static String buildInterval(String note, String interv, boolean anglo) throws IntBinomException {
        String out;
        String noteToTranslate;


        try {
            if (Note.isAnglo(note))
                noteToTranslate = Note.translateAngloToNote(note);

            if (!checkInterval(interv) || !Binom.checkFullAlteredNote(note))
                throw new IntBinomException("Note or interval not valid => (" + note + "," + interv + ")");
            Binom b;
            int pc = IntBinom.findPC(interv);
            int nc = IntBinom.findNC(interv);
            b = new Binom(note);
            out = new Binom(((b.getPC() + pc) % 12), ((b.getNC() + nc) % 7)).getNote(anglo);
        } catch (Exception e) {
            throw new IntBinomException(e.getMessage());
        }
        return out;

    }

    /**
     * Check if an interval is valid
     *
     * @param interv
     * @return
     */
    public static boolean checkInterval(String interv) {
        try {
            new IntBinom(interv);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Check if interval is valid
     *
     * @param pc
     * @param nc
     * @return
     */
    public static boolean checkInterval(int pc, int nc) {
        try {
            new IntBinom(pc, nc);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
