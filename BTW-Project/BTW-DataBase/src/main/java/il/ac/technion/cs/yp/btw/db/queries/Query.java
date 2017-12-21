package il.ac.technion.cs.yp.btw.db.queries;

import java.sql.ResultSet;
/*
* the query string is of the form: "SELECT * FROM dbo."+ mapName + "Road WHERE nameID = 'Ayalonn'";
* SELECT provides the columns we are looking at. * means all the columns, otherwise, needs to spwcify the names with ',' separats between.
* From provides the tabels we are looking at.
* WHERE apply condition to filter the rows by. strings must be between ' ', equality is checked with a single "=" sign.
* the mapName is the prefix of every table that belongs to the map "mapNmae"
* */
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