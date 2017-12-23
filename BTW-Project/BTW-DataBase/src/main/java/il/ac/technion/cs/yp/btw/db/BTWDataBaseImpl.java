package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.Street;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;

import java.util.Set;

public class BTWDataBaseImpl implements BTWDataBase{

    private String mapName;
    private CentralLocationsDataBase centralLocationsDataBase;
    private RoadsDataBase roadsDataBase;
    private TrafficLightsDataBase trafficLightsDataBase;
    private StreetsDataBase streetsDataBase;

    public BTWDataBaseImpl(String mapName){
        this.mapName = mapName;
        this.centralLocationsDataBase = new CentralLocationsDataBase(mapName);
        this.roadsDataBase = new RoadsDataBase(mapName);
        this.trafficLightsDataBase = new TrafficLightsDataBase(mapName);
        this.streetsDataBase = new StreetsDataBase(mapName);
    }

    /**
     * @return Set of all TrafficLights in the system
     */
    @Override
    public Set<TrafficLight> getAllTrafficLights(){
        return trafficLightsDataBase.getAllTrafficLights();
    }

    /**
     * @param streetName - name of a street
     * @return the Street with the given name
     * TODO thinks of an error if doesn't exist, maybe throw new IllegalArgument()
     */
    @Override
    public Street getStreetByName(String streetName){
        return streetsDataBase.getStreet(streetName);
    }

    /**
     * @param locationName - the name of the central location
     *                     we are looking for
     * @return CentralLocation according to the given name
     */
    @Override
    public CentralLocation getCentralLocationByName(String locationName){
        return centralLocationsDataBase.getCentralLocation(locationName);
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