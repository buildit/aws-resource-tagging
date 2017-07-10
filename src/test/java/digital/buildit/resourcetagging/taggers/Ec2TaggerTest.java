package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AmazonEC2ClientBuilder.class})
public class Ec2TaggerTest {

    private Ec2Tagger tagger;
    private AmazonEC2 ec2Client;

    @Before
    public void setUp() throws Exception {
        tagger = new Ec2Tagger() { };
        ec2Client = mock(AmazonEC2.class);

        mockStatic(AmazonEC2ClientBuilder.class);
        PowerMockito.when(AmazonEC2ClientBuilder.defaultClient()).thenReturn(ec2Client);
    }

    @Test
    public void tag() throws Exception {
        tagger.tag(Collections.singletonList("ec2resource"), "user");

        ArgumentCaptor<CreateTagsRequest> createTagsRequest =
                ArgumentCaptor.forClass(CreateTagsRequest.class);

        verify(ec2Client, times(1)).createTags(createTagsRequest.capture());

        CreateTagsRequest actualRequest = createTagsRequest.getValue();
        assertTrue(actualRequest.getTags().contains(new Tag(ResourceTagger.USER_ARN_TAG_KEY, "user")));
        assertTrue(actualRequest.getResources().contains("ec2resource"));
    }
}