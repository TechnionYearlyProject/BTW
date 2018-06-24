package il.ac.technion.cs.yp.btw.navigation;

/**
 * @author Guy Rephaeli
 *
 * Exception thrown when no path is found by the navigation algorithm
 */
public class PathNotFoundException extends RuntimeException {
    PathNotFoundException(String message) {
        super(message);
    }
}
