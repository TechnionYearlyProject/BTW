package il.ac.technion.cs.yp.btw.objects;

/*represent a transition from one road to another*/
public class TrafficLight {

    private int id;
    private Road from;
    private Road to;
    private Map<Time, Weight>;


    public TrafficLight(int id, Road from, Road to){
        this.id=id;
        this.from=from;
        this.to=to;
    }

    public int getId(){
        return id;
    }

    public Road getFrom(){
        return from;
    }

    public Road getTo(){
        return to;
    }

    public double getWeight(Time time){

    }
}