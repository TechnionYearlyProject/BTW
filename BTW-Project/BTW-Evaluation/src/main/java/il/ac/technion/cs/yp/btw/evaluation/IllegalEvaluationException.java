package il.ac.technion.cs.yp.btw.evaluation;

import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;

/**
 * @author Guy Rephaeli
 * @date 08-Jun-18.
 */
public class IllegalEvaluationException extends RuntimeException {
    protected IllegalEvaluationException(String message) {
        super(message);
    }
}

class NoSuchDescriptorException extends IllegalEvaluationException {
    NoSuchDescriptorException(String descNum) {
        super("Vehicle with descriptor number " + descNum + " should not exist");
    }
}

class NoSuchRoadException extends IllegalEvaluationException {
    NoSuchRoadException(String roadName) {
        super("Road with name \"" + roadName + "\" should not exist");
    }
}

class NoSuchTrafficLightException extends IllegalEvaluationException {
    NoSuchTrafficLightException(String tlName) {
        super("Traffic-light with name \"" + tlName + "\" should not exist");
    }
}

class DescriptorAlreadySeenException extends IllegalEvaluationException {
    DescriptorAlreadySeenException(String descNum) {
        super("Vehicle with descriptor number " + descNum + " was already seen");
    }
}

