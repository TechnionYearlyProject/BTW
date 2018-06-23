package il.ac.technion.cs.yp.btw.citysimulation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class JsonVehiclesParser implements FileParser {
    private List<VehicleEntry> vehicleEntryList;

    public List<VehicleEntry> parseVehiclesFromFile(URL pathToFile){
        File vehiclesFile = new File(pathToFile.getFile());
        if(!vehiclesFile.exists()){
            throw new VehicleFileNotFoundException("path to vehicles file doesn't exist");
        }
        String name = vehiclesFile.getName();
        if(!name.endsWith(".json")){
            throw new VehicleFileNotOfJsonFormatException("vehicles file doesn't end with .json");
        }
        String fileContents = "";
        try {
            fileContents = new Scanner(vehiclesFile).useDelimiter("\\Z").next();
        } catch (FileNotFoundException ignored) {}
        return parseVehiclesFromData(fileContents);
    }

    List<VehicleEntry> parseVehiclesFromData(String jsonData) {
        this.vehicleEntryList = new ArrayList<>();
        Gson gs = new GsonBuilder().create();
        Type listType = new TypeToken<List>() {
        }.getType();
        List<Map<String, Object>> readFromJson;
        try{
            readFromJson = gs.fromJson(jsonData, listType);}
        catch (JsonSyntaxException e){
            throw new FileNotOfJsonSyntaxException(e.getMessage());
        }
        if (readFromJson != null)
            readFromJson
                    .forEach(entryAsMap -> this.vehicleEntryList.add(convertMapToEntry((Map<String, Object>) entryAsMap)));
        VehicleDescriptorFactory vdf = new VehicleDescriptorFactory();
        this.vehicleEntryList.forEach(vehicleEntry -> vehicleEntry.setDescriptor(vdf.get()));
        return this.vehicleEntryList;
    }
    private VehicleEntry convertMapToEntry(Map<String, Object> entryAsMap) {
        VehicleEntry entry = new VehicleEntry();
        if(entryAsMap.get("sourceRoad") == null)
            throw  new MandatoryFieldNotFoundException("source road field is missing");
        if(entryAsMap.get("sourceRatio") == null)
            throw  new MandatoryFieldNotFoundException("source ratio field is missing");
        if(entryAsMap.get("destinationRoad") == null)
            throw  new MandatoryFieldNotFoundException("destination road field is missing");
        if(entryAsMap.get("destinationRatio") == null)
            throw  new MandatoryFieldNotFoundException("destination ratio field is missing");
        if(entryAsMap.get("timeOfDrivingStart") == null)
            throw  new MandatoryFieldNotFoundException("time of driving start field is missing");
        return entry
                .setSourceRoadName((String) entryAsMap.get("sourceRoad"))
                .setSourceRoadRatio((Double) entryAsMap.get("sourceRatio"))
                .setDestinationRoadName((String) entryAsMap.get("destinationRoad"))
                .setDestinationRoadRatio((Double) entryAsMap.get("destinationRatio"))
                .setTimeOfDrivingStart((String) entryAsMap.get("timeOfDrivingStart"));
    }

    public List<VehicleEntry> getVehicleEntryList() {
        return vehicleEntryList;
    }

}
