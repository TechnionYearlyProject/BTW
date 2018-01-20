package il.ac.technion.cs.yp.btw.mapgeneration.objects;

import il.ac.technion.cs.yp.btw.classes.Range;

/**
 * @author Adam Elgressy
 * @Date 13-2-2017
 * Range interface implementation to define the Range
 * of addresses on a Road/Street
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

    /**
     * @author Adam Elgressy
     * @Date 13-2-2017
     * @return this Range's lower bound
     */
    @Override
    public int getLowerBound() {
        return this.lowerBound;
    }

    /**
     * @author Adam Elgressy
     * @Date 13-2-2017
     * @return this Range's upper bound
     */
    @Override
    public int getUpperBound() {
        return this.upperBound;
    }

    /**
     * @author Adam Elgressy
     * @Date 13-2-2017
     * @return this Range's type:
     *         ODD - odd numbers only
     *         EVEN - even numbers only
     *         CONTINUES - all numbers
     */
    @Override
    public RangeType getRangeType() {
        return this.rangeType;
    }
}
