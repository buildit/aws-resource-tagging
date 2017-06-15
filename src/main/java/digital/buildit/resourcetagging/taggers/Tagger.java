package digital.buildit.resourcetagging.taggers;

import digital.buildit.resourcetagging.event.Event;

/**
 * Defines the ability to tag an aws resource
 * Created by will on 08/06/2017.
 */
public interface Tagger {

    String USER_ARN_TAG_KEY = "OwnerARN";

    /**
     * Execute the tagging
     */
    void tag(String userARN, Event event);

}
