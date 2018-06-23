package il.ac.technion.cs.yp.btw.citysimulation;

import java.util.function.Supplier;

/**
 * @author Guy Rephaeli
 * @date 06-Jun-18.
 */
public class VehicleDescriptorFactory implements Supplier<VehicleDescriptor> {
    private static int currID = 0;

    public VehicleDescriptorFactory() {
    }

    @Override
    public VehicleDescriptor get() {
        currID++;
        return new VehicleDescriptor(currID);
    }

    public VehicleDescriptor get(Integer id) {
        if (id > currID || id <= 0) {
            throw new NoSuchVehicleException("No vehicle with descriptor " + id.toString() + " had been created");
        }
        return new VehicleDescriptor(id);
    }
}
