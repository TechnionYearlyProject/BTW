package il.ac.technion.cs.yp;

import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.classes.TrafficLightImpl;
import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.RoadImpl;
import junit.framework.TestCase;
import il.ac.technion.cs.yp.btw.db.queries.Query;
import il.ac.technion.cs.yp.btw.db.MainDataBase;
import junit.framework.TestResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class TestDataBaseQuerying  extends TestCase {
    //the test method name needs to begin with the word 'test'
    public void testGetAllRoadsList(){
        TestResult result = new TestResult();
        Query query = new QueryRoadExample("first");
        MainDataBase database = new MainDataBase();
        List<Road> roads = (List<Road>) database.queryDataBase(query);
        Iterator<Road> iterator = roads.iterator();
        System.out.println("\n\n\nthe result list is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }


    }

    public void testGetAllCenteralLocations(){
        TestResult result = new TestResult();
        Query query = new QueryCenteralLocationExample("first");
        MainDataBase database = new MainDataBase();
        List<CentralLocation> centeralLocations = (List<CentralLocation>) database.queryDataBase(query);
        Iterator<CentralLocation> iterator = centeralLocations.iterator();
        System.out.println("\n\n\nthe result list is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }

    public void testGetAllTrafficLights(){
        TestResult result = new TestResult();
        Query query = new QueryTrafficLightExample("first");
        MainDataBase database = new MainDataBase();
        List<TrafficLight> trafficLights = (List<TrafficLight>) database.queryDataBase(query);
        Iterator<TrafficLight> iterator = trafficLights.iterator();
        System.out.println("\n\n\nthe result list is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }
}


class QueryRoadExample extends Query{

    public QueryRoadExample(String mapName){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "Road";
    }

    @Override
    public List<Road> arrangeRecievedData(ResultSet resultSet){
        List<Road> roads = new LinkedList();
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

                Road road = new RoadImpl(nameID, length,myStreet, sourceCrossroadId, destinationCrossroadId);
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

    public QueryCenteralLocationExample(String mapName){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "Place";
    }

    @Override
    public List<CentralLocation> arrangeRecievedData(ResultSet resultSet){
        List<CentralLocation> centralLocations = new LinkedList();
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

                Set<Point> points = new HashSet<Point>();
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

    public QueryTrafficLightExample(String mapName){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "TrafficLight";
    }

    @Override
    public List<TrafficLight> arrangeRecievedData(ResultSet resultSet){
        List<TrafficLight> trafficLights = new LinkedList();
        try{
            while (resultSet.next()){


                String nameID = resultSet.getString("nameID");
                int cordx =  resultSet.getInt("cordx");
                int cordy =  resultSet.getInt("cordy");
                String sourceRoadId = resultSet.getString("from");
                String destinationRoadIf = resultSet.getString("to");
                String overload =  resultSet.getString("overload");
                Point position = new PointImpl(cordx, cordy);
                TrafficLight trafficLight  = new TrafficLightImpl(position, sourceRoadId,destinationRoadIf, overload);

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
    public List<CrossRoad> arrangeRecievedData(ResultSet resultSet){
        List<CrossRoad> crossRoads = new LinkedList();
        try{
            while (resultSet.next()){


                String nameID = resultSet.getString("nameID");
                int cordx =  resultSet.getInt("cordx");
                int cordy =  resultSet.getInt("cordy");
                String sourceRoadId = resultSet.getString("from");
                String destinationRoadIf = resultSet.getString("to");
                String overload =  resultSet.getString("overload");
                Point position = new PointImpl(cordx, cordy);
                TrafficLight trafficLight  = new TrafficLightImpl(position, sourceRoadId,destinationRoadIf, overload);

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