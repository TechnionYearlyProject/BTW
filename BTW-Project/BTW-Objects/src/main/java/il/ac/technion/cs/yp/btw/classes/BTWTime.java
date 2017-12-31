package il.ac.technion.cs.yp.btw.classes;

/**
 * Proposed new time class
 */
public class BTWTime extends BTWTimeUnit{

    private BTWTime(double value) throws BTWIllegalTimeException {
        super(value);
        if (value >= 24.0) {
            throw new BTWIllegalTimeException("Time must be less than 24");
        }
    }

    public BTWTime progressBy(double progress) throws BTWIllegalTimeException {
        if (progress < 0.0) {
            throw new BTWIllegalTimeException("Progress must be positive");
        }
        Double value = this.value + progress;
        if (value >= 24.0) {
            int intValue = value.intValue();
            double gap = value - intValue;
            value = (intValue % 24) + gap;
        }
        return new BTWTime(value);
    }

    public static BTWTime of(double value) throws BTWIllegalTimeException {
        return new BTWTime(value);
    }
}
