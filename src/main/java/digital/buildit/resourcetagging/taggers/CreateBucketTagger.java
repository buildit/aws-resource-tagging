package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketTaggingConfiguration;
import com.amazonaws.services.s3.model.TagSet;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Tags S3 buckets
 */
@SuppressWarnings("unused")
public class CreateBucketTagger implements ResourceTagger {

    private static final Logger LOG = Logger.getLogger(CreateBucketTagger.class);

    @Override
    public void tag(List<String> resourceIds, String userARN) {
        String bucketName = resourceIds.get(0);

        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
        LOG.info("Fetching bucket tags for: " + bucketName);

        BucketTaggingConfiguration bucketTaggingConfiguration = s3Client.getBucketTaggingConfiguration(bucketName);
        if (bucketTaggingConfiguration == null) {
            bucketTaggingConfiguration = new BucketTaggingConfiguration();
            bucketTaggingConfiguration.withTagSets(new TagSet());
        }
        bucketTaggingConfiguration
                .getTagSet()
                .getAllTags()
                .put(USER_ARN_TAG_KEY, userARN);

        LOG.info("Updating " + bucketName + " with bucketConfiguration " + bucketTaggingConfiguration.toString());

        s3Client.setBucketTaggingConfiguration(bucketName, bucketTaggingConfiguration);
    }
}
