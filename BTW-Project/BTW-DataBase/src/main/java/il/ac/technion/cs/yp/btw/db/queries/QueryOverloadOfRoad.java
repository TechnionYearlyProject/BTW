package il.ac.technion.cs.yp.btw.db.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

/*get from data base a road's  overload
* this class is not in use in the current semester*/
public class QueryOverloadOfRoad extends Query {

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public QueryOverloadOfRoad(String mapName, String nameID) {
        super(mapName);
        query = "SELECT * FROM dbo." + mapName + "Road WHERE nameID = '" + nameID + "'";
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * return the overload on a road queried from the data base*/
    @Override
    public Long arrangeRecievedData(ResultSet resultSet){
        long overloadOfRoad = 0;
        try{
            resultSet.next();
            overloadOfRoad =  resultSet.getLong("overload");
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return overloadOfRoad;

    }
}