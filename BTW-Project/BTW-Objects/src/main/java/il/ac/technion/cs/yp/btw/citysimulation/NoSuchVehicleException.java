package il.ac.technion.cs.yp.btw.citysimulation;

/**
 * @author Guy Rephaeli
 * @date 06-Jun-18.
 */
public class NoSuchVehicleException extends RuntimeException {
    public NoSuchVehicleException(String message) {
        super(message);
    }
}
