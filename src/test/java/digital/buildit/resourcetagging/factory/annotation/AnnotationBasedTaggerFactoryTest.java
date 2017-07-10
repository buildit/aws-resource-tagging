package digital.buildit.resourcetagging.factory.annotation;

import digital.buildit.resourcetagging.factory.TaggerFactory;
import digital.buildit.resourcetagging.taggers.ResourceTagger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AnnotationBasedTaggerFactoryTest {

    private TaggerFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new AnnotationBasedTaggerFactory();
    }

    @Test
    public void createInstance() throws Exception {
        ResourceTagger instance = factory.createInstance("testing");
        assertNotNull(instance);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInstance_MissingClass() throws Exception {
        ResourceTagger instance = factory.createInstance("not there");
        assertNotNull(instance);
    }

    @Test(expected = IllegalStateException.class)
    public void createInstance_AmbiguousImplementations() throws Exception {
        ResourceTagger instance = factory.createInstance("dupe");
        assertNotNull(instance);
    }
}