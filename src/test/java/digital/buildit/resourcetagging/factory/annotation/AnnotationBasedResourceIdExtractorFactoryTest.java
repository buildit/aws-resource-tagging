package digital.buildit.resourcetagging.factory.annotation;

import digital.buildit.resourcetagging.event.idextractors.ResourceIdExtractor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AnnotationBasedResourceIdExtractorFactoryTest {

    private AnnotationBasedResourceIdExtractorFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new AnnotationBasedResourceIdExtractorFactory();
    }

    @Test
    public void createInstance() throws Exception {
        ResourceIdExtractor instance = factory.createInstance("testing");
        assertNotNull(instance);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInstance_MissingClass() throws Exception {
        ResourceIdExtractor instance = factory.createInstance("not there");
        assertNotNull(instance);
    }

    @Test(expected = IllegalStateException.class)
    public void createInstance_AmbiguousImplementations() throws Exception {
        ResourceIdExtractor instance = factory.createInstance("dupe");
        assertNotNull(instance);
    }
}