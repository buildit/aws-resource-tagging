package digital.buildit.resourcetagging.event.idextractors;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static digital.buildit.resourcetagging.TestHelper.getFileAsString;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class CreateNetworkInterfaceIdExtractorTest {

    private ResourceIdExtractor extractor;

    @Before
    public void setUp() throws Exception {
        extractor = new CreateNetworkInterfaceIdExtractor();
    }

    @Test
    public void extractResourceIds() throws Exception {
        List<String> ids = extractor.extractResourceIds(getFileAsString("/unit/createNetworkInterface.json"));
        assertEquals(singletonList("eni-000001"), ids);
    }
}