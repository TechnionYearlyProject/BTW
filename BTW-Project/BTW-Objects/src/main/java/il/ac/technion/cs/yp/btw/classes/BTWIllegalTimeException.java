package il.ac.technion.cs.yp.btw.classes;

/**
 * Exception if time is not is range
 */
public class BTWIllegalTimeException extends Exception {
    public BTWIllegalTimeException(String message) {
        super(message);
    }
}
