package digital.buildit.resourcetagging.taggers;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.ListTagsRequest;
import com.amazonaws.services.lambda.model.ListTagsResult;
import com.amazonaws.services.lambda.model.TagResourceRequest;
import digital.buildit.resourcetagging.event.IdentifierSupplier;

import java.util.Map;

/**
 * Tags Lambda functions.
 * Created by will on 08/06/2017.
 */
public class LambdaTagger extends AbstractTagger<String> {

    public LambdaTagger(IdentifierSupplier<String> identifierSupplier) {
        super(identifierSupplier);
    }

    @Override
    public void tag(String userARN) {
        String functionARN = idSupplier.get();

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
