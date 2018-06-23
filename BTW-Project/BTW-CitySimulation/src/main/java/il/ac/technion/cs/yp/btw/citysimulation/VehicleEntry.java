package il.ac.technion.cs.yp.btw.citysimulation;
import il.ac.technion.cs.yp.btw.classes.BTWIllegalTimeException;
import il.ac.technion.cs.yp.btw.classes.BTWTime;

import java.util.Optional;

/**
 * POJO representing a vehicle we get from Json
 */
public class VehicleEntry {

    private Optional<RoadName> sourceRoadName;
    private Optional<Ratio> sourceRoadRatio;
    private Optional<RoadName> destinationRoadName;
    private Optional<Ratio> destinationRoadRatio;
    private Optional<BTWTime> timeOfDrivingStart;
    private Optional<VehicleDescriptor> descriptor;

    /**
     * All fields will contain Optional.empty, use setters to change them
     */
    VehicleEntry() {
        sourceRoadName = Optional.empty();
        sourceRoadRatio = Optional.empty();
        destinationRoadName = Optional.empty();
        destinationRoadRatio = Optional.empty();
        timeOfDrivingStart = Optional.empty();
    }



    public Optional<RoadName> getSourceRoadName() {
        return sourceRoadName;
    }

    public VehicleEntry setSourceRoadName(String sourceRoadName) {
        if(sourceRoadName == null) return this;
        this.sourceRoadName = Optional.of(new RoadName(sourceRoadName));
        return this;
    }

    public Optional<Ratio> getSourceRoadRatio() {
        return sourceRoadRatio;
    }

    public VehicleEntry setSourceRoadRatio(Double sourceRoadRatio) {
        if(sourceRoadRatio == null) return this;
        this.sourceRoadRatio = Optional.of(new Ratio(sourceRoadRatio));
        return this;
    }

    public Optional<RoadName> getDestinationRoadName() {
        return destinationRoadName;
    }

    public VehicleEntry setDestinationRoadName(String destinationRoadName) {
        if(destinationRoadName == null) return this;
        this.destinationRoadName = Optional.of(new RoadName(destinationRoadName));
        return this;
    }

    public Optional<Ratio> getDestinationRoadRatio() {
        return destinationRoadRatio;
    }

    public VehicleEntry setDestinationRoadRatio(Double destinationRoadRatio) {
        if(destinationRoadRatio == null) return this;
        this.destinationRoadRatio = Optional.of(new Ratio(destinationRoadRatio));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleEntry)) return false;

        VehicleEntry that = (VehicleEntry) o;
        if (!sourceRoadName.equals(that.sourceRoadName)) return false;
        if (!sourceRoadRatio.equals(that.sourceRoadRatio)) return false;
        if (!destinationRoadName.equals(that.destinationRoadName)) return false;
        return destinationRoadRatio.equals(that.destinationRoadRatio);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + sourceRoadName.hashCode();
        result = 31 * result + sourceRoadRatio.hashCode();
        result = 31 * result + destinationRoadName.hashCode();
        result = 31 * result + destinationRoadRatio.hashCode();
        return result;
    }

    public Optional<BTWTime> getTimeOfDrivingStart() {
        return timeOfDrivingStart;
    }

    public VehicleEntry setTimeOfDrivingStart(String timeOfDrivingStart) {
        if(timeOfDrivingStart == null) return this;
        try {
            this.timeOfDrivingStart = Optional.of(BTWTime.of(timeOfDrivingStart));
        } catch (BTWIllegalTimeException e) {
            throw new IllegalTimeException(e.getMessage());
        }
        return this;
    }

    public VehicleEntry setTimeOfDrivingStart(BTWTime timeOfDrivingStart){
        this.timeOfDrivingStart = Optional.of(timeOfDrivingStart);
        return this;
    }

    public Optional<VehicleDescriptor> getDescriptor() {
        return descriptor;
    }

    public VehicleEntry setDescriptor(VehicleDescriptor descriptor) {
        this.descriptor = Optional.of(descriptor);
        return this;
    }

    public String jsonString(){
        return "{"+
                "\"sourceRoad\": \""+this.sourceRoadName.get().getId()+"\",\n" +
                "\"sourceRatio\": "+this.sourceRoadRatio.get().getValue()+",\n" +
                "\"destinationRoad\": \""+this.destinationRoadName.get().getId()+"\",\n" +
                "\"destinationRatio\": "+this.destinationRoadRatio.get().getValue()+",\n" +
                "\"timeOfDrivingStart\": \""+this.timeOfDrivingStart.get().toString()+"\""
                +"}";
    }
}

class NumericalData {
    private static final double DEFAULT_NUMERICAL_UPPER_BOUND = 1000000.0;
    private static final double DEFAULT_NUMERICAL_LOWER_BOUND = 0.0;


    private double value;
    protected NumericalData(){}
    NumericalData(double value) {
        double numericalUpperBound = getNumericalUpperBound();
        double numericalLowerBound = getNumericalLowerBound();
        if (value > numericalUpperBound
                || value < numericalLowerBound) throw new RuntimeException();
        this.setValue(value);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Double.toString(this.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumericalData)) return false;

        NumericalData that = (NumericalData) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    protected double getNumericalUpperBound() {
        return DEFAULT_NUMERICAL_UPPER_BOUND;
    }
    protected double getNumericalLowerBound() {
        return DEFAULT_NUMERICAL_LOWER_BOUND;
    }
}

class Ratio extends NumericalData {

    private static final double RATIO_LOWER_BOUND = 0.0;
    private static final double RATIO_UPPER_BOUND = 1.0;

    Ratio(double ratio) {
        double numericalUpperBound = getNumericalUpperBound();
        double numericalLowerBound = getNumericalLowerBound();
        if (ratio > numericalUpperBound
                || ratio < numericalLowerBound)
            throw new RatioOutOfBoundException("ration in a given vehicle entry is " +
                "above 1.0 or below 0.0");
        this.setValue(ratio);
    }

    @Override
    protected double getNumericalLowerBound() {
        return RATIO_LOWER_BOUND;
    }

    @Override
    protected double getNumericalUpperBound() {
        return RATIO_UPPER_BOUND;
    }
}

class NameInDatabase implements Comparable {
    private String id;

    NameInDatabase(String id) {
        if (id.length() > 1000) throw new RuntimeException();
        if (!isAlphanumerical(id))
            throw new RuntimeException();
        this.setId(id);
    }

    private boolean isAlphanumerical(String str) {
        for (int i = 0; i < str.length(); i++) {
            char currChar = str.charAt(i);
            if (!isAlphanumericalCharacter(currChar))
                return false;
        }
        return true;
    }

    private boolean isAlphanumericalCharacter(char currChar) {
        return (currChar >= '0' && currChar <= '9')
                || (currChar >= 'a' && currChar <= 'z')
                || (currChar >= 'A' && currChar <= 'Z')
                || currChar == ' ';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameInDatabase)) return false;

        NameInDatabase id1 = (NameInDatabase) o;

        return id != null ? id.equals(id1.id) : id1.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof NameInDatabase) {
            NameInDatabase compared = (NameInDatabase) o;
            return getId().compareTo(compared.getId());
        } else {
            return -1;
        }
    }
}

class RoadName extends NameInDatabase {
    RoadName(String id) {
        super(id);

    }
}


