package digital.buildit.resourcetagging.factory;

import digital.buildit.resourcetagging.event.EventExtractor;
import digital.buildit.resourcetagging.taggers.AbstractEc2Tagger;
import digital.buildit.resourcetagging.taggers.LambdaTagger;
import digital.buildit.resourcetagging.taggers.S3Tagger;
import digital.buildit.resourcetagging.taggers.Tagger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Creates Tagger instances based on the event source and action performed.
 * Created by will on 08/06/2017.
 */
public class TaggerFactory {

    private Map<String, Tagger> taggers = new HashMap<>();
    private EventExtractor eventExtractor;

    public TaggerFactory(EventExtractor eventExtractor) {
        this.eventExtractor = eventExtractor;

        taggers.put("CreateBucket", new S3Tagger(eventExtractor));
        taggers.put("CreateFunction20150331", new LambdaTagger(eventExtractor));

        taggers.put("RunInstances", new AbstractEc2Tagger(eventExtractor) {
            @Override
            protected List<String> getResourceIds() {
                return eventExtractor.extractInstanceIds();
            }
        });
        taggers.put("CreateNetworkInterface", new AbstractEc2Tagger(eventExtractor) {
            @Override
            protected List<String> getResourceIds() {
                return Collections.singletonList(eventExtractor.extractNetworkInterfaceId());
            }
        });
        taggers.put("CreateInternetGateway", new AbstractEc2Tagger(eventExtractor) {
            @Override
            protected List<String> getResourceIds() {
                return Collections.singletonList(eventExtractor.extractInternetGateway());
            }
        });
        taggers.put("CreateRouteTable", new AbstractEc2Tagger(eventExtractor) {
            @Override
            protected List<String> getResourceIds() {
                return Collections.singletonList(eventExtractor.extractRouteTable());
            }
        });
        taggers.put("CreateSubnet", new AbstractEc2Tagger(eventExtractor) {
            @Override
            protected List<String> getResourceIds() {
                return Collections.singletonList(eventExtractor.extractSubnetId());
            }
        });
        taggers.put("CreateVpc", new AbstractEc2Tagger(eventExtractor) {
            @Override
            protected List<String> getResourceIds() {
                return Collections.singletonList(eventExtractor.extractVPCId());
            }
        });

    }

    public Tagger createTagger() {
        String eventName = eventExtractor.extractEventName();

        Tagger tagger = taggers.get(eventName);
        if (tagger == null) {
            throw new UnsupportedOperationException("No appropriate Tagger found for this source " + eventName);
        }
        else {
            return tagger;
        }

    }
}
