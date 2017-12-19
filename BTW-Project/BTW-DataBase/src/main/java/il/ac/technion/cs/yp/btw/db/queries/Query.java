package il.ac.technion.cs.yp.btw.db.queries;

import java.sql.ResultSet;

public abstract class Query {
    static protected String query;
    static protected String mapName = "";
    public Query(String mapName){
        this.mapName = mapName;
    }
    public String getQuery(){
        return query;
    }

    public abstract Object arrangeRecievedData(ResultSet resultSet);
}
