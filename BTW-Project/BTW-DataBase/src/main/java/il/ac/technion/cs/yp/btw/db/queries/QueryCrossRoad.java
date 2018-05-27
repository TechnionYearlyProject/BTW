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

/*get from data base a cross road by its position
* this class is not in use in the current semester*/
public class QueryCrossRoad extends Query{

    private Point position;

    public QueryCrossRoad(String mapName, Point position){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "TrafficLight WHERE cordx = " + position.getCoordinateX() + " AND cordy = " + position.getCoordinateY();
        this.position = position;
    }

    /*
    * @author Sharon Hadar
    * @Date 21/01/2018
    * construct a cross road from the data base results
    * */
    @Override
    public Crossroad arrangeRecievedData(ResultSet resultSet){
        Set<TrafficLight> trafficLights = new HashSet();
        try{
            while (resultSet.next()){


                String nameID = resultSet.getString("nameID");
                double cordx =  resultSet.getDouble("cordx");
                double cordy =  resultSet.getDouble("cordy");
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
            logger.error("queryCrossRoad has failed");
        }
        Crossroad crossRoad = new DataCrossRoad(this.position, trafficLights, mapName);
        return crossRoad;

    }
}