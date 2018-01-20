package il.ac.technion.cs.yp.btw.classes;

/**
 * @author Adam Elgressy
 * @Date 13-2-2017
 * interface to describe Ranges of Roads and Streets
 */
public interface Range {
    /**
     * @author Adam Elgressy
     * @Date 13-2-2017
     * enum to describe different
     * type of Ranges
     */
    enum RangeType {
        EVEN,ODD,CONTINUOUS;
        boolean filter(int num){
            switch (this){
                case ODD:
                    return num%2==1;
                case EVEN:
                    return num%2==0;
            }
            return true;
        }
    }
    /**
     * @author Adam Elgressy
     * @Date 13-2-2017
     * @return this Range's lower bound
     */
    int getLowerBound();
    /**
     * @author Adam Elgressy
     * @Date 13-2-2017
     * @return this Range's upper bound
     */
    int getUpperBound();
    /**
     * @author Adam Elgressy
     * @Date 13-2-2017
     * @return this Range's type:
     *         ODD - odd numbers only
     *         EVEN - even numbers only
     *         CONTINUES - all numbers
     */
    RangeType getRangeType();
    /**
     * @author Adam Elgressy
     * @Date 13-2-2017
     * @param num - the number to check if in this Range
     * @return true if num is in Range, false otherwise
     */
    default boolean isInRange(int num){
        return getRangeType().filter(num)
                && num<=this.getUpperBound()
                && num>=this.getLowerBound();
    }
}

