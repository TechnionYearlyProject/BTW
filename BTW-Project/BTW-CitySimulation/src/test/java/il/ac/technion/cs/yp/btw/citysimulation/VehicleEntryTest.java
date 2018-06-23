package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import org.junit.Test;

import static org.junit.Assert.*;

public class VehicleEntryTest {

    @Test
    public void sourceRoadNameTest() {
        VehicleEntry ve = new VehicleEntry();
        ve.setSourceRoadName("test1");
        assertEquals(ve.getSourceRoadName().get().getId(),"test1");
    }


    @Test
    public void sourceRoadRatioTest() {
        VehicleEntry ve = new VehicleEntry();
        ve.setSourceRoadRatio(0.111);
        assertEquals(ve.getSourceRoadRatio().get().getValue(),0.111,0.00001);
    }

    @Test
    public void destinationRoadNameTest() {
        VehicleEntry ve = new VehicleEntry();
        ve.setDestinationRoadName("test1");
        assertEquals(ve.getDestinationRoadName().get().getId(),"test1");
    }

    @Test
    public void destinationRoadRatioTest() {
        VehicleEntry ve = new VehicleEntry();
        ve.setDestinationRoadRatio(0.111);
        assertEquals(ve.getDestinationRoadRatio().get().getValue(),0.111,0.00001);

    }

    @Test
    public void equals() {
        VehicleEntry ve1 = new VehicleEntry();
        ve1.setDestinationRoadRatio(0.111);
        VehicleEntry ve2 = new VehicleEntry();
        ve2.setDestinationRoadRatio(0.111);
        assertEquals(ve1,ve2);
    }

    @Test
    public void timeOfDrivingStart() {
        VehicleEntry ve = new VehicleEntry();
        ve.setTimeOfDrivingStart("01:00:21");
        assertEquals(ve.getTimeOfDrivingStart().get().seconds().intValue(),3621);
        ve.setTimeOfDrivingStart(BTWTime.of(152));
        assertEquals(ve.getTimeOfDrivingStart().get().seconds().intValue(),152);

    }

    @Test
    public void descriptorTest() {
        VehicleEntry ve = new VehicleEntry();
        ve.setDescriptor(new VehicleDescriptor(1131));
        assertEquals(ve.getDescriptor().get().getID().intValue(),1131);
    }

    @Test
    public void jsonString() {
        VehicleEntry ve = new VehicleEntry();
        ve.setTimeOfDrivingStart("00:21:13")
                .setSourceRoadName("source1")
                .setSourceRoadRatio(0.1)
                .setDestinationRoadName("destination1")
                .setDestinationRoadRatio(0.3);
        assertEquals("{\"sourceRoad\": \"source1\",\n" +
                "\"sourceRatio\": 0.1,\n" +
                "\"destinationRoad\": \"destination1\",\n" +
                "\"destinationRatio\": 0.3,\n" +
                "\"timeOfDrivingStart\": \"00:21:13\"}"
                ,ve.jsonString());
    }
}