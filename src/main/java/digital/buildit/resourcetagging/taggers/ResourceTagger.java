package digital.buildit.resourcetagging.taggers;

import java.util.List;

/**
 * Defines the ability to tag an aws resource
 */
public interface ResourceTagger {

    String USER_ARN_TAG_KEY = "buildit:CreatorARN";

    /**
     * Applies an applicable "ownership" tag to resources.
     * @param resourceIds The resource IDs representing the resources to tag.
     * @param userARN The ARN of the user to be tagged to the resources.
     */
    void tag(List<String> resourceIds, String userARN);
}
