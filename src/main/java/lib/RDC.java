package lib;

import lib.Exceptions.RDCException;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Reciprocal Duration Code
 *
 * @author @EdoardoVignati
 */

public class RDC {

    static final int SECONDS = 60;
    private int num, den;

    /**
     * Buil a RDC object by numerator and denominator
     *
     * @param num
     * @param den
     * @throws RDCException
     */
    public RDC(int num, int den) throws RDCException {
        if (!checkNum(num))
            throw new RDCException("Numerator not valid => " + num);
        if (!checkDen(den))
            throw new RDCException("Denominator not valid => " + den);

        this.num = simplify(num, den)[0];
        this.den = simplify(num, den)[1];
    }

    /**
     * Calculate seconds per beat given total BeatsPerMinute
     *
     * @param bpm
     * @return
     * @throws RDCException
     */
    public static float getSecondsPerBeat(int bpm) throws RDCException {
        if (!checkBPM(bpm))
            throw new RDCException("bpm not valid => " + bpm);
        return (float) SECONDS / bpm;
    }

    /**
     * Check if bpm is valid
     *
     * @param bpm
     * @return
     */
    public static boolean checkBPM(int bpm) {
        return bpm > 0;
    }


    /**
     * Check if numerator is valid
     *
     * @param n
     * @return
     */
    public static boolean checkNum(int n) {
        return n > 0;
    }


    /**
     * Check denominator if is power of 2
     *
     * @param n
     * @return
     */
    public static boolean checkDen(int n) {
        double d = (Math.log(n) / Math.log(2));
        double r = (int) d;
        return (d - r) == 0;
    }

    /**
     * Calculate Gratest Common Multiple to simplify
     *
     * @param a
     * @param b
     * @return
     */
    public static long gcm(long a, long b) {
        return b == 0 ? a : gcm(b, a % b);
    }

    /**
     * Simplify the fraction
     *
     * @param a
     * @param b
     * @return
     */
    public static Integer[] simplify(long a, long b) {
        long gcm = gcm(a, b);
        return new Integer[]{Math.toIntExact((a / gcm)), Math.toIntExact((b / gcm))};
    }

    /**
     * Returns the numerator of fraction
     *
     * @return
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Returns the denominator of fraction
     *
     * @return
     */
    public int getDen() {
        return this.den;
    }

    /**
     * Calculate seconds per beats
     *
     * @return
     */
    public float getBeatDurationInSeconds() {
        float dur = (float) this.num / this.den;
        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);
        return Float.parseFloat(df.format(dur));
    }

}
