package il.ac.technion.cs.yp.btw.classes;

/**
 * Proposed new weight class
 */
public class BTWWeight extends BTWTimeUnit{
    private BTWWeight(double value) throws BTWIllegalTimeException {
        super(value);
    }

    public static BTWWeight of(double value) throws BTWIllegalTimeException {
        return new BTWWeight(value);
    }
}
