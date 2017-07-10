package digital.buildit.resourcetagging.factory.annotation;

import digital.buildit.resourcetagging.event.idextractors.ResourceIdExtractor;
import digital.buildit.resourcetagging.factory.IdExtractor;

import java.util.List;

@IdExtractor(eventName = "dupe")
public class TestIdExtractorDupe1 implements ResourceIdExtractor {
    @Override
    public List<String> extractResourceIds(String jsonEvent) {
        return null;
    }
}
