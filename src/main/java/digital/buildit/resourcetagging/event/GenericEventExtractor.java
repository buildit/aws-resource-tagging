package digital.buildit.resourcetagging.event;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

/**
 * Extracts relevant values from events.
 */
public class GenericEventExtractor {

    private final ReadContext ctx;

    public GenericEventExtractor(String jsonEvent) {
        ctx = JsonPath.parse(jsonEvent);
    }

    public String extractEventName() {
        return ctx.read("$.detail.eventName");
    }

    public String extractUserArn() {
        return ctx.read("$.detail.userIdentity.arn");
    }
}
