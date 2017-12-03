package il.ac.technion.cs.yp.btw.objects;

public class Road implements IRoad{

    private int id;
    private String name;
    private int startAddress;
    private int endAddress;
    private int startPoint;
    private int endPoint;
    private double length; //in kilometers
    private Street street;

    public Road(int id, String name, int startAddress, int endAddress,TrafficLight startPoint,
                TrafficLight endPoint, double length) {

        this.id = id;
        this.name = name;
        this.startAddress=startAddress;
        this.endAddress=endAddress;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.length = length

        }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getStartAddress(){
        return startAddress;
    }

    public int getEndAddress(){
        return endAddress;
    }

    public ITrafficLight getStartPoint(){
        return startPoint;
    }

    public ITrafficLight getEndPoint(){
        return endPoint;
    }

    public double getLength(){
        return length;
    }
}