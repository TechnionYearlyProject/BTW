package il.ac.technion.cs.yp.btw.db.queries;


import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryTrafficLight extends Query {

    public QueryTrafficLight(String mapName, String nameID) {
        super(mapName);
        this.query = "SELECT * FROM dbo." + mapName + "TrafficLight WHERE nameID = '" + nameID + "'";
    }

    @Override
    public TrafficLight arrangeRecievedData(ResultSet resultSet){
        TrafficLight trafficLight = null;
        try{
            resultSet.next();
            String nameID = resultSet.getString("nameID");
            int cordx =  resultSet.getInt("cordx");
            int cordy =  resultSet.getInt("cordy");
            String sourceRoadId = nameID.split("-")[1];
            String destinationRoadIf = nameID.split("-")[2];
            String overload =  resultSet.getString("overload");
            Point position = new PointImpl(cordx, cordy);
            trafficLight  = new DataTrafficLight(nameID, position, sourceRoadId,destinationRoadIf, overload, mapName);

        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return trafficLight;

    }
}