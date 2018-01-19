package il.ac.technion.cs.yp.btw.db.queries;

import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCrossRoad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.HashSet;

/*get from data base a cross road by its position*/
public class QueryCrossRoad extends Query{

    private Point position;

    public QueryCrossRoad(String mapName, Point position){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "TrafficLight WHERE cordx = " + position.getCoordinateX() + " AND cordy = " + position.getCoordinateY();
        this.position = position;
    }

    @Override
    public Crossroad arrangeRecievedData(ResultSet resultSet){
        Set<TrafficLight> trafficLights = new HashSet();
        try{
            while (resultSet.next()){


                String nameID = resultSet.getString("nameID");
                int cordx =  resultSet.getInt("cordx");
                int cordy =  resultSet.getInt("cordy");
                String sourceRoadId = nameID.split("from:|to:")[1];
                //        resultSet.getString("from");
                String destinationRoadIf = nameID.split("to:")[1];
                //        resultSet.getString("to");
                long overload =  resultSet.getLong("overload");
                Point position = new PointImpl(cordx, cordy);
                TrafficLight trafficLight  = new DataTrafficLight(nameID, position, sourceRoadId,destinationRoadIf, overload, mapName);
                trafficLights.add(trafficLight);

            }
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        Crossroad crossRoad = new DataCrossRoad(this.position, trafficLights, mapName);
        return crossRoad;

    }
}