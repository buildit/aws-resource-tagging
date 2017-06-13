package digital.buildit.resourcetagging.taggers;

import digital.buildit.resourcetagging.event.EventExtractor;

/**
 *
 * Created by will on 08/06/2017.
 */
abstract class AbstractTagger implements Tagger {

    final String USER_ARN_TAG_KEY = "OwnerARN";

    EventExtractor eventExtractor;

    AbstractTagger(EventExtractor eventExtractor) {
        this.eventExtractor = eventExtractor;
    }

    String extractUserArn() {
        return eventExtractor.extractUserArn();
    }


}
