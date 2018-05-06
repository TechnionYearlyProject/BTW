package il.ac.technion.cs.yp.btw.db;

import org.junit.Test;

/**
 * Created by shay on 04/05/2018.
 */
public class TestDBStatsiticsProvider {

    /**
     * @Author: Shay
     * @Date: 4/5/18
     * testing DBStatisticsProvider module - getting right results from DB
     */

    @Test
    public void testProvider() {
        BTWDataBaseImpl db = new BTWDataBaseImpl("statsTestShay");
        db.loadMap();
        //db.createStatisticsTables(db.getAllRoads(),db.getAllTrafficLights());
        DBStatisticsProvider stats = new DBStatisticsProvider(db);

    }
}
