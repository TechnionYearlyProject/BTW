package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.Road;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class VehiclesGeneratorTest {
    @Test
    public void test1(){
        Set<Road> roadSet = new HashSet<>();
        Road r1 = Mockito.mock(Road.class);
        Mockito.when(r1.getName()).thenReturn("1 Street");
        roadSet.add(r1);
        Road r2 = Mockito.mock(Road.class);
        Mockito.when(r2.getName()).thenReturn("2 Street");
        roadSet.add(r2);
        Road r3 = Mockito.mock(Road.class);
        Mockito.when(r3.getName()).thenReturn("3 Street");
        roadSet.add(r3);
        VehiclesGenerator gen = new VehiclesGenerator(roadSet,50,BTWTime.of(9*3600),BTWTime.of(17*3600));
        List<VehicleEntry> lst = gen.generateList();
        assertEquals(lst.size(),50);
    }
}