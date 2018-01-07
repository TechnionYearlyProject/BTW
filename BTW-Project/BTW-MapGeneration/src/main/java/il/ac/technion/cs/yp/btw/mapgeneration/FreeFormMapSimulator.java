package il.ac.technion.cs.yp.btw.mapgeneration;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationCrossroadImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationRoadImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationStreetImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.voronoi.Voronoi;
import il.ac.technion.cs.yp.btw.mapgeneration.voronoi.VoronoiEdge;

import java.util.*;
import java.util.stream.Collectors;

public class FreeFormMapSimulator implements MapSimulator {
    private static final int NUM_OF_CITY_BLOCKS = 150;
    private Set<TrafficLight> trafficLights;
    private Set<Road> roads;
    private Set<Crossroad> crossRoads;
    private Set<CentralLocation> centralLocations;
    private Set<Street> streets;
    private il.ac.technion.cs.yp.btw.classes.Point cityCenter;
    private int cityRadius;

    public FreeFormMapSimulator(){
        this.roads = new HashSet<Road>();
        this.crossRoads = new HashSet<Crossroad>();
        this.trafficLights = new HashSet<TrafficLight>();
        this.centralLocations = new HashSet<CentralLocation>();
        this.streets = new HashSet<Street>();
    }
    public void build() {
        int N = NUM_OF_CITY_BLOCKS;
        ArrayList<il.ac.technion.cs.yp.btw.mapgeneration.voronoi.Point> sites = new ArrayList<il.ac.technion.cs.yp.btw.mapgeneration.voronoi.Point>();
        Random rnd = new Random();
        for (int i = 0; i < N; i++) {
            double x = rnd.nextDouble() + rnd.nextInt(5);
            double y = rnd.nextDouble() + rnd.nextInt(5);
            sites.add(new il.ac.technion.cs.yp.btw.mapgeneration.voronoi.Point(x, y));
        }
        Voronoi v = new Voronoi(sites);
        List<VoronoiEdge> edgeList = v.getEdgeList();
        declareAllCrossRoads(edgeList);
        buildRoadsOnCrossRoads(edgeList);
    }

    private void declareAllCrossRoads(List<VoronoiEdge> edgeList) {
        Set<il.ac.technion.cs.yp.btw.classes.Point> locationSet = new HashSet<>();
        for (VoronoiEdge voronoiEdge : edgeList) {
            locationSet.add(new PointImpl(voronoiEdge.p1.x, voronoiEdge.p1.y));
            locationSet.add(new PointImpl(voronoiEdge.p2.x, voronoiEdge.p2.y));
        }
        this.crossRoads.addAll(
                locationSet
                        .stream()
                        .map(MapSimulationCrossroadImpl::new)
                        .collect(Collectors.toSet())
        );
        buildRoadsOnCrossRoads(edgeList);
    }

    private Crossroad getCrossroadByLocation(il.ac.technion.cs.yp.btw.classes.Point location) {
        return this.crossRoads
                .stream()
                .filter(crossroad -> crossroad.getCoordinateX()==(location.getCoordinateX())
                && crossroad.getCoordinateY()==(location.getCoordinateY()))
                .findAny()
                .orElse(null);
    }

    private void buildRoadsOnCrossRoads(List<VoronoiEdge> edgeList) {
        Street myStreet = new MapSimulationStreetImpl("Krembo Street");
        this.streets.add(myStreet);
        int roadNum = 1;
        for (VoronoiEdge voronoiEdge : edgeList) {
            il.ac.technion.cs.yp.btw.classes.Point p1 = (new PointImpl(voronoiEdge.p1.x, voronoiEdge.p1.y));
            il.ac.technion.cs.yp.btw.classes.Point p2 = (new PointImpl(voronoiEdge.p2.x, voronoiEdge.p2.y));
            Crossroad cr1 = getCrossroadByLocation(p1);
            Crossroad cr2 = getCrossroadByLocation(p2);
            Road rd1 = new MapSimulationRoadImpl(myStreet.getStreetName() +" " +roadNum
                    , calculateLengthBetween2Points(p1, p2)
                    , myStreet, cr1, cr2);
            Road rd2 = new MapSimulationRoadImpl(myStreet.getStreetName() +" " +roadNum+"'"
                    , calculateLengthBetween2Points(p1, p2)
                    , myStreet, cr2, cr1);
            this.roads.add(rd1);
            this.roads.add(rd2);
            myStreet.addRoad(rd1).addRoad(rd2);
            roadNum++;
        }
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
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


    private static int calculateLengthBetween2Points(il.ac.technion.cs.yp.btw.classes.Point p1, il.ac.technion.cs.yp.btw.classes.Point p2) {
        return (int) distanceBetween2PointsOnEarth(p1.getCoordinateX(), p2.getCoordinateX()
                , p1.getCoordinateY(), p2.getCoordinateY(), 0, 0);
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
}
