package il.ac.technion.cs.yp.btw.mapsimulation.objects;

import il.ac.technion.cs.yp.btw.classes.Range;

/**
 * Created by Adam on 13/12/2017.
 */
public class MapSimulationRangeImpl implements Range {
    private RangeType rangeType;
    private int upperBound;
    private int lowerBound;
    public MapSimulationRangeImpl(RangeType rangeType, int upperBound, int lowerBound){
        this.rangeType = rangeType;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }
    @Override
    public int getLowerBound() {
        return this.lowerBound;
    }

    @Override
    public int getUpperBound() {
        return this.upperBound;
    }

    @Override
    public RangeType getRangeType() {
        return this.rangeType;
    }
}
