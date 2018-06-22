package il.ac.technion.cs.yp.btw.trafficlights;

import il.ac.technion.cs.yp.btw.citysimulation.CityCrossroad;
import il.ac.technion.cs.yp.btw.citysimulation.CityRoad;
import il.ac.technion.cs.yp.btw.citysimulation.CityTrafficLight;
import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class SmartTrafficLightManagerTest {

    private String allCharacters = "(?s).*[\t\n\r]*.*"; // a regex mathes all the characters, including white spaces and new lines
    private int roadLength = 10;

    private CityRoad horizontalLeftRoad02_12;
    private CityRoad horizontalLeftRoad12_02;
    private CityRoad horizontalLeftRoad01_11;
    private CityRoad horizontalLeftRoad11_01;
    private CityRoad horizontalLeftRoad00_10;
    private CityRoad horizontalLeftRoad10_00;

    private CityRoad horizontalRightRoad12_22;
    private CityRoad horizontalRightRoad22_12;
    private CityRoad horizontalRightRoad11_21;
    private CityRoad horizontalRightRoad21_11;
    private CityRoad horizontalRightRoad10_20;
    private CityRoad horizontalRightRoad20_10;

    private CityRoad verticalUpRoad02_01;
    private CityRoad verticalUpRoad01_02;
    private CityRoad verticalUpRoad12_11;
    private CityRoad verticalUpRoad11_12;
    private CityRoad verticalUpRoad22_21;
    private CityRoad verticalUpRoad21_22;

    private CityRoad verticalDownRoad01_00;
    private CityRoad verticalDownRoad00_01;
    private CityRoad verticalDownRoad10_11;
    private CityRoad verticalDownRoad11_10;
    private CityRoad verticalDownRoad21_20;
    private CityRoad verticalDownRoad20_21;






    private CityTrafficLight trafficLight00FromUpToRight;

    private CityTrafficLight trafficLight00FromRightToUp;


    private CityTrafficLight trafficLight10FromUpToRight;
    private CityTrafficLight trafficLight10FromUpToLeft;

    private CityTrafficLight trafficLight10FromRightToUp;
    private CityTrafficLight trafficLight10FromRightToLeft;

    private CityTrafficLight trafficLight10FromLeftToUp;
    private CityTrafficLight trafficLight10FromLeftToRight;


    private CityTrafficLight trafficLight20FromUpToLeft;

    private CityTrafficLight trafficLight20FromLeftToUp;


    private CityTrafficLight trafficLight01FromUpToRight;
    private CityTrafficLight trafficLight01FromUpToDown;

    private CityTrafficLight trafficLight01FromRightToUp;
    private CityTrafficLight trafficLight01FromRightToDown;

    private CityTrafficLight trafficLight01FromDownToUp;
    private CityTrafficLight trafficLight01FromDownToRight;

    private CityTrafficLight trafficLight11FromUpToRight;
    private CityTrafficLight trafficLight11FromUpToDown;
    private CityTrafficLight trafficLight11FromUpToLeft;

    private CityTrafficLight trafficLight11FromRightToUp;
    private CityTrafficLight trafficLight11FromRightToDown;
    private CityTrafficLight trafficLight11FromRightToLeft;

    private CityTrafficLight trafficLight11FromDownToUp;
    private CityTrafficLight trafficLight11FromDownToRight;
    private CityTrafficLight trafficLight11FromDowntoLeft;

    private CityTrafficLight trafficLight11FromLeftToUp;
    private CityTrafficLight trafficLight11FromLeftToRight;
    private CityTrafficLight trafficLight11FromLeftToDown;


    private CityTrafficLight trafficLight21FromUpToDown;
    private CityTrafficLight trafficLight21FromUpToLeft;

    private CityTrafficLight trafficLight21FromDownToUp;
    private CityTrafficLight trafficLight21FromDownToLeft;

    private CityTrafficLight trafficLight21FromLeftToUp;
    private CityTrafficLight trafficLight21FromLeftToDown;


    private CityTrafficLight trafficLight02FromRightToDown;
    private CityTrafficLight trafficLight02FromDownToRight;

    private CityTrafficLight trafficLight12FromRightToDown;
    private CityTrafficLight trafficLight12FromRightToLeft;

    private CityTrafficLight trafficLight12FromDownToRight;
    private CityTrafficLight trafficLight12FromDownToLeft;

    private CityTrafficLight trafficLight12FromLeftToRight;
    private CityTrafficLight trafficLight12FromLeftToDown;


    private CityTrafficLight trafficLight22FromDownToLeft;
    private CityTrafficLight trafficLight22FromLeftToDown;


    private CityCrossroad crossroad00;
    private CityCrossroad crossroad10;
    private CityCrossroad crossroad20;
    private CityCrossroad crossroad01;
    private CityCrossroad crossroad11;
    private CityCrossroad crossroad21;
    private CityCrossroad crossroad02;
    private CityCrossroad crossroad12;
    private CityCrossroad crossroad22;

    private Set<CityCrossroad> allCrossroads;


    private int descCount = 0;
    class TestingVehicleDescriptor implements Comparable<VehicleDescriptor> {
        private int desc;

        TestingVehicleDescriptor() {
            this.desc = descCount;
            descCount++;
        }

        @Override
        public int compareTo(VehicleDescriptor descriptor) {
            return this.hashCode() - descriptor.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof VehicleDescriptor)) {
                return false;
            }
            VehicleDescriptor descriptor = (VehicleDescriptor) o;
            return descriptor.hashCode() == this.hashCode();
        }
    }



        private CityRoad configureRoad(String name, int length, CityCrossroad sourceCrossroad, CityCrossroad destinationCrossroad,
                                long minimumWeight){

        BTWWeight btwWeight = BTWWeight.of(minimumWeight);
        CityRoad road = mock(CityRoad.class);
        Mockito.when(road.getName()).thenReturn(name);
        Mockito.when(road.getRoadLength()).thenReturn(length);
        Mockito.when(road.getStreet()).thenReturn(null);
        Mockito.when(road.getSourceCrossroad()).thenReturn(sourceCrossroad);
        Mockito.when(road.getDestinationCrossroad()).thenReturn(destinationCrossroad);
        Mockito.when(road.getMinimumWeight()).thenReturn(btwWeight);
        return road;
    }



    private void configureAllRoads(){

        this.horizontalLeftRoad02_12 = configureRoad("horizontalLeftRoad02_12", this.roadLength, this.crossroad02, this.crossroad11, 0);
        this.horizontalLeftRoad12_02 = configureRoad( "horizontalLeftRoad12_02", this.roadLength, this.crossroad12, this.crossroad02, 0);

        this.horizontalLeftRoad01_11 = configureRoad("horizontalLeftRoad01_11", this.roadLength, this.crossroad01, this.crossroad11, 0);
        this.horizontalLeftRoad11_01 = configureRoad("horizontalLeftRoad11_01", this.roadLength, this.crossroad11, this.crossroad01, 0);

        this.horizontalLeftRoad00_10 = configureRoad("horizontalLeftRoad00_10", this.roadLength, this.crossroad00, this.crossroad10, 0);
        this.horizontalLeftRoad10_00 = configureRoad("horizontalLeftRoad10_00", this.roadLength, this.crossroad10, this.crossroad00, 0);

        this.horizontalRightRoad12_22 = configureRoad("horizontalRightRoad12_22", this.roadLength, this.crossroad12, this.crossroad22, 0);
        this.horizontalRightRoad22_12 = configureRoad("horizontalRightRoad22_12", this.roadLength, this.crossroad22, this.crossroad12, 0);

        this.horizontalRightRoad11_21 = configureRoad("horizontalRightRoad11_21", this.roadLength, this.crossroad11, this.crossroad21, 0);
        this.horizontalRightRoad21_11 = configureRoad("horizontalRightRoad21_11", this.roadLength, this.crossroad21, this.crossroad11, 0);

        this.horizontalRightRoad10_20 = configureRoad("horizontalRightRoad10_20", this.roadLength, this.crossroad10, this.crossroad20, 0);
        this.horizontalRightRoad20_10 = configureRoad("horizontalRightRoad20_10", this.roadLength, this.crossroad20, this.crossroad10, 0);

        this.verticalUpRoad02_01 = configureRoad("verticalUpRoad02_01", this.roadLength, this.crossroad02, this.crossroad01, 0);
        this.verticalUpRoad01_02 = configureRoad("verticalUpRoad01_02", this.roadLength, this.crossroad01, this.crossroad02, 0);

        this.verticalUpRoad12_11 = configureRoad("verticalUpRoad12_11", this.roadLength, this.crossroad12, this.crossroad11, 0);
        this.verticalUpRoad11_12 = configureRoad("verticalUpRoad11_12", this.roadLength, this.crossroad11, this.crossroad12, 0);

        this.verticalUpRoad22_21 = configureRoad("verticalUpRoad22_21", this.roadLength, this.crossroad22, this.crossroad21, 0);
        this.verticalUpRoad21_22 = configureRoad("verticalUpRoad21_22", this.roadLength, this.crossroad21, this.crossroad22, 0);

        this.verticalDownRoad01_00 = configureRoad("verticalDownRoad01_00", this.roadLength, this.crossroad01, this.crossroad00, 0);
        this.verticalDownRoad00_01 = configureRoad("verticalDownRoad00_01", this.roadLength, this.crossroad00, this.crossroad01, 0);

        this.verticalDownRoad11_10 = configureRoad("verticalDownRoad11_10", this.roadLength, this.crossroad11, this.crossroad10, 0);
        this.verticalDownRoad10_11 = configureRoad("verticalDownRoad10_11", this.roadLength, this.crossroad10, this.crossroad11, 0);

        this.verticalDownRoad21_20 = configureRoad("verticalDownRoad21_20", this.roadLength, this.crossroad21, this.crossroad20, 0);
        this.verticalDownRoad20_21 = configureRoad("verticalDownRoad20_21", this.roadLength, this.crossroad20, this.crossroad21, 0);
    }

    private CityTrafficLight configureTrafficLight(double coordX, double coordY, String name,
                                       CityRoad sourceRoad, CityRoad destinationRoad){

        CityTrafficLight trafficLight = mock(CityTrafficLight.class);
        Mockito.when(trafficLight.getCoordinateX()).thenReturn(coordX);
        Mockito.when(trafficLight.getCoordinateY()).thenReturn(coordY);
        Mockito.when(trafficLight.getName()).thenReturn(name);
        Mockito.when(trafficLight.getSourceRoad()).thenReturn(sourceRoad);
        Mockito.when(trafficLight.getDestinationRoad()).thenReturn(destinationRoad);
        Mockito.when(trafficLight.getMinimumOpenTime()).thenReturn(0);
        return trafficLight;
    }


    private void configureAllTrafficLights(){

        //source road is horizontalLeftRoad01_11
        this.trafficLight00FromUpToRight = configureTrafficLight(0.0 * this.roadLength, 0.0 * this.roadLength, "trafficLight00FromUpToRight",
                verticalDownRoad01_00, horizontalLeftRoad00_10);
        this.trafficLight00FromRightToUp = configureTrafficLight(0.0 * this.roadLength, 0.0 * this.roadLength, "trafficLight00FromRightToUp",
                horizontalLeftRoad10_00, verticalDownRoad00_01);


        this.trafficLight10FromUpToLeft = configureTrafficLight(1.0 * this.roadLength, 0.0 * this.roadLength, "trafficLight10FromUpToLeft",
                verticalDownRoad11_10, horizontalLeftRoad10_00);
        this.trafficLight10FromUpToRight = configureTrafficLight(1.0 * this.roadLength, 0.0 * this.roadLength, "trafficLight10FromUpToRight",
                verticalDownRoad11_10, horizontalRightRoad10_20);

        this.trafficLight10FromRightToUp = configureTrafficLight(1.0 * this.roadLength, 0.0 * this.roadLength, "trafficLight10FromRightToUp",
                horizontalRightRoad20_10, verticalDownRoad10_11);
        this.trafficLight10FromRightToLeft = configureTrafficLight(1.0 * this.roadLength, 0.0 * this.roadLength, "trafficLight10FromRightToLeft",
                horizontalRightRoad20_10, horizontalLeftRoad10_00);

        this.trafficLight10FromLeftToUp = configureTrafficLight(1.0 * this.roadLength, 0.0 * this.roadLength, "trafficLight10FromLeftToUp",
                horizontalLeftRoad00_10, verticalDownRoad10_11);
        this.trafficLight10FromLeftToRight = configureTrafficLight(1.0 * this.roadLength, 0.0 * this.roadLength, "trafficLight10FromLeftToRight",
                horizontalLeftRoad00_10, horizontalRightRoad10_20);

        this.trafficLight20FromUpToLeft = configureTrafficLight(2.0 * this.roadLength, 0.0 * this.roadLength, "trafficLight20FromUpToLeft",
                verticalDownRoad21_20, horizontalRightRoad20_10);
        this.trafficLight20FromLeftToUp = configureTrafficLight(2.0 * this.roadLength, 0.0 * this.roadLength, "trafficLight20FromLeftToUp",
                horizontalRightRoad10_20, verticalDownRoad20_21);

        this.trafficLight01FromUpToRight = configureTrafficLight(0.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight01FromUpToRight",
                verticalUpRoad02_01, horizontalLeftRoad01_11);
        this.trafficLight01FromUpToDown = configureTrafficLight(0.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight01FromUpToDown",
                verticalUpRoad02_01, verticalDownRoad01_00);

        this.trafficLight01FromRightToUp = configureTrafficLight(0.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight01FromRightToUp",
                horizontalLeftRoad11_01, verticalUpRoad01_02);
        this.trafficLight01FromRightToDown = configureTrafficLight(0.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight01FromRightToDown",
                horizontalLeftRoad11_01, verticalDownRoad01_00);

        this.trafficLight01FromDownToUp = configureTrafficLight(0.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight01FromDownToUp",
                verticalDownRoad00_01, verticalUpRoad01_02);
        this.trafficLight01FromDownToRight = configureTrafficLight(0.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight01FromDownToRight",
                verticalDownRoad00_01, horizontalLeftRoad01_11);

        this.trafficLight11FromUpToRight = configureTrafficLight(1.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight11FromUpToRight",
                verticalUpRoad12_11, horizontalRightRoad11_21);
        this.trafficLight11FromUpToDown = configureTrafficLight(1.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight11FromUpToDown",
                verticalUpRoad12_11, verticalDownRoad11_10);
        this.trafficLight11FromUpToLeft = configureTrafficLight(1.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight11FromUpToLeft",
                verticalUpRoad12_11, horizontalLeftRoad11_01);

        this.trafficLight11FromRightToUp = configureTrafficLight(1.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight11FromRightToUp",
                horizontalRightRoad21_11, verticalUpRoad11_12);
        this.trafficLight11FromRightToDown = configureTrafficLight(1.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight11FromRightToDown",
                horizontalRightRoad21_11, verticalDownRoad11_10);
        this.trafficLight11FromRightToLeft = configureTrafficLight(1.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight11FromRightToLeft",
                horizontalRightRoad21_11, horizontalLeftRoad11_01);

        this.trafficLight11FromDownToUp = configureTrafficLight(1.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight11FromDownToUp",
                verticalDownRoad10_11, verticalUpRoad11_12);
        this.trafficLight11FromDownToRight = configureTrafficLight(1.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight11FromDownToRight",
                verticalDownRoad10_11, horizontalRightRoad11_21);
        this.trafficLight11FromDowntoLeft = configureTrafficLight(1.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight11FromDowntoLeft",
                verticalDownRoad10_11, horizontalLeftRoad11_01);

        this.trafficLight11FromLeftToUp = configureTrafficLight(1.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight11FromLeftToUp",
                horizontalLeftRoad01_11, verticalUpRoad11_12);
        this.trafficLight11FromLeftToRight = configureTrafficLight(1.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight11FromLeftToRight",
                horizontalLeftRoad01_11, horizontalRightRoad11_21);
        this.trafficLight11FromLeftToDown = configureTrafficLight(1.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight11FromLeftToDown",
                horizontalLeftRoad01_11, verticalDownRoad11_10);

        this.trafficLight21FromUpToDown = configureTrafficLight(2.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight21FromUpToDown",
                verticalUpRoad22_21, verticalDownRoad21_20);
        this.trafficLight21FromUpToLeft = configureTrafficLight(2.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight21FromUpToLeft",
                verticalUpRoad22_21, horizontalRightRoad21_11);

        this.trafficLight21FromDownToUp = configureTrafficLight(2.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight21FromDownToUp",
                verticalDownRoad20_21, verticalUpRoad21_22);
        this.trafficLight21FromDownToLeft = configureTrafficLight(2.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight21FromDownToLeft",
                verticalDownRoad20_21, horizontalRightRoad21_11);

        this.trafficLight21FromLeftToUp = configureTrafficLight(2.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight21FromLeftToUp",
                horizontalRightRoad11_21, verticalUpRoad21_22);
        this.trafficLight21FromLeftToDown = configureTrafficLight(2.0 * this.roadLength, 1.0 * this.roadLength, "trafficLight21FromLeftToDown",
                horizontalRightRoad11_21, verticalDownRoad21_20);

        this.trafficLight02FromRightToDown = configureTrafficLight(0.0 * this.roadLength, 2.0 * this.roadLength, "trafficLight02FromRightToDown",
                horizontalLeftRoad12_02, verticalUpRoad02_01);
        this.trafficLight02FromDownToRight = configureTrafficLight(0.0 * this.roadLength, 2.0 * this.roadLength, "trafficLight02FromDownToRight",
                verticalUpRoad01_02, horizontalLeftRoad02_12);

        this.trafficLight12FromRightToDown = configureTrafficLight(1.0 * this.roadLength, 2.0 * this.roadLength, "trafficLight12FromRightToDown",
                horizontalRightRoad22_12, verticalUpRoad12_11);
        this.trafficLight12FromRightToLeft = configureTrafficLight(1.0 * this.roadLength, 2.0 * this.roadLength, "trafficLight12FromRightToLeft",
                horizontalRightRoad22_12, horizontalLeftRoad12_02);

        this.trafficLight12FromDownToRight = configureTrafficLight(1.0 * this.roadLength, 2.0 * this.roadLength, "trafficLight12FromDownToRight",
                verticalUpRoad11_12, horizontalRightRoad12_22);
        this.trafficLight12FromDownToLeft = configureTrafficLight(1.0 * this.roadLength, 2.0 * this.roadLength, "trafficLight12FromDownToLeft",
                verticalUpRoad11_12, horizontalLeftRoad12_02);

        this.trafficLight12FromLeftToRight = configureTrafficLight(1.0 * this.roadLength, 2.0 * this.roadLength, "trafficLight12FromLeftToRight",
                horizontalLeftRoad02_12, horizontalRightRoad12_22);
        this.trafficLight12FromLeftToDown = configureTrafficLight(1.0 * this.roadLength, 2.0 * this.roadLength, "trafficLight12FromLeftToDown",
                horizontalLeftRoad02_12, verticalUpRoad12_11);

        this.trafficLight22FromDownToLeft = configureTrafficLight(2.0 * this.roadLength, 2.0 * this.roadLength, "trafficLight22FromDownToLeft",
                verticalUpRoad21_22, horizontalRightRoad22_12);
        this.trafficLight22FromLeftToDown = configureTrafficLight(2.0 * this.roadLength, 2.0 * this.roadLength, "trafficLight22FromLeftToDown",
                horizontalRightRoad12_22, verticalUpRoad22_21);


    }

    public CityCrossroad configureCrossroad(String name, double coordX, double coordY, Set<CityTrafficLight> trafficLights){
        CityCrossroad crossroad = mock(CityCrossroad.class);
        Mockito.when(crossroad.getName()).thenReturn(name);
        Mockito.when(crossroad.getCoordinateX()).thenReturn(coordX);
        Mockito.when(crossroad.getCoordinateY()).thenReturn(coordY);
        Mockito.when(crossroad.getRealTrafficLights()).thenReturn(trafficLights);
        return crossroad;
    }

    private void configureAllCrossroads(){

        crossroad00 = configureCrossroad("crossroad00", 0.0 * this.roadLength, 0.0 * this.roadLength,
                new HashSet<>(Arrays.asList(trafficLight00FromUpToRight, trafficLight00FromRightToUp)));

        crossroad10 = configureCrossroad("crossroad10", 1.0 * this.roadLength, 0.0 * this.roadLength,
                new HashSet<>(Arrays.asList(trafficLight10FromUpToRight, trafficLight10FromUpToLeft,
                        trafficLight10FromRightToUp, trafficLight10FromRightToLeft,
                        trafficLight10FromLeftToUp, trafficLight10FromLeftToRight)));

        crossroad20 = configureCrossroad("crossroad20", 2.0 * this.roadLength, 0.0 * this.roadLength,
                new HashSet<>(Arrays.asList(trafficLight20FromUpToLeft, trafficLight20FromLeftToUp)));

        crossroad01 = configureCrossroad("crossroad01", 0.0 * this.roadLength, 1.0 * this.roadLength,
                new HashSet<>(Arrays.asList(trafficLight01FromUpToRight, trafficLight01FromUpToDown,
                        trafficLight01FromRightToUp, trafficLight01FromRightToDown,
                        trafficLight01FromDownToUp, trafficLight01FromDownToRight)));


        crossroad11 = configureCrossroad("crossroad11", 1.0 * this.roadLength, 1.0 * this.roadLength,
                new HashSet<>(Arrays.asList(trafficLight11FromUpToRight, trafficLight11FromUpToDown, trafficLight11FromUpToLeft,
                        trafficLight11FromRightToUp, trafficLight11FromRightToDown, trafficLight11FromRightToLeft,
                        trafficLight11FromDownToUp, trafficLight11FromDownToRight, trafficLight11FromDowntoLeft,
                        trafficLight11FromLeftToUp, trafficLight11FromLeftToRight, trafficLight11FromLeftToDown)));

        crossroad21 = configureCrossroad("crossroad21", 2.0 * this.roadLength, 1.0 * this.roadLength,
                new HashSet<>(Arrays.asList(trafficLight21FromUpToDown, trafficLight21FromUpToLeft,
                        trafficLight21FromDownToUp, trafficLight21FromDownToLeft,
                        trafficLight21FromLeftToUp, trafficLight21FromLeftToDown)));

        crossroad02 = configureCrossroad("crossroad02", 0.0 * this.roadLength, 2.0 * this.roadLength,
                new HashSet<>(Arrays.asList(trafficLight02FromRightToDown, trafficLight02FromDownToRight)));

        crossroad12 = configureCrossroad("crossroad12", 1.0 * this.roadLength, 2.0 * this.roadLength,
                new HashSet<>(Arrays.asList(trafficLight12FromRightToDown, trafficLight12FromRightToLeft,
                        trafficLight12FromDownToRight, trafficLight12FromDownToLeft,
                        trafficLight12FromLeftToRight, trafficLight12FromLeftToDown)));

        crossroad22 = configureCrossroad("crossroad22", 2.0 * this.roadLength, 2.0 * this.roadLength,
                new HashSet<>(Arrays.asList(trafficLight22FromDownToLeft, trafficLight22FromLeftToDown)));

        this.allCrossroads = new HashSet<>(Arrays.asList(crossroad00, crossroad10, crossroad20,
                crossroad01, crossroad11, crossroad21, crossroad02, crossroad12, crossroad22));


    }

    @Before
    public void configureMap(){

        configureAllRoads();
        configureAllTrafficLights();
        configureAllCrossroads();
    }

    private  class Pair<T,S>{
        private T first;
        private S second;
        Pair(T first, S second){
            this.first = first;
            this.second = second;
        }
        T getFirst(){
            return this.first;
        }

        S getSecond(){
            return this.second;
        }
    }

    private void configureOverload(CityCrossroad crossroad, Pair<CityRoad, Double>... roadsAndOverloadsPairs){

        double crossRoadOverLoad = 0;
        for(Pair<CityRoad, Double> pair : roadsAndOverloadsPairs){
            CityRoad road = pair.getFirst();
            double overload = pair.getSecond();
            Mockito.when(road.getOverload()).thenReturn(overload);
            crossRoadOverLoad += overload;
        }
        Mockito.when(crossroad.getOverload()).thenReturn(crossRoadOverLoad);
    }

    /*@Test
    public void insertCrossroads() throws Exception {

    }

    @Test
    public void tick() throws Exception {
    }
    */

    private void clearMap(){
        horizontalLeftRoad02_12 = null;
        horizontalLeftRoad12_02 = null;
        horizontalLeftRoad01_11 = null;
        horizontalLeftRoad11_01 = null;
        horizontalLeftRoad00_10 = null;
        horizontalLeftRoad10_00 = null;

        horizontalRightRoad12_22 = null;
        horizontalRightRoad22_12 = null;
        horizontalRightRoad11_21 = null;
        horizontalRightRoad21_11 = null;
        horizontalRightRoad10_20 = null;
        horizontalRightRoad20_10 = null;

        verticalUpRoad02_01 = null;
        verticalUpRoad01_02 = null;
        verticalUpRoad12_11 = null;
        verticalUpRoad11_12 = null;
        verticalUpRoad22_21 = null;
        verticalUpRoad21_22 = null;

        verticalDownRoad01_00 = null;
        verticalDownRoad00_01 = null;
        verticalDownRoad10_11 = null;
        verticalDownRoad11_10 = null;
        verticalDownRoad21_20 = null;
        verticalDownRoad20_21 = null;
        trafficLight00FromUpToRight = null;

        trafficLight00FromRightToUp = null;


        trafficLight10FromUpToRight = null;
        trafficLight10FromUpToLeft = null;

        trafficLight10FromRightToUp = null;
        trafficLight10FromRightToLeft = null;

        trafficLight10FromLeftToUp = null;
        trafficLight10FromLeftToRight = null;


        trafficLight20FromUpToLeft = null;

        trafficLight20FromLeftToUp = null;


        trafficLight01FromUpToRight = null;
        trafficLight01FromUpToDown = null;

        trafficLight01FromRightToUp = null;
        trafficLight01FromRightToDown = null;

        trafficLight01FromDownToUp = null;
        trafficLight01FromDownToRight = null;

        trafficLight11FromUpToRight = null;
        trafficLight11FromUpToDown = null;
        trafficLight11FromUpToLeft = null;

        trafficLight11FromRightToUp = null;
        trafficLight11FromRightToDown = null;
        trafficLight11FromRightToLeft = null;

        trafficLight11FromDownToUp = null;
        trafficLight11FromDownToRight = null;
        trafficLight11FromDowntoLeft = null;

        trafficLight11FromLeftToUp = null;
        trafficLight11FromLeftToRight = null;
        trafficLight11FromLeftToDown = null;


        trafficLight21FromUpToDown= null;
        trafficLight21FromUpToLeft = null;

        trafficLight21FromDownToUp = null;
        trafficLight21FromDownToLeft = null;

        trafficLight21FromLeftToUp = null;
        trafficLight21FromLeftToDown = null;


        trafficLight02FromRightToDown = null;
        trafficLight02FromDownToRight = null;

        trafficLight12FromRightToDown = null;
        trafficLight12FromRightToLeft = null;

        trafficLight12FromDownToRight = null;
        trafficLight12FromDownToLeft = null;

        trafficLight12FromLeftToRight = null;
        trafficLight12FromLeftToDown = null;


        trafficLight22FromDownToLeft = null;
        trafficLight22FromLeftToDown = null;


        crossroad00 = null;
        crossroad10 = null;
        crossroad20 = null;
        crossroad01 = null;
        crossroad11 = null;
        crossroad21 = null;
        crossroad02 = null;
        crossroad12 = null;
        crossroad22 = null;

        allCrossroads = new HashSet<>();


        descCount = 0;
    }


    @Test
    public void overloadFromUpToDownInCrossroad11CheckInitialize(){
        configureMap();
        SmartTrafficLightManager smartTrafficLightManager = new SmartTrafficLightManager();
        configureOverload(crossroad11,  new Pair<>(horizontalLeftRoad01_11, 0.0),
                new Pair<>(horizontalRightRoad21_11, 0.0),
                new Pair<>(verticalUpRoad12_11, 10.0),
                new Pair<>(verticalDownRoad10_11, 0.0));
        smartTrafficLightManager.insertCrossroads(this.allCrossroads);
        String res = smartTrafficLightManager.getTrafficLightStates();
        Assert.assertTrue(res.matches(allCharacters + "horizontalLeftRoad01_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "horizontalRightRoad21_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalDownRoad10_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalUpRoad12_11: state: GREEN" + allCharacters));
        clearMap();
    }

    @Test
    public void overloadFromUpToDownInCrossroad11CheckAfterInitializationTest(){
        configureMap();
        SmartTrafficLightManager smartTrafficLightManager = new SmartTrafficLightManager();
        configureOverload(crossroad11,  new Pair<>(horizontalLeftRoad01_11, 0.0),
                new Pair<>(horizontalRightRoad21_11, 0.0),
                new Pair<>(verticalUpRoad12_11, 10.0),
                new Pair<>(verticalDownRoad10_11, 0.0));
        smartTrafficLightManager.insertCrossroads(this.allCrossroads);
        String res = smartTrafficLightManager.getTrafficLightStates();
        Assert.assertTrue(res.matches(allCharacters + "horizontalLeftRoad01_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "horizontalRightRoad21_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalDownRoad10_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalUpRoad12_11: state: GREEN" + allCharacters));
        clearMap();

    }

    @Test
    public void checkLightsChangingByOverloadsOverOnePeriodOnePeriodTest(){
        configureMap();
        SmartTrafficLightManager smartTrafficLightManager = new SmartTrafficLightManager();
        configureOverload(crossroad11,  new Pair<>(horizontalLeftRoad01_11, 10.0),
                new Pair<>(horizontalRightRoad21_11, 8.0),
                new Pair<>(verticalUpRoad12_11, 6.0),
                new Pair<>(verticalDownRoad10_11, 4.0));

        smartTrafficLightManager.insertCrossroads(this.allCrossroads);
        String res = smartTrafficLightManager.getTrafficLightStates();
        Assert.assertTrue(res.matches(allCharacters + "horizontalLeftRoad01_11: state: GREEN" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "horizontalRightRoad21_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalUpRoad12_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalDownRoad10_11: state: RED" + allCharacters));

        for(int i =0 ; i<86 ; i++){
            smartTrafficLightManager.tick();
        }

        res = smartTrafficLightManager.getTrafficLightStates();
        Assert.assertTrue(res.matches(allCharacters + "horizontalLeftRoad01_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "horizontalRightRoad21_11: state: GREEN" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalUpRoad12_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalDownRoad10_11: state: RED" + allCharacters));

        for(int i =0 ; i<69 ; i++){
            smartTrafficLightManager.tick();
        }

        res = smartTrafficLightManager.getTrafficLightStates();
        Assert.assertTrue(res.matches(allCharacters + "horizontalLeftRoad01_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "horizontalRightRoad21_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalUpRoad12_11: state: GREEN" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalDownRoad10_11: state: RED" + allCharacters));

        for(int i =0 ; i<52 ; i++){
            smartTrafficLightManager.tick();
        }

        res = smartTrafficLightManager.getTrafficLightStates();
        Assert.assertTrue(res.matches(allCharacters + "horizontalLeftRoad01_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "horizontalRightRoad21_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalUpRoad12_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalDownRoad10_11: state: GREEN" + allCharacters));

        for(int i =0 ; i<33 ; i++){
            smartTrafficLightManager.tick();
        }
        smartTrafficLightManager.tick();

        res = smartTrafficLightManager.getTrafficLightStates();
        Assert.assertTrue(res.matches(allCharacters + "horizontalLeftRoad01_11: state: GREEN" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "horizontalRightRoad21_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalUpRoad12_11: state: RED" + allCharacters));
        Assert.assertTrue(res.matches(allCharacters + "verticalDownRoad10_11: state: RED" + allCharacters));

        clearMap();

    }

    @Test
    public void TwoCompensationPeriodsTest(){
        configureMap();
        SmartTrafficLightManager smartTrafficLightManager = new SmartTrafficLightManager();
        configureOverload(crossroad11,  new Pair<>(horizontalLeftRoad01_11, 10.0),
                new Pair<>(horizontalRightRoad21_11, 8.0),
                new Pair<>(verticalUpRoad12_11, 6.0),
                new Pair<>(verticalDownRoad10_11, 4.0));

        int periodTime = 60 * 4 + 2 ;


        smartTrafficLightManager.insertCrossroads(this.allCrossroads);

        //pass time to the start of the first compensation period
        for(int i =0 ; i<4 * periodTime + 1; i++){
            smartTrafficLightManager.tick();
        }
        String res = smartTrafficLightManager.getTrafficLightStates();
        int compensationsCounter = 0;

        for(int currentPeriod = 0; currentPeriod < 4; currentPeriod++){
            String[] crossroadRes = res.split("\n\n");
            for(int i=0; i<crossroadRes.length; i++) {
                if (crossroadRes[i].matches(allCharacters + "crossroad11" + allCharacters)) {
                    String[] states = crossroadRes[i].split("\n");
                    for (int j = 0; j < states.length; j++) {
                        if((states[j].matches(allCharacters + "horizontalLeftRoad01_11" + allCharacters))
                        ||(states[j].matches(allCharacters + "horizontalRightRoad21_11" + allCharacters))
                        ||(states[j].matches(allCharacters + "verticalDownRoad10_11" + allCharacters))
                        ||(states[j].matches(allCharacters + "verticalUpRoad12_11" + allCharacters))) {
                            double parsed = Double.parseDouble(states[j].split("Period: ")[1].split(" time")[0]);
                            int y = 0;
                        }
                        if (((states[j].matches(allCharacters + "horizontalLeftRoad01_11" + allCharacters))
                                && (Double.parseDouble(states[j].split("Period: ")[1].split(" time")[0]) > 86.0))

                                || ((states[j].matches(allCharacters + "horizontalRightRoad21_11" + allCharacters))
                                && (Double.parseDouble(states[j].split("Period: ")[1].split(" time")[0]) > 69.0))

                                || ((states[j].matches(allCharacters + "verticalDownRoad10_11" + allCharacters))
                                && (Double.parseDouble(states[j].split("Period: ")[1].split(" time")[0]) > 35.0))

                                || ((states[j].matches(allCharacters + "verticalUpRoad12_11" + allCharacters))
                                && (Double.parseDouble(states[j].split("Period: ")[1].split(" time")[0]) > 52.0))

                                ) {
                            compensationsCounter++;
                        }
                    }
                }
            }
            for(int i =0 ; i<4 * periodTime + 1; i++){
                smartTrafficLightManager.tick();
            }
            res = smartTrafficLightManager.getTrafficLightStates();
        }
        //check that there were exactly 2 periods that got compensation times for roads in them.
        Assert.assertEquals(2, compensationsCounter);
        clearMap();

    }





}