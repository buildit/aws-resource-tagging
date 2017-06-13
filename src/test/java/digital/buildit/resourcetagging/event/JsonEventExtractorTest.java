package digital.buildit.resourcetagging.event;

import com.jayway.jsonpath.PathNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Extracts relevant values from JSON SNS events using Json Path
 * Created by will on 08/06/2017.
 */
public class JsonEventExtractorTest {


    @Test
    public void extractUserArn() {

        String userARN = loadEventExtractor(MESSAGE).extractUserArn();
        Assert.assertEquals("ARN not equal", "arn:aws:iam::0000000000:user/test.testerson@test.com", userARN);
    }

    @Test(expected = PathNotFoundException.class)
    public void extractFailWhenMissing() {
        loadEventExtractor(MESSAGE_WITHOUT_ARN).extractUserArn();
    }

    @Test
    public void extractBucketName() {
        String bucketName = loadEventExtractor(CREATE_BUCKET)
                .extractBucketName();
        Assert.assertEquals("Incorrect or missing bucket name", "will-test123", bucketName);
    }

    @Test
    public void extractLambdaFunctionARN() {
        String functionARN = loadEventExtractor(CREATE_FUNCTION)
                .extractLambdaFunctionARN();
        Assert.assertEquals("Wrong or missing function arn", "arn:aws:lambda:us-east-1:000000000:function:Test", functionARN);
    }

    @Test
    public void extractSingleEc2InstanceId() {
        String instanceId = loadEventExtractor(EC2_EVENT_SINGLE)
                .extractInstanceIds().get(0);
        Assert.assertEquals("Not correct instance id", "i-04a36458b8eecb6e7", instanceId);
    }

    @Test
    public void extractMultipleEc2InstanceId() {
        List<String> instanceIds = loadEventExtractor(EC2_EVENT_MULTIPLE)
                .extractInstanceIds();
        Assert.assertEquals("Not enough instanceIds", 4, instanceIds.size());
        Assert.assertEquals("Not correct instance id", Arrays.asList("i-04a36458b8eecb6a7", "i-04a36458b8dddb6e2","i-04a36045b8eecb0ff","i-04a36458b8a02b1cd"), instanceIds);
    }

    @Test
    public void extractInternetGateway() {
        String internetGateway = loadEventExtractor(CREATE_INTERNET_GATEWAY)
                .extractInternetGateway();
        Assert.assertEquals("Wrong or missing internet gateway", "igw-94fb66f2", internetGateway);
    }

    @Test
    public void extractRouteTable() {
        String routeTable = loadEventExtractor(CREATE_ROUTE_TABLE)
                .extractRouteTable();
        Assert.assertEquals("Wrong or missing route table", "rtb-ce91f3b6", routeTable);
    }

    @Test
    public void extractVPC() {
        String vpcId = loadEventExtractor(CREATE_VPC)
                .extractVPCId();
        Assert.assertEquals("Wrong or missing VPC id", "vpc-5627872f", vpcId);

    }

    @Test
    public void extractSubnet() {
        String subnetId = loadEventExtractor(CREATE_SUBNET)
                .extractSubnetId();
        Assert.assertEquals("Wrong or missing Subnet id", "subnet-ebe04ec7", subnetId);

    }



    private EventExtractor loadEventExtractor(String json) {
        return new JsonEventExtractor(json);
    }

    private static final String MESSAGE_WITHOUT_ARN = "{\n" +
            "  \"detail\": {\n" +
            "    \"userIdentity\": {\n" +
            "      \"userName\": \"test.testerson@test.com\"\n" +
            "    },\n" +
            "    \"requestParameters\": {\n" +
            "      \"x-amz-acl\": [\n" +
            "        \"private\"\n" +
            "      ],\n" +
            "      \"bucketName\": \"test_bucket\"\n" +
            "    },\n" +
            "  }\n" +
            "}";

    private static final String MESSAGE = "{\n" +
            "  \"detail\": {\n" +
            "    \"userIdentity\": {\n" +
            "      \"arn\": \"arn:aws:iam::0000000000:user/test.testerson@test.com\",\n" +
            "      \"userName\": \"william.ferguson@wipro.com\"\n" +
            "    },\n" +
            "    \"requestParameters\": {\n" +
            "      \"x-amz-acl\": [\n" +
            "        \"private\"\n" +
            "      ],\n" +
            "      \"bucketName\": \"Will_test_bucket\"\n" +
            "    },\n" +
            "  }\n" +
            "}";

    private static final String EC2_EVENT_SINGLE = "{\n" +
            "    \n" +
            "    \"region\": \"us-east-1\",\n" +
            "    \"detail\": {\n" +
            "        \"responseElements\": {\n" +
            "            \"instancesSet\": {\n" +
            "                \"items\": [\n" +
            "                    {\n" +
            "                        \"instanceId\": \"i-04a36458b8eecb6e7\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";


