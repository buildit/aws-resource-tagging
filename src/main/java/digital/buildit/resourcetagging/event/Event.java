package digital.buildit.resourcetagging.event;

/**
 * Wrapper around and sns message
 * Created by will on 15/06/2017.
 */
public class Event {

    private String snsMessage;

    public Event(String snsMessage) {
        this.snsMessage = snsMessage;
    }

    public String getSnsMessage() {
        return snsMessage;
    }
}
