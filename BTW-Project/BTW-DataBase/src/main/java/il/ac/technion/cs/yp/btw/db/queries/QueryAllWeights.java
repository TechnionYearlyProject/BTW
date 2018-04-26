package il.ac.technion.cs.yp.btw.db.queries;

import il.ac.technion.cs.yp.btw.classes.BTWWeight;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shay on 26/04/2018.
 */
public class QueryAllWeights extends Query {

    public QueryAllWeights(String tableName) {
        super(mapName);
        this.query = "SELECT * FROM dbo."+ tableName;
    }

    @Override
    public BTWWeight[] arrangeRecievedData(ResultSet resultSet){
        BTWWeight[] weights = new BTWWeight[48];
        try{
            int i = 0;
            while (resultSet.next()){
                long overload =  resultSet.getLong("overload");
                BTWWeight weight = BTWWeight.of(overload);
                weights[i] = weight;
            }
            if (i != 48) {
                logger.error("Something Went Wrong with retrieving weights from DB");
            }
        }catch(SQLException e){
            logger.error("query has failed");
        }
        return weights;

    }
}
