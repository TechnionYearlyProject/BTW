package il.ac.technion.cs.yp.btw.db.queries;


import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

/*get from data base a traffic light by its name
* this class is not in use in the current semester*/
public class QueryTrafficLight extends Query {

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public QueryTrafficLight(String mapName, String nameID) {
        super(mapName);
        this.query = "SELECT * FROM dbo." + mapName + "TrafficLight WHERE nameID = '" + nameID + "'";
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * construct a traffic light by the result of the data base*/
    @Override
    public TrafficLight arrangeRecievedData(ResultSet resultSet){
        TrafficLight trafficLight = null;
        try{
            resultSet.next();
            String nameID = resultSet.getString("nameID");
            double cordx =  resultSet.getDouble("cordx");
            double cordy =  resultSet.getDouble("cordy");
            String sourceRoadId = nameID.split("from:|to:")[1];
            String destinationRoadIf = nameID.split("to:")[1];
            long overload =  resultSet.getLong("overload");
            Point position = new PointImpl(cordx, cordy);
            trafficLight  = new DataTrafficLight(nameID, position, sourceRoadId,destinationRoadIf, overload, mapName);

        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return trafficLight;

    }
}