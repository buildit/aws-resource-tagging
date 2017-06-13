package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketTaggingConfiguration;
import digital.buildit.resourcetagging.event.EventExtractor;
import org.apache.log4j.Logger;

/**
 * Tags S3 buckets
 * Created by will on 08/06/2017.
 */
public class S3Tagger extends AbstractTagger {

    private static final Logger LOG = Logger.getLogger(S3Tagger.class);

    public S3Tagger(EventExtractor eventExtractor) {
        super(eventExtractor);

    }

    @Override
    public void tag() {

        String bucketName = extractBucketName();

        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
        BucketTaggingConfiguration bucketTaggingConfiguration = s3Client.getBucketTaggingConfiguration(bucketName);
        bucketTaggingConfiguration
                .getTagSet()
                .getAllTags()
                .put(USER_ARN_TAG_KEY, extractUserArn());

        LOG.info("Updating " + bucketName + " with bucketConfiguration " + bucketTaggingConfiguration.toString());

        s3Client.setBucketTaggingConfiguration(bucketName, bucketTaggingConfiguration);
    }

    private String extractBucketName() {
        return eventExtractor.extractBucketName();
    }
}
