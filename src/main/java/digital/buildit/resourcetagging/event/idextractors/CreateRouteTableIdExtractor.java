package digital.buildit.resourcetagging.event.idextractors;

import java.util.List;

import static java.util.Collections.singletonList;

public class CreateRouteTableIdExtractor extends AbstractResourceIdExtractor implements ResourceIdExtractor {

    public List<String> extractResourceIds(String jsonEvent) {
        return singletonList(extractSingleFromJson(jsonEvent, "$.detail.responseElements.routeTable.routeTableId"));
    }
}
