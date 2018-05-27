package il.ac.technion.cs.yp.btw.classes;

/**
 * Abstract class to provide basic infrastructure for time and weight classes
 */
abstract class BTWTimeUnit {
    protected long seconds;

    protected BTWTimeUnit(long value) throws BTWIllegalTimeException {
        if (value < 0) {
            throw new BTWIllegalTimeException("Time must be positive");
        } else {
            this.seconds = value;
        }
    }

    public Long seconds() {
        return seconds;
    }
}
