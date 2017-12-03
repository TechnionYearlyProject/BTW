package il.ac.technion.cs.yp.btw.objects;

interface IRoad{

    public int getId();
    public String getName();
    public int getStartAddress();
    public int getEndAddress();
    public ITrafficLight getStartPoint();
    public ITrafficLight getEndPoint();
    public double getLength();
}