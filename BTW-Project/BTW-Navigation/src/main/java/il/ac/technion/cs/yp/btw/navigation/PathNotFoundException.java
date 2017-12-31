package il.ac.technion.cs.yp.btw.navigation;

/**
 * Exception thrown when no path is found by the navigation algorithm
 */
public class PathNotFoundException extends Exception {
    PathNotFoundException(String message) {
        super(message);
    }
}
