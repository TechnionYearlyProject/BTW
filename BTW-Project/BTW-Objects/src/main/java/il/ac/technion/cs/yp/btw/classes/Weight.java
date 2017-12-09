package il.ac.technion.cs.yp.btw.classes;

/**
 * Interface which represents weight on roads and
 * traffic lights
 */
public interface Weight {
    /**
     * @return the weight value in long
     */
    long getWeight();

    /**
     * @param length - road length
     * @param averageSpeed - average speed on road
     * @return Weight object which represents these
     *         parameters
     */
    static Weight calculateWeight(int length, int averageSpeed){
        return null;//TODO how Weight is needed to be calculated
    }
}
