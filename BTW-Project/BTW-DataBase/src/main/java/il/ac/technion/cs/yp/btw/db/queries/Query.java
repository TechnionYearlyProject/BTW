package il.ac.technion.cs.yp.btw.db.queries;

import java.sql.ResultSet;

public abstract class Query {
    static protected String query;
    public Query(){

    }
    public String getQuery(){
        return query;
    }

    public abstract Object arrangeRecievedData(ResultSet resultSet);
}
