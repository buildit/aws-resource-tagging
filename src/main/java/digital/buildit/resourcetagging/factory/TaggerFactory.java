package digital.buildit.resourcetagging.factory;

import digital.buildit.resourcetagging.taggers.ResourceTagger;

public interface TaggerFactory {
    ResourceTagger createInstance(String eventName);
}
