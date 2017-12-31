package il.ac.technion.cs.yp.btw.classes;

/**
 * Abstract class to provide basic infrastructure for time and weight classes
 */
abstract class BTWTimeUnit {
    protected final double value;

    protected BTWTimeUnit(double value) throws BTWIllegalTimeException {
        if (value < 0) {
            throw new BTWIllegalTimeException("Time must be positive");
        } else {
            this.value = value;
        }
    }

    public Double getValue() {
        return value;
    }
}
