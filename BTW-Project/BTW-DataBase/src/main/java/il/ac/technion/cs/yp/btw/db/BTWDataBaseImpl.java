package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.Street;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;

import java.util.Set;

public class BTWDataBaseImpl implements BTWDataBase{

    private String mapName;

    public BTWDataBaseImpl(String mapName){
        this.mapName = mapName;
    }

    /**
     * @return Set of all TrafficLights in the system
     */
    @Override
    public Set<TrafficLight> getAllTrafficLights(){
        return TrafficLightsDataBase.getAllTrafficLights(mapName);
    }

    /**
     * @param streetName - name of a street
     * @return the Street with the given name
     * TODO thinks of an error if doesn't exist, maybe throw new IllegalArgument()
     */
    @Override
    public Street getStreetByName(String streetName){
        return StreetsDataBase.getStreet(streetName, mapName);
    }

    /**
     * @param locationName - the name of the central location
     *                     we are looking for
     * @return CentralLocation according to the given name
     */
    @Override
    public CentralLocation getCentralLocationByName(String locationName){
        return CentralLocationsDataBase.getCentralLocation(locationName, mapName);
    }

    /**
     * returns all Roads which are next to the
     * given CenralLocation
     * @param centralLocation - the location we
     *                          are looking for
     * @return Set of Roads, which have the location
     *         on them
     */
    @Override
    public Set<Road> getAllRoadsNextToCentralLocation(CentralLocation centralLocation){
        //return centralLocationsDataBase.getAllCentralLocations();
        return null;
    }

    /**
     * for future use, implementation not yet decided
     */
    @Override
    public void updateWeight(){

    }

}