package il.ac.technion.cs.yp.btw.citysimulation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Guy Rephaeli
 * @date 22-Jun-18.
 */
public class VehicleDescriptorTest {
    private VehicleDescriptorFactory testedFactory;
    public VehicleDescriptorTest() {
        this.testedFactory = new VehicleDescriptorFactory();
    }

    @Before
    public void setUp() {
        this.testedFactory = new VehicleDescriptorFactory();
    }

    @Test
    public void testFactoryOrder() {
        for (int i = 0; i < 10; i++) {
            VehicleDescriptor descriptor = this.testedFactory.get();
            Assert.assertEquals(Integer.valueOf(i), descriptor.getID());
        }
    }

    @Test
    public void testExistingDescriptor() {
        VehicleDescriptor descriptor1 = this.testedFactory.get();
        VehicleDescriptor descriptor2 = this.testedFactory.get(0);
        Assert.assertTrue(descriptor1.equals(descriptor2));
    }

    @Test(expected = NoSuchVehicleException.class)
    public void testNonExistingDescriptor() {
        this.testedFactory.get(0);
    }

    @Test
    public void testNonEqualDescriptors() {
        VehicleDescriptor descriptor1 = this.testedFactory.get();
        VehicleDescriptor descriptor2 = this.testedFactory.get();
        Assert.assertFalse(descriptor1.equals(descriptor2));
    }

    @Test
    public void testNotEqualsObject() {
        VehicleDescriptor descriptor1 = this.testedFactory.get();
        Assert.assertFalse(descriptor1.equals(0));
    }

    @Test
    public void testEqualsHashCode() {
        VehicleDescriptor descriptor1 = this.testedFactory.get();
        VehicleDescriptor descriptor2 = this.testedFactory.get(0);
        Assert.assertEquals(descriptor1.hashCode(), descriptor2.hashCode());
    }
}
