Automatically tags aws resources on create with user ARN. 
 
Uses the Serverless framework to build and deploy necessary resources. 

Currently supports the following events:

    - CreateBucket
    - CreateFunction20150331
    - RunInstances
    - CreateNetworkInterface
    - CreateInternetGateway
    - CreateRouteTable
    - CreateSubnet
    - CreateVpc


*Events flow from CloudTrail -> CloudWatch -> SNS -> Lambda.

To add tagging for more resource types:

The solution uses a "by convention" method of constructing instances to handle different CloudWatch Event types.

1. Implement an appropriate `ResourceIdExtractor`.  Follow the convention `<Event Name>IdExtractor`
1. Implement an appropriate `Tagger`.  Follow the convention `<Event Name>Tagger`
1. Add the necessary permissions to iamStatements in serverless.yml 
1. Update the CloudWatch EventPattern with source & eventName in serverless.yml 
  
  