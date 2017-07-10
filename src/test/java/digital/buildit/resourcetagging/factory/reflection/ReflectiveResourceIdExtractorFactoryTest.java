package digital.buildit.resourcetagging.factory.reflection;

import digital.buildit.resourcetagging.event.GenericEventExtractor;
import digital.buildit.resourcetagging.event.idextractors.ResourceIdExtractor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GenericEventExtractor.class)
public class ReflectiveResourceIdExtractorFactoryTest {

    private ReflectiveResourceIdExtractorFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new ReflectiveResourceIdExtractorFactory("digital.buildit.resourcetagging.event.idextractors");
    }

    @Test
    public void instantiate() {
        ResourceIdExtractor resourceIdExtractor = factory.createInstance("Test");
        assertNotNull(resourceIdExtractor);
    }
}