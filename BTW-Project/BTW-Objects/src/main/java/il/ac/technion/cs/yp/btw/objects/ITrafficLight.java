package il.ac.technion.cs.yp.btw.objects;

interface ITrafficLight{

    public int getId();
    public Road getFrom();
    public Road getTo();
    public double getWeight(Time time);
}