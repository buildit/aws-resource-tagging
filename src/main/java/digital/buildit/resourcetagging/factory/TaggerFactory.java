package digital.buildit.resourcetagging.factory;

import digital.buildit.resourcetagging.event.Event;
import digital.buildit.resourcetagging.event.EventExtractor;
import digital.buildit.resourcetagging.event.JsonEventExtractor;
import digital.buildit.resourcetagging.taggers.Ec2Tagger;
import digital.buildit.resourcetagging.taggers.LambdaTagger;
import digital.buildit.resourcetagging.taggers.S3Tagger;
import digital.buildit.resourcetagging.taggers.Tagger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.Collections.singletonList;


/**
 * Creates Tagger instances based on the event source and action performed.
 * Created by will on 08/06/2017.
 */
public class TaggerFactory {

    private static Map<String, Tagger> taggers = new HashMap<>();

    static {
        //Function to convert event -> event extractor.
        Function<Event, EventExtractor> extractorConversion = event -> new JsonEventExtractor(event.getSnsMessage());

        taggers.put("CreateBucket", new S3Tagger(extractorConversion.andThen(EventExtractor::extractBucketName)));
        taggers.put("CreateFunction20150331", new LambdaTagger(extractorConversion.andThen(EventExtractor::extractLambdaFunctionARN)));
        taggers.put("RunInstances", new Ec2Tagger(extractorConversion.andThen(EventExtractor::extractInstanceIds)));
        taggers.put("CreateNetworkInterface", new Ec2Tagger(extractorConversion.andThen(eventExtractor -> singletonList(eventExtractor.extractNetworkInterfaceId()))));
        taggers.put("CreateInternetGateway", new Ec2Tagger(extractorConversion.andThen(eventExtractor -> singletonList(eventExtractor.extractInternetGateway()))));
        taggers.put("CreateRouteTable", new Ec2Tagger(extractorConversion.andThen(eventExtractor -> singletonList(eventExtractor.extractRouteTable()))));
        taggers.put("CreateSubnet", new Ec2Tagger(extractorConversion.andThen(eventExtractor -> singletonList(eventExtractor.extractSubnetId()))));
        taggers.put("CreateVpc", new Ec2Tagger(extractorConversion.andThen(eventExtractor -> singletonList(eventExtractor.extractVPCId()))));
    }

    public Tagger loadTagger(String eventName) {
        Tagger tagger = taggers.get(eventName);
        if (tagger == null) {
            throw new UnsupportedOperationException("No appropriate Tagger found for this source " + eventName);
        }
        else {
            return tagger;
        }
    }
}
