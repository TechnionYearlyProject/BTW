package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.statistics.StatisticsProvider;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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
    public void testCreateStatisticsTable() {
        BTWDataBaseImpl db = new BTWDataBaseImpl("ShayTest1");
        db.StatisticsMode = true;
        String s = "{\"type\": \"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 avenue section 1R to:1 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:2 avenue section 1R to:2 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:2 avenue section 1R to:1 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 street section 1R to:1 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1R to:2 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:1 street section 1 to:1 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 avenue section 1R to:1 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 avenue section 1 to:2 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 avenue section 1 to:2 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:1 avenue section 1 to:2 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:1 street section 1 to:2 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1 to:2 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 street section 1R to:1 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1 to:2 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1R to:1 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:1 avenue section 1 to:1 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0054000054000054],[0.0,0.0]]},\"properties\":{\"name\":\"1 avenue section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0054000054000054],[0.0,0.0054000054000054]]},\"properties\":{\"name\":\"2 street section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0054000054000054],[0.0054000054000054,0.0]]},\"properties\":{\"name\":\"2 avenue section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0],[0.0054000054000054,0.0]]},\"properties\":{\"name\":\"1 street section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0],[0.0,0.0054000054000054]]},\"properties\":{\"name\":\"1 avenue section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0054000054000054],[0.0054000054000054,0.0054000054000054]]},\"properties\":{\"name\":\"2 street section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0],[0.0054000054000054,0.0054000054000054]]},\"properties\":{\"name\":\"2 avenue section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0],[0.0,0.0]]},\"properties\":{\"name\":\"1 street section 1R\",\"length\":\"600\",\"overload\":0}}]}";
        db.saveMap(s);
        db.createStatisticsTables(db.getAllRoads(),db.getAllTrafficLights());
        assertNotNull(db);

    }

    /**
     * @Author: Shay
     * @Date: 4/5/18
     * testing DBStatisticsProvider module - not from DB... only unit test
     */

    @Test
    public void testStatisticsProviderUnitTest() {
        StatisticsProvider stats = new DBStatisticsProvider(null,null);
        assertNotNull(stats);

        // need to implement with roads and traffic lights

    }

    /**
     * @Author: Shay
     * @Date: 4/5/18
     * testing DBStatisticsProvider module - with DB
     */

    @Test
    public void testStatisticsProviderWithDB() {
        BTWDataBaseImpl db = new BTWDataBaseImpl("ShayTest1");

        // NEED TO IMPLEMENT HERE !!!

        assertNotNull(db);

    }
}
