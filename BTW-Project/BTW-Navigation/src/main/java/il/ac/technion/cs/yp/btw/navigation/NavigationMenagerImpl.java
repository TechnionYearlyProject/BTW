package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.BTWDataBase;


/**
 * Implementation of the navigation manager
 */
public class NavigationMenagerImpl implements NavigationManager {
    private final BTWDataBase database;

    public NavigationMenagerImpl(BTWDataBase db) {
        this.database = db.updateHeuristics();
    }

    @Override
    public Navigator getNavigator() {
        return new NavigatorImpl(database);
    }
}
