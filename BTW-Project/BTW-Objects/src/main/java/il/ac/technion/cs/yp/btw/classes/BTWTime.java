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

    public static BTWTime of(long hours, long minutes, long seconds) throws BTWIllegalTimeException {
        if (hours < 0 || hours > 23) {
            throw new BTWIllegalTimeException("Number of hours must be between 0 and 23");
        }

        if (minutes < 0 || minutes > 59) {
            throw new BTWIllegalTimeException("Number of minutes must be between 0 and 59");
        }

        if (seconds < 0 || seconds > 59) {
            throw new BTWIllegalTimeException("Number of seconds must be between 0 and 59");
        }
        return new BTWTime(hours);
    }

    public Long getHoutsOnly() {
        return this.seconds / 3600;
    }

    public Long getMinutesOnly() {
        return (this.seconds % 60) / 60;
    }

    public Long getSecondsOnly() {
        return this.seconds % 3600;
    }

    public BTWTime startTimeWindow(long timeWindow) {
        return new BTWTime(this.seconds - (this.seconds % timeWindow));
    }

    @Override
    public int hashCode(){
        return this.seconds().hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (! (o instanceof BTWTime)) {
            return false;
        }
        BTWTime t = (BTWTime)o;
        return this.seconds().equals(t.seconds());
    }
}
