package digital.buildit.resourcetagging;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import digital.buildit.resourcetagging.event.GenericEventExtractor;
import digital.buildit.resourcetagging.event.idextractors.ResourceIdExtractor;
import digital.buildit.resourcetagging.factory.GenericEventExtractorFactory;
import digital.buildit.resourcetagging.factory.reflection.ReflectiveTaggerFactory;
import digital.buildit.resourcetagging.factory.ResourceIdExtractorFactory;
import digital.buildit.resourcetagging.factory.TaggerFactory;
import digital.buildit.resourcetagging.taggers.ResourceTagger;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

public class HandlerTest {

    private static final SNSEvent.SNSRecord SNS_RECORD_1 = mock(SNSEvent.SNSRecord.class);
    private static final SNSEvent.SNSRecord SNS_RECORD_2 = mock(SNSEvent.SNSRecord.class);
    private static final SNSEvent.SNS SNS_1 = mock(SNSEvent.SNS.class);
    private static final SNSEvent.SNS SNS_2 = mock(SNSEvent.SNS.class);
    private static final String SNS_MESSAGE_1 = "message 1";
    private static final String SNS_MESSAGE_2 = "message 2";

    private Handler handler;
    private ResourceIdExtractorFactory resourceIdExtractorFactory;
    private TaggerFactory taggerFactory;
    private GenericEventExtractor genericExtractor;
    private GenericEventExtractorFactory genericEventExtractorFactory;

    @Before
    public void setUp() throws Exception {
        when(SNS_RECORD_1.getSNS()).thenReturn(SNS_1);
        when(SNS_1.getMessage()).thenReturn(SNS_MESSAGE_1);

        when(SNS_RECORD_2.getSNS()).thenReturn(SNS_2);
        when(SNS_2.getMessage()).thenReturn(SNS_MESSAGE_2);

        handler = new Handler();
        genericEventExtractorFactory = mock(GenericEventExtractorFactory.class);
        handler.setGenericEventExtractorFactory(genericEventExtractorFactory);

        resourceIdExtractorFactory = mock(ResourceIdExtractorFactory.class);
        handler.setResourceIdExtractorFactory(resourceIdExtractorFactory);

        taggerFactory = mock(ReflectiveTaggerFactory.class);
        handler.setTaggerFactory(taggerFactory);
        genericExtractor = mock(GenericEventExtractor.class);
    }

    @Test
    public void handleOneRequest() throws Exception {
        SNSEvent event = mock(SNSEvent.class);
        when(event.getRecords()).thenReturn(Collections.singletonList(SNS_RECORD_1));

        when(genericEventExtractorFactory.getInstance(SNS_MESSAGE_1)).thenReturn(genericExtractor);
        when(genericExtractor.extractEventName()).thenReturn("FooEvent");
        when(genericExtractor.extractUserArn()).thenReturn("FooArn");

        ResourceIdExtractor resourceIdExtractor = mock(ResourceIdExtractor.class);
        when(resourceIdExtractorFactory.createInstance("FooEvent")).thenReturn(resourceIdExtractor);
        List<String> resourceIds = asList("id1", "id2", "id3");
        when(resourceIdExtractor.extractResourceIds(SNS_MESSAGE_1)).thenReturn(resourceIds);

        ResourceTagger resourceTagger = mock(ResourceTagger.class);
        when(taggerFactory.createInstance("FooEvent")).thenReturn(resourceTagger);

        handler.handleRequest(event, null);

        verify(resourceTagger, times(1)).tag(resourceIds, "FooArn");
    }
}