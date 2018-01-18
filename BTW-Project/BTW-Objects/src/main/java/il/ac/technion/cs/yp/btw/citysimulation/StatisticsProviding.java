package il.ac.technion.cs.yp.btw.citysimulation;

/**
 * interface for all classes with statistical data
 */
public interface StatisticsProviding<T extends StatisticalData> {
    /**
     * @return StatisticalData of current object
     */
    T getStatisticalData();
}
