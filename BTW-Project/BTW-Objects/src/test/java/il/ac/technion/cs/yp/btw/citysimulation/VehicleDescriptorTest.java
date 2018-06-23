package il.ac.technion.cs.yp.btw.citysimulation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Guy Rephaeli
 * @date 22-Jun-18.
 */
public class VehicleDescriptorTest {
    private VehicleDescriptorFactory wrapperFactory;
    private static Integer currDescID = 0;

    public VehicleDescriptorTest() {
        this.wrapperFactory = Mockito.mock(VehicleDescriptorFactory.class);
        this.configMock();
    }

    private void configMock() {
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

        Mockito.when(wrapperFactory.get())
                .thenAnswer(invocation -> {
                    currDescID++;
                    VehicleDescriptorFactory tmpFactory = new VehicleDescriptorFactory();
                    return tmpFactory.get();
        });

        Mockito.when(wrapperFactory.get(captor.capture()))
                .thenAnswer(invocation -> {
                    VehicleDescriptorFactory tmpFactory = new VehicleDescriptorFactory();
                    return tmpFactory.get(captor.getValue());
                });
    }

    @Test
    public void testFactoryOrder() {
        for (int i = 0; i < 10; i++) {
            VehicleDescriptor descriptor = this.wrapperFactory.get();
            Assert.assertEquals(currDescID, descriptor.getID());
        }
    }

    @Test
    public void testExistingDescriptor() {
        VehicleDescriptor descriptor1 = this.wrapperFactory.get();
        VehicleDescriptor descriptor2 = this.wrapperFactory.get(currDescID);
        Assert.assertTrue(descriptor1.equals(descriptor2));
    }

    @Test(expected = NoSuchVehicleException.class)
    public void testNonExistingDescriptor() {
        this.wrapperFactory.get(currDescID + 1);
    }

    @Test
    public void testNonEqualDescriptors() {
        VehicleDescriptor descriptor1 = this.wrapperFactory.get();
        VehicleDescriptor descriptor2 = this.wrapperFactory.get();
        Assert.assertFalse(descriptor1.equals(descriptor2));
    }

    @Test
    public void testNotEqualsObject() {
        VehicleDescriptor descriptor1 = this.wrapperFactory.get();
        Assert.assertFalse(descriptor1.equals(currDescID));
    }

    @Test
    public void testEqualsHashCode() {
        VehicleDescriptor descriptor1 = this.wrapperFactory.get();
        VehicleDescriptor descriptor2 = this.wrapperFactory.get(currDescID);
        Assert.assertEquals(descriptor1.hashCode(), descriptor2.hashCode());
    }
}
