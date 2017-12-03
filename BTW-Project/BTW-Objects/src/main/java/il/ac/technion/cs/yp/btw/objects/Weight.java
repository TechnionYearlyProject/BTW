package il.ac.technion.cs.yp.btw.objects;

import java.sql.*
/*a weight of a road*/
public class Weight {
    private int id;
    private Time time;
    private double value;

    public Weight(int id, double traffic, Time time){
        this.id=id;
        this.traffic=traffic;
        this.time=time;
    }

    public int getId(){
        return id;
    }

    public double getValue(){
        return value;
    }

    public Time getTime(){
        return time;
    }
}