package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.Road;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;


/**
 * Implementation of the navigation manager
 */
public class NaiveNavigationManager extends AbstractNavigationManager {
    final static Logger logger = Logger.getLogger(NaiveNavigationManager.class);

    public NaiveNavigationManager(BTWDataBase db) {
        super(db);
    }

    @Override
    public synchronized Navigator getNavigator(VehicleDescriptor vehicleDescriptor, Road source, double sourceRoadRatio, Road destination, double destinationRoadRatio, BTWTime time) throws PathNotFoundException{
        logger.debug("Create new navigator");
        Navigator navigator = new NaiveNavigator(this.staticAStar(source, destination, sourceRoadRatio, time));
        logger.debug("Navigator created successfully");
        return navigator;
    }
}
