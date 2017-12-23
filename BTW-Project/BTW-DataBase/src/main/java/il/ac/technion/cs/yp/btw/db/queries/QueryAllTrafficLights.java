package il.ac.technion.cs.yp.btw.db.queries;

import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Point;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.HashSet;

public class QueryAllTrafficLights extends Query{

    public QueryAllTrafficLights(String mapName){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "TrafficLight";
    }

    public QueryAllTrafficLights(String mapName, Point position){
        super(mapName);
        this.query = "SELECT * FROM dbo." + mapName + "TrafficLight"
                +" WHERE cordx = " +  position.getCoordinateX() + " AND " + "cordy = " + position.getCoordinateY();
        System.out.println(this.query);
    }
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
                String overload =  resultSet.getString("overload");
                Point position = new PointImpl(cordx, cordy);
                TrafficLight trafficLight  = new DataTrafficLight(nameID, position, sourceRoadId,destinationRoadIf, overload, mapName);

                trafficLights.add(trafficLight);

            }
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return trafficLights;

    }
}