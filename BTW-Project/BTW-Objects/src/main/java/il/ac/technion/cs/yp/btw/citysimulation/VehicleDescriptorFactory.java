package il.ac.technion.cs.yp.btw.citysimulation;

import java.util.function.Supplier;

/**
 * @author Guy Rephaeli
 * @date 06-Jun-18.
 */
public class VehicleDescriptorFactory implements Supplier<VehicleDescriptor> {
    private int currID;

    public VehicleDescriptorFactory() {
        this.currID = 0;
    }

    @Override
    public VehicleDescriptor get() {
        return new VehicleDescriptor(this.currID++);
    }

    public VehicleDescriptor get(Integer id) {
        if (id >= this.currID || id < 0) {
            throw new NoSuchVehicleException("No vehicle with descriptor " + id.toString() + " had been created");
        }
        return new VehicleDescriptor(id);
    }
}
