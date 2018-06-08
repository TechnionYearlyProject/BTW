package il.ac.technion.cs.yp.btw.citysimulation;

public class VehicleDescriptor implements Comparable<VehicleDescriptor> {
    private Integer id;
    VehicleDescriptor(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(VehicleDescriptor descriptor) {
        return this.id
                .compareTo(descriptor.id);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof VehicleDescriptor
                && id.equals(((VehicleDescriptor) o).id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    public Integer getID() {
        return this.id;
    }
}
