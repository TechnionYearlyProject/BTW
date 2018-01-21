package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import junit.framework.TestCase;
import il.ac.technion.cs.yp.btw.db.queries.Query;
import il.ac.technion.cs.yp.btw.db.MainDataBase;
import junit.framework.TestResult;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.assertNotNull;


public class TestDataBaseQuerying {
    //the test method name needs to begin with the word 'test'
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testGetAllRoadsSet(){
        TestResult result = new TestResult();
        MainDataBase.openConnection();
        Query query = new QueryRoadExample("first");
        //MainDataBase database = new MainDataBase();
        //Set<Road> roads = (Set<Road>) database.queryDataBase(query);
        Set<Road> roads = (Set<Road>) MainDataBase.queryDataBase(query);
        Iterator<Road> iterator = roads.iterator();
        //System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            assertNotNull(iterator.next());
            //System.out.println(iterator.next().toString());
        }
        MainDataBase.closeConnection();

    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testGetAllCenteralLocations(){
        TestResult result = new TestResult();
        MainDataBase.openConnection();
        Query query = new QueryCenteralLocationExample("first");
        //MainDataBase database = new MainDataBase();
        //Set<CentralLocation> centeralLocations = (Set<CentralLocation>) database.queryDataBase(query);
        Set<CentralLocation> centeralLocations = (Set<CentralLocation>) MainDataBase.queryDataBase(query);
        Iterator<CentralLocation> iterator = centeralLocations.iterator();
        //System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            assertNotNull(iterator.next());
            //System.out.println(iterator.next().toString());
        }
        MainDataBase.closeConnection();
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testGetAllTrafficLights(){
        TestResult result = new TestResult();
        MainDataBase.openConnection();
        Query query = new QueryTrafficLightExample("first");
        //MainDataBase database = new MainDataBase();
        //Set<TrafficLight> trafficLights = (Set<TrafficLight>) database.queryDataBase(query);
        Set<TrafficLight> trafficLights = (Set<TrafficLight>) MainDataBase.queryDataBase(query);
        Iterator<TrafficLight> iterator = trafficLights.iterator();
        //System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            assertNotNull(iterator.next());
            //System.out.println(iterator.next().toString());
        }
        MainDataBase.closeConnection();
    }
}


class QueryRoadExample extends Query{

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public QueryRoadExample(String mapName){
        super(mapName);
        query = "SELECT * FROM dbo."+ mapName + "Road";
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Override
    public Set<Road> arrangeRecievedData(ResultSet resultSet){
        Set<Road> roads = new HashSet();
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

                String myStreet = nameID.split("st")[0];
                Point sourceCrossroadId  = new PointImpl(cord1x, cord1y);
                Point destinationCrossroadId = new PointImpl(cord2x, cord2y);

                Road road = new DataRoad(nameID, length,myStreet, sourceCrossroadId, destinationCrossroadId, mapName);
                System.out.println(road.toString());

                roads.add(road);

            }
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return roads;

    }
}


class QueryCenteralLocationExample extends Query{
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public QueryCenteralLocationExample(String mapName){
        super(mapName);
        query = "SELECT * FROM dbo."+ mapName + "Place";
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Override
    public Set<CentralLocation> arrangeRecievedData(ResultSet resultSet){
        Set<CentralLocation> centralLocations = new HashSet();
        try{
            while (resultSet.next()){
                String nameID = resultSet.getString("nameID");
                String street = resultSet.getString("street");
                int cord1x =  resultSet.getInt("cord1x");
                int cord2x =  resultSet.getInt("cord2x");
                int cord3x =  resultSet.getInt("cord3x");
                int cord4x =  resultSet.getInt("cord4x");
                int cord1y =  resultSet.getInt("cord1y");
                int cord2y =  resultSet.getInt("cord2y");
                int cord3y =  resultSet.getInt("cord3y");
                int cord4y =  resultSet.getInt("cord4y");

                Set<Point> points = new HashSet<>();
                points.add(new PointImpl(cord1x, cord1y));
                points.add(new PointImpl(cord2x, cord2y));
                points.add(new PointImpl(cord3x, cord3y));
                points.add(new PointImpl(cord4x, cord4y));



                System.out.println(  "nameID = "  + nameID
                        +" cord1x = " + cord1x
                        +" cord2x = " + cord2x
                        +" cord3x = " + cord3x
                        +" cord4x = " + cord4x
                        +" cord1y = " + cord1y
                        +" cord2y = " + cord2y
                        +" cord3y = " + cord3y
                        +" cord4y = " + cord4y);


                CentralLocation centralLocation = new CentralLocation(points, nameID);
                System.out.println(centralLocation.toString());

                centralLocations.add(centralLocation);

            }
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return centralLocations;

    }
}



class QueryTrafficLightExample extends Query{
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public QueryTrafficLightExample(String mapName){
        super(mapName);
        query = "SELECT * FROM dbo."+ mapName + "TrafficLight";
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Override
    public Set<TrafficLight> arrangeRecievedData(ResultSet resultSet){
        Set<TrafficLight> trafficLights = new HashSet();
        try{
            while (resultSet.next()){


                String nameID = resultSet.getString("nameID");
                int cordx =  resultSet.getInt("cordx");
                int cordy =  resultSet.getInt("cordy");
                String sourceRoadId = nameID.split("-")[1];
                //        resultSet.getString("from");
                String destinationRoadIf = nameID.split("-")[2];
                //        resultSet.getString("to");
                long overload =  resultSet.getLong("overload");
                Point position = new PointImpl(cordx, cordy);
                TrafficLight trafficLight  = new DataTrafficLight(nameID, position, sourceRoadId,destinationRoadIf, overload, mapName);

                System.out.println(trafficLight.toString());

                trafficLights.add(trafficLight);

            }
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return trafficLights;

    }
}
/*
class QueryCrossRoadExample extends Query{

    public QueryCrossRoadExample(String mapName){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "CrossRoad";
    }

    @Override
    public Set<CrossRoad> arrangeRecievedData(ResultSet resultSet){
        Set<CrossRoad> crossRoads = new HashSet();
        try{
            while (resultSet.next()){


                String nameID = resultSet.getString("nameID");
                int cordx =  resultSet.getInt("cordx");
                int cordy =  resultSet.getInt("cordy");
                String sourceRoadId = resultSet.getString("from");
                String destinationRoadIf = resultSet.getString("to");
                String overload =  resultSet.getString("overload");
                Point position = new PointImpl(cordx, cordy);
                TrafficLight trafficLight  = new DataTrafficLight(position, sourceRoadId,destinationRoadIf, overload);

                System.out.println(trafficLight.toString());

                trafficLights.add(trafficLight);

            }
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return trafficLights;

    }
}


*/