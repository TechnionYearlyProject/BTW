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
}
