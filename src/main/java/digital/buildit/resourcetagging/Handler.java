package digital.buildit.resourcetagging;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import digital.buildit.resourcetagging.event.Event;
import digital.buildit.resourcetagging.event.EventExtractor;
import digital.buildit.resourcetagging.event.JsonEventExtractor;
import digital.buildit.resourcetagging.factory.TaggerFactory;
import org.apache.log4j.Logger;

public class Handler implements RequestHandler<SNSEvent, Void> {

	private static final Logger LOG = Logger.getLogger(Handler.class);
    private static TaggerFactory taggerFactory = new TaggerFactory();

	@Override
	public Void handleRequest(SNSEvent snsEvent, Context context) {

		snsEvent.getRecords()
				.forEach(record -> {
					String message = record.getSNS().getMessage();
					LOG.info("Received event record: " + message);

                    EventExtractor eventExtractor = new JsonEventExtractor(message);
					String eventName = eventExtractor.extractEventName();
					String userARN = eventExtractor.extractUserArn();

					taggerFactory.loadTagger(eventName).tag(userARN, new Event(message));
				});

		//Meh Generics!
		return null;
	}
}
