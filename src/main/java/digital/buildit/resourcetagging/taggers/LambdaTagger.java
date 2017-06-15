package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.ListTagsRequest;
import com.amazonaws.services.lambda.model.ListTagsResult;
import com.amazonaws.services.lambda.model.TagResourceRequest;
import digital.buildit.resourcetagging.event.Event;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.function.Function;

/**
 * Tags Lambda functions.
 * Created by will on 08/06/2017.
 */
public class LambdaTagger extends AbstractTagger<String> {

    private static final Logger LOG = Logger.getLogger(LambdaTagger.class);

    public LambdaTagger(Function<Event, String> identifierFunction) {
        super(identifierFunction);
    }

    @Override
    public void tag(String userARN, Event event) {
        String functionARN = identifierFunction.apply(event);
        LOG.info("Fetching tags for function " + functionARN);

        ListTagsRequest listTagsRequest = new ListTagsRequest();
        listTagsRequest.setResource(functionARN);
        AWSLambda awsLambda = AWSLambdaClientBuilder.defaultClient();
        ListTagsResult listTagsResult = awsLambda.listTags(listTagsRequest);

        Map<String, String> tags = listTagsResult.getTags();
        tags.put(USER_ARN_TAG_KEY, userARN);

        TagResourceRequest tagResourceRequest = new TagResourceRequest();
        tagResourceRequest.setResource(functionARN);
        tagResourceRequest.setTags(tags);
        awsLambda.tagResource(tagResourceRequest);
    }
}
