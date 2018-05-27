package il.ac.technion.cs.yp.btw.db.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: shay
 * @date: 08/01/2018.
 * gets from the data base the distance between two roads.
 */
public class QueryHeuristicDist extends Query{
    public QueryHeuristicDist(String mapName, String srcID, String dstID) {
        super(mapName);
        this.query = "SELECT * FROM dbo." + mapName + "Heuristics WHERE sourceID = '" + srcID + "'"
                + " AND targetID = '"  + dstID + "';";
    }

    @Override
    public Long arrangeRecievedData(ResultSet resultSet){
        long overloadOfRoad = 0;
        try{
            resultSet.next();
            overloadOfRoad =  resultSet.getLong("overload");
        }catch(SQLException e){
            logger.error("query heuristicsDist has failed");
        }
        return overloadOfRoad;

    }
}
