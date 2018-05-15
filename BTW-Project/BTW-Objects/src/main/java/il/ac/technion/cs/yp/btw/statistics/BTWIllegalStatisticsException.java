package il.ac.technion.cs.yp.btw.statistics;

/**
 * @author Guy Rephaeli
 * @date 07-May-18.
 *
 * Exception class for illegal statistics operations
 */
public class BTWIllegalStatisticsException extends RuntimeException {
    public BTWIllegalStatisticsException(String message) {
        super(message);
    }
}
