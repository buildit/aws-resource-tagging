package digital.buildit.resourcetagging;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import digital.buildit.resourcetagging.event.GenericEventExtractor;
import digital.buildit.resourcetagging.event.idextractors.ResourceIdExtractor;
import digital.buildit.resourcetagging.factory.GenericEventExtractorFactory;
import digital.buildit.resourcetagging.factory.ResourceIdExtractorFactory;
import digital.buildit.resourcetagging.factory.TaggerFactory;
import digital.buildit.resourcetagging.factory.reflection.ReflectiveResourceIdExtractorFactory;
import digital.buildit.resourcetagging.factory.reflection.ReflectiveTaggerFactory;
import digital.buildit.resourcetagging.taggers.ResourceTagger;
import org.apache.log4j.Logger;

import java.util.List;

public class Handler implements RequestHandler<SNSEvent, Void> {

	private static final Logger LOG = Logger.getLogger(Handler.class);

	private GenericEventExtractorFactory genericEventExtractorFactory = new GenericEventExtractorFactory();
	private ResourceIdExtractorFactory resourceIdExtractorFactory = new ReflectiveResourceIdExtractorFactory();
	private TaggerFactory taggerFactory = new ReflectiveTaggerFactory();

    @Override
	public Void handleRequest(SNSEvent event, Context context) {

		event.getRecords()
				.forEach(record -> {
					String message = record.getSNS().getMessage();
					LOG.info("Received event record: " + message);

                    GenericEventExtractor genericExtractor = genericEventExtractorFactory.getInstance(message);
                    String eventName = genericExtractor.extractEventName();
					String userARN = genericExtractor.extractUserArn();

					ResourceIdExtractor resourceIdExtractor = resourceIdExtractorFactory.createInstance(eventName);
					List<String> resourceIds = resourceIdExtractor.extractResourceIds(message);

					ResourceTagger resourceTagger = taggerFactory.createInstance(eventName);
					resourceTagger.tag(resourceIds, userARN);
				});

		//Meh Generics!
		return null;
	}

	void setResourceIdExtractorFactory(ResourceIdExtractorFactory resourceIdExtractorFactory) {
		this.resourceIdExtractorFactory = resourceIdExtractorFactory;
	}

	void setTaggerFactory(TaggerFactory taggerFactory) {
		this.taggerFactory = taggerFactory;
	}

    void setGenericEventExtractorFactory(GenericEventExtractorFactory genericEventExtractorFactory) {
        this.genericEventExtractorFactory = genericEventExtractorFactory;
    }
}
