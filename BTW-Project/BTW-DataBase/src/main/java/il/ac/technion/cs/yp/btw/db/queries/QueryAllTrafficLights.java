package il.ac.technion.cs.yp.btw.db.queries;

import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Point;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.HashSet;

/*get from the data base all traffic lights*/
public class QueryAllTrafficLights extends Query{
    /*
    * @author Sharon Hadar
     * @Date 21/01/2018
     * query all traffic lights from the database*/
    public QueryAllTrafficLights(String mapName){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "TrafficLight";
    }

    /*
    * @author Sharon Hadar
    * @Date 21/01/2018
    * query all traffic lights in a certain position*/
    public QueryAllTrafficLights(String mapName, Point position){
        super(mapName);
        this.query = "SELECT * FROM dbo." + mapName + "TrafficLight"
                +" WHERE cordx = " +  position.getCoordinateX() + " AND " + "cordy = " + position.getCoordinateY();
    }

    /*
    * @author Sharon Hadar
    * @Date 21/01/2018
    * build the recieved data from the data base results
    * */
    @Override
    public Set<TrafficLight> arrangeRecievedData(ResultSet resultSet){
        Set<TrafficLight> trafficLights = new HashSet();
        try{
            while (resultSet.next()){


                String nameID = resultSet.getString("nameID");
                double cordx =  resultSet.getDouble("cordx");
                double cordy =  resultSet.getDouble("cordy");
                String sourceRoadId = nameID.split("from:| to:")[1];
                //        resultSet.getString("from");
                String destinationRoadIf = nameID.split(" to:")[1];
                //        resultSet.getString("to");
                long overload =  resultSet.getLong("overload");
                Point position = new PointImpl(cordx, cordy);
                TrafficLight trafficLight  = new DataTrafficLight(nameID, position, sourceRoadId,destinationRoadIf, overload, mapName);

                trafficLights.add(trafficLight);

            }
        }catch(SQLException e){
            logger.error("query all trafficlights failed");
        }
        return trafficLights;

    }
}