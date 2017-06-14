package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.Tag;
import digital.buildit.resourcetagging.event.IdentifierSupplier;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

/**
 * Tags ec2 resources.
 * Created by will on 09/06/2017.
 */
public class Ec2Tagger extends AbstractTagger<List<String>> {

    private static final Logger LOG = Logger.getLogger(Ec2Tagger.class);

    public Ec2Tagger(IdentifierSupplier<List<String>> idSupplier) {
        super(idSupplier);
    }

    @Override
    public void tag(String userARN) {

        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.defaultClient();
        Tag tag = new Tag(USER_ARN_TAG_KEY, userARN);
        CreateTagsRequest createTagsRequest = new CreateTagsRequest(idSupplier.get(), Collections.singletonList(tag));
        ec2Client.createTags(createTagsRequest);
    }
}
