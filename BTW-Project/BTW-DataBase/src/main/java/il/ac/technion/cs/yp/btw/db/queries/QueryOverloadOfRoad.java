package il.ac.technion.cs.yp.btw.db.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

/*get from data base a road's  overload*/
public class QueryOverloadOfRoad extends Query {

    public QueryOverloadOfRoad(String mapName, String nameID) {
        super(mapName);
        query = "SELECT * FROM dbo." + mapName + "Road WHERE nameID = '" + nameID + "'";
    }

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