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
    public Map build() {
        ArrayList<VoronoiPoint> sites = new ArrayList<VoronoiPoint>();
        Random rnd = new Random();
        for (int i = 0; i < this.numOfCityBlocks; i++) {
            double x = rnd.nextDouble()*metersToDegrees(this.cityRadius);
            double y = rnd.nextDouble()*metersToDegrees(this.cityRadius);
            sites.add(new VoronoiPoint(x, y));
        }
        Voronoi v = new Voronoi(sites);
        List<VoronoiEdge> edgeList = v.getEdgeList();
        declareAllCrossRoads(edgeList);
        buildRoadsOnCrossRoads(edgeList);
        addTrafficLights();
        return extractMap();
    }

    private static List<VoronoiPoint> getCircleLineIntersectionPoint(VoronoiPoint pointA,
                                                             VoronoiPoint pointB, Point center, double radius) {
        double baX = pointB.x - pointA.x;
        double baY = pointB.y - pointA.y;
        double caX = center.getCoordinateX() - pointA.x;
        double caY = center.getCoordinateY() - pointA.y;

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }
        // if disc == 0 ... dealt with later
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;

        VoronoiPoint p1 = new VoronoiPoint(pointA.x - baX * abScalingFactor1, pointA.y
                - baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        VoronoiPoint p2 = new VoronoiPoint(pointA.x - baX * abScalingFactor2, pointA.y
                - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }

    private void declareAllCrossRoads(List<VoronoiEdge> edgeList) {
        Set<Point> locationSet = new HashSet<>();
        for (VoronoiEdge voronoiEdge : edgeList) {
            Point p1,p2;
            List<VoronoiPoint> intersectionPoints =
                    getCircleLineIntersectionPoint(voronoiEdge.p1, voronoiEdge.p2
                            , this.cityCenter, metersToDegrees(this.cityRadius));
            if (intersectionPoints.size() == 2) {
                voronoiEdge.p1.x = intersectionPoints.get(0).x;
                voronoiEdge.p1.y = intersectionPoints.get(0).y;
                voronoiEdge.p2.x = intersectionPoints.get(1).x;
                voronoiEdge.p2.y = intersectionPoints.get(1).y;
            } else if (intersectionPoints.size() == 1) {
                if (calculateLengthBetween2Points(this.cityCenter, new PointImpl(voronoiEdge.p1.x, voronoiEdge.p1.y)) > metersToDegrees(this.cityRadius)) {
                    voronoiEdge.p1.x = intersectionPoints.get(0).x;
                    voronoiEdge.p1.y = intersectionPoints.get(0).y;
                } else {
                    voronoiEdge.p2.x = intersectionPoints.get(0).x;
                    voronoiEdge.p2.y = intersectionPoints.get(0).y;
                }
            }
            p1 = new PointImpl(voronoiEdge.p1.x, voronoiEdge.p1.y);
            p2 = new PointImpl(voronoiEdge.p2.x, voronoiEdge.p2.y);
            locationSet.add(p1);
            locationSet.add(p2);
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

    public int getCityRadius() {
        return cityRadius;
    }

    public void setCityRadius(int cityRadius) {
        this.cityRadius = cityRadius;
    }

    public Point getCityCenter() {
        return cityCenter;
    }

    public void setCityCenter(Point cityCenter) {
        this.cityCenter = cityCenter;
    }
}
