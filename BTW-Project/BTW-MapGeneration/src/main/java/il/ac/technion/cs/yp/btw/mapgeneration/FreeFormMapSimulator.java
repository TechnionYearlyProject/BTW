package il.ac.technion.cs.yp.btw.mapgeneration;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.classes.Map;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationCrossroadImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationRoadImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationStreetImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.voronoi.Voronoi;
import il.ac.technion.cs.yp.btw.mapgeneration.voronoi.VoronoiEdge;
import il.ac.technion.cs.yp.btw.mapgeneration.voronoi.VoronoiPoint;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * Free form map simulation implementation
 * in order to execute this generator use the
 * build() function after giving the wanted parameters
 */
public class FreeFormMapSimulator extends AbstractMapSimulator{
    private static final int DEFAULT_NUM_OF_CITY_BLOCKS = 50;
    private static final int DEFAULT_CITY_RADIUS = 5000;
    private Point cityCenter;
    private int cityRadius;//in meters
    private int numOfCityBlocks;

    public FreeFormMapSimulator(){
        super();
        this.numOfCityBlocks = DEFAULT_NUM_OF_CITY_BLOCKS;
        this.cityRadius = DEFAULT_CITY_RADIUS;
        this.cityCenter = new PointImpl(0,0);
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * in order to get a built simulated map there
     * is a need to call this build() function
     * for all of the Sets which describe the map
     * to be updated with valid values
     * @return Map with all updated Sets after
     *         simulating a FreeFormMap with the
     *         current parameters
     */
    public Map build() {
        initializeAllSets();
        ArrayList<VoronoiPoint> sites = new ArrayList<VoronoiPoint>();
        Random rnd = new Random();
        for (int i = 0; i < this.numOfCityBlocks; i++) {
            double x = rnd.nextDouble()*metersToDegrees(this.cityRadius);
            double y = rnd.nextDouble()*metersToDegrees(this.cityRadius);
            sites.add(new VoronoiPoint(x, y));
        }
        Voronoi v = new Voronoi(sites);
        List<VoronoiEdge> edgeList = v.getEdgeList();
        edgeList = declareAllCrossRoads(edgeList);
        buildRoadsOnCrossRoads(edgeList);
        addTrafficLights();
        return extractMap();
    }

    private List<VoronoiEdge> declareAllCrossRoads(List<VoronoiEdge> edgeList) {
        Set<Point> locationSet = new HashSet<>();
        List<VoronoiEdge> modifiedList = new ArrayList<>();
        for (VoronoiEdge voronoiEdge : edgeList) {
            Point p1,p2;
            int numOfPointsOutOfRadius = getNumOfPointsOutOfRadius(voronoiEdge);
            if(numOfPointsOutOfRadius == 0) {
                modifiedList.add(voronoiEdge);
                p1 = new PointImpl(voronoiEdge.p1.x, voronoiEdge.p1.y);
                p2 = new PointImpl(voronoiEdge.p2.x, voronoiEdge.p2.y);
                locationSet.add(p1);
                locationSet.add(p2);
            }
        }
        this.crossRoads.addAll(
                locationSet
                        .stream()
                        .map(MapSimulationCrossroadImpl::new)
                        .collect(Collectors.toSet())
        );
        return modifiedList;
    }

    private int getNumOfPointsOutOfRadius(VoronoiEdge voronoiEdge) {
        int counter = 0;
        if(calculateLengthBetween2Points(this.cityCenter, new PointImpl(voronoiEdge.p1.x, voronoiEdge.p1.y)) > (this.cityRadius) + 100)
            counter++;
        if(calculateLengthBetween2Points(this.cityCenter, new PointImpl(voronoiEdge.p2.x, voronoiEdge.p2.y)) > (this.cityRadius) + 100)
            counter++;
        return counter;
    }

    private Crossroad getCrossroadByLocation(Point location) {
        return this.crossRoads
                .stream()
                .filter(crossroad -> crossroad.getCoordinateX()==(location.getCoordinateX())
                && crossroad.getCoordinateY()==(location.getCoordinateY()))
                .findAny()
                .orElse(null);
    }

    private void buildRoadsOnCrossRoads(List<VoronoiEdge> edgeList) {
        int roadNum = 1;
        for (VoronoiEdge voronoiEdge : edgeList) {
            Street myStreet = new MapSimulationStreetImpl(roadNum+" Street");
            this.streets.add(myStreet);
            Point p1 = (new PointImpl(voronoiEdge.p1.x, voronoiEdge.p1.y));
            Point p2 = (new PointImpl(voronoiEdge.p2.x, voronoiEdge.p2.y));
            Crossroad cr1 = getCrossroadByLocation(p1);
            Crossroad cr2 = getCrossroadByLocation(p2);
            Road rd1 = new MapSimulationRoadImpl(myStreet.getStreetName()
                    , calculateLengthBetween2Points(p1, p2)
                    , myStreet, cr1, cr2);
            Road rd2 = new MapSimulationRoadImpl(myStreet.getStreetName()+"R"
                    , calculateLengthBetween2Points(p1, p2)
                    , myStreet, cr2, cr1);
            this.roads.add(rd1);
            this.roads.add(rd2);
            myStreet.addRoad(rd1).addRoad(rd2);
            roadNum++;
        }
    }

    static int calculateLengthBetween2Points(il.ac.technion.cs.yp.btw.classes.Point p1, il.ac.technion.cs.yp.btw.classes.Point p2) {
        return (int) distanceBetween2PointsOnEarth(p1.getCoordinateX(), p2.getCoordinateX()
                , p1.getCoordinateY(), p2.getCoordinateY(), 0, 0);
    }


    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the simulated map's
     * traffic Lights
     */
    public Set<TrafficLight> getTrafficLights() {
        return this.trafficLights;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the simulated map's
     * roads
     */
    public Set<Road> getRoads() {
        return this.roads;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the simulated map's
     * cross roads
     */
    public Set<Crossroad> getCrossRoads() {
        return this.crossRoads;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the simulated map's
     * central locations
     */
    public Set<CentralLocation> getCentralLocations() {
        return this.centralLocations;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the simulated map's
     * central locations
     */
    @Override
    public Set<Street> getStreets() {
        return this.streets;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return current Simulation's city radius in meters
     */
    public int getCityRadius() {
        return cityRadius;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @param cityRadius - wanted city radius in meters
     * sets the given cityRadius to be the current
     *                   Simulation's city radius
     * @return self
     */
    public FreeFormMapSimulator setCityRadius(int cityRadius) {
        this.cityRadius = cityRadius;
        return this;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return this city center
     */
    public Point getCityCenter() {
        return cityCenter;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @param cityCenter - wanted new city center
     * sets this simulation's city center to be the
     *                   given parameter cityCenter
     * @return self
     */
    public FreeFormMapSimulator setCityCenter(Point cityCenter) {
        this.cityCenter = cityCenter;
        return this;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @param numOfCityBlocks - wanted maximum number of blocks
     * sets this simulation's maximum number of blocks to be
     *                        the given parameter
     * @return self
     */
    public FreeFormMapSimulator setNumOfCityBlocks(int numOfCityBlocks) {
        this.numOfCityBlocks = numOfCityBlocks;
        return this;
    }
}
