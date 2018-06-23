package il.ac.technion.cs.yp.btw.classes;

/**
 * Proposed new time class
 */
public class BTWTime extends BTWTimeUnit{

    private static final int MINUTES_MULTIPLIER_TO_SECONDS = 60;
    private static final int HOURS_MULTIPLIER_TO_SECONDS = 60*60;

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
        this.seconds = secs;
        return this;
    }

    public static BTWTime of(long value) throws BTWIllegalTimeException {
        return new BTWTime(value);
    }

    /**
     * @author Adam Elgressy
     * @Date 23-5-2018
     * @param value - String representing time in format HH:MM:SS
     * @return BTWTime object with value according to input
     * @throws BTWIllegalTimeException when the format of value
     *               doesn't corresponds with the HH:MM:SS format, or
     *               the time is of illegal value, example: 54:72:90
     */
    public static BTWTime of(String value) throws BTWIllegalTimeException {
        if(!value.matches("[0-9][0-9]:[0-9][0-9]:[0-9][0-9]"))
            throw new BTWIllegalTimeException("Illegal Time Format");
        return BTWTime.of(Long.valueOf(value.substring(0,2))
                ,Long.valueOf(value.substring(3,5))
                ,Long.valueOf(value.substring(6,8)));
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
        return new BTWTime(seconds
                + minutes* MINUTES_MULTIPLIER_TO_SECONDS
                + hours* HOURS_MULTIPLIER_TO_SECONDS);
    }

    public Long getHoursOnly() {
        return this.seconds / 3600;
    }

    public Long getMinutesOnly() {
        return (this.seconds % 3600) / 60;
    }

    public Long getSecondsOnly() {
        return this.seconds % 60;
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

    @Override
    public String toString() {
        return String.format("%2s", this.getHoursOnly()).replace(' ', '0')
                + ":" + String.format("%2s", this.getMinutesOnly()).replace(' ', '0')
                + ":" + String.format("%2s", this.getSecondsOnly()).replace(' ', '0');
    }
}
