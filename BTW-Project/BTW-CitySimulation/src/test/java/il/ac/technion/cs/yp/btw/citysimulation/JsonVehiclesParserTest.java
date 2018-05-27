package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class JsonVehiclesParserTest {
    private String getDataFromResourceFileByName(String fileName) throws FileNotFoundException {
        return new Scanner(new File(JsonVehiclesParserTest.class.getResource(fileName).getFile())).useDelimiter("\\Z").next();
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testEmptyJsonFile() throws FileNotFoundException {
        String fileName = "emptyFile.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        JsonVehiclesParser parser = new JsonVehiclesParser();
        parser.parseVehiclesFromData(fileContents);
        assertEquals(0, parser.getVehicleEntryList().size());
    }

    @Test
    public void testOneLegalEntry() throws Exception {
        String fileName = "oneLegalVehicleEntry.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        assert !fileContents.isEmpty();
        JsonVehiclesParser parser = new JsonVehiclesParser();
        parser.parseVehiclesFromData(fileContents);
        assertEquals(1, parser.getVehicleEntryList().size());
        assertEquals("11 Street"
                , parser.getVehicleEntryList()
                        .get(0).getSourceRoadName().get().getId());
        assertEquals(0.2
                , parser.getVehicleEntryList()
                        .get(0).getSourceRoadRatio().get().getValue(), 0.00001);
        assertEquals("39 Street"
                , parser.getVehicleEntryList()
                        .get(0).getDestinationRoadName().get().getId());
        assertEquals(0.6
                , parser.getVehicleEntryList()
                        .get(0).getDestinationRoadRatio().get().getValue(), 0.00001);
        assertEquals(BTWTime.of("10:40:56"), parser.getVehicleEntryList()
                .get(0).getTimeOfDrivingStart().get());
    }

    @Test
    public void testMoreThanOneLegalEntry() throws Exception {
        String fileName = "twoLegalVehicleEntries.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        assert !fileContents.isEmpty();
        JsonVehiclesParser parser = new JsonVehiclesParser();
        parser.parseVehiclesFromData(fileContents);
        assertEquals(2, parser.getVehicleEntryList().size());
    }


    @Test(expected = RatioOutOfBoundException.class)
    public void testSourceRatioOutOfUpperBoundEntry() throws FileNotFoundException {
        String fileName = "IllegalSourceRatioUpperBoundEntry.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        assert !fileContents.isEmpty();
        JsonVehiclesParser parser = new JsonVehiclesParser();
        parser.parseVehiclesFromData(fileContents);
    }

    @Test(expected = RatioOutOfBoundException.class)
    public void testSourceRatioOutOfLowerBoundEntry() throws FileNotFoundException {
        String fileName = "IllegalSourceRatioLowerBoundEntry.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        assert !fileContents.isEmpty();
        JsonVehiclesParser parser = new JsonVehiclesParser();
        parser.parseVehiclesFromData(fileContents);
    }

    @Test(expected = RatioOutOfBoundException.class)
    public void testDestinationRatioOutOfLowerBoundEntry() throws FileNotFoundException {
        String fileName = "IllegalDestinationRatioLowerBoundEntry.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        assert !fileContents.isEmpty();
        JsonVehiclesParser parser = new JsonVehiclesParser();
        parser.parseVehiclesFromData(fileContents);
    }

    @Test(expected = RatioOutOfBoundException.class)
    public void testDestinationRatioOutOfUpperBoundEntry() throws FileNotFoundException {
        String fileName = "IllegalDestinationRatioUpperBoundEntry.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        assert !fileContents.isEmpty();
        JsonVehiclesParser parser = new JsonVehiclesParser();
        parser.parseVehiclesFromData(fileContents);
    }

    @Test
    public void testExtraFieldsInEntryAreIgnored() throws FileNotFoundException {
        String fileName = "validEntryWithExtraFields.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        assert !fileContents.isEmpty();
        JsonVehiclesParser parser = new JsonVehiclesParser();
        parser.parseVehiclesFromData(fileContents);
        assertEquals(1, parser.getVehicleEntryList().size());
        assertEquals("11 Street"
                , parser.getVehicleEntryList()
                        .get(0).getSourceRoadName().get().getId());
        assertEquals(0.2
                , parser.getVehicleEntryList()
                        .get(0).getSourceRoadRatio().get().getValue(), 0.00001);
        assertEquals("39 Street"
                , parser.getVehicleEntryList()
                        .get(0).getDestinationRoadName().get().getId());
        assertEquals(0.6
                , parser.getVehicleEntryList()
                        .get(0).getDestinationRoadRatio().get().getValue(), 0.00001);
        assertEquals(BTWTime.of("10:40:56"), parser.getVehicleEntryList()
                .get(0).getTimeOfDrivingStart().get());
    }

    @Test
    public void testSourceRoadIsMissing() throws FileNotFoundException {
        String fileName = "sourceRoadIsMissing.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        assert !fileContents.isEmpty();
        JsonVehiclesParser parser = new JsonVehiclesParser();
        thrown.expect(MandatoryFieldNotFoundException.class);
        thrown.expectMessage("source road field is missing");

        parser.parseVehiclesFromData(fileContents);
    }

    @Test
    public void testSourceRatioIsMissing() throws FileNotFoundException {
        String fileName = "sourceRatioIsMissing.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        assert !fileContents.isEmpty();
        JsonVehiclesParser parser = new JsonVehiclesParser();
        thrown.expect(MandatoryFieldNotFoundException.class);
        thrown.expectMessage("source ratio field is missing");

        parser.parseVehiclesFromData(fileContents);
    }

    @Test
    public void testDestinationRoadIsMissing() throws FileNotFoundException {
        String fileName = "destinationRoadIsMissing.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        assert !fileContents.isEmpty();
        JsonVehiclesParser parser = new JsonVehiclesParser();
        thrown.expect(MandatoryFieldNotFoundException.class);
        thrown.expectMessage("destination road field is missing");

        parser.parseVehiclesFromData(fileContents);
    }

    @Test
    public void testDestinationRatioIsMissing() throws FileNotFoundException {
        String fileName = "destinationRatioIsMissing.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        assert !fileContents.isEmpty();
        JsonVehiclesParser parser = new JsonVehiclesParser();
        thrown.expect(MandatoryFieldNotFoundException.class);
        thrown.expectMessage("destination ratio field is missing");
        parser.parseVehiclesFromData(fileContents);
    }


    @Test
    public void testTimeOfDrivingStartIsMissing() throws FileNotFoundException {
        String fileName = "timeOfDrivingStartIsMissing.json";
        String fileContents =
                getDataFromResourceFileByName(fileName);
        assert !fileContents.isEmpty();
        JsonVehiclesParser parser = new JsonVehiclesParser();
        thrown.expect(MandatoryFieldNotFoundException.class);
        thrown.expectMessage("time of driving start field is missing");
        parser.parseVehiclesFromData(fileContents);
    }
}