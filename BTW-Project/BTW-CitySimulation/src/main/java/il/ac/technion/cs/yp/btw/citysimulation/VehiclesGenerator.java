package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.Road;
import org.apache.commons.math3.distribution.PoissonDistribution;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class VehiclesGenerator {

    private Set<Road> availableRoads;
    private int totalNumOfCars;
    private BTWTime rushHour1;
    private BTWTime rushHour2;

    public VehiclesGenerator(Set<Road> roads, int totalNumOfCars, BTWTime rushHour1,
                             BTWTime rushHour2) {
        this.availableRoads = roads;
        this.totalNumOfCars = totalNumOfCars;
        this.rushHour1 = rushHour1;
        this.rushHour2 = rushHour2;
    }

    public List<VehicleEntry> generateList() {
        PoissonDistribution pd1 = new PoissonDistribution(this.rushHour1.seconds());
        PoissonDistribution pd2 = new PoissonDistribution(this.rushHour2.seconds());
        VehicleDescriptorFactory vdf = new VehicleDescriptorFactory();
        List<VehicleEntry> vehicleList = new ArrayList<>();
        Random randomGenerator = new Random();
        for (int i = 0; i < this.totalNumOfCars / 2; i++) {
            addRandomEntryToList(pd1, vdf, vehicleList, randomGenerator);
        }
        for (int i = 0; i < this.totalNumOfCars / 2 + this.totalNumOfCars % 2; i++) {
            addRandomEntryToList(pd2, vdf, vehicleList, randomGenerator);
        }
        return vehicleList;
    }

    private void addRandomEntryToList(PoissonDistribution pd, VehicleDescriptorFactory vehicleDescriptorsFactory, List<VehicleEntry> vehicleEntriesList, Random randomGenerator) {
        int index = randomGenerator.nextInt(this.availableRoads.size());
        List<Road> roads = new ArrayList<>(this.availableRoads);
        Road r1 = roads.get(index);
        index = randomGenerator.nextInt(this.availableRoads.size());
        Road r2 = roads.get(index);
        Double ratio1 = randomGenerator.nextDouble();
        Double ratio2 = randomGenerator.nextDouble();
        vehicleEntriesList.add(new VehicleEntry()
                .setDestinationRoadName(r1.getName())
                .setSourceRoadName(r2.getName())
                .setSourceRoadRatio(ratio1)
                .setDestinationRoadRatio(ratio2)
                .setTimeOfDrivingStart(BTWTime.of(pd.sample()))
                .setDescriptor(vehicleDescriptorsFactory.get()));
    }

    public File generateToFile(File f) throws IOException {

        f.createNewFile();
        //print to file
        try {
            FileOutputStream fileWriter = new FileOutputStream(f);
            String startFeature = "[";
            fileWriter.write(startFeature.getBytes());
            fileWriter.flush();
            List<VehicleEntry> lst = generateList();
            if (lst.size() > 0) {
                VehicleEntry ve = lst.get(0);
                fileWriter.write((ve.jsonString()).getBytes());
                fileWriter.flush();
            }
            lst.subList(1, lst.size() - 1).forEach(vehicleEntry -> {
                try {
                    fileWriter.write(("," + vehicleEntry.jsonString()).getBytes());
                    fileWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            String endFeature = "]";
            fileWriter.write(endFeature.getBytes());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }
}
