package digital.buildit.resourcetagging.event.idextractors;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static digital.buildit.resourcetagging.TestHelper.getFileAsString;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class RunInstancesIdExtractorTest {
    private ResourceIdExtractor extractor;

    @Before
    public void setUp() throws Exception {
        extractor = new RunInstancesIdExtractor();
    }

    @Test
    public void extractResourceIdsSingle() throws Exception {
        List<String> ids = extractor.extractResourceIds(getFileAsString("/unit/runInstancesSingle.json"));
        assertEquals(singletonList("i-0000001"), ids);
    }

    @Test
    public void extractResourceIdsMultiple() throws Exception {
        List<String> ids = extractor.extractResourceIds(getFileAsString("/unit/runInstancesMultiple.json"));
        assertEquals(asList("i-000001","i-000002","i-000003"), ids);
    }
}