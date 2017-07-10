package digital.buildit.resourcetagging.factory.reflection;

import digital.buildit.resourcetagging.event.idextractors.ResourceIdExtractor;
import digital.buildit.resourcetagging.factory.ResourceIdExtractorFactory;

public class ReflectiveResourceIdExtractorFactory implements ResourceIdExtractorFactory {
    private static final String DEFAULT_PKG = "digital.buildit.resourcetagging.event.idextractors";
    private final String pkg;

    /**
     * @param pkg Package to scan for <code>ResourceIdExtractor</code>implementors.
     */
    public ReflectiveResourceIdExtractorFactory(String pkg) {
        this.pkg = pkg;
    }

    /**
     * Constructs with default package.
     */
    public ReflectiveResourceIdExtractorFactory() {
        pkg = DEFAULT_PKG;
    }

    public ResourceIdExtractor createInstance(String eventName) {
        try {
            String className = pkg + "." + eventName + "IdExtractor";
            return (ResourceIdExtractor) Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
