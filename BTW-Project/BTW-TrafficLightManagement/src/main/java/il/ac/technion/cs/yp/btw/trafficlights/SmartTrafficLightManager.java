package il.ac.technion.cs.yp.btw.trafficlights;

import il.ac.technion.cs.yp.btw.citysimulation.CityCrossroad;
import il.ac.technion.cs.yp.btw.citysimulation.CityRoad;
import il.ac.technion.cs.yp.btw.citysimulation.CityTrafficLight;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/*
* @Author: Sharon Hadar
* @Date: 11/05/2018
* */
public class SmartTrafficLightManager extends AbstractTrafficLightManager{

    /*
     * @Author: Sharon Hadar
     * @Date: 4/06/2018
     * */
    private class RoadManager{
        private Road road;
        private Set<TrafficLight> trafficLightsSet;
        private double roadOpenTime;
        private double timeCounter;
        private double compensationTime;
        private String name;
        private int roadSerialNumberInCrossroad;

        /*this field was created for testing, because all the traffic lights in the test are fake,
         this is caused because their implementation is private.*/
        private CityTrafficLight.TrafficLightState currentTrafficLightState;

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        RoadManager(Road road, Set<TrafficLight> trafficLightsSet, int roadSerialNumberInCrossroad){
            this.road = road;
            this.trafficLightsSet = trafficLightsSet;
            this.roadOpenTime = 0.0;
            this.timeCounter = 0.0;
            this.compensationTime = 0.0;
            this.currentTrafficLightState = CityTrafficLight.TrafficLightState.RED;
            this.name = road.getName();
            this.roadSerialNumberInCrossroad = roadSerialNumberInCrossroad;
        }

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        private void AggregateCompensationTime(){
            double periodCompensationTime = SmartTrafficLightManager.this.averageOpenTimeForRoad - this.roadOpenTime;
            periodCompensationTime = (periodCompensationTime > 0.0) ? periodCompensationTime : 0.0;
            compensationTime = periodCompensationTime;
        }

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        boolean isClose(){
            return (this.timeCounter <= 0.0);
        }

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        void tick(){
            this.timeCounter--;
        }

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        Road getRoad(){
            return this.road;
        }

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        private void turnAllTrafficLights(CityTrafficLight.TrafficLightState state){
            this.currentTrafficLightState = state;
            this.trafficLightsSet.forEach(trafficLight -> ((CityTrafficLight)trafficLight).setTrafficLightState(state));
        }

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        void turnAllTrafficLightsGreen(){
            turnAllTrafficLights(CityTrafficLight.TrafficLightState.GREEN);
        }

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        void turnAllTrafficLightsRed(){
            turnAllTrafficLights(CityTrafficLight.TrafficLightState.RED);
        }

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * prepare to a new period.
         * return the time the road will be open in the new period.
         * */
        double newPeriod(int roadSerialNumberInCrossroad,  double crossroadOverload, int numOfRoadsInCrossroad, int currentPeriod, double averagePeriodTime){

            Double timeForRoad = crossroadOverload == 0.0 ? 0.0 : (this.road.getOverload() / crossroadOverload) * averagePeriodTime;
            timeForRoad = (timeForRoad < SmartTrafficLightManager.this.minimumOpenTime) ? SmartTrafficLightManager.this.minimumOpenTime : timeForRoad;
            double maxTimeForRoad = averagePeriodTime - (SmartTrafficLightManager.this.minimumOpenTime * (numOfRoadsInCrossroad-1));
            timeForRoad = (timeForRoad > maxTimeForRoad) ? maxTimeForRoad : timeForRoad;

            // enter compensation period
            if(currentPeriod == SmartTrafficLightManager.this.periodsNum-1-roadSerialNumberInCrossroad){
                Double factoredAggregatedCompensationTime = this.compensationTime * SmartTrafficLightManager.this.compensationFactor;
                timeForRoad += factoredAggregatedCompensationTime;
                this.compensationTime = 0.0;
            }else {//not a compensation period
                AggregateCompensationTime();
            }
            this.roadOpenTime = timeForRoad;
            this.timeCounter = this.roadOpenTime;
            return this.roadOpenTime;
        }

        public String getTrafficLightState(){
            return "state: " + this.currentTrafficLightState.toString() + " time for Current Period: " + this.roadOpenTime
                    + " time counter for road: " + this.timeCounter;
        }

        public String getRoadName(){
            return this.name;
        }

    }

