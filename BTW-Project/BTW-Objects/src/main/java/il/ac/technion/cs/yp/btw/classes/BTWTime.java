package il.ac.technion.cs.yp.btw.classes;

/**
 * Proposed new time class
 */
public class BTWTime extends BTWTimeUnit{

    private BTWTime(long value) throws BTWIllegalTimeException {
        super(value);
        if (value >= 86400) {
            throw new BTWIllegalTimeException("Time must be less than 24 hours");
        }
    }

    public BTWTime progressBy(BTWWeight progress) throws BTWIllegalTimeException {
        Long secs = this.seconds + progress.seconds();
        if (secs >= 86400) {
            secs = secs % 86400;
        }
        return new BTWTime(secs);
    }

    public static BTWTime of(long value) throws BTWIllegalTimeException {
        return new BTWTime(value);
    }
}
