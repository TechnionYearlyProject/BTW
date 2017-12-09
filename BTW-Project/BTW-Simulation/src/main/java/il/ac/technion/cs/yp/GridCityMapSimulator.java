package il.ac.technion.cs.yp;

import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Street;
import il.ac.technion.cs.yp.btw.mapsimulation.MapSimulator;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * map simulator of a grid shaped city
 */
public class GridCityMapSimulator implements MapSimulator {
    private static final int DEFAULT_NUM_OF_STREETS = 6;
    private static final int DEFAULT_NUM_OF_AVENUES = 6;
    private static final int DEFAULT_ROAD_LENGTH = 1;
    private static final int DEFAULT_START_X_COORDINATE = 0;
    private static final int DEFAULT_START_Y_COORDINATE = 0;
    private Set<TrafficLight> trafficLights;
    private Set<Road> roads;
    private Set<Crossroad> crossRoads;
    private Set<CentralLocation> centralLocations;
    Point avenueRoadAdvance(Point p){
        return new Point(p.getCoordinateX(),p.getCoordinateY()+1);
    }

    Point avenueSectionAdvance(Point p){
        return new Point(p.getCoordinateX()+1,p.getCoordinateY());
    }

    Point streetRoadAdvance(Point p){
        return new Point(p.getCoordinateX()+1,p.getCoordinateY());
    }

    Point streetSectionAdvance(Point p){
        return new Point(p.getCoordinateX(),p.getCoordinateY()+1);
    }
    /**
     * by using the constructor, the city simulation
     * is taking place
     * default city is 6x6 blocks
     * block length is 1 mile
     * the blocks are numbered:
     * | 31 | 32 | 33 | 34 | 35 | 36 |
     * | 30 | 29 | 28 | 27 | 26 | 25 |
     * | 19 | 20 | 21 | 22 | 23 | 24 |
     * | 18 | 17 | 16 | 15 | 14 | 13 |
     * | 7  | 8  | 9  | 10 | 11 | 12 |
     * | 1  | 2  | 3  | 4  | 5  | 6  |
     * block 16 is reserved for an
     * education central location
     * road names are x street part y or x avenue part y
     * street - a road going horizontally
     * avenue - a road going vertically
     * all roads are two-way
     */
    GridCityMapSimulator() {
        this.roads = new HashSet<Road>();
        this.crossRoads = new HashSet<Crossroad>();
        int numOfStreets = DEFAULT_NUM_OF_STREETS;
        int numOfAvenues = DEFAULT_NUM_OF_AVENUES;
        int streetLength = DEFAULT_ROAD_LENGTH;
        int avenueLength = DEFAULT_ROAD_LENGTH;
        declareAllCrossRoads(numOfStreets, numOfAvenues, streetLength, avenueLength);
        int startXCoordinate = DEFAULT_START_X_COORDINATE;
        int startYCoordinate = DEFAULT_START_Y_COORDINATE;
        String roadBaseName;
        roadBaseName = "street";
        addRoadsByDirection(numOfStreets, numOfAvenues
                , streetLength, avenueLength
                , roadBaseName, startXCoordinate, startYCoordinate
        ,this::streetRoadAdvance,this::streetSectionAdvance);
        roadBaseName = "avenue";
        addRoadsByDirection(numOfAvenues, numOfStreets
                , avenueLength,streetLength
                , roadBaseName, startXCoordinate, startYCoordinate
                ,this::avenueRoadAdvance ,this::avenueSectionAdvance);
    }



    private void addRoadsByDirection(int numOfRoads, int numOfSections, int sectionLength, int roadsDistance
            , String roadBaseName, int startXCoordinate, int startYCoordinate
            , Function<Point,Point> roadAdvance
            ,Function<Point,Point> sectionAdvance) {
        Point startPoint = new Point(startXCoordinate, startYCoordinate);
        Point endPoint = new Point(startXCoordinate, startYCoordinate);
        for (int roadNumber = 1; roadNumber <= numOfRoads; roadNumber++) {
            endPoint = roadAdvance.apply(endPoint);
            for (int roadSectionNumber = 1; roadSectionNumber <= numOfSections; roadSectionNumber++) {
                endPoint = sectionAdvance.apply(endPoint);
                String roadName = roadNumber + " " + roadBaseName + " part " + roadSectionNumber;
                addRoad(startPoint, endPoint, roadName);
                startPoint = sectionAdvance.apply(startPoint);
            }
            startPoint = roadAdvance.apply(startPoint);
        }
    }

    private void addRoad(Point startPoint, Point endPoint, String roadName) {

    }

    private void declareAllCrossRoads(int numOfStreets, int numOfAvenues, int streetLength, int avenueLength) {
        int xCoordinate = 0;
        int yCoordinate = 0;
        for (int streetNumber = 1; streetNumber <= numOfStreets; streetNumber++) {
            for (int streetSection = 1; streetSection <= numOfAvenues; streetSection++) {
                Crossroad currCrossRoad = new Crossroad(null, null);//TODO adjust to new objects
                this.crossRoads.add(currCrossRoad);
                xCoordinate += streetLength;
            }
            yCoordinate += avenueLength;
        }
    }

    /**
     * @return Set of the simulated map's
     * traffic Lights
     */
    public Set<TrafficLight> getTrafficLights() {
        return this.trafficLights;
    }

    /**
     * @return Set of the simulated map's
     * roads
     */
    public Set<Road> getRoads() {
        return this.roads;
    }

    /**
     * @return Set of the simulated map's
     * cross roads
     */
    public Set<Crossroad> getCrossRoads() {
        return this.crossRoads;
    }

    /**
     * @return Set of the simulated map's
     * central locations
     */
    public Set<CentralLocation> getCentralLocations() {
        return this.centralLocations;
    }

    /**
     * @return Set of the simulated map's
     * central locations
     */
    @Override
    public Set<Street> getStreets() {
        return null;
    }
}