    /*
     * @Author: Sharon Hadar
     * @Date: 4/06/2018
     * */
    private class CrossroadManager{
        private Crossroad crossroad;
        private int currentPeriod; // current period number. turn to zero after we pass periodsNum.
        private double currentPeriodTime;// time of current period in seconds
        private double currentPeriodTimeCounter; //time left for the current period in seconds
        private List<RoadManager> roadManagersList;
        private Iterator<RoadManager> roadManagersIterator;
        private RoadManager currentRoadManager;
        private double averagePeriodTime; //time for a period with no compensations in seconds
        private String name;

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        CrossroadManager(Crossroad crossroad, List<RoadManager> roadManagersList){
            this.crossroad = crossroad;
            this.currentPeriod = 0;
            this.currentPeriodTime = 0.0;
            this.currentPeriodTimeCounter =0.0;
            this.roadManagersList = roadManagersList;
            this.roadManagersIterator = this.roadManagersList.iterator();
            this.averagePeriodTime = this.roadManagersList.size() * SmartTrafficLightManager.this.averageOpenTimeForRoad;
            this.name = crossroad.getName();
            newPeriod();
        }

        Crossroad getCrossroad(){
            return this.crossroad;
        }

        private void createNewRoadManagersIterator(){
            Collections.sort(this.roadManagersList,
                    new Comparator<RoadManager>(){
                        //sort is from the biggest overload to the smallest
                        @Override
                        public int compare(RoadManager roadManager1, RoadManager roadManager2){
                            double overload1 = roadManager1.getRoad().getOverload();
                            double overload2 = roadManager2.getRoad().getOverload();

                            if(overload1 - overload2 > 0){
                                return -1;
                            }else{
                                if(overload1 - overload2 < 0){
                                    return 1;
                                }
                                else{
                                    return 0;
                                }
                            }
                        }
                    });
            this.roadManagersIterator = this.roadManagersList.iterator();
            this.currentRoadManager = this.roadManagersIterator.next();
        }

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        private void iterateNextRoad(){
            if (this.roadManagersIterator.hasNext()) {
                this.currentRoadManager = this.roadManagersIterator.next();
            } else {
                createNewRoadManagersIterator();
            }
        }

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        private boolean isPeriodOver(){
            return (this.currentPeriodTimeCounter <= 0);
        }

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        private void newPeriod(){
            double crossroadOverload = this.crossroad.getOverload();
            int numOfRoadsInCrossroad = this.roadManagersList.size();
            int roadSerialNumberInCrossroad = 0;
            this.currentPeriodTime = this.averagePeriodTime;
            Iterator<RoadManager> roadManagersListnewPeriodIterator = this.roadManagersList.iterator();
            this.currentPeriodTime = 0;
            while(roadManagersListnewPeriodIterator.hasNext()){
                RoadManager roadManager= roadManagersListnewPeriodIterator.next();
                this.currentPeriodTime += roadManager.newPeriod(roadSerialNumberInCrossroad,  crossroadOverload, numOfRoadsInCrossroad, this.currentPeriod, this.averagePeriodTime);
                roadSerialNumberInCrossroad++;
            }
            this.currentPeriodTimeCounter = this.currentPeriodTime;
            this.currentPeriod = (this.currentPeriod + 1) % SmartTrafficLightManager.this.periodsNum;
            createNewRoadManagersIterator();
            this.currentRoadManager.turnAllTrafficLightsGreen();
        }

        /*
         * @Author: Sharon Hadar
         * @Date: 4/06/2018
         * */
        void tick(){
            if(isPeriodOver()){
                this.currentRoadManager.turnAllTrafficLightsRed();
                newPeriod();
            }
            this.currentRoadManager.tick();
            if(this.currentRoadManager.isClose()){
                this.currentRoadManager.turnAllTrafficLightsRed();
                iterateNextRoad();
                this.currentRoadManager.turnAllTrafficLightsGreen();
            }
            this.currentPeriodTimeCounter--;
        }

        String getCurrentState(){
            List<String> stateAccumulator = new ArrayList<>();
            Iterator<RoadManager> roadManagerIterator = this.roadManagersList.iterator();
            while (roadManagerIterator.hasNext()){
                RoadManager roadManager = roadManagerIterator.next();
                stateAccumulator.add("\t\t" + roadManager.getRoad().getName() + ": " + roadManager.getTrafficLightState() + "\n");
            }
            String statesString = "";
            stateAccumulator = stateAccumulator.stream().sorted().collect(Collectors.toList());
            Iterator<String> stateAccumulatorIterator = stateAccumulator.iterator();
            while(stateAccumulatorIterator.hasNext()){
                String roadStateString = stateAccumulatorIterator.next();
                statesString += roadStateString;
            }
            return " time for period: " + this.currentPeriodTime
                    + " time counter: " + this.currentPeriodTimeCounter + "\n" + statesString;

        }

