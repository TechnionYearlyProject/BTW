package il.ac.technion.cs.yp.btw.classes;

/**
 * @author Guy Rephaeli
 * @date 16-Jun-18.
 */
public interface TrafficObject {
    /**
     * @author Guy Rephaeli
     * @date 16-Jun-18.
     * @return the unique name of the object
     */
    String getName();

    /**
     * @author Guy Rephaeli
     * @date 16-Jun-18.
     *
     * returns the right Weight for the given Time
     * @param time - Time we want to check the load
     *             on the object at
     * @return Weight of this object according
     *         to the given Time
     */
    BTWWeight getWeightByTime(BTWTime time);

    /**
     * @author Guy Rephaeli
     * @date 16-Jun-18.
     * @return minimum possible Weight of object
     */
    BTWWeight getMinimumWeight();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
