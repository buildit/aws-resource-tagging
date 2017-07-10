package digital.buildit.resourcetagging.factory.annotation;

import digital.buildit.resourcetagging.factory.Tagger;
import digital.buildit.resourcetagging.taggers.ResourceTagger;

import java.util.List;

@Tagger(eventName = "testing")
public class TestResourceTagger implements ResourceTagger {

    @Override
    public void tag(List<String> resourceIds, String userARN) {
    }
}