        String getCrossroadName(){
            return this.name;
        }
    }



    private int periodsNum; // number of periods in a compensation cycle.
    private double averageOpenTimeForRoad; // in seconds
    private double compensationFactor;// every compensation factor we will mutiply the aggregated compensation time in this factor

    private Set<CrossroadManager> crossroadsManagers;// this will be used to manage each crossroad individually

    /*
    * @Author: Sharon Hadar
    * @Date: 11/05/2018
    * */
    SmartTrafficLightManager(){
        super();
        this.periodsNum = 8;
        this.averageOpenTimeForRoad = 60.0;
        this.compensationFactor = 0.1;

        this.crossroadsManagers = new HashSet<>();
        //this.crossRoadsToRoadsMap = new HashMap<>();
        //this.roadToTrafficlightsMap = new HashMap<>();
    }

    /*
    * @Author: Sharon Hadar
    * @Date: 2/06/2018
    * here the crossroads are the real crossroads in the map
    * */
    @Override
    public SmartTrafficLightManager insertCrossroads(Set<CityCrossroad> crossroads){

        //for every cross road attach all its roads and initialize their time to 0.0

        //get all roads in map
        Set<Road> roadsSet = new HashSet<>();
        crossroads.stream().map(crossroad -> (crossroad.getTrafficLights().stream().map(trafficlight -> roadsSet.add(trafficlight.getSourceRoad())).collect(Collectors.toSet()))).collect(Collectors.toSet());

        //for every road attach all its trafficlights
        Map<Road, Set<TrafficLight>> roadToTrafficlightsMap = new HashMap<>();
        roadsSet.stream()
                .map(road -> roadToTrafficlightsMap.put(road, road.getDestinationCrossroad().getTrafficLightsFromRoad(road))).collect(Collectors.toSet());


        //for every crossroad attach all roads that it ends
        Map<Crossroad, List<Road>> crossRoadsToRoadsMap = new HashMap<>();
        crossroads.stream()
                .map(crossroad ->
                        crossRoadsToRoadsMap.put(crossroad,
                                crossroad.getTrafficLights().stream()
                                        .map(trafficLight -> trafficLight.getSourceRoad())
                                        .collect(Collectors.toSet())
                                        .stream().collect(Collectors.toList()))).collect(Collectors.toSet());

        //create a set of CrossroadManagers
        this.crossroadsManagers =   crossRoadsToRoadsMap.entrySet()
                                    .stream()
                                    .map(entry ->   {
                                                Crossroad crossroad = entry.getKey();
                                                List<Road> roadsList = entry.getValue();
                                                AtomicInteger roadSerialNumber = new AtomicInteger(0);
                                                List<RoadManager> roadManagers =
                                                        roadsList.stream()
                                                                 .map(road ->new RoadManager(road, roadToTrafficlightsMap.get(road), roadSerialNumber.getAndIncrement()))
                                                                 .collect(Collectors.toList());

                                                return new CrossroadManager(crossroad, roadManagers);
                                                })
                                    .collect(Collectors.toSet());
        return this;
    }


    /*
    * @Author: Sharon Hadar
    * @Date: 11/05/2018
    *
    * - get all the real crossroads
    * - for each crossroad calculate its overload
    * - divide the period time among  the trafficlights in proportion to their overload
    * */
    public SmartTrafficLightManager tick(){
        this.crossroadsManagers.stream().forEach(CrossroadManager::tick);
        return this;
    }

    String getTrafficLightStates(){

        List<String> state = new ArrayList<>();
        Iterator<CrossroadManager> crossroadsManagersIterator = this.crossroadsManagers.iterator();
        while(crossroadsManagersIterator.hasNext()){
            CrossroadManager crossroadManager = crossroadsManagersIterator.next();
            String crossroadState = "\n\tcrossroad " + crossroadManager.getCrossroadName() + " state is:\n";
            crossroadState += crossroadManager.getCurrentState();
            state.add(crossroadState);
        }
        String AllCrossroadsState = "the TrafficLights states are:\n";
        Iterator<String> stateIterator = state.stream().sorted().collect(Collectors.toList()).iterator();
        while(stateIterator.hasNext()){
            AllCrossroadsState += stateIterator.next();
        }
        return AllCrossroadsState;
    }

}
