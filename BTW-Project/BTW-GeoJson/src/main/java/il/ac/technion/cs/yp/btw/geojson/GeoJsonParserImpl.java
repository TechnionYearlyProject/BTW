package il.ac.technion.cs.yp.btw.geojson;

import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Street;
import il.ac.technion.cs.yp.btw.mapsimulation.MapSimulator;
import il.ac.technion.cs.yp.btw.mapsimulation.objects.MapSimulationCrossroadImpl;
import il.ac.technion.cs.yp.btw.mapsimulation.objects.MapSimulationRoadImpl;
import il.ac.technion.cs.yp.btw.mapsimulation.objects.MapSimulationStreetImpl;
import il.ac.technion.cs.yp.btw.mapsimulation.objects.MapSimulationTrafficLightImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Implementing the GeoJson parser
 */
public class GeoJsonParserImpl implements GeoJsonConverter {

/********why return String?*********/
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

            String startFeature = "{\"type\": \"FeatureCollection\""+","+
                    "\"features\":[" ;

            fileWriter.write(startFeature);
            fileWriter.flush();

            if(trafficLights!=null) {
                for (TrafficLight tl : trafficLights) {
                    String jsonData = ((MapSimulationTrafficLightImpl)tl).toStringTrafficLight();
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }
            fileWriter.flush();
            if(roads!=null) {
                for (Road rd : roads) {
                    String jsonData = ((MapSimulationRoadImpl)rd).toStringRoad();
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }
            fileWriter.flush();
            /*if(streets!=null) {
                for (Street st : streets) {
                    String jsonData = ((MapSimulationStreetImpl)st).toStringStreet();
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }*/
            //fileWriter.flush();
            if(crossRoads!=null) {
                for (Crossroad cr : crossRoads) {
                    String jsonData = ((MapSimulationCrossroadImpl)cr).toStringCrossRoad();
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }
            fileWriter.flush();
            if(centralLocations!=null) {
                for (CentralLocation cl : centralLocations) {
                    String jsonData = cl.toStringLocation();
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }

            //return the string that include all the data/

            String endFeature = "]}";

            fileWriter.write(endFeature);
            fileWriter.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return  file;
    }
}
//return file name- full path