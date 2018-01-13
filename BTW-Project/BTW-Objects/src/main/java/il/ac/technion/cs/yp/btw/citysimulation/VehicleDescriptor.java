package il.ac.technion.cs.yp.btw.citysimulation;

public interface VehicleDescriptor extends Comparable<VehicleDescriptor> {
    @Override
    int compareTo(VehicleDescriptor descriptor);

    @Override
    boolean equals(Object o);
}
