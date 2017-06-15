package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketTaggingConfiguration;
import com.amazonaws.services.s3.model.TagSet;
import digital.buildit.resourcetagging.event.IdentifierSupplier;
import org.apache.log4j.Logger;

/**
 * Tags S3 buckets
 * Created by will on 08/06/2017.
 */
public class S3Tagger extends AbstractTagger<String> {

    private static final Logger LOG = Logger.getLogger(S3Tagger.class);

    public S3Tagger(IdentifierSupplier<String> idSupplier) {
        super(idSupplier);
    }

    @Override
    public void tag(String userARN) {
        String bucketName = idSupplier.get();

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
