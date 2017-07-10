package digital.buildit.resourcetagging.factory;

import digital.buildit.resourcetagging.event.idextractors.ResourceIdExtractor;

public interface ResourceIdExtractorFactory {
    ResourceIdExtractor createInstance(String eventName);
}
