package il.ac.technion.cs.yp.btw.geojson;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Set;

/**
 * @author: Anat
 * @date: 20/1/18
 * Implementing the GeoJson parser
 */


public class GeoJsonParserImpl implements GeoJsonConverter {

    /**
     * @author: Anat
     * @date: 20/1/18
     * Create file that contains the appropriate geoJson
     * string for the given simulator.
     */
    public File buildGeoJsonFromSimulation(MapSimulator simulator) {
        Set<TrafficLight> trafficLights = simulator.getTrafficLights();
        Set<Road> roads = simulator.getRoads();
        Set<Street> streets = simulator.getStreets();
        Set<Crossroad> crossRoads = simulator.getCrossRoads();
        Set<CentralLocation> centralLocations = simulator.getCentralLocations();

        File file = new File("JsonFile.json");

        try {
            boolean success = file.createNewFile();
            assert (success);

            FileWriter fileWriter = new FileWriter(file);

            //The { moved to the end, after delete the last ,
            String startFeature = "{\"type\": \"FeatureCollection\""+","+
                    "\"features\":[" ;

            fileWriter.write(startFeature);
            fileWriter.flush();

            if(trafficLights!=null) {
                for (TrafficLight tl : trafficLights) {
                    String jsonData =this.toStringTrafficLightFull(tl);
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }
            fileWriter.flush();
            if(roads!=null) {
                for (Road rd : roads) {
                    String jsonData = this.toStringRoadFull(rd);
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }
            fileWriter.flush();
            /*if(streets!=null) {
                for (Street st : streets) {
                    String jsonData = this.toStringStreetFull(st);
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }*/
            //fileWriter.flush();
            //if(crossRoads!=null) {
             //   for (Crossroad cr : crossRoads) {
             //       String jsonData = this.toStringCrossRoad(cr);
             //       //write the data to json file
             //       fileWriter.write(jsonData);
             //   }
            //}
            //fileWriter.flush();
            if(centralLocations!=null) {
                for (CentralLocation cl : centralLocations) {
                    String jsonData = this.toStringLocation(cl);
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }

            //Deleting last ,
            String content = new Scanner (file).useDelimiter("\\Z").next();
            String withoutLast = content.substring(0 , content.length() - 1);

            FileWriter fileWriter2 = new FileWriter(file);
            fileWriter2.write(withoutLast);
            fileWriter2.flush();

            //return the string that include all the data/

            String endFeature = "]}";

            fileWriter2.write(endFeature);
            fileWriter2.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return  file;
    }

    /**
     * @author: Anat
     * @date: 20/1/18
     * Create GeoJson string for central locations.
     * @Param: centralLocation- the location that we want geoJson string for.
     * @return: string in geoJson format for the given central location.
     */
    @Override
    public String toStringLocation(CentralLocation centralLocation) {
        Point p1,p2,p3,p4;
        Set<Point> vertices = centralLocation.getVertices();
        p1 = (Point)vertices.toArray()[0];
        p2 = (Point)vertices.toArray()[1];
        p3 = (Point)vertices.toArray()[2];
        p4 = (Point)vertices.toArray()[3];

        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
                "[["+p1.getCoordinateX()+","+p1.getCoordinateY()+"],"+"["+p2.getCoordinateX()+","+p2.getCoordinateY()+"],"+
                "["+p3.getCoordinateX()+","+p3.getCoordinateY()+"],"+"["+p4.getCoordinateX()+","+p4.getCoordinateY()+"]]},"+
                "\"properties\":{"+"\"name\":"+"\""+centralLocation.getName()+"\"},\n";
    }

    /**
     * @author: Anat
     * @date: 20/1/18
     * Create GeoJson full string for Road.
     * @Param: road- the road that we want geoJson string for.
     * @return: string in geoJson full format for the given road.
     */
    @Override
    public String toStringRoadFull(Road road) {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"LineString\","+"\"coordinates\""+":"+
                "[["+road.getSourceCrossroad().getCoordinateX()+","+road.getSourceCrossroad().getCoordinateY()+"],"+
                "["+road.getDestinationCrossroad().getCoordinateX()+","+road.getDestinationCrossroad().getCoordinateY()+"]]},"+
                "\"properties\":{"+"\"name\":"+"\""+road.getRoadName()+"\","+
                "\"length\":"+"\""+road.getRoadLength()+"\","+"\"overload\":"+0/*road.getMinimumWeight()*/+"}},\n";
    }

    /**
     * @author: Anat
     * @date: 20/1/18
     * Create GeoJson string for road.
     * @Param: road- the road that we want geoJson string for.
     * @return: string in geoJson format for the given road.
     */
    @Override
    public String toStringRoad(Road road) {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"LineString\","+"\"coordinates\""+":"+
                "[["+road.getSourceCrossroad().getCoordinateX()+","+road.getSourceCrossroad().getCoordinateY()+"],"+
                "["+road.getDestinationCrossroad().getCoordinateX()+","+road.getDestinationCrossroad().getCoordinateY()+"]]},"+
                "\"properties\":{"+"\"name\":"+0/*road.getMinimumWeight()*/+"}},\n";
    }

    /**
     * @author: Anat
     * @date: 20/1/18
     * Create GeoJson full string for traffic light.
     * @Param: trafficLight- the traffic light that we want geoJson string for.
     * @return: string in geoJson full format for the given traffic light.
     */
    @Override
    public String toStringTrafficLightFull(TrafficLight trafficLight) {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
                "["+trafficLight.getCoordinateX()+","+trafficLight.getCoordinateY()+"]},"+
                "\"properties\":{"+"\"name\":"+"\""+trafficLight.getName()+"\","+
                "\"overload\":"+0/*road.getMinimumWeight()*/+"}},\n";
    }

    /**
     * @author: Anat
     * @date: 20/1/18
     * Create GeoJson string for traffic light.
     * @Param: trafficLight- the traffic light that we want geoJson string for.
     * @return: string in geoJson format for the given traffic light.
     */
    @Override
    public String toStringTrafficLight(TrafficLight trafficLight) {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
                "[["+trafficLight.getCoordinateX()+","+trafficLight.getCoordinateY()+"]},"+
                "\"properties\":{"+"\"name\":"+"\""+trafficLight.getName()+"\"}},\n";
    }

    /**
     * @author: Anat
     * @date: 20/1/18
     * Create GeoJson string for crossRoad.
     * @Param: crossroad- the street that we want geoJson string for.
     * @return: string in geoJson format for the given crossRoad.
     */
    @Override
    public String toStringCrossRoad(Crossroad crossroad) {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
                "["+crossroad.getCoordinateX()+","+crossroad.getCoordinateY()+"]},"+
                "\"properties\":{"+"\"name\":"+"\""+crossroad.getName()+"\"}},\n";
    }

    /**
     * @author: Anat
     * @date: 20/1/18
     * Create GeoJson full string for street.
     * @Param: street- the street that we want geoJson string for.
     * @return: string in geoJson full format for the given street.
     */
    @Override
    public String toStringStreetFull(Street street) {
        String roadsNames = "";
        for (Road road: street.getAllRoadsInStreet()) {
            roadsNames+=road.getRoadName();
        }

        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"LineString\"},"+
                "\"properties\":{"+"\"name\":"+"\""+street.getStreetName()+"\","+
                "\"included_streets\":"+"\""+roadsNames+"\"}},\n";
    }

    /**
     * @author: Anat
     * @date: 20/1/18
     * Create GeoJson full string for street.
     * @Param: street- the street that we want geoJson string for.
     * @return: string in geoJson full format for the given street.
     */
    @Override
    public String toStringStreet(Street street){
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"LineString\"},"+
                "\"properties\":{"+"\"name\":"+"\""+street.getStreetName()+"\"}},\n";
    }
}
//return file name- full path