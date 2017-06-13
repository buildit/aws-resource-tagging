package digital.buildit.resourcetagging.event;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.util.List;

/**
 * Created by will on 08/06/2017.
 */
public class JsonEventExtractor implements EventExtractor {

    private ReadContext ctx;

    public JsonEventExtractor(String jsonEvent) {
        ctx = JsonPath.parse(jsonEvent);
    }

    /*** Generic ***/
    @Override
    public String extractEventName() {
        return ctx.read("$.details.eventName");
    }

    @Override
    public String extractUserArn() {
        return ctx.read("$.detail.userIdentity.arn");
    }


    /*** S3 Specific ***/
    @Override
    public String extractBucketName() {
        return ctx.read("$.detail.requestParameters.bucketName");
    }


    /*** Lambda Specific ***/
    @Override
    public String extractLambdaFunctionARN() {
        return ctx.read("$.detail.responseElements.functionArn");
    }


    /*** Ec2 Specific ***/

    @Override
    public String extractNetworkInterfaceId() {
        return ctx.read("$.detail.responseElements.networkInterface.networkInterfaceId");
    }

    @Override
    public List<String> extractInstanceIds() {
        return ctx.read("$.detail.responseElements.instancesSet.items[*].instanceId");
    }

    @Override
    public String extractInternetGateway() {
        return ctx.read("$.detail.responseElements.internetGateway.internetGatewayId");
    }

    @Override
    public String extractRouteTable() {
        return ctx.read("$.detail.responseElements.routeTable.routeTableId");
    }

    @Override
    public String extractVPCId() {
        return ctx.read("$.detail.responseElements.vpc.vpcId");
    }

    @Override
    public String extractSubnetId() {
        return ctx.read("$.detail.responseElements.subnet.subnetId");
    }
}
