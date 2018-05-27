package il.ac.technion.cs.yp.btw.geojson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Anat and Adam Elgressy
 * @date 20/1/18
 * Implementing the GeoJson parser
 */


public class GeoJsonParserImpl implements GeoJsonConverter {
    final static Logger logger = Logger.getLogger("GeoJsonParserImpl");
    /**
     * @author Anat
     * @date 20/1/18
     * Create file that contains the appropriate geoJson
     * string for the given simulator.
     * @Param: simulator- the simulator of the map that
     * we want to create a geoJson file for her
     * @return : File contains GeoJson String discribing the map.
     */
    public File buildGeoJsonFromSimulation(MapSimulator simulator) {
        Set<TrafficLight> trafficLights = simulator.getTrafficLights();
        Set<Road> roads = simulator.getRoads();
        Set<Street> streets = simulator.getStreets();
        Set<Crossroad> crossRoads = simulator.getCrossRoads();
        Set<CentralLocation> centralLocations = simulator.getCentralLocations();

        File file = new File("JsonFile.json");

        try {
//            boolean success = file.createNewFile();
//            assert (success);

            FileOutputStream fileWriter = new FileOutputStream(file);
//            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
//            FileWriter fileWriter = new FileWriter(file);

            //The { moved to the end, after delete the last ,
            String startFeature = "{\"type\": \"FeatureCollection\""+","+
                    "\"features\":[" ;

            fileWriter.write(startFeature.getBytes());
//            fileWriter.write(startFeature);
            fileWriter.flush();

            if(trafficLights!=null) {
                for (TrafficLight tl : trafficLights) {
                    String jsonData =this.toStringTrafficLightFull(tl);
                    //write the data to json file
                    fileWriter.write(jsonData.getBytes());
                }
            }
            fileWriter.flush();
            if(roads!=null) {
                for (Road rd : roads) {
                    String jsonData = this.toStringRoadFull(rd);
                    //write the data to json file
                    fileWriter.write(jsonData.getBytes());
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
                    fileWriter.write(jsonData.getBytes());
                }
            }
            fileWriter.flush();
            fileWriter.close();
            //Deleting last ,
            Scanner s = new Scanner(file);
            String content = s.useDelimiter("\\Z").next();
            s.close();
            String withoutLast = content.substring(0 , content.length() - 1);

            FileOutputStream fileWriter2 = new FileOutputStream(file);

//            BufferedWriter fileWriter2 = new BufferedWriter(new FileWriter(file));
//            FileWriter fileWriter2 = new FileWriter(file);
            fileWriter2.write(withoutLast.getBytes());

//            fileWriter2.write(withoutLast);
            fileWriter2.flush();

            //return the string that include all the data/

            String endFeature = "]}";

            fileWriter2.write(endFeature.getBytes());
            fileWriter2.flush();
            fileWriter2.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            Files.delete(file.toPath());
//        } catch (IOException e) {
//            System.out.println("BAD");
//        }
//        System.out.println("Hello World");
//        file.delete();
        return file;
    }

    /**
     * @author  Anat
     * @date  20/1/18
     * Create GeoJson string for central locations.
     * @param centralLocation- the location that we want geoJson string for.
     * @return string in geoJson format for the given central location.
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
     * @author Anat
     * @date 20/1/18
     * Create GeoJson full string for Road.
     * @param road- the road that we want geoJson string for.
     * @return string in geoJson full format for the given road.
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
     * @author Anat
     * @date 20/1/18
     * Create GeoJson string for road.
     * @param road- the road that we want geoJson string for.
     * @return string in geoJson format for the given road.
     */
    @Override
    public String toStringRoad(Road road) {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"LineString\","+"\"coordinates\""+":"+
                "[["+road.getSourceCrossroad().getCoordinateX()+","+road.getSourceCrossroad().getCoordinateY()+"],"+
                "["+road.getDestinationCrossroad().getCoordinateX()+","+road.getDestinationCrossroad().getCoordinateY()+"]]},"+
                "\"properties\":{"+"\"name\":"+0/*road.getMinimumWeight()*/+"}},\n";
    }

    /**
     * @author Anat
     * @date 20/1/18
     * Create GeoJson full string for traffic light.
     * @param trafficLight- the traffic light that we want geoJson string for.
     * @return string in geoJson full format for the given traffic light.
     */
    @Override
    public String toStringTrafficLightFull(TrafficLight trafficLight) {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
                "["+trafficLight.getCoordinateX()+","+trafficLight.getCoordinateY()+"]},"+
                "\"properties\":{"+"\"name\":"+"\""+trafficLight.getName()+"\","+
                "\"overload\":"+0/*road.getMinimumWeight()*/+"}},\n";
    }

    /**
     * @author Anat
     * @date 20/1/18
     * Create GeoJson string for traffic light.
     * @param trafficLight- the traffic light that we want geoJson string for.
     * @return string in geoJson format for the given traffic light.
     */
    @Override
    public String toStringTrafficLight(TrafficLight trafficLight) {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
                "[["+trafficLight.getCoordinateX()+","+trafficLight.getCoordinateY()+"]},"+
                "\"properties\":{"+"\"name\":"+"\""+trafficLight.getName()+"\"}},\n";
    }

    /**
     * @author Anat
     * @date 20/1/18
     * Create GeoJson string for crossRoad.
     * @param crossroad- the street that we want geoJson string for.
     * @return string in geoJson format for the given crossRoad.
     */
    @Override
    public String toStringCrossRoad(Crossroad crossroad) {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
                "["+crossroad.getCoordinateX()+","+crossroad.getCoordinateY()+"]},"+
                "\"properties\":{"+"\"name\":"+"\""+crossroad.getName()+"\"}},\n";
    }

    /**
     * @author Anat
     * @date 20/1/18
     * Create GeoJson full string for street.
     * @param street- the street that we want geoJson string for.
     * @return string in geoJson full format for the given street.
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
     * @author Anat
     * @date 20/1/18
     * Create GeoJson full string for street.
     * @param street- the street that we want geoJson string for.
     * @return string in geoJson full format for the given street.
     */
    @Override
    public String toStringStreet(Street street){
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"LineString\"},"+
                "\"properties\":{"+"\"name\":"+"\""+street.getStreetName()+"\"}},\n";
    }

    /**
     * @author Adam Elgressy
     * @Date 24-4-2018
     * @param pathToFile - the URL of the file to check, and read from
     *                   the geojson data.
     * @return String of the geojson map file, located at the given URL
     * @throws MapFileNotFoundException - when the given path does  not exist, or if
     *                              pathToFile is null.
     * @throws MapFileNotWithGeoJsonExtensionException - whe the map file doesn't
     *                              ends with the .geojson extension
     * @throws FileNotOfJsonSyntaxException - when the geojson data is not following
     *                              the Json syntax rules
     */
    public String getDataFromFile(URL pathToFile) throws MapParsingException{
        if(pathToFile == null) {
            logger.debug("GeoJsonParser gets null parameter");
            throw new MapFileNotFoundException("path to map file doesn't exist");
        }
        logger.debug("GeoJsonParser tries to open the file from the given URL:"+pathToFile.toString());
        File mapFile = new File(pathToFile.getFile());
        if(!mapFile.exists()){
            logger.debug("the URL:"+pathToFile.toString()+" leads to a non existing file");
            throw new MapFileNotFoundException("path to map file doesn't exist");
        }
        String name = mapFile.getName();
        logger.debug("The URL is valid, now checking extension");
        if(!name.endsWith(".geojson")){
            throw new MapFileNotWithGeoJsonExtensionException("map file doesn't end with .geojson");
        }
        String fileContents = "";
        try {
            logger.debug("Reading all lines of the file from:"+pathToFile.toString());
            fileContents = new Scanner(mapFile).useDelimiter("\\Z").next();
        } catch (FileNotFoundException ignored) {}
        logger.debug("Begin Gson initialization");
        Gson gs = new GsonBuilder().create();
        Type listType = new TypeToken<List>() {
        }.getType();
        try{
            logger.debug("Try to read with Gson the file content");
            gs.fromJson("["+fileContents+"]", listType);}
        catch (JsonSyntaxException e){
            throw new FileNotOfJsonSyntaxException(e.getMessage());
        }
        return fileContents;
    }
}