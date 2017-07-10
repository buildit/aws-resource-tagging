package digital.buildit.resourcetagging.event.idextractors;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.util.List;

public class AbstractResourceIdExtractor {

    List<String> extractListFromJson(String jsonEvent, String path) {
        ReadContext ctx = JsonPath.parse(jsonEvent);
        return ctx.read(path);
    }

    String extractSingleFromJson(String jsonEvent, String path) {
        ReadContext ctx = JsonPath.parse(jsonEvent);
        return ctx.read(path);
    }
}
