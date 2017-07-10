package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.CreateTagsResult;
import com.amazonaws.services.ec2.model.Tag;
import org.apache.log4j.Logger;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Tags ec2 resources.
 */
public abstract class Ec2Tagger implements ResourceTagger {

    private static final Logger LOG = Logger.getLogger(Ec2Tagger.class);

    @Override
    public void tag(List<String> resourceIds, String userARN) {
        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.defaultClient();
        Tag tag = new Tag(USER_ARN_TAG_KEY, userARN);
        LOG.info("Tagging resource(s) " + resourceIds);
        CreateTagsRequest createTagsRequest = new CreateTagsRequest(resourceIds, singletonList(tag));

        CreateTagsResult result = ec2Client.createTags(createTagsRequest);
        LOG.info("Tagging result:" + result);
    }
}
