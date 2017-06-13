package digital.buildit.resourcetagging.taggers;

/**
 * Defines the ability to tag an aws resource
 * Created by will on 08/06/2017.
 */
public interface Tagger {

    /**
     * Execute the tagging
     */
    void tag();
}
