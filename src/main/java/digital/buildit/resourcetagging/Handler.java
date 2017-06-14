package digital.buildit.resourcetagging;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import digital.buildit.resourcetagging.event.EventExtractor;
import digital.buildit.resourcetagging.event.JsonEventExtractor;
import digital.buildit.resourcetagging.factory.TaggerFactory;
import org.apache.log4j.Logger;

public class Handler implements RequestHandler<SNSEvent, Void> {

	private static final Logger LOG = Logger.getLogger(Handler.class);

	@Override
	public Void handleRequest(SNSEvent event, Context context) {

		event.getRecords()
				.forEach(record -> {
					String message = record.getSNS().getMessage();
					LOG.info("Received event record: " + message);

					EventExtractor eventExtractor = new JsonEventExtractor(message);
					String eventName = eventExtractor.extractEventName();
					String userARN = eventExtractor.extractUserArn();

					new TaggerFactory(eventExtractor).loadTagger(eventName).tag(userARN);
				});

		//Meh Generics!
		return null;
	}
}
