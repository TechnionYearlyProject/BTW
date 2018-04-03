package il.ac.technion.cs.yp.btw.db;

import com.google.gson.JsonElement;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCrossRoad;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import java.util.*;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class GeoJasonToJavaParser {

    private Set<TrafficLight> trafficLights;
    private Set<Crossroad> crossRoads;
    private Set<Road> roads;
    private String mapName;
    private int roadsCoordsNum = 2;
    private int sourceCoordIndex = 0;
    private int destinationCoordIndex = 1;
    private int coordXIndex = 0;
    private int coordYIndex = 1;
    private int coordsNum = 2;

    /*@author: Sharon Hadar
    *@Date: 30/3/2018
    * a constructor to the class
    * get a geojason string and parse from it sets of: roads, crossroads and trafficlights
    * */
    public GeoJasonToJavaParser(String mapName, String geoJson){
        this.trafficLights = new HashSet<>();
        this.crossRoads = new HashSet<>();
        this.roads = new HashSet<>();
        this.mapName = mapName;
        parseFromGeoJasonToJava(geoJson);
    }

    /*@Author: Sharon Hadar
    *@Date: 30/3/2018
    *return all parsed traffic lights
    * */
    public Set<TrafficLight> getTrafficLights() {
        return trafficLights;
    }

    /*@Author: Sharon Hadar
    *@Date: 30/3/2018
    *return all parsed crossroads
    * */
    public Set<Crossroad> getCrossRoads() {
        return crossRoads;
    }

    /*@Author: Sharon Hadar
    *@Date: 30/3/2018
    *return all parsed roads
    * */
    public Set<Road> getRoads() {
        return roads;
    }

    /*@Author: Sharon Hadar
    *@Date: 30/3/201
    *get a string in a geojson form and build java sets of trafficlights, roads and crossroads.
    * an example to the string pattern is:
    * "{\"type\": \"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0023005753157462274,0.004581399302080609]},\"properties\":{\"name\":\"from:43 Street to:43 StreetR\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.014402113034462271,0.006739623239087599]},\"properties\":{\"name\":\"from:33 StreetR to:31 Street\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0014190683853251293,0.014645366930914668]},\"properties\":{\"name\":\"from:3 StreetR to:3 Street\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.01635651734518988,0.008806996592190556],[0.014402113034462271,0.006739623239087599]]},\"properties\":{\"name\":\"31 StreetR\",\"length\":\"316\",\"overload\":0}}]}";
    * the string is an array of features and every feature has two attributes "geometry" and "properties"
    * the feature types are "Point" and "LineString"  as shown in the example.
    * */
    private void parseFromGeoJasonToJava(String geoJson){
        Map<Point, Crossroad> crossroadMap = new HashMap<>();
        JsonParser mapParser = new JsonParser();
        JsonElement jsonTree = mapParser.parse(geoJson);
        JsonObject jsonOb = jsonTree.getAsJsonObject();
        JsonArray features =jsonOb.getAsJsonArray("features");
        Iterator<JsonElement> iterator = features.iterator();
        while (iterator.hasNext()) {
            JsonObject feature = iterator.next().getAsJsonObject();
            JsonObject geometry = feature.get("geometry").getAsJsonObject();
            String geometryType = geometry.get("type").getAsString();
            JsonObject properties = feature.get("properties").getAsJsonObject();
            if(geometryType.equalsIgnoreCase("Point")){
                Point point = parsePointFromPointGeometry(geometry);
                TrafficLight trafficLight = parseTrafficLight(/*geometry*/ point, properties);
                trafficLights.add(trafficLight);

                //Point point = parsePointFromPointGeometry(geometry);
                if(crossroadMap.containsKey(point)){
                    crossroadMap.get(point).addTrafficLight(trafficLight);
                }else{
                    Crossroad crossroad = parseCrossroad(/*geometry*/ point);
                    crossroad.addTrafficLight(trafficLight);
                    crossroadMap.put(point, crossroad);
                }
            }
            if(geometryType.equalsIgnoreCase("LineString")){
                Road road = parseRoad(geometry, properties);
                roads.add(road);
            }
        }
        this.crossRoads = new HashSet<>(crossroadMap.values());
    }

    /*@Author: Sharon Hadar
    *@Date: 30/3/2018
    *get the geometry an properties of feature from type Point, and parse it to java class TrafficLight
    * the geometry pattern is: {"type":"Point","coordinates":[0.0023005753157462274,0.004581399302080609]}
    * the properties pattern is: {"name":"from:43 Street to:43 StreetR","overload":0}
    * */
    private TrafficLight parseTrafficLight(/*JsonObject geometry*/ Point pos,  JsonObject properties){
        String nameID = properties.get("name").getAsString();
        //Point pos = parsePointFromPointGeometry(geometry);
        String sourceRoadName = nameID.split("from:| to:")[1];
        String destinationRoadName = nameID.split(" to:")[1];
        long overload =  properties.get("overload").getAsLong();
        return new DataTrafficLight(nameID, pos, sourceRoadName, destinationRoadName, overload, mapName);
    }

    /*@Author: Sharon Hadar
    *@Date: 30/3/2018
    *get the geometry of feature from type Point, and parse it to java class Point
    * the geometry's pattern is: {"type":"Point","coordinates":[0.0023005753157462274,0.004581399302080609]}
    * */
    private Point parsePointFromPointGeometry(JsonObject geometry){
        JsonArray coordinates = geometry.get("coordinates").getAsJsonArray();
        return parsePoint(coordinates);
    }

    /*@Author: Sharon Hadar
    *@Date: 30/3/201
    *get the geometry of feature from type LineString, and parse it to array of two java class Point
    * the 0 index is the source
    * the 1 index is the destination
    * the geometry pattern is: {"type":"LineString","coordinates":[[0.01635651734518988,0.008806996592190556],[0.014402113034462271,0.006739623239087599]]}
    * */
    private Point[] parsePointFromLineStringGeometry(JsonObject geometry){
        Point[] points = new Point[roadsCoordsNum];
        JsonArray coordinates = geometry.get("coordinates").getAsJsonArray();
        Iterator<JsonElement> coordinatesIterator = coordinates.iterator();
        points[sourceCoordIndex] = parsePoint(coordinatesIterator.next().getAsJsonArray());
        points[destinationCoordIndex] = parsePoint(coordinatesIterator.next().getAsJsonArray());
        return points;
    }


    /*@Author: Sharon Hadar
     *@Date: 30/3/2018
     * parse a json array of coordinates to a java class Point
     * the coordinates pattern is: [0.01635651734518988,0.008806996592190556]
     * */
    private Point parsePoint(JsonArray coordinates){
        double coord[] = new double[coordsNum];
        Iterator<JsonElement> iterator = coordinates.iterator();
        coord[coordXIndex] = iterator.next().getAsDouble();
        coord[coordYIndex] = iterator.next().getAsDouble();
        return new PointImpl(coord[coordXIndex], coord[coordYIndex]);
    }

    /*@Author: Sharon Hadar
    *@Date: 30/3/2018
    *get the geometry of feature from type Point, and parse it to java class CrossRoad
    * the geometry's pattern is: {"type":"Point","coordinates":[0.0023005753157462274,0.004581399302080609]}
    * */
    private Crossroad parseCrossroad(/*JsonObject geometry*/ Point position){
        //Point position = parsePointFromPointGeometry(geometry);
        String name = "cross " + position.toString();
        return new DataCrossRoad(position,name,mapName);
    }

    /*@Author: Sharon Hadar
    *@Date: 30/3/2018
    *get the geometry of feature from type LineString, and parse it to java class Road
    * the geometry's pattern is: {"type":"LineString","coordinates":[[0.01635651734518988,0.008806996592190556],[0.014402113034462271,0.006739623239087599]]}
    * the properties pattern is: {"name":"31 StreetR","length":"316","overload":0}}]}
    * */
    private Road parseRoad(JsonObject geometry,  JsonObject properties){
        String name = properties.get("name").getAsString();
        int roadLength =  properties.get("length").getAsInt();
        String myStreet = name.split(" Street| StreetR")[0];
        Point[] points = parsePointFromLineStringGeometry(geometry);
        Point sourceCrossroadId = points[sourceCoordIndex];
        Point destinationCrossroadId = points[destinationCoordIndex];
        int secStart = 0;
        if(name.contains("section")) {
            secStart = Integer.parseInt(name.split("section ")[1].split("R")[0]);
        }
        int secEnd = secStart;
        long overload = properties.get("overload").getAsLong();
        return new DataRoad(name, roadLength, myStreet, sourceCrossroadId, destinationCrossroadId, secStart, secEnd, overload, mapName);

    }

}