    private static final String EC2_EVENT_MULTIPLE = "{\n" +
            "  \"region\": \"us-east-1\",\n" +
            "  \"detail\": {\n" +
            "    \"responseElements\": {\n" +
            "      \"instancesSet\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"instanceId\": \"i-04a36458b8eecb6a7\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"instanceId\": \"i-04a36458b8dddb6e2\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"instanceId\": \"i-04a36045b8eecb0ff\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"instanceId\": \"i-04a36458b8a02b1cd\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";


    private static final String CREATE_INTERNET_GATEWAY = "{\n" +
            "  \"detail\": {\n" +
            "    \"eventName\": \"CreateInternetGateway\",\n" +
            "    \"awsRegion\": \"us-east-1\",\n" +
            "    \"responseElements\": {\n" +
            "      \"internetGateway\": {\n" +
            "        \"internetGatewayId\": \"igw-94fb66f2\"\n" +
            "      }\n" +
            "    },\n" +
            "    \n" +
            "  }\n" +
            "}";

    private static final String CREATE_ROUTE_TABLE = "{\n" +
            "  \"detail\": {\n" +
            "    \"responseElements\": {\n" +
            "      \"routeTable\": {\n" +
            "        \"routeTableId\": \"rtb-ce91f3b6\",\n" +
            "        \"vpcId\": \"vpc-5627872f\",\n" +
            "        \"routeSet\": {\n" +
            "          \"items\": [\n" +
            "            {\n" +
            "              \"destinationCidrBlock\": \"172.16.6.0/24\",\n" +
            "              \"gatewayId\": \"local\",\n" +
            "              \"state\": \"active\",\n" +
            "              \"origin\": \"CreateRouteTable\"\n" +
            "            }\n" +
            "          ]\n" +
            "        },\n" +
            "        \"associationSet\": {\n" +
            "          \n" +
            "        },\n" +
            "        \"propagatingVgwSet\": {\n" +
            "          \n" +
            "        },\n" +
            "        \"tagSet\": {\n" +
            "          \n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";


    private static final String CREATE_SUBNET = "{\n" +
            "  \"detail\": {\n" +
            "    \"responseElements\": {\n" +
            "      \"subnet\": {\n" +
            "        \"subnetId\": \"subnet-ebe04ec7\",\n" +
            "        \"state\": \"pending\",\n" +
            "        \"vpcId\": \"vpc-5627872f\",\n" +
            "        \"cidrBlock\": \"172.16.6.0/25\",\n" +
            "        \"ipv6CidrBlockAssociationSet\": {\n" +
            "          \n" +
            "        },\n" +
            "        \"availableIpAddressCount\": 123,\n" +
            "        \"availabilityZone\": \"us-east-1b\",\n" +
            "        \"defaultForAz\": false,\n" +
            "        \"mapPublicIpOnLaunch\": false,\n" +
            "        \"assignIpv6AddressOnCreation\": false\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    private static final String CREATE_VPC = "{\n" +
            "  \"detail\": {\n" +
            "    \"responseElements\": {\n" +
            "      \"requestId\": \"babf318d-c083-48ff-9daf-966a3bdca91d\",\n" +
            "      \"vpc\": {\n" +
            "        \"vpcId\": \"vpc-5627872f\",\n" +
            "        \"state\": \"pending\",\n" +
            "        \"cidrBlock\": \"172.16.6.0/24\",\n" +
            "        \"ipv6CidrBlockAssociationSet\": {\n" +
            "          \n" +
            "        },\n" +
            "        \"dhcpOptionsId\": \"dopt-c516f3a0\",\n" +
            "        \"instanceTenancy\": \"default\",\n" +
            "        \"tagSet\": {\n" +
            "          \n" +
            "        },\n" +
            "        \"isDefault\": false\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    private static final String CREATE_FUNCTION = "{\n" +
            "  \n" +
            "  \"detail\": {    \n" +
            "    \"responseElements\": {\n" +
            "      \"handler\": \"index.handler\",\n" +
            "      \"memorySize\": 128,\n" +
            "      \"runtime\": \"nodejs6.10\",\n" +
            "      \"functionArn\": \"arn:aws:lambda:us-east-1:000000000:function:Test\",\n" +
            "      \"functionName\": \"WillTest\",\n" +
            "      \"codeSize\": 333,\n" +
            "      \"version\": \"$LATEST\",\n" +
            "      \"tracingConfig\": {\n" +
            "        \"mode\": \"PassThrough\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    private static final String CREATE_BUCKET = "{\n" +
            "  \"detail\": {\n" +
            "    \"requestParameters\": {\n" +
            "      \"bucketName\": \"will-test123\"\n" +
            "    },\n" +
            "    \n" +
            "  }\n" +
            "}";

}