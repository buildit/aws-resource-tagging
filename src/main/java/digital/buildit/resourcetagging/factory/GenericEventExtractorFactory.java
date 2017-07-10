package digital.buildit.resourcetagging.factory;

import digital.buildit.resourcetagging.event.GenericEventExtractor;

// Here to support easy testing using a mock for this factory rather than hooking
// new instance creation.
public class GenericEventExtractorFactory {
    public GenericEventExtractor getInstance(String snsMessage) {
        return new GenericEventExtractor(snsMessage);
    }
}
