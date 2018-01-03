package il.ac.technion.cs.yp.btw.mapsimulation;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.mapsimulation.objects.MapSimulationCrossroadImpl;
import il.ac.technion.cs.yp.btw.mapsimulation.objects.MapSimulationRoadImpl;
import il.ac.technion.cs.yp.btw.mapsimulation.objects.MapSimulationStreetImpl;
import il.ac.technion.cs.yp.btw.mapsimulation.objects.MapSimulationTrafficLightImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * map simulator of a grid shaped city
 */
public class GridCityMapSimulator implements MapSimulator {
    private static final int DEFAULT_NUM_OF_STREETS = 7;
    private static final int DEFAULT_NUM_OF_AVENUES = 7;
    private static final double DEFAULT_ROAD_LENGTH = 1;
    private static final double DEFAULT_START_X_COORDINATE = 0;
    private static final double DEFAULT_START_Y_COORDINATE = 0;
    private int numOfStreets;
    private int numOfAvenues;
    private double streetLength;
    private double avenueLength;
    private double startXCoordinate;
    private double startYCoordinate;
    private Set<TrafficLight> trafficLights;
    private Set<Road> roads;
    private Set<Crossroad> crossRoads;
    private Set<CentralLocation> centralLocations;
    private Set<Street> streets;
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
    public GridCityMapSimulator() {
        initializeAllSets();
        this.numOfStreets = DEFAULT_NUM_OF_STREETS;
        this.numOfAvenues = DEFAULT_NUM_OF_AVENUES;
        this.streetLength = DEFAULT_ROAD_LENGTH;
        this.avenueLength = DEFAULT_ROAD_LENGTH;
        this.startXCoordinate = DEFAULT_START_X_COORDINATE;
        this.startYCoordinate = DEFAULT_START_Y_COORDINATE;
    }

    private void initializeAllSets() {
        this.roads = new HashSet<>();
        this.crossRoads = new HashSet<>();
        this.trafficLights = new HashSet<>();
        this.centralLocations = new HashSet<>();
        this.streets = new HashSet<>();
    }

    public void build(){
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
    }

    private void addCentralLocations() {

    }

    private void addTrafficLights() {
        for(Crossroad cr : getCrossRoads()){
            Set<Road> roadsEndsInCurrCrossroad =
                    getRoads().stream()
                            .filter(road -> road.getDestinationCrossroad().equals(cr))
                            .collect(Collectors.toSet());
            Set<Road> roadsStartInCurrCrossroad =
                    getRoads().stream()
                            .filter(road -> road.getSourceCrossroad().equals(cr))
                            .collect(Collectors.toSet());
            for (Road sourceRoad : roadsEndsInCurrCrossroad){
                for (Road destRoad : roadsStartInCurrCrossroad){
                    TrafficLight tl = new MapSimulationTrafficLightImpl(cr, sourceRoad, destRoad);
                    cr.addTrafficLight(tl);
                    this.trafficLights.add(tl);
                }
            }
        }
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
        Road rd2 = new MapSimulationRoadImpl(roadName+"'", calculateLengthBetween2Points(startPoint,endPoint),street,endCrossroad,startCrossroad);
        this.roads.add(rd1);
        this.roads.add(rd2);
        street.addRoad(rd1);
        street.addRoad(rd2);
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @return Distance in Meters
     */
    private static double distanceBetween2PointsOnEarth(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }


    private int calculateLengthBetween2Points(Point p1, Point p2) {
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
    public GridCityMapSimulator setNumOfStreets(int numOfStreets){
        this.numOfStreets = numOfStreets;
        return this;
    }
    public GridCityMapSimulator setNumOfAvenues(int numOfAvenues) {
        this.numOfAvenues = numOfAvenues;
        return this;
    }

    public GridCityMapSimulator setStreetLength(double streetLength) {
        this.streetLength = streetLength;
        return this;
    }

    public GridCityMapSimulator setAvenueLength(double avenueLength) {
        this.avenueLength = avenueLength;
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
