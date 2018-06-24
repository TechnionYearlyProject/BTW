package il.ac.technion.cs.yp.btw.evaluation;

public class UnfinishedVehicleException extends IllegalEvaluationException {
    UnfinishedVehicleException(String descNum) {
        super("Vehicle with descriptor number " + descNum + " has not finished driving");
    }
}
