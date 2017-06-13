package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.ListTagsRequest;
import com.amazonaws.services.lambda.model.ListTagsResult;
import com.amazonaws.services.lambda.model.TagResourceRequest;
import digital.buildit.resourcetagging.event.EventExtractor;

import java.util.Map;

/**
 * Tags Lambda functions.
 * Created by will on 08/06/2017.
 */
public class LambdaTagger extends AbstractTagger {


    public LambdaTagger(EventExtractor eventExtractor) {
        super(eventExtractor);
    }

    @Override
    public void tag() {
        String functionARN = eventExtractor.extractLambdaFunctionARN();

        ListTagsRequest listTagsRequest = new ListTagsRequest();
        listTagsRequest.setResource(functionARN);
        AWSLambda awsLambda = AWSLambdaClientBuilder.defaultClient();
        ListTagsResult listTagsResult = awsLambda.listTags(listTagsRequest);

        Map<String, String> tags = listTagsResult.getTags();
        tags.put(USER_ARN_TAG_KEY, extractUserArn());

        TagResourceRequest tagResourceRequest = new TagResourceRequest();
        tagResourceRequest.setResource(functionARN);
        tagResourceRequest.setTags(tags);
        awsLambda.tagResource(tagResourceRequest);
    }
}
