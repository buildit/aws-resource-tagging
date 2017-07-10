package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketTaggingConfiguration;
import com.amazonaws.services.s3.model.TagSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Map;

import static digital.buildit.resourcetagging.taggers.ResourceTagger.USER_ARN_TAG_KEY;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AmazonS3ClientBuilder.class})
public class CreateBucketTaggerTest {

    private static final String BUCKET_NAME = "bucketName";
    private static final String USER = "user";

    private CreateBucketTagger tagger;
    private AmazonS3 s3Client;

    @Before
    public void setUp() throws Exception {
        tagger = new CreateBucketTagger();
        s3Client = mock(AmazonS3.class);

        mockStatic(AmazonS3ClientBuilder.class);
        PowerMockito.when(AmazonS3ClientBuilder.defaultClient()).thenReturn(s3Client);
    }

    @Test
    public void tagExistingBucketConfiguration() throws Exception {
        testWithBucketConfiguration(new BucketTaggingConfiguration().withTagSets(new TagSet()));
    }

    @Test
    public void tagNewBucketConfiguration() throws Exception {
        testWithBucketConfiguration(null);
    }

    private void testWithBucketConfiguration(BucketTaggingConfiguration configuration) {
        when(s3Client.getBucketTaggingConfiguration(BUCKET_NAME)).thenReturn(configuration);

        tagger.tag(singletonList(BUCKET_NAME), USER);

        ArgumentCaptor<BucketTaggingConfiguration> newConfiguration =
                ArgumentCaptor.forClass(BucketTaggingConfiguration.class);

        verify(s3Client, times(1))
                .setBucketTaggingConfiguration(eq(BUCKET_NAME), newConfiguration.capture());

        Map<String, String> actualTags = newConfiguration.getValue().getTagSet().getAllTags();
        assertEquals(1, actualTags.size());
        assertTrue(actualTags.containsKey(USER_ARN_TAG_KEY));
        assertTrue(actualTags.containsValue(USER));
    }

}