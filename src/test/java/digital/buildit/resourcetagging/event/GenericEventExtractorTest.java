package digital.buildit.resourcetagging.event;

import org.junit.Test;

import static digital.buildit.resourcetagging.TestHelper.getFileAsString;
import static org.junit.Assert.assertEquals;

public class GenericEventExtractorTest {
    @Test
    public void extractFromVpcEvent() throws Exception {
        GenericEventExtractor eventExtractor = getExtractor("/unit/createVpc.json");
        assertEquals("CreateVpc", eventExtractor.extractEventName());
        assertEquals("arn:aws:iam::000000000001:user/testuser", eventExtractor.extractUserArn());
    }

    @Test
    public void extractFromNetworkInterfaceEvent() throws Exception {
        GenericEventExtractor eventExtractor = getExtractor("/unit/createNetworkInterface.json");
        assertEquals("CreateNetworkInterface", eventExtractor.extractEventName());
        assertEquals("arn:aws:iam::000000000001:user/testuser", eventExtractor.extractUserArn());
    }

    private GenericEventExtractor getExtractor(String eventJson) {
        String json = getFileAsString(eventJson);
        return new GenericEventExtractor(json);
    }
}