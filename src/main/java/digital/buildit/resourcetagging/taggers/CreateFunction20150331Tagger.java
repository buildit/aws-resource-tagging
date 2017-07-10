package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.ListTagsRequest;
import com.amazonaws.services.lambda.model.ListTagsResult;
import com.amazonaws.services.lambda.model.TagResourceRequest;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Tags Lambda functions.
 */
@SuppressWarnings("unused")
public class CreateFunction20150331Tagger implements ResourceTagger {

    private static final Logger LOG = Logger.getLogger(CreateFunction20150331Tagger.class);

    public void tag(List<String> resourceIds, String userARN) {
        String functionARN = resourceIds.get(0);
        LOG.info("Fetching tags for function " + functionARN);

        AWSLambda awsLambda = AWSLambdaClientBuilder.defaultClient();

        ListTagsRequest listTagsRequest = new ListTagsRequest();
        listTagsRequest.setResource(functionARN);
        ListTagsResult listTagsResult = awsLambda.listTags(listTagsRequest);

        Map<String, String> tags = listTagsResult.getTags();
        tags.put(USER_ARN_TAG_KEY, userARN);

        TagResourceRequest tagResourceRequest = new TagResourceRequest();
        tagResourceRequest.setResource(functionARN);
        tagResourceRequest.setTags(tags);
        awsLambda.tagResource(tagResourceRequest);
    }
}
