package il.ac.technion.cs.yp.btw.classes;

/**
 * Proposed new weight class
 */
public class BTWWeight extends BTWTimeUnit{
    private BTWWeight(long value) throws BTWIllegalTimeException {
        super(value);
    }

    public static BTWWeight of(long value) throws BTWIllegalTimeException {
        return new BTWWeight(value);
    }

    public static BTWWeight weightedAverage(BTWWeight prev, BTWWeight curr, Double w) {
        Long val = Double.valueOf(prev.seconds * (1 - w) + curr.seconds * w).longValue();
        return new BTWWeight(val);
    }
}
