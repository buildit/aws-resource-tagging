package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import digital.buildit.resourcetagging.event.EventExtractor;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

/**
 * Tags ec2 resources.
 * Created by will on 09/06/2017.
 */
public abstract class AbstractEc2Tagger extends AbstractTagger {

    private static final Logger LOG = Logger.getLogger(AbstractEc2Tagger.class);

    protected AbstractEc2Tagger(EventExtractor eventExtractor) {
        super(eventExtractor);
    }

    @Override
    public void tag() {

        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.defaultClient();
        Tag tag = new Tag(super.USER_ARN_TAG_KEY, eventExtractor.extractUserArn());
        CreateTagsRequest createTagsRequest = new CreateTagsRequest(getResourceIds(), Collections.singletonList(tag));
        ec2Client.createTags(createTagsRequest);
    }

    /**
     * Returns a list of ids for ec2 resources.
     * Multiple as many ec2 machines can be started in the same request.
     * @return The resource ids contained in this event.
     */
    protected abstract List<String> getResourceIds();



}
