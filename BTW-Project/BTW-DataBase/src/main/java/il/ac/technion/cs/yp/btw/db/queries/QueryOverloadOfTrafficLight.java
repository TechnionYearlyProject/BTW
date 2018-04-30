package il.ac.technion.cs.yp.btw.db.queries;

import java.sql.ResultSet;
import java.sql.SQLException;


/*get from data base an overload on a traffic light by its name
* this class is not in use in the current semester*/
public class QueryOverloadOfTrafficLight extends Query {
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public QueryOverloadOfTrafficLight(String mapName, String nameID) {
        super(mapName);
        this.query = "SELECT * FROM dbo." + mapName + "TrafficLight WHERE nameID = '" + nameID + "'";
    }

    /*
    * @author Sharon Hadar
    * @Date 21/01/2018
    * return the overload on a traffic light queried from the data base
    * */
    @Override
    public Long arrangeRecievedData(ResultSet resultSet){
        long overloadOfTL = 0;
        try{
            resultSet.next();
            overloadOfTL =  resultSet.getLong("overload");
        }catch(SQLException e){
            logger.error("query has failed");
        }
        return overloadOfTL;

    }
}
