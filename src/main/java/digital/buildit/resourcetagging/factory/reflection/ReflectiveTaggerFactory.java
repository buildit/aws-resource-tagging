package digital.buildit.resourcetagging.factory.reflection;

import digital.buildit.resourcetagging.factory.TaggerFactory;
import digital.buildit.resourcetagging.taggers.ResourceTagger;

public class ReflectiveTaggerFactory implements TaggerFactory {
    private static final String DEFAULT_PKG = "digital.buildit.resourcetagging.taggers";
    private final String pkg;

    /**
     * @param pkg Package to scan for <code>ResourceTagger</code>implementors.
     */
    public ReflectiveTaggerFactory(String pkg) {
        this.pkg = pkg;
    }

    /**
     * Constructs with default package.
     */
    public ReflectiveTaggerFactory() {
        pkg = DEFAULT_PKG;
    }

    @Override
    public ResourceTagger createInstance(String eventName) {
        try {
            String className = pkg + "." + eventName + "Tagger";
            return (ResourceTagger) Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
