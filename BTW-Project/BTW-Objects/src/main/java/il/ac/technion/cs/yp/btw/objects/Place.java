package il.ac.technion.cs.yp.btw.objects;

public class Place implements IPlace {

    private int id;
    private String name;
    private Location location;
    private Road road;

    public Place(int id, String name, Location location){
        this.id=id;
        this.name=name;
        this.location=location;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Location getlocation(){
        return location;
    }
}