package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.ListTagsRequest;
import com.amazonaws.services.lambda.model.ListTagsResult;
import com.amazonaws.services.lambda.model.TagResourceRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static digital.buildit.resourcetagging.taggers.ResourceTagger.USER_ARN_TAG_KEY;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AWSLambdaClientBuilder.class})
public class CreateFunction20150331TaggerTest {

    private CreateFunction20150331Tagger tagger;
    private AWSLambda lambdaClient;
    private ListTagsResult listTagsResult;

    @Before
    public void setUp() throws Exception {
        tagger = new CreateFunction20150331Tagger();
        lambdaClient = mock(AWSLambda.class);

        mockStatic(AWSLambdaClientBuilder.class);
        PowerMockito.when(AWSLambdaClientBuilder.defaultClient()).thenReturn(lambdaClient);
        listTagsResult = mock(ListTagsResult.class);
    }

    @Test
    public void tag() throws Exception {
        when(listTagsResult.getTags()).thenReturn(new HashMap<>());

        ListTagsRequest expectedListTagsRequest = new ListTagsRequest().withResource("functionId");
        when(lambdaClient.listTags(expectedListTagsRequest)).thenReturn(listTagsResult);

        tagger.tag(singletonList("functionId"), "user");

        ArgumentCaptor<TagResourceRequest> newResourceRequest =
                ArgumentCaptor.forClass(TagResourceRequest.class);

        verify(lambdaClient, times(1)).tagResource(newResourceRequest.capture());

        Map<String, String> tags = newResourceRequest.getValue().getTags();
        assertEquals(1, tags.size());
        assertTrue(tags.containsKey(USER_ARN_TAG_KEY));
        assertTrue(tags.containsValue("user"));
    }
}