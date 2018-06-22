package il.ac.technion.cs.yp.btw.app;

import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.StatisticalReport;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**@Author: Anat Tetroashvili
 * @Date: 30/5/18
 */
public class LearningSimulationController {
    Long timeOfRun;
    private Map<Road, StatisticalReport> roadsStatistics;
    private Map<TrafficLight, StatisticalReport> trafficLightsStatistics;
    BTWDataBase db;

    final static Logger logger = Logger.getLogger("LearningSimulationController");

    LearningSimulationController(Long timeOfRun, BTWDataBase db) {
        logger.debug("Initializing LearningSimulationController");
        this.timeOfRun = timeOfRun;
        this.roadsStatistics = new HashMap<>();
        this.trafficLightsStatistics = new HashMap<>();
        this.db = db;
    }

    void runLearningSimulation() {
        logger.debug("Start Learning Simulation");
        Long remainTime = timeOfRun;
        while (remainTime >= 1) {
            //do a tick, calculate statistics for that moment, add to maps
        }

    }

}
