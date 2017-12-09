package il.ac.technion.cs.yp.btw.geojson;

import il.ac.technion.cs.yp.btw.mapsimulation.MapSimulator;

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
    String buildGeoJsonFromSimulation(MapSimulator simulator);
}
