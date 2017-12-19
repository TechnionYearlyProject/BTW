package il.ac.technion.cs.yp;

import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Street;
import il.ac.technion.cs.yp.btw.classes.StreetImpl;
import junit.framework.TestCase;
import il.ac.technion.cs.yp.btw.db.queries.Query;
import il.ac.technion.cs.yp.btw.db.MainDataBase;
import junit.framework.TestResult;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDataBaseQuerying  extends TestCase {
    //the test method name needs to begin with the word 'test'
    public void testRun(){
        TestResult result = new TestResult();
        Query query = new QueryRoadExample("first");
        MainDataBase database = new MainDataBase();
        Object obj = database.queryDataBase(query);
        //System.out.println(obj.toString());

    }

    public void testBuildPlace(){

    }
}


class QueryRoadExample extends Query{

    public QueryRoadExample(String mapName){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "Road";
    }

    @Override
    public Object arrangeRecievedData(ResultSet resultSet){
        try{
            while (resultSet.next()){
                String nameID = resultSet.getString("nameID");
                int cord1x =  resultSet.getInt("cord1x");
                int cord2x =  resultSet.getInt("cord2x");
                int cord1y =  resultSet.getInt("cord1y");
                int cord2y =  resultSet.getInt("cord2y");
                int length =  resultSet.getInt("length");
                int secStart =  resultSet.getInt("secStart");
                int secEnd =  resultSet.getInt("secEnd");
                String overload =  resultSet.getString("overload");



                System.out.println(  "nameID = "  + nameID
                                    +" cord1x = " + cord1x
                                    +" cord2x = " + cord2x
                                    +" cord1y = " + cord1y
                                    +" cord2y = " + cord2y
                                    +" length = " + length
                                    +" secStart = " + secStart
                                    +" secEnd = " + secEnd
                                    +" overload = " + overload);


              //  RoadImpl road = new RoadImpl(nameID, length, Street myStreet, Crossroad sourceCrossroad, Crossroad destinationCrossroad);
            }
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        //return road;
        return null;
    }
}
