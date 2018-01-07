package il.ac.technion.cs.yp.btw.geojson;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;

import java.io.File;

/**
 * GeoJsonConverter layer interface for the BTW project
 */
public interface GeoJsonConverter {

    /**
     * @param simulator - map simulation to be converted
     *                    to geoJson format
     * @return filename of the saved geoJson generated
     *         for the given simulation
     */
    File buildGeoJsonFromSimulation(MapSimulator simulator);

    String toStringLocation(CentralLocation centralLocation);

    public String toStringRoadFull(Road road);

    public String toStringRoad(Road road);

    public String toStringTrafficLightFull(TrafficLight trafficLight);

    public String toStringStreetFull(Street street);

    public String toStringStreet(Street street);

    public String toStringTrafficLight(TrafficLight trafficLight);

    public String toStringCrossRoad(Crossroad crossroad);
}
