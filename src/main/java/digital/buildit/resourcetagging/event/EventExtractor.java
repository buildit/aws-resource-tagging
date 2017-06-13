package digital.buildit.resourcetagging.event;

import java.util.List;

/**
 * Extracts relevant values from events
 * Created by will on 08/06/2017.
 */
public interface EventExtractor {

    String extractEventName();
    String extractUserArn();
    String extractBucketName();
    String extractLambdaFunctionARN();
    String extractNetworkInterfaceId();
    List<String> extractInstanceIds();
    String extractInternetGateway();
    String extractRouteTable();
    String extractVPCId();
    String extractSubnetId();
}
