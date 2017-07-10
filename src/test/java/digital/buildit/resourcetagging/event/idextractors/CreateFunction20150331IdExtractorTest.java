package digital.buildit.resourcetagging.event.idextractors;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static digital.buildit.resourcetagging.TestHelper.getFileAsString;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class CreateFunction20150331IdExtractorTest {
    private ResourceIdExtractor extractor;

    @Before
    public void setUp() throws Exception {
        extractor = new CreateFunction20150331IdExtractor();
    }

    @Test
    public void extractResourceIds() throws Exception {
        List<String> ids = extractor.extractResourceIds(getFileAsString("/unit/createFunction.json"));
        assertEquals(singletonList("arn:aws:lambda:some-region-2:000000000001:function:testFunction"), ids);
    }
}