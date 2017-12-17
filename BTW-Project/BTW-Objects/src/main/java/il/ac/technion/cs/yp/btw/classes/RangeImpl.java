package il.ac.technion.cs.yp.btw.classes;

/**
 * Created by Adam on 13/12/2017.
 */
public class RangeImpl implements Range {
    private RangeType rangeType;
    private int upperBound;
    private int lowerBound;
    public RangeImpl(RangeType rangeType, int upperBound, int lowerBound){
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
