package il.ac.technion.cs.yp.btw.db.queries;

import java.sql.ResultSet;
import java.sql.SQLException;


/*get from data base an overload on a traffic light by its name*/
public class QueryOverloadOfTrafficLight extends Query {
    public QueryOverloadOfTrafficLight(String mapName, String nameID) {
        super(mapName);
        this.query = "SELECT * FROM dbo." + mapName + "TrafficLight WHERE nameID = '" + nameID + "'";
    }

    @Override
    public Long arrangeRecievedData(ResultSet resultSet){
        long overloadOfTL = 0;
        try{
            resultSet.next();
            overloadOfTL =  resultSet.getLong("overload");
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return overloadOfTL;

    }
}
