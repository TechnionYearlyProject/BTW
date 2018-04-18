package il.ac.technion.cs.yp.btw.classes;

/**
 * Exception if time is not is range
 */
public class BTWIllegalTimeException extends RuntimeException {
    public BTWIllegalTimeException(String message) {
        super(message);
    }
}
