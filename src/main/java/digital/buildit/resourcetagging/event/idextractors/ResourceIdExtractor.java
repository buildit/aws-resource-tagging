package digital.buildit.resourcetagging.event.idextractors;

import java.util.List;

/**
 * Accepts a JSON-formatted CloudWatch event and extracts identifiers for the affected resource.
 */
public interface ResourceIdExtractor {
    List<String> extractResourceIds(String jsonEvent);
}
