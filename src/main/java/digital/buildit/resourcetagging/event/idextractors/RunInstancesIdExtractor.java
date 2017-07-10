package digital.buildit.resourcetagging.event.idextractors;

import java.util.List;

public class RunInstancesIdExtractor extends AbstractResourceIdExtractor implements ResourceIdExtractor {

    public List<String> extractResourceIds(String jsonEvent) {
        return extractListFromJson(jsonEvent, "$.detail.responseElements.instancesSet.items[*].instanceId");
    }
}
