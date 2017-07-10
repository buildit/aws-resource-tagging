package digital.buildit.resourcetagging.factory.reflection;

import digital.buildit.resourcetagging.event.GenericEventExtractor;
import digital.buildit.resourcetagging.factory.TaggerFactory;
import digital.buildit.resourcetagging.taggers.ResourceTagger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GenericEventExtractor.class)
public class ReflectiveTaggerFactoryTest {

    private TaggerFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new ReflectiveTaggerFactory("digital.buildit.resourcetagging.taggers");
    }

    @Test
    public void instantiate() {
        ResourceTagger resourceTagger = factory.createInstance("Test");
        assertNotNull(resourceTagger);
    }
}