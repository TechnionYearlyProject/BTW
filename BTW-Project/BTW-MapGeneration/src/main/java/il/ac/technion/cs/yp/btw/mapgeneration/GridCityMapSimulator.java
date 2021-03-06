package il.ac.technion.cs.yp.btw.mapgeneration;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationCrossroadImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationRoadImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationStreetImpl;

import java.util.Set;
import java.util.function.Function;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * map simulator of a grid shaped city
 */
public class GridCityMapSimulator extends AbstractMapSimulator {
    private static final int DEFAULT_NUM_OF_STREETS = 7;
    private static final int DEFAULT_NUM_OF_AVENUES = 7;
    private static final int DEFAULT_ROAD_LENGTH_IN_METERS = 600;
    private static final double DEFAULT_ROAD_LENGTH_IN_DEGREES = metersToDegrees(DEFAULT_ROAD_LENGTH_IN_METERS);
    private static final double DEFAULT_START_X_COORDINATE = 0;
    private static final double DEFAULT_START_Y_COORDINATE = 0;
    private int numOfStreets;
    private int numOfAvenues;
    private double streetLength;
    private double avenueLength;
    private double startXCoordinate;
    private double startYCoordinate;
    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * by using the constructor, the city simulation
     * is taking place
     * default city is 6x6 blocks
     * block length is 600 meters
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
    public GridCityMapSimulator() {
        super();
        this.numOfStreets = DEFAULT_NUM_OF_STREETS;
        this.numOfAvenues = DEFAULT_NUM_OF_AVENUES;
        this.streetLength = DEFAULT_ROAD_LENGTH_IN_DEGREES;
        this.avenueLength = DEFAULT_ROAD_LENGTH_IN_DEGREES;
        this.startXCoordinate = DEFAULT_START_X_COORDINATE;
        this.startYCoordinate = DEFAULT_START_Y_COORDINATE;
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
    @Override
    public Map build(){
        initializeAllSets();
        declareAllCrossRoads(numOfStreets, numOfAvenues, streetLength, avenueLength);
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
        addTrafficLights();
        addCentralLocations();
        return extractMap();
    }

    private void addCentralLocations() {

    }

    private void addRoadsByDirection(int numOfRoads, int numOfSections, double sectionLength, double roadsDistance
            , String roadBaseName, double startXCoordinate, double startYCoordinate
            , Function<Point,Point> roadAdvance
            ,Function<Point,Point> sectionAdvance) {
        Point startPoint = new PointImpl(startXCoordinate, startYCoordinate);
        Point innerStartPoint,innerEndPoint;
        Point endPoint = new PointImpl(startXCoordinate, startYCoordinate);
        Street currStreet;
        for (int roadNumber = 1; roadNumber <= numOfRoads; roadNumber++) {
            innerEndPoint = new PointImpl(endPoint);
            innerStartPoint = new PointImpl(startPoint);
            currStreet = new MapSimulationStreetImpl(roadNumber + " " + roadBaseName);
            this.streets.add(currStreet);
            for (int roadSectionNumber = 1; roadSectionNumber < numOfSections; roadSectionNumber++) {
                innerEndPoint = sectionAdvance.apply(innerEndPoint);
                String roadName = currStreet.getStreetName() + " section " + roadSectionNumber;
                addRoad(innerStartPoint, innerEndPoint, roadName,currStreet);
                innerStartPoint = sectionAdvance.apply(innerStartPoint);
            }
            startPoint = roadAdvance.apply(startPoint);
            endPoint = roadAdvance.apply(endPoint);
        }
    }

    private void addRoad(Point startPoint, Point endPoint, String roadName, Street street) {
        Crossroad startCrossroad = getCrossroadByPosition(startPoint);
        Crossroad endCrossroad = getCrossroadByPosition(endPoint);
        Road rd1 = new MapSimulationRoadImpl(roadName, calculateLengthBetween2Points(startPoint,endPoint),street,startCrossroad,endCrossroad);
        Road rd2 = new MapSimulationRoadImpl(roadName+"R", calculateLengthBetween2Points(startPoint,endPoint),street,endCrossroad,startCrossroad);
        this.roads.add(rd1);
        this.roads.add(rd2);
        street.addRoad(rd1);
        street.addRoad(rd2);
    }


    private static int calculateLengthBetween2Points(Point p1, Point p2) {
        return (int) distanceBetween2PointsOnEarth(p1.getCoordinateX(),p2.getCoordinateX()
                ,p1.getCoordinateY(),p2.getCoordinateY(),0,0);
    }

    private Crossroad getCrossroadByPosition(Point pos) {
        return this.crossRoads
                .stream()
                .filter(crossroad -> crossroad.getCoordinateX() == pos.getCoordinateX()
                && crossroad.getCoordinateY() == pos.getCoordinateY())
                .findAny().orElse(null);
    }

    private void declareAllCrossRoads(int numOfStreets, int numOfAvenues, double streetLength, double avenueLength) {
        double xCoordinate;
        double yCoordinate = this.startYCoordinate;
        Point p;
        for (int streetNumber = 1; streetNumber <= numOfStreets; streetNumber++) {
            xCoordinate = this.startXCoordinate;
            for (int streetSection = 1; streetSection <= numOfAvenues; streetSection++) {
                p = new PointImpl(xCoordinate, yCoordinate);
                Crossroad currCrossRoad = new MapSimulationCrossroadImpl(p);
                this.crossRoads.add(currCrossRoad);
                xCoordinate += streetLength;
            }
            yCoordinate += avenueLength;
        }
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
    private Point avenueRoadAdvance(Point p){
        return new PointImpl(p.getCoordinateX()+this.streetLength,p.getCoordinateY());
    }

    private Point avenueSectionAdvance(Point p){
        return new PointImpl(p.getCoordinateX(),p.getCoordinateY()+this.avenueLength);
    }

    private Point streetRoadAdvance(Point p){
        return new PointImpl(p.getCoordinateX(),p.getCoordinateY()+this.avenueLength);
    }

    private Point streetSectionAdvance(Point p){
        return new PointImpl(p.getCoordinateX()+this.streetLength,p.getCoordinateY());
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @param numOfStreets - new number of streets
     * sets the simulation's number of streets
     *                     to be numOfStreets
     * @return self
     */
    public GridCityMapSimulator setNumOfStreets(int numOfStreets){
        this.numOfStreets = numOfStreets;
        return this;
    }
    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @param numOfAvenues - new number of avenues
     * sets the simulation's number of avenues
     *                     to be numOfAvenues
     * @return self
     */
    public GridCityMapSimulator setNumOfAvenues(int numOfAvenues) {
        this.numOfAvenues = numOfAvenues;
        return this;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @param streetLength - new street length
     * sets the simulation's street length
     *                     to be streetLength
     * @return self
     */
    public GridCityMapSimulator setStreetLength(int streetLength) {
        this.streetLength = metersToDegrees(streetLength);
        return this;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @param avenueLength - new avenue length
     * sets the simulation's avenue length
     *                     to be avenueLength
     * @return self
     */
    public GridCityMapSimulator setAvenueLength(int avenueLength) {
        this.avenueLength = metersToDegrees(avenueLength);
        return this;
    }

    public GridCityMapSimulator setStartXCoordinate(double startXCoordinate) {
        this.startXCoordinate = startXCoordinate;
        return this;
    }

    public GridCityMapSimulator setStartYCoordinate(double startYCoordinate) {
        this.startYCoordinate = startYCoordinate;
        return this;
    }
}
