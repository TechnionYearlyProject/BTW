package il.ac.technion.cs.yp.btw.geojson;

import il.ac.technion.cs.yp.CentralLocation;
import il.ac.technion.cs.yp.Road;
import il.ac.technion.cs.yp.TrafficLight;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Street;
import il.ac.technion.cs.yp.btw.mapsimulation.MapSimulator;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Created by anat ana on 10/12/2017.
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

            if(trafficLights!=null) {
                for (TrafficLight tl : trafficLights) {
                    String jsonData = tl.toString();
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }

            if(roads!=null) {
                for (Road rd : roads) {
                    String jsonData = rd.toString();
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }

            if(streets!=null) {
                for (Street st : streets) {
                    String jsonData = st.toString();
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }

            if(crossRoads!=null) {
                for (Crossroad cr : crossRoads) {
                    String jsonData = cr.toString();
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }

            if(centralLocations!=null) {
                for (CentralLocation cl : centralLocations) {
                    String jsonData = cl.toString();
                    //write the data to json file
                    fileWriter.write(jsonData);
                }
            }

            //return the string that include all the data/

        }catch (IOException e) {
            e.printStackTrace();
        }
        return  file;
    }
}
//return file name- full path